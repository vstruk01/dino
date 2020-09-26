package world.ucode;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import java.util.Random;

public class Cloud extends ObjectGame {
    static double XOfLastGround;
    public double speed = 1;

    Cloud(double x, double width, double height, Image image) {
        this.height = height;   // 50
        this.width = width;     // 130
        this.image = image;
        Random rand = new Random(System.currentTimeMillis());
        canvas = new Canvas(this.width, this.height);

        if (this.XOfLastGround <= x) {
            this.XOfLastGround = x;
        }

        this.XOfLastGround += rand.nextInt(100) + 100;
        canvas.setTranslateX(this.XOfLastGround);
        canvas.setTranslateY(rand.nextInt(300) + 20);
        this.x = this.XOfLastGround;
        this.XOfLastGround += this.width;

        draw();
    }

    @Override
    public void Restart() {
        if (x < 1000) {
            Random rand = new Random(System.currentTimeMillis());

            this.XOfLastGround += rand.nextInt(100) + 100;
            canvas.setTranslateX(this.XOfLastGround);
            canvas.setTranslateY(rand.nextInt(300) + 20);
            this.x = this.XOfLastGround;
            this.XOfLastGround += this.width;

            draw();
        }
    }

    @Override
    public void updateObject() {
        if (this.x < -(this.width)) {
            Random rand = new Random(System.currentTimeMillis());

            this.XOfLastGround += rand.nextInt(100) + 100;
            canvas.setTranslateX(this.XOfLastGround);
            canvas.setTranslateY(rand.nextInt(300) + 20);
            this.x = XOfLastGround;
            XOfLastGround += this.width;
        }
        if (XOfLastGround == this.x + this.width) {
            XOfLastGround -= speed;
        }
        this.x -= speed;
        canvas.setTranslateX(this.x);
    }

    @Override
    public void clear() {
        canvas.getGraphicsContext2D().clearRect(0, 0, this.width, this.height);
    }

    @Override
    public void draw() {
        canvas.getGraphicsContext2D().drawImage(this.image, 0, 0, this.width, this.height);
    }
}