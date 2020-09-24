package world.ucode;

import javafx.scene.image.*;
import java.util.Random;
import javafx.scene.canvas.Canvas;
import javafx.animation.*;
import javafx.util.Duration;
import javafx.scene.*;

public class Obstacle extends ObjectGame {
    static double YOfLastObstacle;
    public double speed = 8;

//    final private double size = 50;
//    public double x;
//    public double y;

    final private double interval;
    final private int areaSpawn;
    public Image obstacleImg = new Image("obstacle1.png");

//    public Canvas obstacleCanvas = new Canvas(this.size, this.size);

    Obstacle(double x, double interval, int areaSpawn) {
        height = 50;
        width = 50;

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

        canvas.getGraphicsContext2D().drawImage(obstacleImg, 0, 0, this.width, this.height);
        RotateTransition rotator = createRotator(canvas);
        rotator.play();
    }


    @Override
    public void clear() {
        canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
    }

    @Override
    public void draw() {
        canvas.getGraphicsContext2D().drawImage(obstacleImg, 0,0,width,height);
    }

    @Override
    public void updateObject() {
        if ((this.x < -50 && width == 50) || (this.x < -150 && width == 150) ) {
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
        RotateTransition rotator = new RotateTransition(Duration.millis(1000), card);
        rotator.setByAngle(360);
        rotator.setInterpolator(Interpolator.LINEAR);
        rotator.setCycleCount(Timeline.INDEFINITE);

        return rotator;
    }
}
