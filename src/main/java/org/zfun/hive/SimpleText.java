package org.zfun.hive;

import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

/**
 * User: skuo Date: Aug 29, 2011
 */
public class SimpleText extends UDF{
    public Text evaluate(final Text s) {
        if (s == null) {
            return null;
        }
        return new Text(s.toString().toUpperCase());
    }

    public static void main(String[] args) {
        SimpleText lower = new SimpleText();
        String oldString = "t=HELLOWORLD";
        System.out.println(oldString);
        Text newText = lower.evaluate(new Text(oldString));
        System.out.println(newText.toString());
    }

}
