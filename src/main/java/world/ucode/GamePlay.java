package world.ucode;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.Effect;
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

    // Sprite
    Image[] imageGround = new Image[3];
    ImageView[] ground = new ImageView[20];

    // window and size
    private int countDino = 1;
    private int countShuriken = 20;

    private double posYLastShuriken = 1800;
    private int lastIdx = countShuriken - 1;

    Shuriken[] shurikens = new Shuriken[countShuriken];
    Group g1;
    Dino[] dinos = new Dino[countDino];
//    Group ground;

    public Text ts;
    public int score = 0;

    // load image

    public void Init() {
        Random rand = new Random(System.currentTimeMillis() / (long)posYLastShuriken);
        for (int i = 0; i < countDino; i++) {
            dinos[i] = new Dino(100 + (i * 200), 644);
        }
        for (int i = 0; i < countShuriken; i++) {
            shurikens[i] = new Shuriken(rand.nextInt(200) + posYLastShuriken + 400);
            posYLastShuriken = shurikens[i].getX();
        }

        imageGround[0] = new Image("land1.png");
        imageGround[1] = new Image("land2.png");
        imageGround[2] = new Image("land3.png");
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

    // start play

    public void Play(Stage primaryStage) {
        g1 = new Group();
        ts = new Text();
        Init();

        for (ImageView imageView : ground) {
            g1.getChildren().add(imageView);
        }
        for (Dino dino : dinos) {
            g1.getChildren().add(dino.dinoCanvas);
        }
        for (Shuriken shuriken : shurikens) {
            g1.getChildren().add(shuriken.shurikenCanvas);

        }

        g1.getChildren().add(ts);

        // Canvas new

//        Canvas cn = new Canvas(100, 100);
//        cn.setTranslateY(500);
//        cn.setTranslateX(1000);
//        var gc = cn.getGraphicsContext2D();
//        gc.setFill(Color.RED);
//        gc.fillRect(0, 0, 50, 50);
//        g1.getChildren().add(cn);


        // end

        AnimationTimer timer = new Update();
        timer.start();
        Scene scene = new Scene(g1, 2000, 1000, Color.web("F7F7F7"));

        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            for (Dino dino : dinos) {
                dino.setDirectionPressed(event);
            }
        });

        scene.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            for (Dino dino : dinos) {
                dino.setDirectionReleased(event);
            }
        });

        primaryStage.setTitle("T-Rex Runner");
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public class Update extends AnimationTimer {
        public void handle(long now) {
            updateDino();
            updateGround();
            updateShuriken();
//            updateCloud();
        }
        private void updateDino() {
            for (Dino dino : dinos) {
                dino.MoveObject();
            }
        }
        private void updateGround() {

        }
        private void updateShuriken() {
            for (int i = 0; i < countShuriken; i++) {
                double x = shurikens[i].getX();
                if (x < -40) {
                    Random rand = new Random(System.currentTimeMillis() / (long)posYLastShuriken);
                    x = rand.nextInt(200) + posYLastShuriken + 400;
                    double y = rand.nextInt(170) + 500; // 500 - 670
                    shurikens[i].setX(x);
                    shurikens[i].setY(y);
                    posYLastShuriken = x;
                    lastIdx = i;
                }
                shurikens[i].MoveObject();
            }
            posYLastShuriken = shurikens[lastIdx].getX();
        }
    }
}
