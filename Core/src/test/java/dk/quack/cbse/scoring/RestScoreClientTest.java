package dk.quack.cbse.scoring;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RestScoreClientTest {

    @Test
    void unavailableServiceDoesNotThrow() {
        RestScoreClient client = new RestScoreClient(new RestTemplate(), "http://127.0.0.1:1", ignored -> {
        });

        assertDoesNotThrow(() -> client.addScore(10));
    }

    @Test
    void keepsConfiguredBaseUrl() {
        RestScoreClient client = new RestScoreClient(new RestTemplate(), "http://127.0.0.1:8080");

        assertEquals("http://127.0.0.1:8080", client.getBaseUrl());
    }
}
