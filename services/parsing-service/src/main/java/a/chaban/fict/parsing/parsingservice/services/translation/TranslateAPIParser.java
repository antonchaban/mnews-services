package a.chaban.fict.parsing.parsingservice.services.translation;

import a.chaban.fict.parsing.parsingservice.services.Parser;
import a.chaban.fict.parsing.parsingservice.services.translation.APIConnector;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public String doParse(String textToTranslate) throws IOException, ParseException {
        HttpURLConnection conn = apiConnector.setTranslateAPIConnection("translate");
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
        HttpURLConnection conn = apiConnector.setTranslateAPIConnection("translate");
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
    }

    private String getString(HttpURLConnection conn, String jsonInputString, String objToSearch) throws IOException, ParseException {
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        StringBuilder inline = new StringBuilder();
        Scanner scanner = new Scanner(conn.getInputStream());
        while (scanner.hasNext()) {
            inline.append(scanner.nextLine());
        }
        scanner.close();
        JSONParser parser = new JSONParser();
        if (parser.parse(inline.toString()).getClass() == JSONObject.class) {
            JSONObject data_obj = (JSONObject) parser.parse(inline.toString());
            return (String) data_obj.get(objToSearch);
        } else {
            JSONArray jsonArray = (JSONArray) parser.parse(inline.toString());
            String lang = "";
            for (Object o : jsonArray) {
                JSONObject jsonObject = (JSONObject) o;
                lang = (String) jsonObject.get("language");
            }
            return lang;
        }

    }

    public String detectLanguage(String text) throws IOException, ParseException {
        HttpURLConnection conn = apiConnector.setTranslateAPIConnection("detect");
        try {
            String jsonInputString = "{\"q\":" + "\"" + text.replace("\"", "\\\"") + "\"}";
            return getString(conn, jsonInputString, "language");
        } finally {
            apiConnector.endConnection(conn);
        }
    }
}

