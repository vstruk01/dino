package world.ucode;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.image.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;


public class Dino extends ObjectGame {
    private long speed = 100;
    private final Speed speedDino = new Speed();

    private double speedFly = 30;
    private final int countSprites = 6;
    private double maxHeight;
    final private double sizeJump = 256;


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

    Dino(double x, double y) {
        width = 80;
        height = 86;
        this.x = x;
        this.y = y;
        this.maxHeight = y;
        canvas = new Canvas(width, height);

        Sprites[0] = new Image("main-character3.png");
        Sprites[1] = new Image("main-character1.png");
        Sprites[2] = new Image("main-character2.png");
        Sprites[3] = new Image("main-character6.png");
        Sprites[4] = new Image("main-character5.png");
        Sprites[5] = new Image("main-character4.png");
    }


    @Override
    public Polygon[] getHitBox() {
        this.countPolygon = 3;
        Polygon[] pol = new Polygon[countPolygon]; // for Head, Body, Legs

        for (int i = 0; i < this.countPolygon; i++) {
            pol[i] = new Polygon();
        }

        Double[] boxHead = new Double[] {
                this.x + (this.width / 2), this.y,
                this.x + this.width, this.y,
                this.x + this.width, this.y + (this.height / 3),
                this.x + (this.width / 2), this.y + (this.height / 3),
        };

        Double[] boxBody = new Double[] {
                this.x, this.y + (this.height / 3),
                this.x + this.width - 15, this.y + (this.height / 3),
                this.x + this.width - 15, this.y + this.height - (this.height / 3),
                this.x, this.y + this.height - (this.height / 3),
        };

        Double[] boxLegs = new Double[] {
                this.x + (this.width / 5), this.y + this.height - (this.height / 3),
                this.x + (this.width - ((this.width / 5) * 2)), this.y + this.height - (this.height / 3),
                this.x + (this.width - ((this.width / 5) * 2)), this.y + this.height,
                this.x + (this.width / 5), this.y + this.height,
        };

        pol[0].getPoints().addAll(boxHead);
        pol[1].getPoints().addAll(boxBody);
        pol[2].getPoints().addAll(boxLegs);
        return pol;
    }

    @Override
    public void Restart() {
        clear();
        if (this.direction == Direction.DOWN) {
            maxHeight -= 30; // height of sprite of down is smaller than height of sprite default on 30px,
                             // when direction is down maxHeight has change on 30px up,
                             // if player was dead in direction down maxHeight have to subtract 30px;
        }
        this.y = this.maxHeight;
        this.width = 80;
        this.height = 86;
        currSpriteIdx = posSprite.DEFAULT;
        direction = Direction.DEFAULT;
        this.image = this.Sprites[this.currSpriteIdx.getValue()];

        canvas.setTranslateX(this.x);
        canvas.setTranslateY(this.y);

        draw();
    }

    @Override
    public void updateObject() {
        clear();
        if (direction == Direction.FLY_UP) {
            this.y -= (y - 40) / maxHeight * speedFly;
            currSpriteIdx = posSprite.DEFAULT;
        } else if (direction == Direction.FLY_DOWN) {
            this.y += (y - 40) / maxHeight * speedFly;
            currSpriteIdx = posSprite.DEFAULT;
        }

        if (y <= maxHeight - sizeJump) {
            this.y = maxHeight - sizeJump;
            this.direction = Direction.FLY_DOWN;
        } else if (y > maxHeight) {
            this.currSpriteIdx = posSprite.UP_LEFT;
            this.y = maxHeight;
        }
        if (this.y == maxHeight) {
            speedFly = 30;
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
        image = this.Sprites[this.currSpriteIdx.getValue()];
        draw();
    }

    public void setDirectionPressed(KeyEvent event) {
        if (event == null) { return; }
        if ((event.getCode() == KeyCode.SPACE || event.getCode() == KeyCode.UP ) && this.direction == Direction.DEFAULT) {
            this.direction = Direction.FLY_UP;
        } else if (event.getCode() == KeyCode.DOWN &&  (this.direction == Direction.FLY_UP || this.direction == Direction.FLY_DOWN)) {
            this.speedFly *= 3;
            this.direction = Direction.FLY_DOWN;
        } else if (event.getCode() == KeyCode.DOWN && this.direction == Direction.DEFAULT) {
            clear();
            this.y += 30; // height of sprite of down is smaller than height of sprite default on 30px
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
            clear();
            this.y -= 30;
            this.maxHeight = this.y;
            this.width = 80;
            this.height = 86;
            this.currSpriteIdx = posSprite.UP_RIGHT;
            this.direction = Dino.Direction.DEFAULT;
        }
    }
}