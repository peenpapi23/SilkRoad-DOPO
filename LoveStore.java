package silkroad;

import java.util.ArrayList;

public class LoveStore extends Store {
    
    public LoveStore(int x, int y, int tenges) {
        super(x, y, tenges);
        body.changeColor("white");
        roof.changeColor("pink");
    }

    @Override
    public boolean canBeMotel() {
        return true;    
    }

    @Override
    public void couple(Cell cell) {
        ArrayList<Robot> robots = cell.getAllRobots();
        
        // Si hay exactamente 2 robots, se crea un nuevo "bebé" robot
        if (robots.size() == 2) {
            Robot newRobot = new Robot(
                cell.getX()+30,
                cell.getY() + 30,
                cell.getId()
            );
            
            // Lo agregamos correctamente usando el método setRobot de la celda
            cell.setRobot(newRobot);

            System.out.println("💞 Nuevo robot creado en el LoveStore!");
        }
    }

    @Override
    public boolean canBeStolen(Robot robot) {
        return false; // No puede ser robada
    }
}
