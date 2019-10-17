import bagel.Window;
import bagel.util.*;

public class Bucket extends Character {

    private Vector2 velocity;
    private static final int SPEED = 4;

    public Bucket() {
        super(new Point(512, 744), "res/bucket.png");
        this.velocity = new Vector2(-SPEED, 0);
    }

    @Override
    public void update() {
        super.move(velocity);

        if (super.getRect().left() < 0 || super.getRect().right() > Window.getWidth()) {
            velocity = new Vector2(-velocity.x, velocity.y);
        }
        super.draw();
    }
}


