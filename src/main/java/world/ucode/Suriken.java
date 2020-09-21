package world.ucode;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.image.*;
import javafx.stage.Stage;
import java.util.Random;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Suriken implements Object {
    public long speed = 100;
    final private Speed speedDino = new Speed();

    public double x = 2000;
    public double y;

    public Canvas surikenCanvas = new Canvas(40, 40);


    Suriken(double x, double y) {
        Random rand = new Random(544);
        y = rand.nextInt(200) + 455;

    }

    @Override
    public void MoveObject() {

    }
}
