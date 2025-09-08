/**
 * Clase Store: representa una tienda con casa y techo
 */
public class Store {
    private Rectangle casa;
    private Triangle techo;

    private int x;
    private int y;

    /**
     * Constructor de Store
     * @param x coordenada X
     * @param y coordenada Y
     */
    public Store(int x, int y) {
        this.x = x;
        this.y = y;

        // casa
        casa = new Rectangle();
        casa.changeSize(16, 16);
        casa.moveHorizontal(x);
        casa.moveVertical(y);
        casa.changeColor("red");
        casa.makeVisible();

        // techo
        techo = new Triangle();
        techo.changeSize(12, 20); // altura, base
        techo.moveHorizontal(x + 8); // centrado sobre la casa
        techo.moveVertical(y - 12);  // encima de la casa
        techo.changeColor("green");
        techo.makeVisible();
    }

    // getters de posición
    public int getX() { return x; }
    public int getY() { return y; }
    
    public void makeInvisible(){
        casa.makeInvisible();
        techo.makeInvisible();
    
    }
    
}

