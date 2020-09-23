package world.ucode;

import javafx.scene.image.*;
import java.util.Random;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.animation.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.scene.*;

public class Shuriken implements Object {
    private static long speedMiliseconds = 1;
    public double speed = 12;
    final private Speed speedSuriken = new Speed();
    final private double size = 50;

    public double x;
    public double y;

    public Canvas shurikenCanvas = new Canvas(this.size, this.size);
    private GraphicsContext gc = shurikenCanvas.getGraphicsContext2D();
    private Image shuriken = new Image("shuriken.png");

    Shuriken(double x) {
        this.x = x;
        gc.setFill(Color.GREEN);
        gc.fillRect(0, 0, 50, 50);
        Random rand = new Random(System.currentTimeMillis());
        y = rand.nextInt(170) + 500; // 400 - 670

        shurikenCanvas.setTranslateX(this.x);
        shurikenCanvas.setTranslateY(this.y);

        gc.drawImage(shuriken, 0, 0, this.size, this.size);
        RotateTransition rotator = createRotator(shurikenCanvas);
        rotator.play();
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }

    @Override
    public void MoveObject() {
        if (speedSuriken.getSpeed(speedMiliseconds)) {
            this.x -= speed;
            shurikenCanvas.setTranslateX(this.x);
        }
    }

    private RotateTransition createRotator(Node card) {
        RotateTransition rotator = new RotateTransition(Duration.millis(1), card);
        rotator.setByAngle(360);
        rotator.setCycleCount(Timeline.INDEFINITE);

        return rotator;
    }
}
