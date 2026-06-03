package dk.quack.cbse.scoringservice;

import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoringServiceMainTest {

    @Test
    void readsPointsFromQueryString() {
        int points = ScoringServiceMain.queryInt(URI.create("/score/increment?points=25"), "points", 0);

        assertEquals(25, points);
    }

    @Test
    void returnsDefaultForInvalidPoints() {
        int points = ScoringServiceMain.queryInt(URI.create("/score/increment?points=nope"), "points", 0);

        assertEquals(0, points);
    }
}
