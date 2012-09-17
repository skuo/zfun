package org.zfun.nio;

import java.util.concurrent.CountDownLatch;

/**
 * User: skuo 
 * Date: May 9, 2011
 */
public class RspHandler {
    private byte[] rsp = null;
    private CountDownLatch latch = new CountDownLatch(1);

    public boolean handleResponse(byte[] rsp) {
        this.rsp = rsp;
        latch.countDown();
        return true;
    }

    public void waitForResponse() {
        try {
            latch.await();
            System.out.println(new String(rsp));
            latch = new CountDownLatch(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
