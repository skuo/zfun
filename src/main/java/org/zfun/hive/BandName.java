package org.zfun.hive;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

/**
 * User: skuo
 * Date: Aug 29, 2011
 */
public class BandName extends UDF {
    private final Text BAND_0_50 = new Text("0_50");
    private final Text BAND_51_100 = new Text("51_100");
    private final Text BAND_101_200 = new Text("101_200");
    private final Text BAND_201_INF = new Text("201_inf");

    public Text evaluate(Text band) {
        if (band == null)
            return null;
        else if (band.equals(new Text("0")))
            return BAND_0_50;
        else if (band.equals(new Text("51")))
            return BAND_51_100;
        else if (band.equals(new Text("101")))
            return BAND_101_200;
        else 
            return BAND_201_INF;
    }

}
