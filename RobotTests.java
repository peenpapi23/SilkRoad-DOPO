package test;

import org.junit.jupiter.api.*;
import silkroad.*;

import static org.junit.jupiter.api.Assertions.*;

public class RobotTests {

    @Test
    public void testTenderRobotHasExtraTengesOnCreation() {
        TenderRobot tender = new TenderRobot(0, 0, 1);
        assertTrue(tender.getTenges() > 0, "TenderRobot debería tener tenges iniciales positivos");
    }

    @Test
    public void testRegularRobotCanStealFromNormalStore() {
        Store store = new Store(30, 30, 10);
        Robot robot = new Robot(0, 0, 0);
        assertTrue(store.canBeStolen(robot), "Store normal sí puede ser robada");
    }
}
