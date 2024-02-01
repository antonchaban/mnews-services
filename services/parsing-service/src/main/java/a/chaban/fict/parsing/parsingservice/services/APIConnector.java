package a.chaban.fict.parsing.parsingservice.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class APIConnector implements Connector {

    @Value("${translate.url}")
    public URL translateUrl;

    @Override
    public HttpURLConnection setTranslateAPIConnection(String page) throws IOException {
        URL url = new URL(translateUrl + page);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);
        return conn;
    }

    @Override
    public void endConnection(HttpURLConnection httpURLConnection) {
        httpURLConnection.disconnect();
    }
}
