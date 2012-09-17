package org.zfun.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * User: skuo
 * Date: May 6, 2011
 */
public class MultiPortEcho {
    private List<Integer> ports = null;
    private Selector selector = null;
    private ByteBuffer echoBuffer = ByteBuffer.allocate(1024);
    
    public MultiPortEcho(List<Integer> ports) {
        this.ports = ports;
    }
    
    public void register() throws IOException {
        // open the selector
        selector = Selector.open();
        
        // Open a listener on each port, and register each one with the selector
        for (int port : ports) {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            ServerSocket serverSocket = serverSocketChannel.socket();
            InetSocketAddress address = new InetSocketAddress(port);
            serverSocket.bind(address);
            
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("Accepting on " + port);
        }
    }
    
    public void exec() throws IOException {
        while (true) {
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            for (SelectionKey key : selectedKeys) {
                if ((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {
                    // Accept the new connection
                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    // Register the accepted socketChannel to selector for read.  
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    // Remove the activated Accept SelectionKey
                    selectedKeys.remove(key);
                    System.out.println("Reading on " + socketChannel);
                } else if ((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {
                    // Read and echo data
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    int bytesEchoed = 0;
                    while (true) {
                        echoBuffer.clear();
                        int r = socketChannel.read(echoBuffer);
                        if (r <= 0)
                            break;
                        echoBuffer.flip();
                        socketChannel.write(echoBuffer);
                        bytesEchoed += r;
                    }
                    System.out.println("Echoed " + bytesEchoed + " from " + socketChannel);
                    // Remove the activated Read SelectionKey
                    selectedKeys.remove(key);
                }
            }
        }
    }
    
    public static void main(String[] args) throws IOException {
        if (args.length <= 0) {
            System.err.println("Usage: java MultiPortEcho port [port port ...]");
            System.exit(-1);
        }
        
        List<Integer> ports = new ArrayList<Integer>();
        for (String arg : args)
            ports.add(Integer.parseInt(arg));
        
       MultiPortEcho mpEcho = new MultiPortEcho(ports);
       mpEcho.register();
       mpEcho.exec();
    }
}
