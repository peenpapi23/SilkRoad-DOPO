package shapes;
import java.awt.*;
import java.awt.geom.*;

/**
 * Clase abstracta base para todas las figuras geométricas.
 * Contiene propiedades y comportamientos comunes.
 */
public abstract class Figure {

    protected int xPosition;
    protected int yPosition;
    protected String color;
    protected boolean isVisible;

    public Figure() {
        xPosition = 0;
        yPosition = 0;
        color = "black";
        isVisible = false;
    }

    // --- Visibilidad ---
    public void makeVisible() {
        isVisible = true;
        draw();
    }

    public void makeInvisible() {
        erase();
        isVisible = false;
    }

    // --- Movimiento ---
    public void moveRight() { moveHorizontal(20); }
    public void moveLeft() { moveHorizontal(-20); }
    public void moveUp() { moveVertical(-20); }
    public void moveDown() { moveVertical(20); }

    public void moveHorizontal(int distance) {
        erase();
        xPosition += distance;
        draw();
    }

    public void moveVertical(int distance) {
        erase();
        yPosition += distance;
        draw();
    }

    public void slowMoveHorizontal(int distance) {
        int delta = distance < 0 ? -1 : 1;
        int steps = Math.abs(distance);
        for (int i = 0; i < steps; i++) {
            xPosition += delta;
            draw();
        }
    }

    public void slowMoveVertical(int distance) {
        int delta = distance < 0 ? -1 : 1;
        int steps = Math.abs(distance);
        for (int i = 0; i < steps; i++) {
            yPosition += delta;
            draw();
        }
    }

    // --- Color ---
    public void changeColor(String newColor) {
        color = newColor;
        draw();
    }

    // --- Lógica de dibujo común ---
    protected void draw() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            Shape shape = getShape(); // 👈 se delega al hijo
            if (shape != null) {
                canvas.draw(this, color, shape);
                canvas.wait(10);
            }
        }
    }

    protected void erase() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }

    // --- Método abstracto que define la forma ---
    protected abstract Shape getShape();
}
