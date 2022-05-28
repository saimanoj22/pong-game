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
    private static final int MAX_BALL_SPEED = 4;
    private boolean gameStarted;
    private boolean winning = false;
    
    Ball ball = new Ball(WIDTH / 2, HEIGHT / 2);
    Player player = new Player(0, HEIGHT / 2);
    Bot bot = new Bot(WIDTH, HEIGHT / 2);
    Score score = new Score();
    
    
    public static void main(String[] args) {
        launch(Pong.class);
    }
    
    @Override
    public void start(Stage window) throws Exception {
        
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Timeline t1 = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
        t1.setCycleCount(Timeline.INDEFINITE);
        
        // mouse control
        canvas.setOnMouseMoved(e ->  player.setyPosition((int) e.getY()));
        canvas.setOnMouseClicked(e -> gameStarted = true);
        window.setScene(new Scene(new StackPane(canvas)));
        window.setTitle("P O N G");
        window.show();
        t1.play();
    }
    
    private void run(GraphicsContext gc){
        setGraphicContent(gc);
        
        if(gameStarted){
            setBallMovement();
            designBot();
            drawBall(gc);
        }
        else{
            setStartingText(gc);
            resetBallPosition();
            resetMovement();
            resetWinningStatus();
        }
        
        
        ballInCanvas();
        playerInCanvas();
        updateScoring();
        increaseBallSpeed();
        drawScoresAndPlayers(gc);
    }
    
    public void setGraphicContent(GraphicsContext gc){  
        // set background color
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);
        
        // set text color
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(25));
        
        // draw line between
        gc.fillRect(WIDTH / 2, 0, 1, HEIGHT);
    }
    
    public void setBallMovement(){
        ball.setxPosition(ball.getxPosition() + ball.getxSpeed());
        ball.setyPosition(ball.getyPosition() + ball.getySpeed());
    }
    
    public void designBot(){
        if(Math.abs(ball.getxSpeed()) < MAX_BALL_SPEED && Math.abs(ball.getySpeed()) < MAX_BALL_SPEED){
            bot.setyPosition((int)ball.getyPosition() - bot.getHeight() / 2);
        }
        else{
            if(winning){
                if(ball.getxPosition() < WIDTH - WIDTH / 4){
                    bot.setyPosition((int)ball.getyPosition() - bot.getHeight() / 2);
                }
                else{
                    if(ball.getyPosition() > bot.getyPosition() + bot.getHeight() / 2){
                        bot.setyPosition(bot.getyPosition() + 1);
                    }
                    else{
                        bot.setyPosition(bot.getyPosition() - 1);
                    }
                } 
            } 
            else{
                bot.setyPosition((int)ball.getyPosition() - bot.getHeight() / 2);
            }
        }
    }
    
    public void drawBall(GraphicsContext gc){
        gc.fillOval(ball.getxPosition(), ball.getyPosition(), ball.getRadius(), ball.getRadius());
    }
    
    public void setStartingText(GraphicsContext gc){
        gc.setStroke(Color.WHITE);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.strokeText("   On Click", WIDTH / 2, HEIGHT / 2);
    }
    
    public void resetBallPosition(){
        ball.setxPosition(WIDTH / 2);
        ball.setyPosition(HEIGHT / 2);
    }
    
    public void resetMovement(){
        ball.setxSpeed(new Random().nextInt(2) == 0 ? 1 : -1);
        ball.setySpeed(new Random().nextInt(2) == 0 ? 1 : -1);
    }
    
    public void resetWinningStatus(){
        winning = false;
    }
    
    public void ballInCanvas(){
        if(ball.getyPosition() > HEIGHT || ball.getyPosition() < 0){
            ball.setySpeed(ball.getySpeed() * -1);
        }
    }
    
    public void playerInCanvas(){
        if(player.getyPosition() <= 0){
            player.setyPosition(0);
        }
        if(player.getyPosition() >= HEIGHT - player.getHeight()){
            player.setyPosition(HEIGHT - player.getHeight());
        }
        if(bot.getyPosition() <= 0){
            bot.setyPosition(0);
        }
        if(bot.getyPosition() >= HEIGHT - bot.getHeight()){
            bot.setyPosition(HEIGHT - bot.getHeight());
        }        
    }
    
    public void updateScoring(){
        if(ball.getxPosition() < player.getxPosition() - player.getWidth()){
            bot.increase();
            gameStarted = false;
        }
        if(ball.getxPosition() > bot.getxPosition() + bot.getWidth()){
            player.increase();
            gameStarted = false;
        }
    }
    
    public void increaseBallSpeed(){
        if((ball.getxPosition() < player.getxPosition() + player.getWidth()) && ball.getyPosition() >= player.getyPosition() && ball.getyPosition() <= player.getyPosition() + player.getHeight()){
            ball.setxSpeed(ball.getxSpeed() * -1);
            if(ball.getxSpeed() < MAX_BALL_SPEED && ball.getySpeed() < MAX_BALL_SPEED){
                ball.setxSpeed(ball.getxSpeed() + 0.5 * Math.signum(ball.getxSpeed()));
                ball.setySpeed(ball.getySpeed() + 0.5 * Math.signum(ball.getySpeed()));
            }
            
            // set 25% chance of winning
            int chance = new Random().nextInt(10);
            if(chance >= 6){
                winning = true;
            }
            else{
                winning = false;
            }
        }
        
        if((ball.getxPosition() + ball.getRadius() > bot.getxPosition()) && ball.getyPosition() >= bot.getyPosition() && ball.getyPosition() <= bot.getyPosition() + bot.getHeight()){
            ball.setxSpeed(ball.getxSpeed() * -1);
            if(ball.getxSpeed() < MAX_BALL_SPEED && ball.getySpeed() < MAX_BALL_SPEED){
                ball.setxSpeed(ball.getxSpeed() + 0.5 * Math.signum(ball.getxSpeed()));
                ball.setySpeed(ball.getySpeed() + 0.5 * Math.signum(ball.getySpeed()));
            }
        }
    }
    
    public void drawScoresAndPlayers(GraphicsContext gc){
        gc.fillText(player.getScore() + "\t" + bot.getScore(), WIDTH / 2, 25);
        
        gc.setFill(Color.CRIMSON);
        gc.fillRect(bot.getxPosition(), bot.getyPosition(), bot.getWidth(), bot.getHeight());
        gc.setFill(Color.AQUA);
        gc.fillRect(player.getxPosition(), player.getyPosition(), bot.getWidth(), bot.getHeight());
    }
}