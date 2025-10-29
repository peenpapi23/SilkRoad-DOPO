package test;
import silkroad.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class SilkRoadTest {

    @Test
    public void shouldTrackRobotProfitsPerMove() {
        SilkRoad road = new SilkRoad(50);
        road.placeStore(10, 30);
        road.placeStore(20, 40);
        road.placeRobot(5);

        // Movimientos simulados manualmente por ahora
        road.moveRobot(5, 5);  // hacia celda 10
        road.moveRobot(10, 10); // hacia celda 20

        int[][] profits = road.profitPerMove(); // ya modificado para devolver int[][]

        assertEquals(1, profits.length);
        assertEquals(20, profits[0][0]);
        assertTrue(profits[0][1] > 0);
    }
    

    @Test
    public void robotsShouldMoveToMostProfitableStores() {
        SilkRoad road = new SilkRoad(30);

        road.placeStore(5, 10);
        road.placeStore(10, 40);
        road.placeStore(15, 20);
        road.placeRobot(0);

        road.moveRobots();  // debería ir a la tienda 10 (más rentable)
        road.moveRobots();  // podría ir a otra si hay profit positivo

        int total = road.profit();

        assertTrue(total > 0, "El total de profit debe ser positivo tras moverse");
        assertEquals(1, road.profitPerMove().length, 
            "Debe haber solo un robot registrado en profitPerMove");

        System.out.println("Ganancia total: " + total);
    }
    
    
    
    @Test
    public void shouldBlinkTopProfitRobot() {
        SilkRoad road = new SilkRoad(20);
        road.placeStore(5, 30);
        road.placeStore(10, 50);
        road.placeRobot(0);
        road.placeRobot(15);

        road.moveRobots();  // ambos robots ganan algo
        road.moveRobots();

        // No debería lanzar errores al parpadear el mejor robot
        assertDoesNotThrow(() -> road.highlightTopRobot(),
            "El robot con más ganancia debería poder parpadear sin errores");
    }
    
}



