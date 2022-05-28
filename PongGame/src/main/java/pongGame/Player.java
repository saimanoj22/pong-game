package pongGame;

public class Player extends Score{
    private int width, height;
    private int xPosition, yPosition;
    
    public Player(int xPosition, int yPosition){
        this.width = 15;
        this.height = 100;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
