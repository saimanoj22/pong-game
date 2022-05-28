package pongGame;

public class Score {
    private int score;
    
    public Score(){
        this.score = 0;
    }
    
    public void increase(){
        this.score++;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
