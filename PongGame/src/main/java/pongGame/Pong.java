package pongGame;

import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Pong extends Application {
    
    private static final int WIDTH = 900;
    private static final int HEIGHT = 500;
    private static final int BAR_WIDTH = 15;
    private static final int BAR_HEIGHT = 100;
    private static final double BALL_RADIUS = 15;
    private float ballYSpeed = 1;
    private float ballXSpeed = 1;
    private static final int MAX_BALL_SPEED = 4;
    private double playerOneYPos = HEIGHT / 2;
    private double playerTwoYPos = HEIGHT / 2;
    private double ballXPos = WIDTH / 2;
    private double ballYPos = WIDTH / 2;
    private int scoreP1 = 0;
    private int scoreP2 = 0;
    private boolean gameStarted;
    private boolean winning = false;
    private int playerOneXPos = 0;
    private double playerTwoXPos = WIDTH - BAR_WIDTH;
    
    public static void main(String[] args) {
        launch(Pong.class);
    }
    @Override
    public void start(Stage window) throws Exception {
        window.setTitle("P O N G");
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Timeline t1 = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
        t1.setCycleCount(Timeline.INDEFINITE);
        
        // mouse control
        canvas.setOnMouseMoved(e -> playerOneYPos = e.getY());
        canvas.setOnMouseClicked(e -> gameStarted = true);
        window.setScene(new Scene(new StackPane(canvas)));
        window.show();
        t1.play();
    }
    
    private void run(GraphicsContext gc){
        // set backgrong color
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);
        
        // setText color
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(25));
        
        // draw line between
        gc.fillRect(WIDTH / 2, 0, 1, HEIGHT);
        
        if(gameStarted){
            //set ball movement
            ballXPos += ballXSpeed;
            ballYPos += ballYSpeed;
            
            // computer
            if(Math.abs(ballXSpeed) < MAX_BALL_SPEED && Math.abs(ballYSpeed) < MAX_BALL_SPEED){
                System.out.println("x : " + ballXSpeed);
                playerTwoYPos = ballYPos - BAR_HEIGHT / 2;
            }
            else{
                if(winning){
                    if(ballXPos < WIDTH - WIDTH / 4){
                        playerTwoYPos = ballYPos - BAR_HEIGHT / 2;
                    }
                    else{
                        playerTwoYPos = ballYPos > playerTwoYPos + BAR_HEIGHT / 2 ? playerTwoYPos += 1 : playerTwoYPos - 1;
                    } 
                } 
                else{
                    playerTwoYPos = ballYPos - BAR_HEIGHT / 2;
                }
            }

            
            // draw ball
            gc.fillOval(ballXPos, ballYPos, BALL_RADIUS, BALL_RADIUS);
            
        }
        else{
            // set start text
            gc.setStroke(Color.WHITE);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.strokeText("   On Click", WIDTH / 2, HEIGHT / 2);
            
            //reset ball start position
            ballXPos = WIDTH / 2;
            ballYPos = HEIGHT / 2;
            
            // reset speed & direction
            ballXSpeed = new Random().nextInt(2) == 0 ? 1 : -1;
            ballYSpeed = new Random().nextInt(2) == 0 ? 1 : -1;
            
            // reset winning status
            winning = false;
            
        }
        
        // ball stays in canvas
        if(ballYPos > HEIGHT || ballYPos < 0)ballYSpeed *= -1;
        
        // player stays above canvas
        if(playerOneYPos <= 0){
            playerOneYPos = 0;
        }
        if(playerOneYPos >= HEIGHT - BAR_HEIGHT){
            playerOneYPos = HEIGHT - BAR_HEIGHT;
        }
        if(playerTwoYPos <= 0){
            playerTwoYPos = 0;
        }
        if(playerTwoYPos >= HEIGHT - BAR_HEIGHT){
            playerTwoYPos = HEIGHT - BAR_HEIGHT;
        }
        
        // computer gets a point
        if(ballXPos < playerOneXPos - BAR_WIDTH){
            scoreP2++;
            gameStarted = false;
        }
        if(ballXPos > playerTwoXPos + BAR_WIDTH){
            scoreP1++;
            gameStarted = false;
        }
        
        //increase ball speed && organise movement
        if((ballXPos < playerOneXPos + BAR_WIDTH) && ballYPos >= playerOneYPos && ballYPos <= playerOneYPos + BAR_HEIGHT){
            ballXSpeed *= -1;
            if(ballXSpeed < MAX_BALL_SPEED && ballYSpeed < MAX_BALL_SPEED){
                ballXSpeed += 1 * Math.signum(ballXSpeed);
                ballYSpeed += 1 * Math.signum(ballYSpeed);
            }
            
            // set 25% chance of winning
            int chance = new Random().nextInt(10);
            if(chance >= 6){
                winning = true;
            }
            else{
                winning = false;
            }
            System.out.println("Chance : " + chance);
        }
        
        if((ballXPos + BALL_RADIUS > playerTwoXPos) && ballYPos >= playerTwoYPos && ballYPos <= playerTwoYPos + BAR_HEIGHT){
            ballXSpeed *= -1;
            if(ballXSpeed < MAX_BALL_SPEED && ballYSpeed < MAX_BALL_SPEED){
                ballXSpeed += 0.5 * Math.signum(ballXSpeed);
                ballYSpeed += 0.5 * Math.signum(ballYSpeed);
            }
        }
        
        // draw score
        gc.fillText(scoreP1 + "\t" + scoreP2, WIDTH / 2, 25);
        
        // draw player 1 & player 2
        gc.setFill(Color.CRIMSON);
        gc.fillRect(playerTwoXPos, playerTwoYPos, BAR_WIDTH, BAR_HEIGHT);
        gc.setFill(Color.AQUA);
        gc.fillRect(playerOneXPos, playerOneYPos, BAR_WIDTH, BAR_HEIGHT);
    }
}