/**
 * SWEN20003 Object Oriented Software Development
 * Project 2, Semester 2, 2019
 *
 * Uses sample solution of Project 1.
 *
 * @author Jason Polychronopoulos
 *
 */

import bagel.util.Point;

public abstract class Peg extends Character {

    private boolean isHorizontal;
    private boolean isVertical;

    public Peg(Point point, String imageSrc,  boolean isHorizontal, boolean isVertical) {
        super(point, imageSrc);
        this.isHorizontal = isHorizontal;
        this.isVertical = isVertical;
    }

    public boolean isVertical() {
        return this.isVertical;
    }

    public boolean isHorizontal() {
        return this.isHorizontal;
    }

    @Override
    public abstract void update();
}
