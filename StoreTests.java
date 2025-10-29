package test;

import org.junit.jupiter.api.*;
import silkroad.*;

import static org.junit.jupiter.api.Assertions.*;

public class StoreTests {

    @Test
    public void testAutonomousStoreGeneratesTengesAutomatically() {
        AutonomousStore store = new AutonomousStore(50, 50, 10);
        int before = store.getTenges();
        store.regenTenges();
        int after = store.getTenges();
        assertTrue(after >= before, "El AutonomousStore debería regenerar o mantener tenges");
    }

    @Test
    public void testFighterStoreCannotBeStolen() {
        FighterStore store = new FighterStore(50, 50, 20);
        Robot dummy = new Robot(0, 0, 0);
        boolean result = store.canBeStolen(dummy);
        assertFalse(result, "FighterStore no debería poder ser robada por ningún robot");
    }

    @Test
    public void testLoveStoreCreatesNewRobotWhenTwoRobotsPresent() {
        Cell cell = new Cell(100, 100);
        LoveStore loveStore = new LoveStore(100, 100, 15);
        cell.addRobot();
        cell.addRobot();
        loveStore.couple(cell);
        int count = cell.getAllRobots().size();
        assertEquals(3, count, "LoveStore debería crear un nuevo robot al tener 2 presentes");
    }

    @Test
    public void testLoveStoreCannotBeStolen() {
        LoveStore loveStore = new LoveStore(100, 100, 20);
        Robot r = new Robot(0, 0, 0);
        assertFalse(loveStore.canBeStolen(r), "LoveStore no debería poder ser robada");
    }
}


