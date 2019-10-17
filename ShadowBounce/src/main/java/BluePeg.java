import bagel.util.Point;

public class BluePeg extends Peg {

    public BluePeg(Point point, String imageSrc, boolean isHorizontal, boolean isVertical) {
        super(point, imageSrc, isHorizontal, isVertical);
    }
    public void update() {
        super.draw();
    }
}
