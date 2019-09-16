import java.io.Serializable;
import java.util.ArrayList;

class Application implements Serializable {

    private static int count = 0;
    private int applicant_id;
    private int id;
    private String status;
    private String cover_letter;
    private ArrayList<String> extraDocuments;

    Application(Applicant applicant, String cover, ArrayList<String> extras) {
        this.id = count;
        count++;
        this.applicant_id = applicant.getId();
        this.status = "Pending";
        this.cover_letter = cover;
        this.extraDocuments = extras;
    }

    /**
     * Register a new round in this applcation
     */
    void nextRound(ApplicantManager manager, JobPosting jobPosting) {
        this.status = "Pending";
        Applicant applicant = manager.getApplicant(this.applicant_id);
        Mailbox mailbox = applicant.getMailbox();
        mailbox.enter("A new round has started for the job posting \"" + jobPosting.getTitle() + "\" with id " +
                jobPosting.getId() + ".");
    }

    /**
     * Get rejected in parameter jobPosting
     */
    void rejected(ApplicantManager manager, JobPosting jobPosting) {
        this.status = "Rejected";
        Applicant applicant = manager.getApplicant(this.applicant_id);
        Mailbox mailbox = applicant.getMailbox();
        mailbox.enter("We regret to inform you that you have been rejected by the job posting \"" +
                jobPosting.getTitle() + "\" with id " + jobPosting.getId() + ".\n Of course, your application " +
                "information will be removed from the system.");
        this.clearInfo();
    }

    /**
     * Get accepted into the next round of parameter jobPosting
     */
    void acceptedToNextRound(ApplicantManager manager, JobPosting jobPosting) {
        this.status = "Accepted into next round";
        Applicant applicant = manager.getApplicant(this.applicant_id);
        Mailbox mailbox = applicant.getMailbox();
        mailbox.enter("We are delighted to inform you that you have been accepted into the next round of the job " +
                "posting \"" + jobPosting.getTitle() + "\" with id " + jobPosting.getId() + ".");
    }

    /**
     * Get accepted for a job posting
     */
    void accepted(ApplicantManager manager, JobPosting jobPosting) {
        this.status = "Accepted for job position";
        Applicant applicant = manager.getApplicant(this.applicant_id);
        Mailbox mailbox = applicant.getMailbox();
        mailbox.enter("We are delighted to inform you that you have been accepted into the job position of the job " +
                "posting \"" + jobPosting.getTitle() + "\" with id " + jobPosting.getId() + ".\n Of course, your " +
                "application information will be removed from the system.");
        this.clearInfo();
    }

    void read(ApplicantManager manager) {
        Applicant applicant = manager.getApplicant(this.applicant_id);
        System.out.println("Applicant info: ");
        System.out.println("-" + applicant);

        System.out.println("Application info: ");
        System.out.println("-Cover letter: " + this.cover_letter);
        System.out.println("-CV: " + applicant.getCV());
        System.out.println("-Reference letters: ");
        applicant.printReferenceLetters();
        System.out.println("-Extra documents: ");
        for (String extra : this.extraDocuments) {
            System.out.println(extra);
        }
        System.out.println();
    }

    void clearInfo() {
        this.cover_letter = "";
        this.extraDocuments = new ArrayList<>();
    }

    // GETTER METHODS

    int getApplicant_id() {
        return this.applicant_id;
    }

    int getId() {
        return this.id;
    }

    String getStatus() {
        return this.status;
    }

    String getCover_letter() {
        return this.cover_letter;
    }

    ArrayList<String> getExtraDocuments() {
        return this.extraDocuments;
    }
}

