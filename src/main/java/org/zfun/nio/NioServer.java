package org.zfun.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
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
 * User: skuo
 * Date: May 9, 2011
 */
public class NioServer implements Runnable {
    private ServerSocketChannel serverChannel;
    private Selector selector;
    private ByteBuffer readBuffer = ByteBuffer.allocate(8192);
    private EchoWorker worker;
    private ConcurrentLinkedQueue<ChangeRequest> pendingChangesCLQ = new ConcurrentLinkedQueue<ChangeRequest>();
    private Map<SocketChannel,List<ByteBuffer>> pendingDataCHM = new ConcurrentHashMap<SocketChannel,List<ByteBuffer>>();

    public NioServer(int port, EchoWorker worker) throws IOException {
        this.worker = worker;
        
        selector = SelectorProvider.provider().openSelector();
        // open a non-blocking socket on the port and register it with selector for accept
        serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        SocketAddress address = new InetSocketAddress(port);
        serverChannel.socket().bind(address);
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
    }
    
    public void send(SocketChannel socketChannel, byte[] data) {
        // Indicate we want the interest ops set changed
        pendingChangesCLQ.add(new ChangeRequest(socketChannel, ChangeRequest.CHANGE_OPS, SelectionKey.OP_WRITE));

        // And queue the data we want written
        List<ByteBuffer> queue = pendingDataCHM.get(socketChannel);
        if (queue == null) {
            queue = new ArrayList<ByteBuffer>();
            pendingDataCHM.put(socketChannel, queue);
        }
        queue.add(ByteBuffer.wrap(data));
        // Finally, wake up our selecting thread so it can make the required changes
        selector.wakeup();
    }
    
    public void run() {
        while (true) {
            try {
                // Process any pending changes
                Iterator<ChangeRequest> changes = pendingChangesCLQ.iterator();
                while (changes.hasNext()) {
                    ChangeRequest change = changes.next();
                    switch (change.type) {
                    case ChangeRequest.CHANGE_OPS:
                        SelectionKey key = change.socketChannel.keyFor(selector);
                        key.interestOps(change.ops);
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
                    if (key.isAcceptable()) 
                        accept(key);
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
    
    /*
     * Accept and register for read
     */
    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);
    }
    
    private void read(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        readBuffer.clear();
        
        // Attempt to read off the channel
        int numRead;
        try {
            numRead = socketChannel.read(readBuffer);
        } catch (IOException ioe) {
            // Remote entity forcibly closed the connection, cancel
            // the selection key and close the channel.
            key.cancel();
            socketChannel.close();
            return;
        }
        
        if (numRead == -1) {
            // Remote entity shut the socket down cleanly.  Do the
            // same from our end and cancel the channel.
            key.channel().close();
            key.cancel();
            return;
        }
        
        // Hand the data off to our worker thread
        worker.processData(this, socketChannel, readBuffer.array(), numRead);
    }
    
    private void write(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        List<ByteBuffer> queue = pendingDataCHM.get(socketChannel);
        // Write until there is no more data
        while (!queue.isEmpty()) {
            ByteBuffer buf = queue.get(0);
            socketChannel.write(buf);
            if (buf.remaining() > 0)
                break;
            queue.remove(0);
        }
        if (queue.isEmpty()) {
            // We wrote all the data so that we're no longer interested in
            // writing on this socket. Switch back to wait for data
            key.interestOps(SelectionKey.OP_READ);
        }
    }
    
    /*
     * Main()
     */
    public static void main(String[] args) {
        try {
            EchoWorker worker = new EchoWorker();
            new Thread(worker).start();
            new Thread(new NioServer(9090, worker)).start();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
}
