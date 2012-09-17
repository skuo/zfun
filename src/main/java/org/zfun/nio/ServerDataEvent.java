package org.zfun.nio;

import java.nio.channels.SocketChannel;

/**
 * User: skuo
 * Date: May 9, 2011
 */
public class ServerDataEvent {
    public NioServer server;
    public SocketChannel socketChannel;
    public byte[] data;
    
    public ServerDataEvent(NioServer server, SocketChannel socketChannel, byte[] data) {
        this.server = server;
        this.socketChannel = socketChannel;
        this.data = data;
    }
}
