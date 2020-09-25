package world.ucode;

import javafx.scene.image.*;
import java.util.Random;
import javafx.scene.canvas.Canvas;
import javafx.animation.*;
import javafx.util.Duration;
import javafx.scene.*;

public class Obstacle extends ObjectGame {
    static double YOfLastObstacle;
    private double speed = 12;
    final private double maxSpeed = 20;

    final private double interval;
    final private int areaSpawn;

    Obstacle(double x, double interval, int areaSpawn, double width, double height) {
        image = new Image("obstacle1.png");

        this.height = height;
        this.width = width;
        canvas = new Canvas(width, height);
        this.areaSpawn = areaSpawn;
        this.interval = interval;
        if (x > this.YOfLastObstacle) {
            YOfLastObstacle = x;
        }

        Random rand = new Random(System.currentTimeMillis());
        y = rand.nextInt(120) + 250;                                                   // 200 - 370
        this.x = rand.nextInt(this.areaSpawn) + this.interval + this.YOfLastObstacle;      // (x + interval) - areaSpawn

        if (this.x > this.YOfLastObstacle) {
            this.YOfLastObstacle = this.x;
        }

        canvas.setTranslateX(this.x);
        canvas.setTranslateY(this.y);

        canvas.getGraphicsContext2D().drawImage(image, 0, 0, this.width, this.height);
        RotateTransition rotator = createRotator(canvas);
        rotator.play();
    }

    public void upSpeed() {
        if (speed < maxSpeed) {
            speed++;
        }
    }

    @Override
    public void updateObject() {
        if (this.x < -(width)) {
            Random rand = new Random(System.currentTimeMillis() / (long)YOfLastObstacle);
            this.x = rand.nextInt(this.areaSpawn) + this.YOfLastObstacle + this.interval;
            this.y = rand.nextInt(120) + 250; // 200 - 370;
            YOfLastObstacle = x;
        }
        if (this.x == YOfLastObstacle - speed) {
            YOfLastObstacle = this.x;
        }
        this.x -= speed;
        canvas.setTranslateX(this.x);
    }

    private RotateTransition createRotator(Node card) {
        RotateTransition rotator = new RotateTransition(Duration.millis(10), card);
        rotator.setByAngle(360);
        rotator.setInterpolator(Interpolator.LINEAR);
        rotator.setCycleCount(Timeline.INDEFINITE);

        return rotator;
    }
}
