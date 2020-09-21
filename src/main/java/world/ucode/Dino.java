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

public class Dino implements Object {
    public long speed = 100;
    final private Speed speedDino = new Speed();

    public double speedFlyDown = 30;
    final private int countSprites = 6;
    private double width = 80;
    private double height = 86;
    final private double x = 100;
    private double y = 644;
    final private double maxHeight = 644;
    final private double minHeight = 200;

    public enum Direction {
        DEFAULT,
        FLY_UP,
        FLY_DOWN,
        DOWN
    }
    public Direction direction = Direction.DEFAULT;

    public enum posSprite {
        DEFAULT(0),
        UP_LEFT(1),
        UP_RIGHT(2),
        DOWN_LEFT(3),
        DOWN_RIGHT(4),
        OVER(5);

        private final int value;

        private posSprite(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
    }
    public posSprite currSpriteIdx = posSprite.DEFAULT;
    final private Image[] Sprites = new Image[countSprites];
    public Canvas dinoCanvas = new Canvas(2000, 1000);
    final private GraphicsContext currSpriteImage = dinoCanvas.getGraphicsContext2D();

    Dino() {
        Sprites[0] = new Image("main-character3.png");
        Sprites[1] = new Image("main-character1.png");
        Sprites[2] = new Image("main-character2.png");
        Sprites[3] = new Image("main-character6.png");
        Sprites[4] = new Image("main-character5.png");
        Sprites[5] = new Image("main-character4.png");
    }


    @Override
    public void MoveObject() {
//        dinoCanvas.setFocusTraversable(true);
//        dinoCanvas.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
//            if (event.getCode() == KeyCode.SPACE) {
//                System.out.println("Space");
//            }
//        });
        if (direction == Direction.FLY_UP) {
            currSpriteImage.clearRect(this.x, this.y, 110, 86);
            y -= (y - 100) / maxHeight * speedFlyDown;
            currSpriteImage.drawImage(Sprites[posSprite.DEFAULT.getValue()], this.x, this.y, this.width, this.height);
        } else if (direction == Direction.FLY_DOWN) {
            currSpriteImage.clearRect(this.x, this.y, 110, 86);
            y += (y - 100) / maxHeight * speedFlyDown;
            currSpriteImage.drawImage(Sprites[posSprite.DEFAULT.getValue()], this.x, this.y, this.width, this.height);
        }

        if (y <= minHeight) {
            currSpriteImage.clearRect(this.x, this.y, 110, 86);
            y = minHeight;
            currSpriteImage.drawImage(Sprites[posSprite.DEFAULT.getValue()], this.x, this.y, this.width, this.height);
            direction = Direction.FLY_DOWN;
        } else if (y > maxHeight) {
            currSpriteImage.clearRect(this.x, this.y, 110, 86);
            y = maxHeight;
            currSpriteImage.drawImage(Sprites[posSprite.DEFAULT.getValue()], this.x, this.y, this.width, this.height);
        }
        if (y == maxHeight) {
            speedFlyDown = 30;
            if (direction == Direction.FLY_DOWN) {
                direction = Direction.DEFAULT;
            }
            if (speedDino.getSpeed(speed)) {
                currSpriteImage.clearRect(this.x, this.y, 110, 86);
                if (direction == Direction.DEFAULT) {
                    if (currSpriteIdx == posSprite.UP_RIGHT) {
                        currSpriteIdx = posSprite.UP_LEFT;
                    } else {
                        currSpriteIdx = posSprite.UP_RIGHT;
                    }
                    currSpriteImage.drawImage(Sprites[currSpriteIdx.getValue()], this.x, this.y, this.width, this.height);
                } else if (direction == Direction.DOWN) {
                    this.width = 110;
                    this.height = 56;
                    if (currSpriteIdx == posSprite.DOWN_LEFT) {
                        currSpriteIdx = posSprite.DOWN_RIGHT;
                    } else {
                        currSpriteIdx = posSprite.DOWN_LEFT;
                    }
                    currSpriteImage.drawImage(Sprites[currSpriteIdx.getValue()], this.x, this.y + 30, this.width, this.height);
                }
                this.width = 80;
                this.height = 86;
            }
        }
    }
}