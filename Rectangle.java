package shapes;
import java.awt.*;

/**
 * Un rectángulo que puede ser manipulado y dibujado en el canvas.
 */
public class Rectangle extends Figure {

    public static final int EDGES = 4;
    private int height;
    private int width;

    public Rectangle() {
        super();
        height = 30;
        width = 40;
        color = "white";
    }

    public void changeSize(int newHeight, int newWidth) {
        erase();
        height = newHeight;
        width = newWidth;
        draw();
    }

    @Override
    protected Shape getShape() {
        return new java.awt.Rectangle(xPosition, yPosition, width, height);
    }
}


