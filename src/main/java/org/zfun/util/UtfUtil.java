package org.zfun.util;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class UtfUtil {
    private static PrintStream out = null;
    
    static {
        try {
            out = new PrintStream(System.out, true, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    
    public static String StrInUtfRepresentation(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<str.length(); i++)
            sb.append(String.format("\\u%04x", (int) str.charAt(i)));
        return sb.toString();
    }
    
    public static String utfRepresentation2Str(String str) {
        StringBuilder sb = new StringBuilder();
        String[] hexTokens = str.split("\\\\u");  // input a regex
        for (int i=0; i<hexTokens.length; i++) {
            if (hexTokens[i].length() > 0)
                sb.append(Character.toString((char) Integer.parseInt(hexTokens[i],16)));
        }
        return sb.toString();
    }
    
    /**
     * This works from cmdline but not within eclipse
     * @param str
     */
    public static void println(String str) {
        out.println(str);
    }
    
    public static void main(String[] args) {
        out.println("Nuñez\u0123\u67e5\u770b\u5168\u90e8");
        out.println("\u7686\u3055\u3093\u3001\u3053\u3093\u306b\u3061\u306f");
        out.println("Mikko Mäkelä Group 1");
    }
    
}
