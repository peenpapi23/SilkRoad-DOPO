public class Cell {
    private Rectangle gap;
    private Rectangle square;
    private Rectangle marcador;

    private int x;       // coordenada en píxeles (esquina superior izquierda del gap)
    private int y;
    private int id;      // identificador de la celda

    
    
    private Robot robot; // referencia al robot si existe
    private Store store; // referencia a la tienda si existe

    // contador estático opcional para asignar ids automáticamente
    private static int nextId = 0;

    /** Constructor con id explícito */
    public Cell(int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;

        // gap (fondo de la celda)
        gap = new Rectangle();
        gap.changeSize(60, 60);
        gap.changeColor("black");
        gap.moveHorizontal(x);
        gap.moveVertical(y);
        gap.makeVisible();

        // square (cuadrado interior)
        square = new Rectangle();
        square.changeSize(54, 54);
        square.changeColor("yellow");
        square.moveHorizontal(x + 3);
        square.moveVertical(y + 3);
        square.makeVisible();
        
        marcador= new Rectangle();
        marcador.changeSize(10,2);
        marcador.moveHorizontal(x+30);
        marcador.moveVertical(y+3);
        marcador.makeVisible();
        
        
        // imprimir en consola para debug
        System.out.println("Celda " + id + " creada en (" + x + "," + y + ")");
    }

    /** Constructor que asigna id automáticamente */
    public Cell(int x, int y) {
        this(x, y, nextId++);
    }

    // --- getters ---
    public int getX() { return x; }
    public int getY() { return y; }
    public int getId() { return id; }

    // --- añadir robot / store en la celda ---
    public void addRobot() {
        if (robot == null) {
            // offset para centrar el robot dentro del square; ajusta si quieres
            robot = new Robot(x + 10, y + 10);
        } else {
            System.out.println("La celda " + id + " ya tiene un robot.");
        }
    }

    public void addStore() {
        if (store == null) {
            store = new Store(x + 30, y + 30);
        } else {
            System.out.println("La celda " + id + " ya tiene una tienda.");
        }
    }

    // opcionales: remover o comprobar existencia
    public void removeRobot() {
        // Si quisieras ocultarlo necesitarías un método en Robot para hide/remove,
        // o guardar referencias a los objetos gráficos y borrarlos.
        if (robot==null){
            System.out.println("no hay robots para eliminar");
        }else{
        
        robot.makeInvisible();
        robot = null;
        }
    }
    
    
    public void removeStore() {
         if (store==null){
            System.out.println("no hay tiendas para eliminar");
        }else{
        
            store.makeInvisible();
            store = null;
        }
    }
    
    public void makeInvisible(){
        gap.makeInvisible();
        square.makeInvisible();
        marcador.makeInvisible();
    }
    
    public void makeVisible(){
        gap.makeVisible();
        square.makeVisible();
        marcador.makeVisible();
    }
}
   
