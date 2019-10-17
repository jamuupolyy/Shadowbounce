import bagel.util.Point;

public class GreenPeg extends Peg {

    public GreenPeg(Point point, String imageSrc, boolean isHorizontal, boolean isVertical) {
        super(point, imageSrc, isHorizontal, isVertical);
    }
    public void update() {
        super.draw();
    }

}
