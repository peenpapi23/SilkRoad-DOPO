package shapes;
import java.awt.*;

/**
 * Un triángulo que puede ser manipulado y dibujado en el canvas.
 */
public class Triangle extends Figure {

    public static final int VERTICES = 3;
    private int height;
    private int width;

    public Triangle() {
        super();
        height = 30;
        width = 40;
        color = "green";
    }

    public void changeSize(int newHeight, int newWidth) {
        erase();
        height = newHeight;
        width = newWidth;
        draw();
    }

    @Override
    protected Shape getShape() {
        int[] xpoints = { xPosition, xPosition + (width / 2), xPosition - (width / 2) };
        int[] ypoints = { yPosition, yPosition + height, yPosition + height };
        return new Polygon(xpoints, ypoints, 3);
    }
}

