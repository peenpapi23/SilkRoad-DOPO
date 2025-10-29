package silkroad;
import java.util.List;
import java.util.ArrayList;
import shapes.*;

/**
 * Class Robot: represents a graphical robot
 * composed of a head, eyes, and mouth.
 *
 * Each robot knows its initial and current position
 * within a cell, as well as its coordinates on the plane (x, y).
 */
public class Robot {
    // --- Attributes ---
    private Rectangle head;
    private Circle eye1;
    private Circle eye2;
    private Rectangle mouth;
    
    
    // top-left corner coordinates of the robot
    private int xPosition;
    private int yPosition;

    
    public int tenges;
    private final int initialPos;
    private int currentPos;
    
    
    private int totalProfit;
    private List<Integer> profitsPerMove;
    
    
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

    private static int nextColorIndex = 0; // índice global


    // --- Constructor ---
    /**
     * Creates a new robot at the given coordinates and draws it on screen.
     *
     * @param x          initial X coordinate
     * @param y          initial Y coordinate
     * @param initialPos id of the cell where the robot starts
     */
    public Robot(int x, int y, int initialPos) {
        this.xPosition = x;
        this.yPosition = y;
        this.initialPos = initialPos;
        this.tenges = 0;
        this.totalProfit = 0;
        this.profitsPerMove = new ArrayList<>();

        
        
        
        // Asignar esquema de color
        String[] colors = COLOR_SCHEMES[nextColorIndex % COLOR_SCHEMES.length];
        String eyeColor = colors[0];
        String headColor = colors[1];
        nextColorIndex++;
        
        
        // Head
        head = new Rectangle();
        head.changeColor(headColor);
        head.changeSize(16, 16);
        head.moveHorizontal(x);
        head.moveVertical(y);
        head.makeVisible();

        // Eyes
        eye1 = new Circle();
        eye2 = new Circle();
        eye1.changeSize(4);
        eye2.changeSize(4);
        eye1.moveHorizontal(x + 2);
        eye2.moveHorizontal(x + 10);
        eye1.moveVertical(y + 5);
        eye2.moveVertical(y + 5);
        eye1.changeColor(eyeColor);
        eye2.changeColor(eyeColor);
        eye1.makeVisible();
        eye2.makeVisible();

        // Mouth
        mouth = new Rectangle();
        mouth.changeSize(2, 10);
        mouth.moveHorizontal(x + 3);
        mouth.moveVertical(y + 11);
        mouth.changeColor("black");
        mouth.makeVisible();
    }

    // --- Basic getters ---
    /**
     * @return current X coordinate of the robot
     */
    public int getXposition() {
        return xPosition;
    }

      
    /**
     * @return current Y coordinate of the robot
     */
    public int getYposition() {
        return yPosition;
    }

    
    /**
     * @return amount of tenges the robot currently has
     */
    public int getTenges() {
        return tenges;
    }

    
    /**
     * @return id of the cell where the robot was initially placed
     */
    public int getInitialPos() {
        return initialPos;
    }

    
    /**
     * @return id of the cell where the robot currently is
     */
    public int getCurrentPos() {
        return currentPos;
    }

    // --- Setters ---
    /**
     * Sets the total amount of tenges the robot has.
     *
     * @param t new tenges value
     */
    public void setTenges(int t) {
        tenges = t;
    }

    
    /**
     * Adds or subtracts tenges from the robot.
     *
     * @param t amount to add (or subtract if negative)
     */
    public void addTenges(int t) {
        tenges += t;
    }

    
    /**
     * Updates the robot’s current cell position.
     *
     * @param cellId id of the current cell
     */
    public void  moveTo(int newPos) {
        this.currentPos = newPos;
    }
    
    
      public void steal(Store store) {
        int cantidad = store.getTenges();
        this.tenges += cantidad;
        store.setTenges(0); // el robot normal roba todo
    }


    public void setProfit(int profit){
     this.totalProfit += profit;
     this.profitsPerMove.add(profit);
    }
    
    
        /**
     * Makes the robot blink (eyes only).
     * The eyes disappear and reappear several times.
     */
    public void blinkEyes() {
        try {
            for (int i = 0; i < 5; i++) {
                eye1.makeInvisible();
                eye2.makeInvisible();
                Thread.sleep(250);
                eye1.makeVisible();
                eye2.makeVisible();
                Thread.sleep(250);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    
    public void setColorScheme(String eyeColor, String headColor) {
        if (eye1 != null) eye1.changeColor(eyeColor);
        if (eye2 != null) eye2.changeColor(eyeColor);
        if (head != null) head.changeColor(headColor);
    }

    

    
    /**
     * Changes the absolute X position of the robot on the screen.
     * All parts (head, eyes, mouth) move accordingly.
     *
     * @param newX new X coordinate
     */
    public void setXposition(int newX) {
        int dx = newX - this.xPosition;
        this.xPosition = newX;

        head.moveHorizontal(dx);
        eye1.moveHorizontal(dx);
        eye2.moveHorizontal(dx);
        mouth.moveHorizontal(dx);
    }

    
    /**
     * Changes the absolute Y position of the robot on the screen.
     * All parts (head, eyes, mouth) move accordingly.
     *
     * @param newY new Y coordinate
     */
    public void setYposition(int newY) {
        int dy = newY - this.yPosition;
        this.yPosition = newY;

        head.moveVertical(dy);
        eye1.moveVertical(dy);
        eye2.moveVertical(dy);
        mouth.moveVertical(dy);
    }


    public boolean canReturn() {
        return true;
    }

    
       public List<Integer> getProfitPerMove() {
        return profitsPerMove;
    }
    
        // --- Visibility control ---
    /**
     * Makes the robot visible on screen (shows all parts).
     */
    public void makeVisible() {
        head.makeVisible();
        eye1.makeVisible();
        eye2.makeVisible();
        mouth.makeVisible();
    }

    
    /**
     * Makes the robot invisible (hides all parts).
     */
    public void makeInvisible() {
        head.makeInvisible();
        eye1.makeInvisible();
        eye2.makeInvisible();
        mouth.makeInvisible();
    }
    
}
