import java.io.Serializable;
import java.util.ArrayList;

public class InterviewerManager implements Serializable {

    private ArrayList<Interviewer> Interviewers;

    InterviewerManager(ArrayList<Interviewer> a) {
        this.Interviewers = a;
    }

    boolean hasInterviewer(String username) {
        for (Interviewer a : this.Interviewers) {
            if (a.getUserName().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    Interviewer getInterviewer(String username) {
        for (Interviewer a : this.Interviewers) {
            if (a.getUserName().equalsIgnoreCase(username)) {
                return a;
            }
        }
        return null;
    }

    Interviewer getInterviewer(Integer id) {
        for (Interviewer a : this.Interviewers) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    void addInterviewer(Interviewer a) {
        this.Interviewers.add(a);
    }

    ArrayList<Interviewer> getInterviewers() {
        return this.Interviewers;
    }
}
