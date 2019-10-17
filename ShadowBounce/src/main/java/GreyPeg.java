import bagel.util.Point;

public class GreyPeg extends Peg {

    public GreyPeg(Point point, String imageSrc, boolean isHorizontal, boolean isVertical) {
        super(point, imageSrc, isHorizontal, isVertical);
    }
    public void update() {
        super.draw();
    }
}