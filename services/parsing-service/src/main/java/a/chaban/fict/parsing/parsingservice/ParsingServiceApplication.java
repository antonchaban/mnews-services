package a.chaban.fict.parsing.parsingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ParsingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParsingServiceApplication.class, args);
    }

}
