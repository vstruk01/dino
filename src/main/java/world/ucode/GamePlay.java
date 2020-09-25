package world.ucode;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.image.*;
import javafx.stage.Stage;
import java.util.Random;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;


public class GamePlay {
    final private double heightMainWindow = 500;
    final private double widthMainWindow = 1000;

    Group g1 = new Group();

    final private int countDino = 1;
    final private int countObstacle = 15;
    final private int countGround = 15;

    Image[] imageGround = new Image[3];
    Ground[] grounds = new Ground[countGround];
    Obstacle[] obstacles = new Obstacle[countObstacle];
    Dino[] dins = new Dino[countDino];


    private Text scoreText = new Text();
    private Text fpsText = new Text();
    private Text recordText = new Text();
    private int score = 0;
    private int record = score;

    // load image

    public void Init() {    
        Random rand = new Random(System.currentTimeMillis());
        imageGround[0] = new Image("land1.png");
        imageGround[1] = new Image("land2.png");
        imageGround[2] = new Image("land3.png");

        for (int i = 0; i < countDino; i++) {
            dins[i] = new Dino(100 + (i * 200), 350);
        }
        for (int i = 0; i < countObstacle; i++) {
            obstacles[i] = new Obstacle(widthMainWindow, 400, 200, 50, 50);
        }
        for (int i  = 0; i < countGround; i++) {
            grounds[i] = new Ground(0, 400, 130, 50, imageGround[rand.nextInt(3)]);
        }

        scoreText.setText("Score: " + score);
        recordText.setText("Record: " + record);
        fpsText.setText("fps: 0");

        scoreText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        recordText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        fpsText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));

        scoreText.setY(20);
        scoreText.setX(20);
        recordText.setY(20);
        recordText.setX(widthMainWindow / 2);
        fpsText.setY(20);
        fpsText.setX(widthMainWindow - 80);

        for (Ground ground : grounds) {
            g1.getChildren().add(ground.getCanvas());
        }
        for (Dino dino : dins)  {
            g1.getChildren().add(dino.getCanvas());
        }
        for (Obstacle obstacle : obstacles) {
            g1.getChildren().add(obstacle.getCanvas());
        }
        g1.getChildren().addAll(scoreText, recordText, fpsText);
    }


    // start play

    public void Play(Stage primaryStage) {
        if (primaryStage == null) {return;}
        Init();

        AnimationTimer timer = new Update();
        timer.start();

        Scene scene = new Scene(g1, widthMainWindow, heightMainWindow, Color.web("F7F7F7"));
        setHandle(scene);

//        Rectangle r1 = new Rectangle(100, 100);
//        Rectangle r2 = new Rectangle(100, 100);
//        r1.setX(100);
//        r1.setY(100);
//        r2.setX(201);
//        r2.setY(201);
//        g1.getChildren().addAll(r1, r2);
//        if (r1.intersects(r2.getBoundsInLocal())) {
//            System.out.println("hello and stop");
//        }

        primaryStage.setTitle("T-Rex Runner");
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public class Update extends AnimationTimer {
        public void handle(long now) {
            update();
            score++;
            scoreText.setText("Score: " + score);
            // for FPS
//            long start = System.currentTimeMillis();

//            long end = System.currentTimeMillis();
//            if (end - start < 16) {
//                try {
//                    Thread.sleep(16 - (end - start));
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            end = System.currentTimeMillis();
//            fpsText.setText("fps: " +  (1000 / (end - start)));

        }

        private void update() {
            for (Dino dino : dins) {
                dino.updateObject();
            }
            for (Obstacle obstacle : obstacles) {
                obstacle.updateObject();
            }
            for (Ground ground : grounds) {
                ground.updateObject();
            }
            collision();
        }
    }

    // collision
    private void collision() {
        for (Obstacle obstacle : obstacles) {
            Rectangle s1 = obstacle.getHitBox();
            s1.setY(obstacle.getY());
            s1.setX(obstacle.getX());
            for (Dino dino : dins) {
                Rectangle s2 = dino.getHitBox();
                s2.setY(dino.getY());
                s2.setX(dino.getX());
                if (s1.intersects(s2.getBoundsInLocal())) {
                    System.out.println("stop here crap !");
                }
            }
        }
    }

    // set handle
    public  void setHandle(Scene scene) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            for (Dino dino : dins) {
                dino.setDirectionPressed(event);
            }
        });
        scene.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            for (Dino dino : dins) {
                dino.setDirectionReleased(event);
            }
        });
    }
}
