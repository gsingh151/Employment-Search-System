import java.io.Serializable;
import java.util.ArrayList;

public class HRCoordinatorManager implements Serializable {
    private ArrayList<HRCoordinator> Coordinators;

    HRCoordinatorManager(ArrayList<HRCoordinator> c) {
        this.Coordinators = c;
    }

    boolean hasCoordinator(String username) {
        for (HRCoordinator c : this.Coordinators) {
            if (c.getUserName().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    HRCoordinator getCoordinator(String username) {
        for (HRCoordinator c : this.Coordinators) {
            if (c.getUserName().equalsIgnoreCase(username)) {
                return c;
            }
        }
        return null;
    }

    void addCoordinator(HRCoordinator c) {
        this.Coordinators.add(c);
    }

    ArrayList<HRCoordinator> getCoordinators() {
        return this.Coordinators;
    }
}
