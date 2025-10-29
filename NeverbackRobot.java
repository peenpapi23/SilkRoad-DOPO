package silkroad;
import shapes.*;
public class NeverbackRobot extends Robot {

    public NeverbackRobot(int x, int y, int initialPos) {
        super(x, y, initialPos);
        
    }
    
    @Override
    public boolean canReturn() {
        return false;
    }

}