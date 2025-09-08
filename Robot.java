/**
 * Clase Robot: representa un robot gráfico
 * con cabeza, ojos y boca.
 */
public class Robot {
    private Rectangle cabeza;
    private Circle ojo1;
    private Circle ojo2;
    private Rectangle boca;

    // posición superior izquierda del robot
    private int x;
    private int y;

    /**
     * Constructor de Robot
     * @param x coordenada X
     * @param y coordenada Y
     */
    public Robot(int x, int y) {
        this.x = x;
        this.y = y;

        // cabeza
        cabeza = new Rectangle();
        cabeza.changeColor("blue");
        cabeza.changeSize(16, 16);
        cabeza.moveHorizontal(x);
        cabeza.moveVertical(y);
        cabeza.makeVisible();
        

        // ojos
        ojo1 = new Circle();
        ojo2 = new Circle();
        ojo1.changeSize(4);
        ojo2.changeSize(4);
        ojo1.moveHorizontal(x + 2);
        ojo2.moveHorizontal(x + 10);
        ojo1.moveVertical(y + 5);
        ojo2.moveVertical(y + 5);
        ojo1.changeColor("green");
        ojo2.changeColor("green");
        ojo1.makeVisible();
        ojo2.makeVisible();

        // boca
        boca = new Rectangle();
        boca.changeSize(2, 10);
        boca.moveHorizontal(x + 3);
        boca.moveVertical(y + 11);
        boca.changeColor("white");
        boca.makeVisible();
    }

    // --- getters ---
    public int getX() { return x; }
    public int getY() { return y; }
    
    public void makeInvisible(){
    
        cabeza.makeInvisible();
        ojo1.makeInvisible();
        ojo2.makeInvisible();
        boca.makeInvisible();
    }
}
