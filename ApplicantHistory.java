import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

class ApplicantHistory implements Serializable {
    private Date created;
    private Date last_closed_Application;
    private ArrayList<String> pastJobs;
    private ArrayList<String> currentJobs;

    ApplicantHistory() {
        this.created = new Date();
        this.last_closed_Application = new Date();
        this.pastJobs = new ArrayList<>();
        this.currentJobs = new ArrayList<>();
    }

    ApplicantHistory(Date created1, Date last_closed, ArrayList<String> pastJobs1, ArrayList<String> currentJobs1) {
        this.created = created1;
        this.last_closed_Application = last_closed;
        this.pastJobs = pastJobs1;
        this.currentJobs = currentJobs1;
    }

    /**
     * Registers the action to close an application
     */
    void closeApplication(String title, int id) {
        this.last_closed_Application = new Date();
        this.currentJobs.remove(title + " " + id);
        this.pastJobs.add(title + " " + id);
    }

    /**
     * Registers the action to make an application
     */
    void apply(String title, int id) {
        this.currentJobs.add(title + " " + id);
    }

    ArrayList<String> getPastJobs() {
        return this.pastJobs;
    }

    ArrayList<String> getCurrentJobs() {
        return this.currentJobs;
    }
}
