package silkroad;
import java.util.ArrayList;
import java.util.Random;
import shapes.*;

/**
 *  Representa una celda individual dentro del tablero de SilkRoad.
 *
 * Cada celda tiene:
 * <ul>
 *   <liUna posición gráfica en pixeles (x, y)</li>
 *   <li>Un identificador único (cellId)</li>
 *   <li>Elementos visuales (bordes y marcadores)</li>
 *   <li>Contenido posible: un  Store o uno o varios {Robot</li>
 * </ul>
 *
 * Esta clase se encarga de la representación gráfica y lógica de cada celda,
 * incluyendo la creación, asignación y eliminación de robots y tiendas.
 */
public class Cell {

    // --- Elementos visuales ---
    private Rectangle gap;       // Marco exterior negro
    private Rectangle square;    // Cuadro interior rojo
    private Rectangle marker;    // Línea superior roja

    // --- Posición e identificación ---
    private int xPosition;
    private int yPosition;
    private int cellId;

    // --- Contenido ---
    private ArrayList<Robot> robots;
    private Store store;

    // --- Contador estático para IDs automáticos ---
    private static int nextId = 0;

 
    /**
     * Crea una celda con un identificador específico.
     *
     * @param xPosition posición X en píxeles
     * @param yPosition posición Y en píxeles
     * @param cellId identificador único de la celda
     */
    public Cell(int xPosition, int yPosition, int cellId) {
        this.robots = new ArrayList<>();
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.cellId = cellId;

        // Crear borde exterior
        gap = new Rectangle();
        gap.changeSize(60, 60);
        gap.changeColor("black");
        gap.moveHorizontal(xPosition);
        gap.moveVertical(yPosition);
        gap.makeVisible();

        // Crear cuadro interior
        square = new Rectangle();
        square.changeSize(54, 54);
        square.changeColor("red");
        square.moveHorizontal(xPosition + 3);
        square.moveVertical(yPosition + 3);
        square.makeVisible();

        // Crear marcador superior
        marker = new Rectangle();
        marker.changeSize(10, 2);
        marker.changeColor("red");
        marker.moveHorizontal(xPosition + 30);
        marker.moveVertical(yPosition + 3);
        marker.makeVisible();

        System.out.println("Celda " + cellId + " creada en (" + xPosition + "," + yPosition + ")");
    }

    
    /**
     * Crea una celda con un identificador generado automáticamente.
     *
     * @param xPosition posición X en píxeles
     * @param yPosition posición Y en píxeles
     */
    public Cell(int xPosition, int yPosition) {
        this(xPosition, yPosition, nextId++);
    }

    
    /**
     * get de posX
     */
    public int getX() {
        return xPosition;
    }

    
    /**
     * get de PosY
     */
    public int getY() { 
        return yPosition; 
    }

    
    /**
     * get de ID
     */
    public int getId() { 
        return cellId; 
    }


    /**
     * @return {@code true} si hay al menos un robot en la celda 
       */
    public boolean hasRobot() {
        return !robots.isEmpty();
    }

    /**
     * @return {@code true} si la celda contiene una tienda
       */
    public boolean hasStore() {
        return store != null;
    }


    /**
     * Agrega un robot del tipo indicado a la celda (si está vacía).
     *
     * @param type tipo de robot ("tender", "neverbackrobot", etc.)
     */
    public void addRobot(String type) {
        
            Robot newRobot;
            switch (type.toLowerCase()) {
                case "tender":
                    newRobot = new TenderRobot(xPosition + 10, yPosition + 10, cellId);
                    break;
                case "neverbackrobot":
                    newRobot = new NeverbackRobot(xPosition + 10, yPosition + 10, cellId);
                    break;
                default:
                    newRobot = new Robot(xPosition + 10, yPosition + 10, cellId);
                    break;
            }
            robots.add(newRobot);
          
    }

    
    /**
     * Agrega un robot genérico a la celda.
     */
    public void addRobot() {
            Robot newRobot = new Robot(xPosition + 10, yPosition + 10, cellId);
            robots.add(newRobot);
        
    }

    
    /**
     * Agrega una tienda con una cantidad de tenges y tipo específico.
     * Si ya existe una tienda en la celda, no hace nada.
     *
     * @param tenges cantidad de tenges inicial
     * @param type tipo de tienda ("normal", "autonomous", "fighter")
     */
    public void addStore(int tenges, String type) {
        if (this.store != null) {
            System.out.println("⚠️ Ya existe una tienda en esta celda.");
            return;
        }

        Store newStore;

        switch (type.toLowerCase()) {
            case "autonomous":
                newStore = new AutonomousStore(xPosition + 42, yPosition + 42, tenges);
                break;
            case "fighter":
                newStore = new FighterStore(xPosition + 42, yPosition + 42, tenges);
                break;
            case "lover":
                newStore = new LoveStore(xPosition + 42, yPosition + 42, tenges);
                break; // <-- ✅ este era el que faltaba
            default:
                newStore = new Store(xPosition + 42, yPosition + 42, tenges);
                break;
        }

        this.store = newStore;
    }

    
    /**
     * Agrega una tienda normal (compatibilidad con versiones anteriores).
     *
     * @param tenges cantidad de tenges inicial
     */
    public void addStore(int tenges) {
        addStore(tenges, "normal");
    }

 
    /**
     * Asigna un robot ya existente a esta celda.
     *
     * @param robot robot que se colocará en la celda
     */
    public void setRobot(Robot robot) {
        if (robots.isEmpty()) {
            robot.setXposition(xPosition + 10);
            robot.setYposition(yPosition + 10);
        } 
        if(robots.size()==1){
            robot.setXposition(xPosition + 10);
            robot.setYposition(yPosition + 30);
        }
        if(robots.size()==2){
            robot.setXposition(xPosition + 30);
            robot.setYposition(yPosition + 10);
        }
        robots.add(robot);
        robot.makeVisible();
    }

    
    /**
     * Elimina la tienda de la celda, si existe.
     */
    public void removeStore() {
        if (store != null) {
            store.makeInvisible();
            store = null;
        } else {
            System.out.println("No hay tienda para eliminar en esta celda.");
        }
    }

    
    /**
     * Elimina el primer robot de la celda, si existe.
     */
    public void removeRobot() {
        if (robots.isEmpty()) {
            System.out.println("No hay robot para eliminar en esta celda.");
        } else {
            robots.remove(0);
        }

        if (!robots.isEmpty()) {
            robots.get(0).setYposition(robots.get(0).getYposition() - 20);
        }
    }


    /** 
     * Regenera los tenges de la tienda y actualiza sus colores.
       */
    public void supplyStore() {
        store.regenTenges();
        store.updateColors();
    }

    
    /**
     * get de robots
     */
    public Robot getRobot() {
        return robots.isEmpty() ? null : robots.get(0);
    }
    
    
    /**
     * get de array
     */
    public ArrayList<Robot> getRobots() {
        return robots;
    }

    
    /**
     * es un getter de tienda
     */
    public Store getStore() {
        return store;
    }

    /**
     * @return copia de la lista de robots en la celda
    */
    public ArrayList<Robot> getAllRobots() {
        return new ArrayList<>(robots);
    }

    
    /**
     * Muestra gráficamente la celda y su contenido. 
       */
    public void makeVisible() {
        gap.makeVisible();
        square.makeVisible();
        marker.makeVisible();
    }

    
    /**
     * Oculta gráficamente la celda y todo su contenido. 
    */
    public void makeInvisible() {
        gap.makeInvisible();
        square.makeInvisible();
        marker.makeInvisible();

        if (store != null) store.makeInvisible();
        if (robots != null && !robots.isEmpty()) {
            for (Robot r : robots) r.makeInvisible();
        }
    }
    
}


