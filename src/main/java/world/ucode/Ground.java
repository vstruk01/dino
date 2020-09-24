package world.ucode;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public class Ground extends ObjectGame {
    static double YOfLastObstacle;
    public double speed = 8;
    Canvas ground;

    Ground(double x, double y, int countElement) {
        height = 50;
        width = 130;

        if (YOfLastObstacle < x) {
            YOfLastObstacle = x;
        }

        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < ground.length; i++) {
            ground[i] = new ImageView(imageGround[rand.nextInt(3)]);
            ground[i].setFitWidth(100);
            ground[i].setFitHeight(50);
            ground[i].setTranslateX(i * 100);
            ground[i].setTranslateY(400);
        }

    }

    @Override
    public void updateObject() {

    }

    @Override
    public void clear() {

    }

    @Override
    public void draw() {

    }
}