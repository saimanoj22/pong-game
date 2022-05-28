package pongGame;

public class Bot extends Player{
    
    public Bot(int xPosition, int yPosition) {
        super(xPosition, yPosition);
        setxPosition(getxPosition() - getWidth());
    }
    
}
