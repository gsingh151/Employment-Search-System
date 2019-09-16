import java.io.Serializable;
import java.util.ArrayList;

public class ApplicantManager implements Serializable {

    private ArrayList<Applicant> Applicants;

    ApplicantManager(ArrayList<Applicant> a) {
        this.Applicants = a;
    }


    boolean hasApplicant(String username) {
        for (Applicant a : this.Applicants) {
            if (a.getUserName().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    Applicant getApplicant(String username) {
        for (Applicant a : this.Applicants) {
            if (a.getUserName().equalsIgnoreCase(username)) {
                return a;
            }
        }
        return null;
    }

    Applicant getApplicant(int id) {
        for (Applicant a : this.Applicants) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    void addApplicant(Applicant a) {
        this.Applicants.add(a);
    }

    ArrayList<Applicant> getApplicants() {
        return this.Applicants;
    }
}
