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

import javafx.scene.transform.Rotate;
import javafx.animation.*;
import javafx.util.Duration;
import javafx.scene.*;

public class GamePlay {

    // speed

    long speedMainMilliseconds = 100;
    long speedScoreMilliseconds = 100;
    public Speed speedMain = new Speed();
    public Speed speedScore = new Speed();

    // Sprite

    Image[] imageGround = new Image[3];
    ImageView[] ground = new ImageView[20];

    // direction

    // window and size
    Group g1;
    Dino dino;
//    Group ground;

    public Text ts;
    public int score = 0;

    // load image

    public void Init() {
        dino = new Dino();

        imageGround[0] = new Image("land1.png");
        imageGround[1] = new Image("land2.png");
        imageGround[2] = new Image("land3.png");
        Random rand = new Random(544);
        for (int i = 0; i < ground.length; i++) {
            ground[i] = new ImageView(imageGround[rand.nextInt(3)]);
            ground[i].setFitWidth(100);
            ground[i].setFitHeight(50);
            ground[i].setTranslateX(i * 100);
            ground[i].setTranslateY(700);
        }

        ts.setText("Score: " + Integer.toString(score));
        ts.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 50));
        ts.setY(100);
        ts.setX(100);


    }

    public void setEventKey(Scene scene) {
        scene.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if (event.getCode() == KeyCode.DOWN && dino.direction == Dino.Direction.DOWN) {
                dino.direction = Dino.Direction.DEFAULT;
            }
        });
        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if ((event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.UP ) && dino.direction == Dino.Direction.DEFAULT) {
               dino.direction = Dino.Direction.FLY_UP;
            } else if (event.getCode() == KeyCode.DOWN && dino.direction == Dino.Direction.FLY_DOWN) {
                dino.speedFlyDown *= 2;
            } else if (event.getCode() == KeyCode.DOWN && dino.direction == Dino.Direction.DEFAULT) {
                dino.direction = Dino.Direction.DOWN;
            }
        });
    }

    // start play

    public void Play(Stage primaryStage) {
        g1 = new Group();
        ts = new Text();
        Init();

        for (ImageView imageView : ground) {
            g1.getChildren().add(imageView);
        }

        Canvas c = new Canvas(40, 40);
        c.setTranslateX(500);
        c.setTranslateY(500);
        GraphicsContext gc = c.getGraphicsContext2D();

        g1.getChildren().addAll(dino.dinoCanvas, ts, c);
        Image s = new Image("suriken.png");
        gc.drawImage(s, 0, 0, 40, 40);
        RotateTransition rotator = createRotator(c);
        rotator.play();

        AnimationTimer timer = new Update();
        timer.start();
        Scene scene = new Scene(g1, 2000, 1000, Color.web("F7F7F7"));
        setEventKey(scene);
        primaryStage.setTitle("T-Rex Runner");
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    private RotateTransition createRotator(Node card) {
        RotateTransition rotator = new RotateTransition(Duration.millis(1), card);
        rotator.setByAngle(360);
        rotator.setCycleCount(Timeline.INDEFINITE);

        return rotator;
    }

    public class Update extends AnimationTimer {
        public void handle(long now) {
            updateDino();
            updateGround();
//            updateCloud();
        }
        private void updateDino() {
            dino.MoveObject();
        }
        private void updateGround() {

        }
    }
}
