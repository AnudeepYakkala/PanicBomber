package sample;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.animation.Timeline;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
public class PanicBomber extends Application {
    int movement=5;
    int bombnumber=1;
    double startTime=System.currentTimeMillis();
    boolean hasbomb=true;
    Timeline timeline;
    Label labelWinner=new Label("");
    Random r=new Random();
    int timer=r.nextInt((60-30))+30;
    int timerLoop=timer;
    int p1Score=0;
    int p2Score=0;
    public static void main(String[] args) { launch(args); }
    @Override public void start(Stage stage) throws Exception {
        Circle circle = createCircle();
        Circle circle2 = createCircle2();
        Circle bomb= createCircleBomb();
        Label timerDisplay=createLabel();
        Text waitingForKey = new Text("Player 1 Wins!");
        waitingForKey.setTextAlignment(TextAlignment.CENTER);
        waitingForKey.setFont(new Font(24));
        waitingForKey.setFill(Color.RED);
        StackPane root = new StackPane();
        root.getChildren().add(waitingForKey);
        Scene scene2 = new Scene(root, 600, 400, Color.BLACK);
        Text waitingForKey2 = new Text("Player 2 Wins!");
        waitingForKey2.setTextAlignment(TextAlignment.CENTER);
        waitingForKey2.setFont(new Font(24));
        waitingForKey2.setFill(Color.RED);
        StackPane root2 = new StackPane();
        root2.getChildren().add(waitingForKey2);
        Scene scene3 = new Scene(root2, 600, 400, Color.BLACK);
        timerDisplay.setText(timer+"");
        timerDisplay.setFont(new Font(20));
        if (timeline != null) {
            timeline.stop();
        }
        timerDisplay.setText(timer+"");
        Timer time = new Timer();
        time.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        timer--;
                        for(int i=0; i<timerLoop; i++) {
                            if (bombnumber==1){
                                p1Score++;
                            }
                            else {
                                p2Score++;
                            }
                        }
                        timerDisplay.setText(null);
                        if (timer<=5){
                            timerDisplay.setText("Timer: "+(Integer.toString(timer)));
                        }
                        if (timer==0) {
                            if (bombnumber==1){
                                p1Score=p1Score/2;
                            }
                            else {
                                p2Score=p2Score/2;
                            }
                            if (p1Score>p2Score){
                                stage.setScene(scene2);
                                waitingForKey.setText("Player 1 Wins!\nPlayer 1: "+p1Score+"\nPlayer 2: "+p2Score);
                                stage.show();
                            }
                        else {
                                stage.setScene(scene3);
                                waitingForKey2.setText("Player 2 Wins!\nPlayer 1: "+p1Score+"\nPlayer 2: "+p2Score);
                                stage.show();
                            }
                        }
                    }
                });
//                int timer = 9;

            }
        }, 0 , 1000);

        Group group = new Group(circle, circle2, bomb, timerDisplay);
        Scene scene = new Scene(group, 600, 400, Color.BLACK);
        moveCircle(scene, circle, circle2, bomb);
        stage.setScene(scene);
        stage.show();
    }
    public Label createLabel() {
        Label timerDisplay=new Label("Time remaining: "+5);
        timerDisplay.setTextFill(Color.BLUE);
        return timerDisplay;
    }
    public Circle createCircle() {
        final Circle circle = new Circle(200, 150, 10, Color.YELLOW);
        circle.setOpacity(0.7);
        return circle;
    }
    public Circle createCircle2() {
        final Circle circle = new Circle(300, 150, 10, Color.YELLOW);
        circle.setOpacity(0.7);
        return circle;
    }
    public Circle createCircleBomb() {
        final Circle circle = new Circle(300, 150, 5, Color.RED);
        circle.setOpacity(0.7);
        return circle;
    }
    public class endGame {

    }

    public void moveCircle(Scene scene, Circle circle, Circle circle2, Circle bomb) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent event) {
                if (Math.abs(circle.getCenterX()-circle2.getCenterX())<20 && Math.abs(circle.getCenterY()-circle2.getCenterY())<20 && hasbomb==true)
                {
                    if (bombnumber==1)
                    {
                        bombnumber=2;
                        hasbomb=false;
                    }
                    else {
                        bombnumber = 1;
                        hasbomb = false;
                    }
                }
                if ((Math.abs(circle.getCenterX()-circle2.getCenterX())>21 || Math.abs(circle.getCenterY()-circle2.getCenterY())>21))
                {
                    hasbomb=true;
                }
                switch (event.getCode()) {
                    case UP:    circle.setCenterY(circle.getCenterY()-movement); break;
                    case RIGHT: circle.setCenterX(circle.getCenterX()+movement); break;
                    case DOWN:  circle.setCenterY(circle.getCenterY()+movement); break;
                    case LEFT:  circle.setCenterX(circle.getCenterX()-movement); break;
                    case W:     circle2.setCenterY(circle2.getCenterY()-movement); break;
                    case D:     circle2.setCenterX(circle2.getCenterX()+movement); break;
                    case S:     circle2.setCenterY(circle2.getCenterY()+movement); break;
                    case A:     circle2.setCenterX(circle2.getCenterX()-movement); break;
                }
                if (bombnumber==1)
                {
                    bomb.setCenterX(circle.getCenterX());
                    bomb.setCenterY(circle.getCenterY());
                }
                else
                {
                    bomb.setCenterX(circle2.getCenterX());
                    bomb.setCenterY(circle2.getCenterY());
                }
            }
        });
    }
    public void endGame() {
        System.out.println("Thanks for playing!");
    }
    }