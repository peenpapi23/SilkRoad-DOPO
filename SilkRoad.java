package silkroad;
import shapes.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


/**
 * Clase SilkRoad: representa el tablero principal del juego.
 *
 * El tablero está compuesto por un conjunto de celdas dispuestas en forma de espiral.
 * Cada celda puede contener un robot y/o una tienda.
 *
 * Esta clase se encarga de:
 * - Crear y almacenar las celdas en espiral.
 * - Administrar robots (colocar, mover, devolver a su posición inicial).
 * - Administrar tiendas (colocar, eliminar).
 * - Controlar la visualización de las celdas y sus elementos.
 */
public class SilkRoad {

    private ArrayList<Cell> cells;
    private ArrayList<Rectangle> paths;
    private final int cellSize = 60;     
    private final int margin = 20;         
    private int totalCollected = 0;
    private int totalProfit = 0;
    private double initialTotalTenges;
    private ProgressBar progressBar;
    

    private boolean simulationFinished = false;
    private boolean lastOperationOk = true;
    private String lastError = "";

    // ======================
    
    /**
     * crea el tablero
     * @param length es la longitud del tablero
     */
      public SilkRoad(int length) {
        initBoard(length, null);
    }
    
    
    /**
     * crea el tablero de acuerdo a entrada del maraton
     * @param days es la lista de acciones por dia
     */
      public SilkRoad(int[][] days) {
            int maxPos = getMaxPosition(days);
            int length = maxPos + 5; // margen de seguridad
            initBoard(length, days);
    }


    /** 
     * 
     * Inicializa el tablero (para ambos constructores
       */
    private void initBoard(int length, int[][] days) {
        cells = new ArrayList<>(); 
        paths = new ArrayList<>();
        

        ArrayList<int[]> gridPositions = generateSpiral(length);
        int[] minValues = getMinGridValues(gridPositions);
        int minGridX = minValues[0];
        int minGridY = minValues[1];

        drawPath(gridPositions, minGridX, minGridY);
        createCells(gridPositions, minGridX, minGridY);

        if (days != null) {
            placeEntities(days);
        }
        progressBar = new ProgressBar();
    }
    
    
    /**
     * coloca la tienda en una posicion con cierta cantidad de tenges
     *  @param cellId identificador de la celda
     *   @tenges cantidad de tenges a asignar
     */
    public void placeStore(int cellId, int tenges) {
        placeStore(cellId, tenges, "normal"); // por defecto normal
    }

    
   /**
     * coloca la tienda en una posicion con cierta cantidad de tenges
     *  @param cellId identificador de la celda
     *  @param tenges cantidad de tenges a asignar
     *  @param type determina el tipo de tienda
     */
    public void placeStore(int cellId, int tenges, String type) {
        if (cellId < 0 || cellId >= cells.size()) {
            lastOperationOk = false;
            System.out.println("⚠️ Celda " + cellId + " no existe en este tablero.");
            return;
        }

        Cell celda = cells.get(cellId);

     
        if (type.equalsIgnoreCase("autonomous")) {
            int randomCell = (int) (Math.random() * cells.size());
            celda = cells.get(randomCell);
            System.out.println("🧠 Autonomous store se colocó aleatoriamente en celda " + randomCell);
        }

        celda.addStore(tenges, type);
        initialTotalTenges += tenges;
    }

    
    /**
     * Elimina la tienda de la celda indicada.
     *
     * @param cellId identificador de la celda
     */
    public void removeStore(int cellId) {
        if (cellId >= 0 && cellId < cells.size()) {
            cells.get(cellId).removeStore();
        }
        else{
             lastOperationOk=false;
        }
    }


    /**
     * Coloca un robot en la celda indicada.
     *
     * @param cellId identificador de la celda destino
     */
    public void placeRobot(int cellId,String type) {
        if (cellId >= 0 && cellId < cells.size()) {
            cells.get(cellId).addRobot(type);
        } else {
            
            System.out.println("⚠️ Celda " + cellId + " no existe en este tablero.");
            lastOperationOk=false;
        }
    }
    
    
    public void placeRobot(int cellId) {
        if (cellId >= 0 && cellId < cells.size()) {
            cells.get(cellId).addRobot();
        } else {
            System.out.println("⚠️ Celda " + cellId + " no existe en este tablero.");
            lastOperationOk = false;
        }
    }
    
    
        /**
     * Elimina el robot de la celda indicada.
     *
     * @param cellId identificador de la celda
     */
    public void removeRobot(int cellId) {
        if (cellId >= 0 && cellId < cells.size()) {
            cells.get(cellId).removeRobot();
        }else{
         lastOperationOk=false;
        }
    }
    
    
    /**
     * determina el mejor movimiento para el robot iterando a traves de las tiendas
     * y asignando bestValue
     */
    public void moveRobots() {
        
        
        if (simulationFinished) {
            lastOperationOk = false;
            lastError = "No se pueden mover robots: la simulación ha finalizado.";
            return;
        }

        boolean movedAtLeastOne = false;

        for (Cell origin : cells) {
            if (!origin.hasRobot()) continue;
            Robot robot = origin.getRobot();

            int bestProfit = Integer.MIN_VALUE;
            Cell bestTarget = null;

            for (Cell destination : cells) {
                if (!destination.hasStore()) continue;
                Store store = destination.getStore();

                int distance = Math.abs(destination.getId() - origin.getId());
                int potentialProfit = store.getTenges() - distance;

                if (potentialProfit > bestProfit) {
                    bestProfit = potentialProfit;
                    bestTarget = destination;
                }
            }

            if (bestTarget != null && bestProfit > 0) {
                int distance = bestTarget.getId() - origin.getId();
                moveRobot(origin.getId(), distance);
                movedAtLeastOne = true;
            } else {
                lastOperationOk = false;
                lastError = "No hay movimiento beneficioso disponible para el robot";
                System.out.println("⚠️ " + lastError);
            }
        }

        if (movedAtLeastOne) {
            lastOperationOk = true;
            lastError = "";
        }
        
        highlightTopRobot();


    }

    /**
     * Mueve un robot de acuerdo a la distancia indicada.
     * @param initialCell celda de origen
     * @param distance número de celdas a moverse (puede ser negativo)
     */
    public void moveRobot(int initialCell, int distance) {

        // --- Actualización visual previa ---
        int acumuladoAntes = profit(); 
        double progressAntes = acumuladoAntes / (initialTotalTenges + 0.0001);
        int porcentajeAntes = (int) Math.round(progressAntes * 100);
        progressBar.update(porcentajeAntes);

        // --- Validaciones ---
        if (initialCell < 0 || initialCell >= cells.size()) {
            System.out.println("⚠️ Celda de origen inválida.");
            lastOperationOk = false;
            return;
        }

        Cell originCell = cells.get(initialCell);
        Robot robot = originCell.getRobot();

        if (robot == null) {
            System.out.println("⚠️ No hay robot en la celda de origen.");
            lastOperationOk = false;
            return;
        }

        int arrivalCellId = initialCell + distance;
        if (arrivalCellId < 0 || arrivalCellId >= cells.size()) {
            System.out.println("⚠️ Movimiento inválido: fuera de los límites del tablero.");
            lastOperationOk = false;
            return;
        }

        Cell arrivalCell = cells.get(arrivalCellId);
        Store arrivalStore = arrivalCell.getStore();
        boolean tieneStore = arrivalCell.hasStore();

        int profit = 0;
        int costo = Math.abs(distance);
        int tengesRobot = robot.getTenges();

        // --- Interacción con tienda ---
        if (tieneStore) {
            int antes = arrivalStore.getTenges();

            if (arrivalStore.canBeStolen(robot)) {
                robot.steal(arrivalStore);
                int despues = arrivalStore.getTenges();
                int robado = antes - despues;
                profit = robado - costo;

                if (despues == 0) {
                    arrivalStore.setColor(); // tienda vacía
                }
            } else {
                // no puede robar
                robot.setTenges(tengesRobot - costo);
                profit = -costo;
            }   
        }

        // --- Mover robot al destino ---
        arrivalCell.setRobot(robot);
        robot.moveTo(arrivalCellId);
        robot.setProfit(profit);
        originCell.removeRobot();

        // --- 💞 Efecto especial LoveStore ---
        if (arrivalStore != null && arrivalStore.canBeMotel()) {
            arrivalStore.couple(arrivalCell);
        }

        // --- Actualizar UI y progreso ---
        highlightTopRobot();
        System.out.println("🤖 Robot movido de " + initialCell + " a " + arrivalCellId);

        int acumulado = profit(); 
        double progress = acumulado / (initialTotalTenges + 0.0001);
        int porcentaje = (int) Math.round(progress * 100);
        progressBar.update(porcentaje);

        lastOperationOk = true;
    }


    
    /**
     * este metodo suple con los tenges iniciales a todas las
     * tiendas del tablero
     */
    public void resupplyStores() {
        for (Cell c : cells) {
            if (c.hasStore()) { // ✅ único chequeo necesario
                c.supplyStore();
            }
        }
    }
    
    
    /**
     * hace que los robots retornen al origen
     */
    public void returnRobots() {
        for (Cell cell : cells) {
            if (!cell.hasRobot()) continue;

            Robot robot = cell.getRobot();

            // Verifica por polimorfismo, sin instanceof
            if (!robot.canReturn()) {
                System.out.println("⚠️ El robot en celda " + cell.getId() + " no se devuelve (Neverback).");
            
            }else{
              int initialPos = robot.getInitialPos();
                int currentPos = robot.getCurrentPos();

                if (initialPos != currentPos) {
                    int distance = initialPos - currentPos;
                    moveRobot(currentPos, distance);
                    robot.setTenges(0);
                }
                
            }

          
        }
    }


    /**
     * lo que tiene que hacer esto es return robots y resuply stores basicamente;
     * mirar el efecto de los dias
     */
    public void reboot() {
      returnRobots();
      resupplyStores();
      progressBar.update(0);
      
    }
    
        
    /**
     * este metodo recorre todas las celdas y extrae los tenges de cada robot para saber la ganancia acumulada
     */
     public int profit() {
        int totalProfit = 0;

        for (Cell c : cells) {
            if (c.hasRobot()) {
                Robot r = c.getRobot();
                totalProfit += r.getTenges(); // suma los tenges actuales de cada robot
            }
        }

        return totalProfit;
    }


    /**
     * Muestra y retorna todas las tiendas ordenadas por id de celda.
     * Cada fila tiene el formato [cellId, tenges].
     */
    public int[][] stores() {
        List<int[]> lista = new ArrayList<>();

        for (Cell c : cells) {
            if (c.hasStore()) {
                lista.add(new int[]{c.getId(), c.getStore().getTenges()});
            }
        }

        lista.sort(Comparator.comparingInt(a -> a[0]));

        int[][] resultado = new int[lista.size()][2];
        for (int i = 0; i < lista.size(); i++) {
            resultado[i] = lista.get(i);
        }

        System.out.println("STORES:");
        for (int[] s : resultado) {
            System.out.println("Cell " + s[0] + " -> " + s[1] + " tenges");
        }

        return resultado;
    }
    
    
    /**
     * devuelve cuantas veces han sido vaciadas las tiendas, ordenadas
     * en una lista de menor a mayor localizacion [location,times]
     */
    public int[][] emptiedStores(){
        List <int[]> lista = new ArrayList<>();
        
        for (Cell c: cells){
            if(c.hasStore()){
                Store store = c.getStore();
                int times = (store.getTimesEmptied() >=0) ? store.getTimesEmptied() :0;
                lista.add(new int[]{c.getId(),times});
            }
        }
        
        lista.sort(Comparator.comparingInt(a->a[0]));
        
        int[][] resultado = new int[lista.size()][2];
        for (int i=0;i<lista.size();i++){
            resultado[i]=lista.get(i);
        }
        
        System.out.println("EMPTIED STORES");
        for (int[] e:resultado){
            System.out.println("Cell "+e[0]+"-> vaciada "+e[1]+" veces");
        }
        
        return resultado;
    
    }


    /**
     * Muestra y retorna todos los robots ordenados por id de celda.
     * Cada fila tiene el formato [cellId, tenges].
     */
    public int[][] robots() {
        List<int[]> lista = new ArrayList<>();

        for (Cell c : cells) {
            if (c.hasRobot()) {
                lista.add(new int[]{c.getId(), c.getRobot().getTenges()});
            }
        }

        lista.sort(Comparator.comparingInt(a -> a[0]));

        int[][] resultado = new int[lista.size()][2];
        for (int i = 0; i < lista.size(); i++) {
            resultado[i] = lista.get(i);
        }

        System.out.println("ROBOTS:");
        for (int[] r : resultado) {
            System.out.println("Cell " + r[0] + " -> " + r[1] + " tenges");
        }

        return resultado;
    }

    
    /**
     * retorna los profits de cada robot segun sus moviemientos
     */
       public void PrintprofitPerMove() {
        for (Robot r : getAllRobots()) {
            System.out.println("Robot " + r.getCurrentPos() + " profits: " + r.getProfitPerMove());
        }
    }

    
    public int[][] profitPerMove() {
        List<int[]> data = new ArrayList<>();
        for (Robot r : getAllRobots()) {
            List<Integer> profits = r.getProfitPerMove();
            int[] arr = new int[profits.size() + 1];
            arr[0] = r.getCurrentPos();
            for (int i = 0; i < profits.size(); i++) {
                arr[i + 1] = profits.get(i);
            }
            data.add(arr);
        }
        return data.toArray(new int[0][]);
    }

    
    /**
     * Hace invisibles todas las celdas y sus elementos.
     */
    public void makeInvisible() {
        for (Cell c : cells) {
            c.makeInvisible();
        }
        for (Rectangle path : paths){
            path.makeInvisible();
        }
        progressBar.makeInvisible();
    }

    
    /**
     * Hace visibles todas las celdas y sus elementos.
     */
    public void makeVisible() {
        for (Cell c : cells) {
            c.makeVisible();
        }
    }

    
    /**
     * verifica que la ultima operacion haya sido exitosa
     */
     public boolean ok() {
        return lastOperationOk;
    }

    
    /**
     * apoyo de ok
     */
    public String getLastError() {
        return lastError;
    }

    
    /**
     * termina la simulacion
     */
    public void finish() {
        if (simulationFinished) {
            lastOperationOk = false;
            lastError = "La simulación ya fue finalizada";
            return;
        }   

        // Marcar simulación como finalizada
        simulationFinished = true;
        System.out.println("Esperando 10 segundos...");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            }
        System.out.println("Pausa terminada!");

        // Asegurar limpieza de todo el tablero
        makeInvisible();

        totalCollected = 0;
        totalProfit = 0;

        lastOperationOk = true;
        lastError = "";
        System.out.println("✅ Simulación finalizada. Todos los datos fueron eliminados.");
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    /**
     * Devuelve todas las celdas del tablero.
     *
     * @return lista de celdas
     */
    private ArrayList<Cell> getCells() {
        return cells;
    }


    /** Dibuja el camino entre las celdas en forma de espiral */
    private void drawPath(ArrayList<int[]> gridPositions, int minGridX, int minGridY) {
        int cellGap = 12;
        int spacing = cellSize + cellGap;
        int pathThickness = 10;

        for (int i = 0; i < gridPositions.size() - 1; i++) {
            int[] p1 = gridPositions.get(i);
            int[] p2 = gridPositions.get(i + 1);

            int gx1 = p1[0] - minGridX;
            int gy1 = p1[1] - minGridY;
            int gx2 = p2[0] - minGridX;
            int gy2 = p2[1] - minGridY;

            int px1 = margin + gx1 * spacing;
            int py1 = margin + gy1 * spacing;
            int px2 = margin + gx2 * spacing;
            int py2 = margin + gy2 * spacing;

            int cx1 = px1 + cellSize / 2;
            int cy1 = py1 + cellSize / 2;
            int cx2 = px2 + cellSize / 2;
            int cy2 = py2 + cellSize / 2;

            Rectangle path = new Rectangle();
            path.changeColor("black");
            paths.add(path);

            if (cx1 == cx2) { // vertical
                int topY = Math.min(py1 + cellSize, py2 + cellSize);
                int bottomY = Math.max(py1, py2);
                int height = bottomY - topY + 2;
                int leftX = cx1 - pathThickness / 2;
                path.changeSize(pathThickness, Math.max(1, height));
                path.moveHorizontal(leftX);
                path.moveVertical(topY);
            } else if (cy1 == cy2) { // horizontal
                int leftX = Math.min(px1 + cellSize, px2 + cellSize);
                int rightX = Math.max(px1, px2);
                int width = rightX - leftX + 2;
                int topY = cy1 - pathThickness / 2;
                path.changeSize(Math.max(1, width), pathThickness);
                path.moveHorizontal(leftX);
                path.moveVertical(topY);
            } else { // diagonal fallback
                int leftX = Math.min(cx1, cx2);
                int topY = Math.min(cy1, cy2);
                int width = Math.abs(cx2 - cx1);
                int height = Math.abs(cy2 - cy1);
                path.changeSize(Math.max(1, width), Math.max(1, height));
                path.moveHorizontal(leftX);
                path.moveVertical(topY);
            }

            path.makeVisible();
        }
    }

    /** Crea las celdas en base a las posiciones generadas */
    private void createCells(ArrayList<int[]> gridPositions, int minGridX, int minGridY) {
        int cellGap = 12;
        int spacing = cellSize + cellGap;

        for (int i = 0; i < gridPositions.size(); i++) {
            int gx = gridPositions.get(i)[0];
            int gy = gridPositions.get(i)[1];
            int px = margin + (gx - minGridX) * spacing;
            int py = margin + (gy - minGridY) * spacing;

            cells.add(new Cell(px, py, i));
        }
    }

    /** Coloca robots y tiendas según la entrada del constructor */
    private void placeEntities(int[][] days) {
        for (int[] day : days) {
            int type = day[0];
            int pos = day[1];

            if (type == 1) {
                Robot r = new Robot(0, 0, pos);
                cells.get(Math.min(pos, cells.size() - 1)).setRobot(r);
            } else if (type == 2) {
                int tenges = day[2];
                Store s = new Store(0, 0, tenges);
                cells.get(Math.min(pos, cells.size() - 1)).addStore(tenges);
            }
        }
    }

    /** Devuelve el valor mínimo de X e Y en la grilla */
    private int[] getMinGridValues(ArrayList<int[]> gridPositions) {
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        for (int[] pos : gridPositions) {
            if (pos[0] < minX) minX = pos[0];
            if (pos[1] < minY) minY = pos[1];
        }
        return new int[]{minX, minY};
    }

    /** Determina la posición más lejana del arreglo de días (solo por posición, no por tenges) */
    private int getMaxPosition(int[][] days) {
        int maxPos = 0;
        for (int[] day : days) {
            if (day[1] > maxPos) {
                maxPos = day[1];
            }
        }
        return maxPos;
    }
    
  
    /**
     * Genera coordenadas en espiral para la creación de celdas.
     *
     * @param n cantidad de posiciones a generar
     * @return lista de coordenadas en espiral
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

    
    private List<Robot> getAllRobots() {
        List<Robot> robots = new ArrayList<>();
        for (Cell c : cells) {
            if (c.hasRobot()) robots.addAll(c.getAllRobots());
        }
        return robots;
    }
    
    
    public void highlightTopRobot() {
        Robot top = null;
        int maxProfit = Integer.MIN_VALUE;

        for (Cell c : cells) {
            if (c.hasRobot()) {
                Robot r = c.getRobot();
                if (r.getTenges() > maxProfit) {
                    maxProfit = r.getTenges();
                    top = r;
                }
            }
        }

        if (top != null) {
            top.blinkEyes();
            System.out.println("⭐ El robot más rentable parpadeó con " + maxProfit + " tenges.");
        }
    }
}

