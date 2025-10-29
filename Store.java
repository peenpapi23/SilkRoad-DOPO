package silkroad;
import java.util.Random;
import shapes.*;

/**
 * Class Store: represents a store with a house, roof, and door.
 * 
 * Each store has a certain amount of tenges (its stock or resources).
 * When tenges reach 0, the store changes color to indicate it's empty.
 */
public class Store {
    // --- Attributes ---
    public Rectangle body;
    public Triangle roof;
    private Rectangle door;
    private String intialBodyColor;
    private String intialRoofColor;

    private int xPosition;
    private int yPosition;
    private int tenges;
    private int initialTenges;
    private int timesEmptied = 0;
    protected int cellId;

    
    
    
private static final String[][] COLOR_SCHEMES = {
    {"green", "white"},
    {"blue", "yellow"},
    {"red", "magenta"},
    {"blue", "orange"},
    {"magenta", "pink"},
    {"orange", "white"},
    {"cyan", "black"},
    {"yellow", "gray"},
    {"pink", "blue"},
    {"white", "red"},
    {"gray", "green"},
    {"darkGray", "cyan"},
    {"lightGray", "magenta"},
    {"black", "yellow"},
    {"orange", "green"},
    {"red", "white"},
    {"blue", "pink"},
    {"cyan", "orange"},
    {"magenta", "yellow"},
    {"green", "lightGray"},
    {"yellow", "black"},
    {"pink", "gray"},
    {"orange", "blue"},
    {"cyan", "white"},
    {"gray", "orange"},
    {"red", "darkGray"},
    {"green", "magenta"},
    {"blue", "white"},
    {"yellow", "lightGray"},
    {"pink", "black"}
};

    private static int nextColorIndex = 0;
    
    
    // --- Constructor ---
    public Store(int x, int y, int tenges) {
        this.xPosition = x;
        this.yPosition = y;
        this.tenges = tenges;
        this.initialTenges = tenges;
        this.cellId = cellId;

        
        
                // Asignar esquema de color
        String[] colors = COLOR_SCHEMES[nextColorIndex % COLOR_SCHEMES.length];
        this.intialBodyColor = colors[0];
        this.intialRoofColor = colors[1];
        nextColorIndex++;
        
        
        
        // House (base)
        body = new Rectangle();
        body.changeColor(intialBodyColor);
        body.changeSize(16, 16);
        body.moveHorizontal(x);
        body.moveVertical(y);

        // Roof (encima del house)
        roof = new Triangle();
        roof.changeColor(intialRoofColor);
        roof.changeSize(12, 20); // height, base
        roof.moveHorizontal(x + 8); // centrado
        roof.moveVertical(y - 12);

        // Door (rectángulo más pequeño al centro)
        door = new Rectangle();
        door.changeSize(4, 8);
        door.moveHorizontal(x + 6);
        door.moveVertical(y + 8);
   

        // Visibilidad
        roof.makeVisible();
        body.makeVisible();
        door.makeVisible();
    }

    // --- Métodos auxiliares de color ---
    public void updateColors() {
        if (tenges > 0) {
            body.changeColor(intialBodyColor);
            roof.changeColor(intialRoofColor);
            door.changeColor("cyan");
        } else {
            body.changeColor("yellow");
            roof.changeColor("black");
            door.changeColor("black");
        }
    }

    
    
      public boolean canBeStolen(Robot robot) {
        return true; // por defecto, sí puede ser robada
    }
    
    
    public boolean canBeMotel(){
        return false; // por defecto
    }
    
    
    // --- Getters ---
    public int getX() {
        return xPosition; 
    }
    
    public int getY() { 
        return yPosition; 
    }
    
    public int getTenges() { 
        return tenges; 
    }
    
    public int getInitialTenges() { 
        return initialTenges;
    }
    
    public int getTimesEmptied() { 
        return timesEmptied;
    }

    // --- Setters ---
    public void setTenges(int t) {
        if (this.tenges > 0 && t == 0) timesEmptied++;
        this.tenges = t;
        updateColors();
    }

    
    
    // --- Functional methods ---
       public void regenTenges() {
        if (initialTenges > 0) {
            // 🔁 lógica para regenerar el dinero
            tenges = initialTenges; // ejemplo simple
        }
    }   
    
       
    public void couple(Cell celda){
    }


    public void setColor() {
        body.changeColor("black");
        roof.changeColor("black");
        door.changeColor("black");
    }

    // --- Visibility ---
    public void makeInvisible() {
        body.makeInvisible();
        roof.makeInvisible();
        door.makeInvisible();
    }
 
}

    


