package org.zfun.nio;

import java.nio.channels.SocketChannel;

/**
 * User: skuo
 * Date: May 9, 2011
 */
public class ChangeRequest {
    public static final int REGISTER = 1;
    public static final int CHANGE_OPS = 2;
    
    public SocketChannel socketChannel;
    public int type;
    public int ops;
    
    public ChangeRequest(SocketChannel socketChannel, int type, int ops) {
        this.socketChannel = socketChannel;
        this.type = type;
        this.ops = ops;
    }
}
