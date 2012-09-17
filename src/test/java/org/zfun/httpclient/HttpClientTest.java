package org.zfun.httpclient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.junit.Ignore;
import org.junit.Test;

/**
 * User: skuo Date: Jun 3, 2011
 */
public class HttpClientTest {

    @Ignore
    @Test
    public void testGet() {
        InputStream is = null;
        try {
            HttpClient httpClient = new DefaultHttpClient();
            //HttpGet httpGet = new HttpGet("http://10.1.73.220:8888/ypads?pid=ADTEST&at=flowers&loc=san+francisco&an=5&uip=1.1.1.1&uua=agent&uid=test&ali=5&prodcfg={\"scoring_model\": \"distance1\", \"ad_selection_info\":  {\"type\":\"layers2\", \"namelayer\": 2000, \"ranklayer\": 1, \"ppclayer\": 50, \"geolayer\": 100, \"vpiplayer\": 4000, \"selector\": {\"type\": \"fuzzydistance\", \"fuzzy_base\": 0.5,\"fuzzy_scale\": 0.5, \"selector\":{\"type\":\"weighted\", \"sort\":\"ctr\"} },\"direct_selector\":{\"type\":\"weighted\",\"sort\":\"ctr\"}  }}");
            //Doing it with URI
            //String url = "pid=ADTEST&at=flowers&loc=san+francisco&an=5&uip=1.1.1.1&uua=agent&uid=test&ali=5&prodcfg={\"scoring_model\": \"distance1\", \"ad_selection_info\":  {\"type\":\"layers2\", \"namelayer\": 2000, \"ranklayer\": 1, \"ppclayer\": 50, \"geolayer\": 100, \"vpiplayer\": 4000, \"selector\": {\"type\": \"fuzzydistance\", \"fuzzy_base\": 0.5,\"fuzzy_scale\": 0.5, \"selector\":{\"type\":\"weighted\", \"sort\":\"ctr\"} },\"direct_selector\":{\"type\":\"weighted\",\"sort\":\"ctr\"}  }}";
            //URI uri = new URI("http", null, "10.1.73.220", 8888, "/ypads", url, null);
            //HttpGet httpGet = new HttpGet(uri.toString());
            
            String urlStr = "http://10.1.73.220:8888/ypads?pid=ADTEST&at=flowers&loc=san+francisco&an=5&uip=1.1.1.1&uua=agent&uid=test&ali=5&";
            //String urlStr="http://adsvc-java.v.int.wc1.atti.com/astroman-web/ypads?query=directorySearchOfTermAtZip.json%3FtransactionId%3D7f5f8712-a382-4886-b158-97c0e8a3d61a%26userId%3D-443861252373031951%26output%3Djson%26sort%3D%26filter%3D%26pageNumber%3D1%26pageSize%3D2%26partnerId%3DMSN%26term%3Dpizza%26profileId%3Dweb%26zip%3D98104%26uip%3D127.0.0.1%26ur%3D%26uua%3DMozilla%252F4.0%2B%2528compatible%253B%2BMSIE%2B6.0%253B%2BMS%2BWeb%2BServices%2BClient%2BProtocol%2B2.0.50727.4927%2529&mode=directorySearchOfTermAtZip";
            String prodCfg = "prodcfg={\"scoring_model\": \"distance1\", \"ad_selection_info\": {\"type\":\"layers2\", \"namelayer\": 2000, \"ranklayer\": 1, \"ppclayer\": 50, \"geolayer\": 100, \"vpiplayer\": 4000, \"selector\": {\"type\": \"fuzzydistance\", \"fuzzy_base\": 0.5,\"fuzzy_scale\": 0.5, \"selector\":{\"type\":\"weighted\", \"sort\":\"ctr\"} },\"direct_selector\":{\"type\":\"weighted\",\"sort\":\"ctr\"}  }}";
            String debugProdCfg = "prodcfg={\"scoring_model\": \"distance1\", \"ad_selection_info\": {\"sort\":\"debug\",\"type\":\"layers2\", \"namelayer\": 2000, \"ranklayer\": 1, \"ppclayer\": 50, \"geolayer\": 100, \"vpiplayer\": 4000, \"selector\": {\"type\": \"fuzzydistance\", \"fuzzy_base\": 0.5,\"fuzzy_scale\": 0.5, \"selector\":{\"type\":\"weighted\", \"sort\":\"ctr\"} },\"direct_selector\":{\"type\":\"weighted\",\"sort\":\"ctr\"}  }}";
            //                                                                                    "{"sort":"debug","type":"stdlayers",  "namelayer":true, "ppclayer":false, "ranklayer":true,  "vpiplayer":false, "selector":{"type":"xmlapi", "sort":"debug"}}"
            String prodCfgEncoded = URLEncoder.encode(prodCfg, HTTP.DEFAULT_CONTENT_CHARSET);
            String debugProdCfgEncoded = URLEncoder.encode(debugProdCfg, HTTP.DEFAULT_CONTENT_CHARSET);
            String finalUrl = urlStr + prodCfgEncoded;
            String debugFinalUrl = urlStr + debugProdCfgEncoded;
            HttpGet httpGet = new HttpGet(debugFinalUrl);
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                is = entity.getContent();
                int l;
                byte[] tmp = new byte[2048];
                StringBuilder sb = new StringBuilder();
                while ((l = is.read(tmp)) != -1) {
                    sb.append(new String(tmp));
                }
                System.out.print(sb.toString());
                // do not need the rest
                // httpGet.abort()
            }
        } catch (ClientProtocolException cpe) {
            System.out.print(cpe.getMessage());
        } catch (IOException ioe) {
            System.out.print(ioe.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                // do nothing
            }
        }
    }
}


