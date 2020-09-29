package world.ucode;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
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

    private Polygon pol1, pol2;

    Button restartButton = new Button();
    Image imageButton = new Image("replay_button.png");
    ImageView gameOver = new ImageView(new Image("gameover_text.png"));

    AnimationTimer timer = new Update();

    Group g1 = new Group();

    final private int countDino = 1;
    final private int countObstacle = 15;
    final private int countGround = 15;
    final private int countCloud = 10;

    Image[] imageGround = new Image[3];
    Cloud[] clouds  = new Cloud[countCloud];
    Ground[] grounds = new Ground[countGround];
    Obstacle[] obstacles = new Obstacle[countObstacle];
    Dino[] dins = new Dino[countDino];

    final private Text scoreText = new Text();
    final private Text recordText = new Text();
    private int score = 0;
    private int record = score;

    Stage primaryStage;

    // start play

    public void Play(Stage primaryStage) {
        if (primaryStage == null) {return;}

        this.primaryStage = primaryStage;
        Init();

        Scene scene = new Scene(g1, widthMainWindow, heightMainWindow, Color.web("F7F7F7"));
        setHandle(scene);

        primaryStage.setTitle("T-Rex Runner");
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();

        primaryStage.show();
        timer.start();
    }

    public class Update extends AnimationTimer {
        public void handle(long now) {
            update();
            score++;
            scoreText.setText("Score: " + score);
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
            for (Cloud cloud : clouds) {
                cloud.updateObject();
            }
            collision();
            if (score % 200 == 0 && score != 0) {
                for (Obstacle obstacle : obstacles) {
                    obstacle.upSpeed();
                }
                for (Dino dino : dins) {
                    dino.upSpeed();
                }
                for (Ground ground : grounds) {
                    ground.upSpeed();
                }
            }
        }

        // collision
        private void collision() {
            for (Obstacle obstacle : obstacles) {
                if (obstacle.getX() > 300) {
                    continue;
                }
                Polygon[] s1 = obstacle.getHitBox();
                for (Dino dino : dins) {
                    Polygon[]  s2 = dino.getHitBox();
                    for (Polygon p1 : s1) {
                        for (Polygon p2 : s2) {
                            if (p1.intersects(p2.getBoundsInLocal())) {
                                pol1 = p1;
                                pol2 = p2;
                                dino.over();
                                for (Obstacle obstacle1 : obstacles) {
                                    obstacle1.rotator.stop();
                                }
                                timer.stop();
                                g1.getChildren().addAll(restartButton, gameOver);
//                                g1.getChildren().addAll(pol1, pol2); for show hit box
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    public void Init() {
        Random rand = new Random(System.currentTimeMillis());
        imageGround[0] = new Image("land1.png");
        imageGround[1] = new Image("land2.png");
        imageGround[2] = new Image("land3.png");

        for (int i = 0; i < countCloud; i++) {
            clouds[i] = new Cloud(0, 200, 100, new Image("cloud.PNG"));
        }
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
        scoreText.setFill(Color.BLUE);
        recordText.setFill(Color.RED);
         scoreText.setStroke(Color.BLACK);
        recordText.setStroke(Color.BLUE);

        scoreText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        recordText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));

        scoreText.setY(20);
        scoreText.setX(20);
        recordText.setY(20);
        recordText.setX(widthMainWindow / 2 - 50);

        this.addAll();

        gameOver.setTranslateX(400);
        gameOver.setTranslateY(200);
        restartButton.setTranslateX(480);
        restartButton.setTranslateY(300);

        restartButton.setGraphic(new ImageView(imageButton));
        restartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (score > record) {
                    record = score;
                    recordText.setText("Record: " + record);
                }
                score = 0;

                for (Dino dino : dins) {
                    dino.Restart();
                }
                for (Obstacle obstacle : obstacles) {
                    obstacle.Restart();
                }
                for (Ground ground : grounds) {
                    ground.Restart();
                }
                g1.getChildren().removeAll(restartButton, gameOver);
//                g1.getChildren().removeAll(pol1, pol2); for delete hit box
                timer.start();
            }
        });
    }

    private void addAll() {
        for (Cloud cloud : clouds) {
            g1.getChildren().add(cloud.getCanvas());
        }
        for (Ground ground : grounds) {
            g1.getChildren().add(ground.getCanvas());
        }
        for (Dino dino : dins)  {
            g1.getChildren().add(dino.getCanvas());
        }
        for (Obstacle obstacle : obstacles) {
            g1.getChildren().add(obstacle.getCanvas());
        }

        g1.getChildren().addAll(scoreText, recordText);
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
