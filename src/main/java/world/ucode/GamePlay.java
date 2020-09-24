package world.ucode;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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

//    Image[] imageGround = new Image[3];
    Group g1 = new Group();

    final private int countDino = 1;
    final private int countObstacle = 15;
    final private int countGround = 15;

    Ground ground = new Ground(0, 400, countGround);
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

        for (int i = 0; i < countDino; i++) {
            dins[i] = new Dino(100 + (i * 200), 350);
        }
        for (int i = 0; i < countObstacle; i++) {
            obstacles[i] = new Obstacle(widthMainWindow, 400, 200);
        }

        imageGround[0] = new Image("land1.png");
        imageGround[1] = new Image("land2.png");
        imageGround[2] = new Image("land3.png");

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

        for (ImageView imageView : ground) {
            g1.getChildren().add(imageView);
        }
        for (Dino dino : dins) {
            g1.getChildren().add(dino.canvas);
        }
        for (Obstacle obstacle : obstacles) {
            g1.getChildren().add(obstacle.canvas);
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

        Rectangle r1 = new Rectangle(100, 100);
        Rectangle r2 = new Rectangle(100, 100);
        r1.setX(100);
        r1.setY(100);
        r2.setX(201);
        r2.setY(201);
//        g1.getChildren().addAll(r1, r2);
        if (r1.intersects(r2.getBoundsInLocal())) {
            System.out.println("hello and stop");
        }

        primaryStage.setTitle("T-Rex Runner");
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public class Update extends AnimationTimer {
        public void handle(long now) {
//            long start = System.currentTimeMillis();

            update();

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
            score++;
            scoreText.setText("Score: " + score);
        }

        private void update() {
            for (Dino dino : dins) {
                dino.clear();
                dino.updateObject();
                dino.draw();
            }
            for (Obstacle obstacle : obstacles) {
                obstacle.clear();
                obstacle.updateObject();
                obstacle.draw();
            }
            for () {
                ground.update();
            }
            for (Obstacle obstacle : obstacles) {
                Rectangle r1 = new Rectangle(obstacle.width - 10, obstacle.height - 10);
                r1.setY(obstacle.y - 5);
                r1.setX(obstacle.x - 5);
                for (Dino dino : dins) {
                    Rectangle r2 = new Rectangle(dino.width - 10, dino.height - 10);
                    r2.setY(dino.y - 5);
                    r2.setX(dino.x - 5);
                    if (r1.intersects(r2.getBoundsInLocal())) {
                        System.out.println("stop here crap !");
                    }
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
