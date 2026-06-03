package dk.quack.cbse.scoring;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.function.Consumer;

public class RestScoreClient implements ScoreClient {
    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final Consumer<String> warningSink;
    private boolean warningPrinted;

    public RestScoreClient(RestTemplate restTemplate, String baseUrl) {
        this(restTemplate, baseUrl, System.err::println);
    }

    RestScoreClient(RestTemplate restTemplate, String baseUrl, Consumer<String> warningSink) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.warningSink = warningSink;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    @Override
    public void reset() {
        post("/score/reset");
    }

    @Override
    public void addScore(int points) {
        if (points > 0) {
            post("/score/increment?points=" + points);
        }
    }

    private void post(String path) {
        try {
            restTemplate.postForObject(baseUrl + path, null, String.class);
        } catch (RestClientException exception) {
            if (!warningPrinted) {
                warningSink.accept("Scoring service not running. Using local score only.");
                warningPrinted = true;
            }
        }
    }
}
