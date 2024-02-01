package a.chaban.fict.parsing.parsingservice.services;

import com.sun.syndication.io.FeedException;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public interface Parser<T> {
    T doParse(String content) throws IOException, FeedException, ParseException;
}
