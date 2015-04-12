package control;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

/**
 * Created by Sicha on 3/22/2015.
 */
public class jsonParser {

    public void parseToJson(String inputString)
    {
        DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
        HttpPost httpPost = new HttpPost(URI.create("http://localhost:19933/DBController.asmx"));

        httpPost.setHeader("Content-type", "application/json");

        InputStream inputStream = null;
        String result = null;

        try
        {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null)
            {
                stringBuilder.append(line + "\n");
            }

            result = stringBuilder.toString();
        }
        catch (Exception e)
        {

        }
        finally
        {
            try
            {
                if (inputStream != null)
                    inputStream.close();
            }
            catch (Exception squish) {}
        }
    }
}

