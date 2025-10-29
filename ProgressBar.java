package silkroad;
import shapes.*;
public class ProgressBar {
    private Rectangle background;
    private Rectangle fill;
    private int maxWidth = 200;  // ancho total
    private int height = 20;     // alto fijo
    private int x = 10;          // posición horizontal estándar
    private int y = 10;         // posición vertical estándar

    public ProgressBar() {
        background = new Rectangle();
        background.changeColor("black");
        background.changeSize(height, maxWidth);
        background.moveHorizontal(x);
        background.moveVertical(y);
        background.makeVisible();

        fill = new Rectangle();
        fill.changeColor("green");
        fill.changeSize(height, 0);
        fill.moveHorizontal(x);
        fill.moveVertical(y);
        fill.makeVisible();
    }

    // valor debe estar entre 0 y 100
    public void update(int progressPercent) {
        if (progressPercent < 0) progressPercent = 0;
        if (progressPercent > 100) progressPercent = 100;

        int newWidth = (maxWidth * progressPercent) / 100;
        fill.changeSize(height, newWidth);
    }

    public void reset() {
        fill.changeSize(height, 0);
    }
    
    public void makeInvisible(){
        background.makeInvisible();
        fill.makeInvisible();
    }

}
