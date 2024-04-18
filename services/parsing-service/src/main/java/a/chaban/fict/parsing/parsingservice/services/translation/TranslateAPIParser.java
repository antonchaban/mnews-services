package a.chaban.fict.parsing.parsingservice.services.translation;

import a.chaban.fict.parsing.parsingservice.services.Parser;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TranslateAPIParser implements Parser<String> {

    private final APIConnector apiConnector;

    @Value("${translate.url}")
    public String translateUrl;

    private static final String TRANSLATE_ENDPOINT = "translate";
    private static final String DETECT_ENDPOINT = "detect";
    private static final String SOURCE_LANG_KEY = "source";
    private static final String TARGET_LANG_KEY = "target";
    private static final String FORMAT_KEY = "format";
    private static final String TEXT_KEY = "q";
    private static final String TRANSLATED_TEXT_KEY = "translatedText";
    private static final String LANGUAGE_KEY = "language";

    /*        @Override
            public String doParse(String textToTranslate) throws IOException, ParseException {
                HttpURLConnection conn = apiConnector.setConnection(translateUrl + "translate");
                try {
                    String jsonInputString = "{\"q\":" + "\"" + textToTranslate.replace("\"", "\\\"") + "\""
                            + ", \"source\": \"uk\"," +
                            " \"target\": \"en\", " +
                            "\"format\": \"text\"}";
                    return getString(conn, jsonInputString, "translatedText");
                } finally {
                    apiConnector.endConnection(conn);
                }
            }

        public String doParse(String textToTranslate, String source, String target) throws IOException, ParseException {
            HttpURLConnection conn = apiConnector.setConnection(translateUrl + "translate");
            try {
                String jsonInputString = "{\"q\":" + "\"" + textToTranslate.replace("\"", "\\\"")
                        .replace("\r\n", ",")
                        .replace("\n", ",") + "\""
                        + ", \"source\": \"" + source.replace("\"", "\\\"") + "\"," +
                        " \"target\": \"" + target.replace("\"", "\\\"") + "\", " +
                        "\"format\": \"text\"}";
                return getString(conn, jsonInputString, "translatedText");
            } finally {
                apiConnector.endConnection(conn);
            }
        }*/
    @Override
    public String doParse(String textToTranslate) throws IOException, ParseException {
        HttpURLConnection conn = null;
        try {
            conn = apiConnector.setConnection(translateUrl + TRANSLATE_ENDPOINT);
            String jsonInputString = buildJsonInputString(textToTranslate, "uk", "en");
            return getString(conn, jsonInputString, TRANSLATED_TEXT_KEY);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public String doParse(String textToTranslate, String source, String target) throws IOException, ParseException {
        HttpURLConnection conn = null;
        try {
            conn = apiConnector.setConnection(translateUrl + TRANSLATE_ENDPOINT);
            String jsonInputString = buildJsonInputString(textToTranslate, source, target);
            return getString(conn, jsonInputString, TRANSLATED_TEXT_KEY);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private String buildJsonInputString(String text, String sourceLang, String targetLang) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(TEXT_KEY, text);
        jsonObject.put(SOURCE_LANG_KEY, sourceLang);
        jsonObject.put(TARGET_LANG_KEY, targetLang);
        jsonObject.put(FORMAT_KEY, "text");
        return jsonObject.toJSONString();
    }

    private String getString(HttpURLConnection conn, String jsonInputString, String objToSearch) throws IOException, ParseException {
        Object parsedObject = getParsedObject(conn, jsonInputString);
        if (parsedObject instanceof JSONObject jsonObject) {
            return (String) jsonObject.get(objToSearch);
        } else if (parsedObject instanceof JSONArray jsonArray) {
            JSONObject jsonObject = (JSONObject) jsonArray.getFirst();
            return (String) jsonObject.get(objToSearch);
        } else {
            throw new ParseException(ParseException.ERROR_UNEXPECTED_TOKEN);
        }
    }

    private Object getParsedObject(HttpURLConnection conn, String jsonInputString) throws IOException, ParseException {
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        StringBuilder inline = new StringBuilder();
        try (Scanner scanner = new Scanner(conn.getInputStream())) {
            while (scanner.hasNext()) {
                inline.append(scanner.nextLine());
            }
        }

        JSONParser parser = new JSONParser();
        Object parsedObject = parser.parse(inline.toString());
        return parsedObject;
    }

    /*    public String detectLanguage(String text) throws IOException, ParseException {
            HttpURLConnection conn = apiConnector.setConnection(translateUrl + "detect");
            try {
                String jsonInputString = "{\"q\":" + "\"" + text.replace("\"", "\\\"") + "\"}";
                return getString(conn, jsonInputString, "language");
            } finally {
                apiConnector.endConnection(conn);
            }
        }*/
    public String detectLanguage(String text) throws IOException, ParseException {
        HttpURLConnection conn = null;
        try {
            conn = apiConnector.setConnection(translateUrl + DETECT_ENDPOINT);
            String jsonInputString = "{\"" + TEXT_KEY + "\": \"" + text.replace("\"", "\\\"") + "\"}";
            return getString(conn, jsonInputString, LANGUAGE_KEY);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }
}

