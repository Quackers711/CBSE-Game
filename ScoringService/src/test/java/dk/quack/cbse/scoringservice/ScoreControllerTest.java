package dk.quack.cbse.scoringservice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoreControllerTest {

    @Test
    void incrementsAndResetsScore() {
        ScoreController controller = new ScoreController();

        assertEquals(0, controller.score().score());
        assertEquals(25, controller.increment(25).score());
        assertEquals(25, controller.increment(-5).score());
        assertEquals(0, controller.reset().score());
    }
}
