package org.zfun.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * User: skuo Date: May 9, 2011
 */
public class NioClient implements Runnable {

    private InetAddress hostAddress;
    private int port;
    private Selector selector;
    private ByteBuffer readBuffer = ByteBuffer.allocate(8192);
    private ConcurrentLinkedQueue<ChangeRequest> pendingChangesCLQ = new ConcurrentLinkedQueue<ChangeRequest>();
    private Map<SocketChannel, List<ByteBuffer>> pendingDataCHM = new ConcurrentHashMap<SocketChannel, List<ByteBuffer>>();
    private Map<SocketChannel, RspHandler> rspHandlersCHM = new ConcurrentHashMap<SocketChannel, RspHandler>();

    public NioClient(InetAddress hostAddress, int port) throws IOException {
        this.hostAddress = hostAddress;
        this.port = port;
        selector = SelectorProvider.provider().openSelector();
    }

    public void send(byte[] data, RspHandler handler) throws IOException {
        // initiate connection
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress(hostAddress, port));        
        pendingChangesCLQ.add(new ChangeRequest(socketChannel, ChangeRequest.REGISTER, SelectionKey.OP_CONNECT));
        // Register the response handler
        rspHandlersCHM.put(socketChannel, handler);

        // Queue the data we want written
        List<ByteBuffer> queue = pendingDataCHM.get(socketChannel);
        if (queue == null) {
            queue = new ArrayList<ByteBuffer>();
            pendingDataCHM.put(socketChannel, queue);
        }
        queue.add(ByteBuffer.wrap(data));
        selector.wakeup();
    }

    public void run() {
        while (true) {
            try {
                // Process any pending changes
                Iterator<ChangeRequest> itr = pendingChangesCLQ.iterator();
                while (itr.hasNext()) {
                    ChangeRequest change = itr.next();
                    switch (change.type) {
                    case ChangeRequest.CHANGE_OPS:
                        SelectionKey key = change.socketChannel.keyFor(selector);
                        key.interestOps(change.ops);
                        break;
                    case ChangeRequest.REGISTER:
                        change.socketChannel.register(selector, change.ops);
                        break;
                    }
                }
                pendingChangesCLQ.clear();

                // Wait for an event from one of the registered channels
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                for (SelectionKey key : selectedKeys) {
                    selectedKeys.remove(key);
                    if (!key.isValid())
                        continue;
                    // Process event
                    if (key.isConnectable())
                        finishConnection(key);
                    else if (key.isReadable())
                        read(key);
                    else if (key.isWritable())
                        write(key);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();

        // Clear out our read buffer so it's ready for new data
        readBuffer.clear();

        // Attempt to read off the channel
        int numRead;
        try {
            numRead = socketChannel.read(readBuffer);
        } catch (IOException e) {
            // The remote forcibly closed the connection, cancel
            // the selection key and close the channel.
            key.cancel();
            socketChannel.close();
            return;
        }

        if (numRead == -1) {
            // Remote entity shut the socket down cleanly. Do the
            // same from our end and cancel the channel.
            key.channel().close();
            key.cancel();
            return;
        }

        // Handle the response
        handleResponse(socketChannel, readBuffer.array(), numRead);
    }

    private void handleResponse(SocketChannel socketChannel, byte[] data, int numRead) throws IOException {
        // Make a correctly sized copy of the data before handing it
        // to the client
        byte[] rspData = new byte[numRead];
        System.arraycopy(data, 0, rspData, 0, numRead);

        // Look up the handler for this channel
        RspHandler handler = (RspHandler) rspHandlersCHM.get(socketChannel);

        // And pass the response to it
        if (handler.handleResponse(rspData)) {
            // The handler has seen enough, close the connection
            socketChannel.close();
            socketChannel.keyFor(selector).cancel();
        }
    }
    
    private void write(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();

        List<ByteBuffer> queue = pendingDataCHM.get(socketChannel);

        // Write until there's not more data ...
        while (!queue.isEmpty()) {
            ByteBuffer buf = (ByteBuffer) queue.get(0);
            socketChannel.write(buf);
            if (buf.remaining() > 0) {
                // ... or the socket's buffer fills up
                break;
            }
            queue.remove(0);
        }

        if (queue.isEmpty()) {
            // We wrote away all data, so we're no longer interested
            // in writing on this socket. Switch back to waiting for
            // data.
            key.interestOps(SelectionKey.OP_READ);
        }
    }
    
    private void finishConnection(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
    
        // Finish the connection. If the connection operation failed
        // this will raise an IOException.
        try {
            socketChannel.finishConnect();
        } catch (IOException e) {
            // Cancel the channel's registration with our selector
            System.out.println(e);
            key.cancel();
            return;
        }
    
        // Register an interest in writing on this channel
        key.interestOps(SelectionKey.OP_WRITE);
    }
    
    public static void main(String[] args) {
        try {
            // NioClient client = new NioClient(InetAddress.getByName("www.google.com"), 80);
            NioClient client = new NioClient(InetAddress.getByName("localhost"), 9090);
            Thread t = new Thread(client);
            t.setDaemon(true);
            t.start();
            RspHandler handler = new RspHandler();
            client.send("GET / HTTP/1.0\r\n\r\n".getBytes(), handler);
            handler.waitForResponse();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
