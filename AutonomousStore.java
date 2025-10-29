package silkroad;
import shapes.*;
public class AutonomousStore extends Store {
    public AutonomousStore(int x, int y, int tenges) {
        super(x, y, tenges);
        System.out.println("🧠 AutonomousStore creada en celda " + cellId + " con " + tenges + " tenges.");
    }
}

