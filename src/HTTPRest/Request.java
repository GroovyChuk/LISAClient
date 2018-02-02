package HTTPRest;


import MQTT.Constants;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by alasdair on 22.01.18.
 */
public class Request {

    public static JSONObject getURL(URL url){
        JSONObject jsonObject = new JSONObject();
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            InputStream inputStream = conn.getInputStream();
            JSONParser jsonParser = new JSONParser();
            jsonObject = (JSONObject)jsonParser.parse(new InputStreamReader(inputStream, "UTF-8"));

            conn.disconnect();
        } catch (Exception e){
            e.printStackTrace();
        }

        return jsonObject;
    }

    public static String getNewSessionKey () {
        String sessionKey = "";

        try {
            JSONObject jsonObject = getURL(new URL("http://localhost:5000/session/" + Constants.MAC_ADRESSE));
            sessionKey = jsonObject.get("Session").toString();
            System.out.println("New Session : " + sessionKey + " MAC: " + Constants.MAC_ADRESSE);
        }catch (Exception e){
            e.printStackTrace();
        }
        return sessionKey;
    }
}
