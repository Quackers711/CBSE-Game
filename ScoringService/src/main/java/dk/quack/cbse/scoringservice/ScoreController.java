package dk.quack.cbse.scoringservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class ScoreController {
    private final AtomicInteger score = new AtomicInteger();

    @GetMapping("/score")
    public ScoreResponse score() {
        return new ScoreResponse(score.get());
    }

    @PostMapping("/score/increment")
    public ScoreResponse increment(@RequestParam(name = "points", defaultValue = "0") int points) {
        if (points > 0) {
            score.addAndGet(points);
        }
        return score();
    }

    @PostMapping("/score/reset")
    public ScoreResponse reset() {
        score.set(0);
        return score();
    }
}
