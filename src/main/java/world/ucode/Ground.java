package world.ucode;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public class Ground extends ObjectGame {
    static double YOfLastGround;
    public double speed = 6;

    Ground(double x, double y, double width, double height, Image image) {
        this.height = height;   // 50
        this.width = width;     // 130
        this.image = image;

        if (this.YOfLastGround <= x) {
            this.YOfLastGround = x;
        }

        canvas = new Canvas(this.width, this.height);
        canvas.setTranslateX(this.YOfLastGround);
        canvas.setTranslateY(400);
        draw();

        this.x = this.YOfLastGround;
        this.YOfLastGround += width;
        System.out.println(this.x);
        System.out.println(this.YOfLastGround);

    }

    @Override
    public void updateObject() {
        if (this.x < -(this.width)) {
            this.x = YOfLastGround;
            YOfLastGround += this.width;
        }
        if (YOfLastGround == this.x + this.width) {
            YOfLastGround -= speed;
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