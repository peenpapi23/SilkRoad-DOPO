package shapes;
import java.awt.geom.*;
import java.awt.Shape;

/**
 * Un círculo que puede ser manipulado y dibujado en el canvas.
 */
public class Circle extends Figure {

    public static final double PI = 3.1416;
    private int diameter;

    public Circle() {
        super();
        diameter = 10;
        color = "blue";
    }

    public void changeSize(int newDiameter) {
        erase();
        diameter = newDiameter;
        draw();
    }

    @Override
    protected Shape getShape() {
        return new Ellipse2D.Double(xPosition, yPosition, diameter, diameter);
    }
}

