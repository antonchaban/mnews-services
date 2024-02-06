package a.chaban.fict.parsing.parsingservice.services.parsing;

import a.chaban.fict.parsing.parsingservice.services.Connector;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class URLConnector implements Connector {
    @Override
    public HttpURLConnection setConnection(String url) throws IOException {
        URL toConnect = new URL(url);
        return (HttpURLConnection) toConnect.openConnection();
    }

    @Override
    public void endConnection(HttpURLConnection httpURLConnection) {
        httpURLConnection.disconnect();
    }
}
