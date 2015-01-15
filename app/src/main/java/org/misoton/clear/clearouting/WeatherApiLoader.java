package org.misoton.clear.clearouting;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.JsonReader;
import android.util.Log;
import android.util.Xml;

import com.ibm.icu.text.Transliterator;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class WeatherApiLoader extends AsyncTaskLoader<String> {
    private String location;

    public WeatherApiLoader(Context context) {
        super(context);
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String loadInBackground() {
        if (location == null) {
            return null;
        }

        if (!location.matches("[0-9A-Za-z, ]+")) {
            location = this.convertJapToRome(location);
        }

        Log.d("sss", location);

        Log.d("sdfghjgfdsfghj", "http://api.openweathermap.org/data/2.5/forecast?q=" + location + "&mode=json");

        HttpClient httpClient = new DefaultHttpClient();

        try {
            Log.d("lld", "1");
            String responseBody = httpClient.execute(new HttpGet("http://api.openweathermap.org/data/2.5/forecast?q=" + location + "&mode=json"), // （1）

                    new ResponseHandler<String>() {

                        @Override
                        public String handleResponse(HttpResponse response)
                                throws ClientProtocolException, IOException {
                            Log.d("lld", "2");

                            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                                return EntityUtils.toString(response.getEntity(), "UTF-8"); // （3）
                            }
                            Log.d("lld", "3");
                            return null;
                        }
                    });
            Log.d("lld", responseBody);

            return responseBody;
        } catch (Exception e) {
            Log.d("lld", "fdfdfdgfggfgf");
            Log.e(this.getClass().getSimpleName(), e.getMessage());
        } finally {
            httpClient.getConnectionManager().shutdown();
        }

        return null;
    }

    private String convertJapToRome(String jap) {
        String rome = "";
        try {
            XmlPullParser parser = Xml.newPullParser();

            URL url = new URL("http://jlp.yahooapis.jp/MAService/V1/parse?appid=dj0zaiZpPVp4VkZ0MVduekhqbiZzPWNvbnN1bWVyc2VjcmV0Jng9NTE-&results=ma,uniq&uniq_filter=9%7C10&sentence=" + URLEncoder.encode(location, "UTF-8"));
            URLConnection connection = url.openConnection();
            parser.setInput(connection.getInputStream(), "UTF-8");
            int eventType;
            while ((eventType = parser.next()) != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && "reading".equals(parser.getName())) {
                    String result = parser.nextText();
                    Transliterator transliterator = Transliterator.getInstance("Hiragana-Latin");
                    result = transliterator.transliterate(result).replaceAll("dz", "z");
                    Log.d("XmlPullParserSampleUrl", result);
                    rome += result;
                }
            }
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        return rome;
    }
}
