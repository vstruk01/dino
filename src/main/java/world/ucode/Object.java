package world.ucode;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;

abstract class ObjectGame {
    public abstract void updateObject();
    public abstract void clear();
    public abstract void draw();
    double x;
    double y;
    double height;
    double width;
    Canvas canvas;
//    double getY();
//    double getX();
//    GraphicsContext getGraphicsContext();
//    Image getImage();
//    double getHeight();
//    double getWith();
}