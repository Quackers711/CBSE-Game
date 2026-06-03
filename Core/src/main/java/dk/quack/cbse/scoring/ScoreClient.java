package dk.quack.cbse.scoring;

public interface ScoreClient {
    void reset();

    void addScore(int points);
}
