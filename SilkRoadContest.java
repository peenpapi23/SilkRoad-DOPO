package silkroad;
import shapes.*;
public class SilkRoadContest {
    private SilkRoad road;
    private int[][] days;

    public SilkRoadContest(int[][] days) {
        this.days = days;
        this.road = new SilkRoad(100);
    }

    public int[] solve() {
        if (days == null) return new int[0];

        int n = days.length;
        int[] dailyProfits = new int[n];

        for (int day = 0; day < n; day++) {
            road.reboot();

            int[] action = days[day];
            if (action == null || action.length == 0) continue;

            int type = action[0];
            if (type == 1 && action.length >= 2)
                road.placeRobot(action[1]);
            else if (type == 2 && action.length >= 3)
                road.placeStore(action[1], action[2]);
            else
                System.out.println("Acción inválida día " + (day + 1));

            road.moveRobots();
            dailyProfits[day] = road.profit();
        }

        road.finish();
        return dailyProfits;
    }

    public void simulate(boolean slow) {
        int[] profits = solve();

        for (int i = 0; i < profits.length; i++) {
            System.out.println("Día " + (i+1) + " → Ganancia: " + profits[i]);
            if (slow) {
                try { Thread.sleep(750); } 
                catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            }
        }
    }
}
