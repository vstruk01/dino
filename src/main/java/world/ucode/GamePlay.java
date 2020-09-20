package world.ucode;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.image.*;
import javafx.stage.Stage;
import java.util.Random;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

//interface GameObject {
//
//}

public class GamePlay {

    // speed


    long speedDinoMilliseconds = 100;
    long speedMainMilliseconds = 100;
    long speedScoreMilliseconds = 100;
    public Speed speedDino = new Speed();
    public Speed speedMain = new Speed();
    public Speed speedScore = new Speed();

    // Sprite

    ImageView[] dinoSprite = new ImageView[6];
    Image[] imageGround = new Image[3];
    ImageView[] ground = new ImageView[20];

    public enum RunSprite {
        UP_LEFT(0),
        UP_RIGHT(1),
        DEFAULT(2),
        OVER(3),
        DOWN_LEFT(4),
        DOWN_RIGHT(5);

        private final int value;

        private RunSprite(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }
    RunSprite sprite = RunSprite.DEFAULT;

    // direction

    public enum Direction {
        UP,
        DEFAULT,
        DOWN
    }
    public Direction direction = Direction.DEFAULT;

    // window and size

    Group g1;
    public Text ts;
    public int score = 0;
    public double maxHeight = 650;
    public double minHeight = 200;
    public double x = 100, y = maxHeight;

    // load image

    public void Init() {
        Image image = new Image("main-character1.png");
        dinoSprite[0] = new ImageView(image);
        image = new Image("main-character2.png");
        dinoSprite[1] = new ImageView(image);
        image = new Image("main-character3.png");
        dinoSprite[2] = new ImageView(image);
        image = new Image("main-character4.png");
        dinoSprite[3] = new ImageView(image);
        image = new Image("main-character5.png");
        dinoSprite[4] = new ImageView(image);
        image = new Image("main-character6.png");
        dinoSprite[5] = new ImageView(image);
        for (int i = 0; i < 6; i++) {
            dinoSprite[i].setFitHeight(100);
            dinoSprite[i].setFitWidth(100);
            dinoSprite[i].setTranslateX(x);
            dinoSprite[i].setTranslateY(y);
        }

        imageGround[0] = new Image("land1.png");
        imageGround[1] = new Image("land2.png");
        imageGround[2] = new Image("land3.png");
        Random rand = new Random(544);
        for (int i = 0; i < ground.length; i++) {
//            ground[i] = new ImageView(imageGround[rand.nextInt(3)]);
//            ground[i].setFitWidth(100);
//            ground[i].setFitHeight(50);
//            ground[i].setTranslateX(i * 100);
//            ground[i].setTranslateY(y + 50);
        }

        Canvas canvas = new Canvas(300, 250);
        GraphicsContext gc = canvas.getGraphicsContext2D();
//        gc.drawImage();


        ts.setText("Score: " + Integer.toString(score));
        ts.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
        ts.setY(100);
        ts.setX(100);


    }

    public void setEventKey(Scene scene) {
        scene.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if (event.getCode() == KeyCode.DOWN && direction == Direction.DOWN) {
                direction = Direction.DEFAULT;
                g1.getChildren().remove(dinoSprite[sprite.getValue()]);
                sprite = RunSprite.DEFAULT;
                g1.getChildren().addAll(dinoSprite[sprite.getValue()]);
            }
        });
        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if ((event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.UP ) && direction == Direction.DEFAULT) {
               direction = Direction.UP;
               g1.getChildren().remove(dinoSprite[sprite.getValue()]);
               sprite = RunSprite.DEFAULT;
               g1.getChildren().addAll(dinoSprite[sprite.getValue()]);
            }
            else if (event.getCode() == KeyCode.DOWN && direction == Direction.DEFAULT) {
                direction = Direction.DOWN;
                g1.getChildren().remove(dinoSprite[sprite.getValue()]);
                sprite = RunSprite.DOWN_RIGHT;
                g1.getChildren().addAll(dinoSprite[sprite.getValue()]);
            }
        });
    }

    // start play

    public void Play(Stage primaryStage) {
        g1 = new Group();
        ts = new Text();
        Init();

        g1.getChildren().addAll(dinoSprite[sprite.getValue()], ts);
        for (int i = 0; i < ground.length; i++) {
            g1.getChildren().add(ground[i]);
        }
        AnimationTimer timer = new Update();
        timer.start();
        Scene scene = new Scene(g1, 2000, 1000, Color.web("F7F7F7"));
        setEventKey(scene);
        primaryStage.setTitle("T-Rex Runner");
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public class Update extends AnimationTimer {
        public void handle(long now) {
            updateDino();
            updateEnemy();
            updateGround();
        }
        private void updateDino () {
            if (direction == Direction.UP) {
                for (int i = 0 ; i < 3; i++) {
                    g1.getChildren().remove(dinoSprite[sprite.getValue()]);
                    y -= 10;
                    g1.getChildren().addAll(dinoSprite[sprite.getValue()]);
                }
            } else if (direction == Direction.DOWN) {
                for (int i = 0 ; i < 3; i++) {
                    g1.getChildren().remove(dinoSprite[sprite.getValue()]);
                    y += 10;
                    g1.getChildren().addAll(dinoSprite[sprite.getValue()]);
                }
            }
            g1.getChildren().remove(dinoSprite[sprite.getValue()]);

            if (y <= minHeight) {
                y = minHeight;
                direction = Direction.DOWN;
            } else if (y >= maxHeight) {
                if (speedDino.getSpeed(speedDinoMilliseconds)) {
                    if (sprite == RunSprite.DEFAULT || sprite == RunSprite.UP_RIGHT) {
                        sprite = RunSprite.UP_LEFT;
                    } else if (sprite == RunSprite.UP_LEFT) {
                        sprite = RunSprite.UP_RIGHT;
                    } else if (sprite == RunSprite.DOWN_RIGHT) {
                        sprite = RunSprite.DOWN_LEFT;
                    } else if (sprite == RunSprite.DOWN_LEFT) {
                        sprite = RunSprite.DOWN_RIGHT;
                    }

                }
                y = maxHeight;
                direction = Direction.DEFAULT;
            }
            g1.getChildren().addAll(dinoSprite[sprite.getValue()]);
            dinoSprite[sprite.getValue()].setTranslateY(y);
            if (speedScore.getSpeed(speedScoreMilliseconds)) {
                score++;
            }
            ts.setText("Score: " + Integer.toString(score));
        }

        private void updateGround () {

        }

        private void updateEnemy () {

        }
    }

    public class Speed {
        public long t = System.currentTimeMillis();
        public boolean getSpeed(long millis) {
            long new_t = System.currentTimeMillis();
            if (new_t - t >= millis) {
                t = new_t;
                return true;
            }
            return false;
        }
    }
}
