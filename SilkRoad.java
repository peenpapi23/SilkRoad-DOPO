import java.util.ArrayList;

public class SilkRoad {
    private ArrayList<Cell> cells;   // lista de todas las celdas
    private int cellSize = 60;       // tamaño de cada celda (en píxeles)
    private int margin = 20;         // margen inicial

    /**
     * Constructor de Board
     * @param n número de celdas a crear en espiral
     */
    public SilkRoad(int lenght) {
        cells = new ArrayList<>();

        // generar posiciones en espiral
        ArrayList<int[]> gridPositions = generateSpiral(lenght);

        // calcular min para desplazar todo a positivo
        int minGridX = Integer.MAX_VALUE;
        int minGridY = Integer.MAX_VALUE;
        for (int[] pos : gridPositions) {
            if (pos[0] < minGridX) minGridX = pos[0];
            if (pos[1] < minGridY) minGridY = pos[1];
        }

        // crear las celdas con id
        for (int i = 0; i < gridPositions.size(); i++) {
            int gx = gridPositions.get(i)[0];
            int gy = gridPositions.get(i)[1];

            int px = margin + (gx - minGridX) * cellSize;
            int py = margin + (gy - minGridY) * cellSize;

            Cell c = new Cell(px, py, i); // id = i
            cells.add(c);
        }
    }

    /**
     * Genera coordenadas en espiral
     */
    private ArrayList<int[]> generateSpiral(int n) {
        ArrayList<int[]> positions = new ArrayList<>();
        int x = 0, y = 0;
        int step = 1;
        int dir = 0;

        positions.add(new int[]{x, y});

        while (positions.size() < n) {
            for (int r = 0; r < 2; r++) {
                for (int s = 0; s < step; s++) {
                    if (positions.size() >= n) break;
                    if (dir == 0) x++;        // derecha
                    else if (dir == 1) y++;   // abajo
                    else if (dir == 2) x--;   // izquierda
                    else if (dir == 3) y--;   // arriba
                    positions.add(new int[]{x, y});
                }
                dir = (dir + 1) % 4;
            }
            step++;
        }
        return positions;
    }

    /**
     * Añadir un robot en la celda con id dado
     */
    public void placeRobot(int cellId) {
        if (cellId >= 0 && cellId < cells.size()) {
            cells.get(cellId).addRobot();
        } else {
            System.out.println("⚠️ Celda " + cellId + " no existe en este tablero.");
        }
    }

    /**
     * Añadir una tienda en la celda con id dado
     */
    public void placeStore(int cellId) {
        if (cellId >= 0 && cellId < cells.size()) {
            cells.get(cellId).addStore();
        } else {
            System.out.println("⚠️ Celda " + cellId + " no existe en este tablero.");
        }
    }

    
    /**
     * 
     */
    public void removeStore(int cellId){
        if (cellId >= 0 && cellId < cells.size()) {
            cells.get(cellId).removeStore();
    
        }
    }
    
    
      public void removeRobot(int cellId){
        if (cellId >= 0 && cellId < cells.size()) {
            cells.get(cellId).removeRobot();
    
        }
    }
    
    
    public void moveRobot(int cellId, int distance){
          if (cellId >= 0 && cellId < cells.size()) {
            cells.get(cellId).removeRobot();
            cells.get(cellId + distance).addRobot();
    
        }
    
    
    }
    /**
     * Obtener todas las celdas
     */
    public ArrayList<Cell> getCells() {
        return cells;
    }
    
   
    public void makeInvisible() {
    for (Cell c : cells) {
        c.makeInvisible();
        }
    }
    
     
    public void makeVisible() {
    for (Cell c : cells) {
        c.makeVisible();
        }
    }
    
    

}
