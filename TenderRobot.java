package silkroad;
import shapes.*;
public class TenderRobot extends Robot {
    
    public TenderRobot(int id, int tenges, int position) {
        super(id, tenges, position);
    }

    @Override

    public void steal(Store store) {
    int cantidad = (int) Math.round(store.getTenges() / 2.0);
    this.tenges += cantidad;
    store.setTenges(store.getTenges() - cantidad); // le queda la otra mitad
    }

}