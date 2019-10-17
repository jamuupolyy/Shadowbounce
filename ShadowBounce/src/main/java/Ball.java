/**
 * SWEN20003 Object Oriented Software Development
 * Project 2, Semester 2, 2019
 *
 * Uses sample solution of Project 1.
 *
 * @author Jason Polychronopoulos
 *
 */

import bagel.Window;
import bagel.util.*;

public class Ball extends Character {
    private Vector2 velocity;
    private Vector2 direction;
    private static final double GRAVITY = 0.15;
    private static final double SPEED = 10;

    public Ball(Point point, Vector2 direction, String imageSrc) {
        super(point, imageSrc);
        this.direction = direction;
        velocity = direction.mul(SPEED);
    }

    public boolean outOfScreen() {
        return getRect().top() > Window.getHeight();
    }

    /**
     * Returns the current velocity of the ball.
     * @return returnVelocity The current velocity of the ball represented as a 2D vector.
     */
    public Vector2 getVelocity() {
        return velocity;
    }

    /**
     * Returns the current velocity of the ball.
     * @return returnVelocity The current velocity of the ball represented as a 2D vector.
     */
    public Vector2 getDirection() {
        Vector2 returnVelocity = new Vector2(velocity.x, velocity.y);
        return returnVelocity;
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
