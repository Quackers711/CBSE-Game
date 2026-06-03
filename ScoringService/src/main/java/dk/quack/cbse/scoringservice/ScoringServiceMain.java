package dk.quack.cbse.scoringservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScoringServiceMain {

    public static void main(String[] args) {
        SpringApplication.run(ScoringServiceMain.class, args);
        System.out.println("Scoring service listening on http://127.0.0.1:8080");
    }
}
