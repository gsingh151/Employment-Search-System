import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class JobPosting implements Serializable {

    private int id;
    private ArrayList<Application> applications;
    private InterviewFormat interviewFormat;
    private String title;
    private Date dateCreated;
    private Date closingDate;
    private String status;
    private Boolean shouldManuallyFill;
    private ArrayList<String> tags;
    private Integer hired_applicant;

    public JobPosting(String title, Date closingDate, String unfiltered_order, ArrayList<String> new_tags) {
        this.applications = new ArrayList<>();
        this.title = title;
        this.closingDate = closingDate;
        this.dateCreated = new Date();
        this.status = "Open";
        this.shouldManuallyFill = false;
        this.interviewFormat = new InterviewFormat(unfiltered_order);
        ArrayList<String> temp = new ArrayList<>();
        for (String s : new_tags) {
            temp.add(s.toLowerCase());
        }
        this.tags = temp;
        this.hired_applicant = -1; // -1 means no one is hired yet
        try {
            File file = new File("JobID.txt");
            if (!file.exists()) {
                file.createNewFile();
                BufferedWriter output = new BufferedWriter(new FileWriter(file, false));
                output.write("0");
                output.close();
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            this.id = Integer.parseInt(reader.readLine());
            reader.close();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
            writer.write(String.valueOf(this.id + 1));
            writer.close();

        } catch (IOException e) {
            System.out.println("IO exception.");
        }
    }

    // ------------------GETTERS---------------------
    int getId() {
        return this.id;
    }

    String getTitle() {
        return this.title;
    }

    String getStatus() {
        return this.status;
    }

    Date getDateCreated() {
        return this.dateCreated;
    }

    Date getClosingDate() {
        return this.closingDate;
    }

    Boolean getShouldManuallyFill() {
        return this.shouldManuallyFill;
    }

    ArrayList<Application> getCurrentApplications() {
        return this.applications;
    }

    Integer getHired_applicant() {
        return this.hired_applicant;
    }

    // ------------------PUBLIC methods--------------

    /**
     * Called at the start of the program as well as whenever an application is accepted or rejected
     * Checks if a) there is only one candidate remaining (hires them in this case)
     * b) all rounds have been completed (makes hrcoordinator of company choose the best candidate)
     * c) current round is completed (goes to next round)
     */
    void updateJobPosting(ApplicantManager manager) {
        if (!this.status.equals("Filled")) {
            this.closePosting();
            Application application = this.getLastCandidateLeft();
            if (application != null) {
                this.hire(manager, application);
            } else if (this.allRoundsCompleted()) {
                this.shouldManuallyFill = true;
            } else if (this.roundCompleted()) {
                this.nextRound(manager);
            }
        }
    }

    /**
     * Add a tag to this JobPosting's list of tags
     */
    void addTag(String tag) {
        this.tags.add(tag);
    }

    /**
     * `* Add an Applicant (its info) to this JobPosting's list of applicants (both lists)
     */
    void addApplicant(Application application) {
        this.applications.add(application);
    }

    /**
     * Remove an Applicant (and its info) from this JobPosting object entirely (as if they never applied)
     *
     * @param id Applicant's Id
     */
    void removeApplicant(Integer id) {
        Application application1 = null;
        for (Application application : this.applications) {
            if (application.getApplicant_id() == id) {
                application1 = application;
            }
        }
        this.applications.remove(application1);
    }

    /**
     * Check if this job posting is open.
     *
     * @return true if it is open
     */
    boolean isOpen() {
        return this.getStatus().equals("Open");
    }

    boolean isFilled() {
        return this.status.equals("Filled");
    }

    /**
     * Print out this posting's title, date posted and date closes, list of  requirements (CV + CL), and status
     */
    public String toString() {
        return "---------------------------------------------"
                + "\nJobPosting id: " + this.id + "\nJob title: " + this.title + "\nJob posted: " + this.dateCreated.toString()
                + "\nApplications closed: " + this.closingDate.toString() + "\nRequirements: CV and CL" + this.interviewFormat.printSummary() + "\nStatus: "
                + this.status + "\nTags: " + this.printTags();
    }

    /**
     * @param id applicant's id
     * @return application of this applicant
     */
    Application getApplicant(int id) {
        for (Application application : this.applications) {
            if (application.getApplicant_id() == id) {
                return application;
            }
        }
        return null;
    }

    /**
     * @param filter represents the filter (can be lower of uppercase)
     * @return true if the job posting passes the filter
     */
    boolean passesFilter(String filter) {
        String lc_filter = filter.toLowerCase();
        if (lc_filter.equals("deadline")) {
            return this.isOpen();
        }
        return this.tags.contains(lc_filter);
    }

    void manuallyFill(ApplicantManager manager) {
        this.readAllApplications(manager);
        System.out.println("Please select one of these applications (enter their applicant id)");
        VerifyInput verifyInput = new VerifyInput();
        int id = verifyInput.getValidNumber();
        for (Application application1 : this.applications) {
            if (application1.getApplicant_id() == id) {
                System.out.println("Hiring " + manager.getApplicant(id).getName());
                this.hire(manager, application1);
                return;
            }
        }
        System.out.println("Applicant does not exist or did not apply to this job posting. Please try again.");
        this.manuallyFill(manager);
    }

    void readAllApplications(ApplicantManager manager) {
        for (Application application1 : this.applications) {
            application1.read(manager);
        }
    }

    //------------------PRIVATE METHODS-----------------------

    /**
     * @return an application object if there is only one candidate remaining and all others are rejected
     * return null otherwise
     */
    private Application getLastCandidateLeft() {
        Application lastCandidate = null;
        int accepted = 0;
        for (Application application : this.applications) {
            if (application.getStatus().equals("Accepted into next round")) {
                accepted++;
                lastCandidate = application;
            } else if (application.getStatus().equals("Pending")) {
                return null;
            }
        }
        if (accepted > 1) {
            return null;
        }
        return lastCandidate;
    }

    /**
     * Hires applicant who submitted application
     */
    private void hire(ApplicantManager manager, Application application) {
        application.accepted(manager, this);
        for (Application application1 : this.applications) {
            if (application1.getId() != application.getId()) {
                application1.rejected(manager, this);
            }
        }
        this.status = "Filled";
        this.deleteApplicantInfo();
        this.hired_applicant = application.getApplicant_id();
    }

    /**
     * @return true only if all rounds are completed
     */
    private boolean allRoundsCompleted() {
        return this.interviewFormat.atLastRound() && this.roundCompleted();
    }

    /**
     * @return true only if the current round is completed
     * a round is completed when all applications have been reviewed
     */
    private boolean roundCompleted() {
        for (Application application : this.applications) {
            if (application.getStatus().equals("Pending")) {
                return false;
            }
        }
        return true;
    }

    /**
     * Commences next round
     */
    private void nextRound(ApplicantManager manager) {
        this.interviewFormat.nextRound();
        Application application1 = null;
        for (Application application : this.applications) {
            if (application.getStatus().equals("Rejected")) {
                application1 = application;
            } else {
                application.nextRound(manager, this);
            }
        }
        this.applications.remove(application1);
    }

    /**
     * Closed when no new applications is allowed
     * Compare today's date (new Date) with closing date
     * Make the status = "Closed"
     */
    private void closePosting() {
        Date currentDate = new Date();
        if (currentDate.compareTo(this.closingDate) >= 0) {
            this.status = "Closed";
            this.deleteApplicantInfo();
        }
    }

    /**
     * Delete the documents saved in this JobPosting
     */
    private void deleteApplicantInfo() {
        for (Application application : this.applications) {
            application.clearInfo();
        }
    }

    private String printTags() {
        String result = "";
        for (String tag : this.tags) {
            result += tag;
            result += ", ";
        }
        return result;
    }
}

