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
    private double speedDefault = 12;
    private double speed = speedDefault;
    final private double maxSpeed = 20;

    private double intervalDefault;
    private double interval;
    private int areaSpawnDefault;
    private int areaSpawn;

    Obstacle(double x, double interval, int areaSpawn, double width, double height) {
        image = new Image("obstacle1.png");

        this.height = height;
        this.width = width;
        canvas = new Canvas(width, height);
        this.areaSpawn = areaSpawn;
        this.interval = interval;
        this.intervalDefault = interval;
        this.areaSpawnDefault = areaSpawn;
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
        this.speed = this.speedDefault;
        this.interval = this.intervalDefault;
        this.areaSpawn = this.areaSpawnDefault;
        if (x < 1000) {
            Random rand = new Random(System.currentTimeMillis());
            this.y = rand.nextInt(120) + 250;                                                   // 200 - 370
            this.x = rand.nextInt(this.areaSpawn) + this.interval + this.YOfLastObstacle;      // (x + interval) - areaSpawn
            YOfLastObstacle = this.x;

            this.canvas.setTranslateX(this.x);
            this.canvas.setTranslateY(this.y);

            draw();
        }
    }

    public void upSpeed() {
        if (this.speed < this.maxSpeed) {
            this.interval += 30;
            this.areaSpawn += 30;
            this.speed += 1;
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
        RotateTransition rotator = new RotateTransition(Duration.millis(500), card);
        rotator.setByAngle(360);
        rotator.setInterpolator(Interpolator.LINEAR);
        rotator.setCycleCount(Timeline.INDEFINITE);

        return rotator;
    }
}
