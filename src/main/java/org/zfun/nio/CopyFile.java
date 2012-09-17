package org.zfun.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * User: skuo Date: May 6, 2011
 */
public class CopyFile {

    public static void main(String[] args) {
        try {
            FileInputStream fin = new FileInputStream("book-jaxb.xml");
            FileChannel fcIn = fin.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            FileOutputStream fout = new FileOutputStream("book-jaxb.copy");
            FileChannel fcOut = fout.getChannel();
            while (true) {
                buffer.clear();
                int r = fcIn.read(buffer);
                if (r == -1) {
                    break;
                }
                buffer.flip();
                fcOut.write(buffer);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
