import bagel.util.Point;

public class RedPeg extends Peg {

    public RedPeg(Point point, String imageSrc, boolean isHorizontal, boolean isVertical) {
        super(point, imageSrc, isHorizontal, isVertical);
    }
    public void update() {
        super.draw();
    }
}
