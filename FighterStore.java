package silkroad;
import shapes.*;
/**
 * FighterStore: una tienda que solo puede ser robada por robots
 * que tienen más dinero que ella.
 */
public class FighterStore extends Store {

    public FighterStore(int x, int y, int tenges) {
        super(x, y, tenges);
    }

    /**
     * Solo permite ser robada si el robot tiene más tenges que la tienda.
     */
    @Override
    public boolean canBeStolen(Robot robot) {
        return robot.getTenges() > getTenges();
    }
}
