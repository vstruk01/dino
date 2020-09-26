package world.ucode;

import javafx.scene.image.*;
import java.util.Random;
import javafx.scene.canvas.Canvas;
import javafx.animation.*;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import javafx.scene.*;

public class Obstacle extends ObjectGame {
    static double YOfLastObstacle;
    private double speed = 12;
    final private double maxSpeed = 20;

    private double interval;
    private int areaSpawn;

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

        draw();
        RotateTransition rotator = createRotator(canvas);
        rotator.play();
    }

    public void Restart() {
        if (x < 1000) {
            Random rand = new Random(System.currentTimeMillis());
            this.y = rand.nextInt(120) + 250;                                                   // 200 - 370
            this.x = rand.nextInt(this.areaSpawn) + this.interval + this.YOfLastObstacle;      // (x + interval) - areaSpawn
            YOfLastObstacle = this.x;

            canvas.setTranslateX(this.x);
            canvas.setTranslateY(this.y);

            draw();
        }
    }

    public void upSpeed() {
        if (speed < maxSpeed) {
            this.interval += 20;
            areaSpawn += 20;
            speed++;
        }
    }

    @Override
    public void updateObject() {
        if (this.x < -(this.width)) {
            Random rand = new Random(System.currentTimeMillis() / (long)YOfLastObstacle);
            this.x = rand.nextInt(this.areaSpawn) + this.YOfLastObstacle + this.interval;
            this.y = rand.nextInt(120) + 250; // 200 - 370;
            YOfLastObstacle = this.x;
            canvas.setTranslateY(this.y);
        }
        if (this.x == YOfLastObstacle - speed) {
            YOfLastObstacle = this.x;
        }
        this.x -= speed;
        canvas.setTranslateX(this.x);
    }

    private RotateTransition createRotator(Node card) {
        RotateTransition rotator = new RotateTransition(Duration.millis(50), card);
        rotator.setByAngle(360);
        rotator.setInterpolator(Interpolator.LINEAR);
        rotator.setCycleCount(Timeline.INDEFINITE);

        return rotator;
    }
}
