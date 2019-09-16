import java.io.Serializable;
import java.util.ArrayList;

public class RefereeManager implements Serializable {

    private ArrayList<Referee> referees;

    RefereeManager(ArrayList<Referee> a) {
        this.referees = a;
    }

    ArrayList<Referee> getReferees() {
        return this.referees;
    }

    void addReferee(Referee referee) {
        this.referees.add(referee);
    }

    boolean hasReferee(String name) {
        for (Referee e : this.referees) {
            if (e.getUserName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    Referee getRefereeByUsername(String name) {

        for (Referee e : this.referees) {
            if (e.getUserName().equalsIgnoreCase(name)) {
                return e;
            }
        }
        return null;
    }

}
