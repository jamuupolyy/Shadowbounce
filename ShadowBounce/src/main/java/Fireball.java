import bagel.Window;
import bagel.util.Point;
import bagel.util.Side;
import bagel.util.Vector2;

public class Fireball extends Ball {

    private Vector2 velocity;
    private static final double GRAVITY = 0.15;

    public Fireball(Point point, Vector2 direction, String imageSrc) {
        super(point, direction, imageSrc);
        velocity = direction;
    }

    /**
     * Changes the direction of the balls velocity depending on which side it collided with another object.
     * @param side The side which a collision occurred.
     */
    public void bounceOff(Side side) {

        if (side.equals(Side.TOP) || side.equals(Side.BOTTOM)) {
            velocity = new Vector2(velocity.x, -velocity.y);
        }

        else if (side.equals(Side.LEFT) || side.equals(Side.RIGHT)){
            velocity = new Vector2(-velocity.x, velocity.y);
        }
    }

    @Override
    public void update() {
        velocity = velocity.add(Vector2.down.mul(GRAVITY));
        super.move(velocity);

        if (super.getRect().left() < 0 || super.getRect().right() > Window.getWidth()) {
            velocity = new Vector2(-velocity.x, velocity.y);
        }

        super.draw();
    }
}
