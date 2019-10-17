import bagel.Window;
import bagel.util.*;

public class PowerUp extends Character {

    private Vector2 velocity;
    private Point destination;
    private static final double SPEED = 3;

    public PowerUp(Point point, Point destination) {
        super(point, "res/powerup.png");
        this.destination = destination;
        this.velocity = (point.asVector().sub(destination.asVector()).normalised()).mul(-SPEED);
    }

    /**
     * Calculates the new destination for the power-up to travel to.
     */
    private void changePosition() {
        if (closeToDestination()) {
            this.destination = new Point(Math.random() * Window.getWidth(), Math.random() * Window.getHeight());
            this.velocity = (this.getRect().centre().asVector().sub(destination.asVector()).normalised()).mul(-SPEED);
        };
    }

    /**
     * Check to see whether the power-up is close to its destination.
     * @return true if the powerup is within 5 pixels of its destination, false otherwise.
     */
    private boolean closeToDestination() {
        return (Math.pow((destination.x - this.getRect().centre().x), 2) +
                Math.pow((destination.y - this.getRect().centre().y), 2)) <= 5;
    }

    @Override
    public void update() {
        changePosition();
        super.move(velocity);
        super.draw();
    }

}
