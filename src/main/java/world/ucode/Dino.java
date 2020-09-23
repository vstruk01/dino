package world.ucode;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.image.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;


public class Dino implements Object {
    public long speed = 100;
    final private Speed speedDino = new Speed();

    public double speedFlyDown = 30;
    final private int countSprites = 6;
    private double width = 80;
    private double height = 86;
    private double x;
    private double y;
    private double maxHeight;
    private double minHeight;


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

    Dino(double x, double y) {
        this.x = x;
        this.y = y;
        this.maxHeight = y;
        this.minHeight = y - 250;
        Sprites[0] = new Image("main-character3.png");
        Sprites[1] = new Image("main-character1.png");
        Sprites[2] = new Image("main-character2.png");
        Sprites[3] = new Image("main-character6.png");
        Sprites[4] = new Image("main-character5.png");
        Sprites[5] = new Image("main-character4.png");
    }

    public void setDirectionPressed(KeyEvent event) {
        if (event == null) { return; }
        if ((event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.UP ) && this.direction == Direction.DEFAULT) {
            this.direction = Direction.FLY_UP;
        } else if (event.getCode() == KeyCode.DOWN && this.direction == Direction.FLY_DOWN) {
            this.speedFlyDown *= 2;
        } else if (event.getCode() == KeyCode.DOWN && this.direction == Direction.DEFAULT) {
            currSpriteImage.clearRect(this.x, this.y, width, height);
            this.y += 30;
            this.maxHeight = this.y;
            this.width = 110;
            this.height = 56;
            this.currSpriteIdx = posSprite.DOWN_RIGHT;
            this.direction = Direction.DOWN;
        }
    }
    public void setDirectionReleased(KeyEvent event) {
        if (event == null) { return; }
        if (event.getCode() == KeyCode.DOWN && this.direction == Dino.Direction.DOWN) {
            currSpriteImage.clearRect(this.x, this.y, width, height);
            this.y -= 30;
            this.maxHeight = this.y;
            this.width = 80;
            this.height = 86;
            this.currSpriteIdx = posSprite.UP_RIGHT;
            this.direction = Dino.Direction.DEFAULT;
        }
    }

    @Override
    public void MoveObject() {
        this.currSpriteImage.clearRect(this.x, this.y, width, height);
        if (direction == Direction.FLY_UP) {
            this.y -= (y - 200) / maxHeight * speedFlyDown;
            currSpriteIdx = posSprite.DEFAULT;
        } else if (direction == Direction.FLY_DOWN) {
            this.y += (y - 200) / maxHeight * speedFlyDown;
            currSpriteIdx = posSprite.DEFAULT;
        }

        if (y <= minHeight) {
            this.y = minHeight;
            this.direction = Direction.FLY_DOWN;
        } else if (y > maxHeight) {
            this.currSpriteIdx = posSprite.UP_LEFT;
            this.y = maxHeight;
        }
        if (this.y == maxHeight) {
            speedFlyDown = 30;
            if (direction == Direction.FLY_DOWN) {
                this.direction = Direction.DEFAULT;
            }
            if (speedDino.getSpeed(speed)) {
                if (direction == Direction.DEFAULT) {
                    if (currSpriteIdx == posSprite.UP_RIGHT) {
                        this.currSpriteIdx = posSprite.UP_LEFT;
                    } else {
                        this.currSpriteIdx = posSprite.UP_RIGHT;
                    }
                } else if (this.direction == Direction.DOWN) {
                    if (currSpriteIdx == posSprite.DOWN_LEFT) {
                        this.currSpriteIdx = posSprite.DOWN_RIGHT;
                    } else {
                        this.currSpriteIdx = posSprite.DOWN_LEFT;
                    }
                }
            }
        }
        this.currSpriteImage.drawImage(this.Sprites[this.currSpriteIdx.getValue()], this.x, this.y, this.width, this.height);
    }
}