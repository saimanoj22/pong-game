package pongGame;

public class Ball {
    private double radius, ySpeed, xSpeed;
    private double xPosition, yPosition;
    
    public Ball(double xPosition, double yPosition){
        this.radius = 15;
        this.xSpeed = 1;
        this.ySpeed = 1;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public double getxPosition() {
        return xPosition;
    }

    public void setxPosition(double xPosition) {
        this.xPosition = xPosition;
    }

    public double getyPosition() {
        return yPosition;
    }

    public void setyPosition(double yPosition) {
        this.yPosition = yPosition;
    }

    public double getySpeed() {
        return ySpeed;
    }

    public void setySpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }

    public double getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }
    
    public double getRadius(){
        return this.radius;
    }
}
