package a.chaban.fict.parsing.parsingservice.services;

import java.io.IOException;
import java.net.HttpURLConnection;

public interface Connector {
    default HttpURLConnection setConnection(String url) throws IOException{
        throw new UnsupportedOperationException("Must be implemented if used");
    };
    void endConnection(HttpURLConnection httpURLConnection);

    default HttpURLConnection setTranslateAPIConnection(String page) throws IOException{
        throw new UnsupportedOperationException("Must be implemented if used");
    }
}
