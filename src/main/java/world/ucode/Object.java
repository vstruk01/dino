package world.ucode;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

abstract class ObjectGame {
    public abstract void updateObject();
    public Rectangle getHitBox() {
        return new Rectangle(width, height);
    }
    public void clear() {
        canvas.getGraphicsContext2D().clearRect(0, 0, this.width, this.height);
    };
    public void draw() {
        canvas.setTranslateX(x);
        canvas.setTranslateY(y);
        canvas.setWidth(width);
        canvas.setHeight(height);
        canvas.getGraphicsContext2D().drawImage(this.image, 0, 0, this.width, this.height);
    };
    public Canvas getCanvas() {
        return this.canvas;
    };
    public double getX() {
        return this.x;
    }
    public double getY() {
        return this.y;
    }
    double x;
    double y;
    double height;
    double width;
    Image image;
    Canvas canvas;
}