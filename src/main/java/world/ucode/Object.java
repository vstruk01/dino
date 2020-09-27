package world.ucode;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.shape.Polygon;

abstract class ObjectGame {
    public abstract void updateObject();
    public abstract void Restart();

    public Polygon getHitBox() {
        Polygon pol = new Polygon();
        Double[] d = new Double[] {
                this.x+ 5.0, y + 5.0,
                this.x+ this.width - 5, y + 5.0,
                this.x+ this.width - 5, y + this.height - 5,
                this.x+ 5.0, y + this.height - 5,
        };

        pol.getPoints().addAll(d);
        return pol;
    }

    public void clear() {
        canvas.getGraphicsContext2D().clearRect(0, 0, this.width, this.height);
    }

    public void draw() {
        canvas.setTranslateX(this.x);
        canvas.setTranslateY(this.y);
        canvas.setWidth(this.width);
        canvas.setHeight(this.height);

        // color for visual hit box
//        canvas.getGraphicsContext2D().setFill(Color.RED);
//        canvas.getGraphicsContext2D().fillRect(0, 0, this.width, this.height);

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