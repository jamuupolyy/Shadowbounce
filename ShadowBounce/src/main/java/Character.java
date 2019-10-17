/**
 * SWEN20003 Object Oriented Software Development
 * Project 2, Semester 2, 2019
 *
 * Uses sample solution of Project 1.
 *
 * @author Jason Polychronopoulos
 *
 */

import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.util.Vector2;

public abstract class Character {
    private Image image;
    private Rectangle rect;

    public Character(Point point, String imageSrc) {
        image = new Image(imageSrc);
        rect = image.getBoundingBoxAt(point);
    }

    public boolean intersects(Character other) {
        return rect.intersects(other.rect);
    }

    public Rectangle getRect() {
        return this.rect;
    }

    public void move(Vector2 dx) {
        rect.moveTo(rect.topLeft().asVector().add(dx).asPoint());
    }

    public void draw() {
        image.draw(rect.centre().x, rect.centre().y);
    }

    public abstract void update();
}
