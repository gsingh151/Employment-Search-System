import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Database d = new Database();
        d.load_from_file_applicants();
        d.load_from_file_companies();
        d.load_from_file_coordinators();
        d.load_from_file_interviewers();
        d.load_from_file_jobposting();
        d.load_from_file_referees();
        Main.updateJobPostings(d);
        Main.Welcome(d);
    }

    /**
     * Welcomes the user and offers two options a) login, b) create a new account
     */
    static void Welcome(Database d) {
        VerifyInput verify = new VerifyInput();
        UserHandler uh = new UserHandler();
        System.out.println("Welcome.");
        System.out.println("Would you like to login using an existing account or create a new one? (1: Login, 2: Create a new account)");
        int input = verify.getValidChoice(2);
        switch (input) {
            case 1:
                uh.login(d);
                break;
            case 2:
                uh.createAccount(d);
                break;
        }
    }

    /**
     * Updates all job postings upon running the program (closes postings that need to be closed and other things)
     */
    private static void updateJobPostings(Database database) {
        ArrayList<JobPosting> jobPostings = database.getAllJobPostings();
        ApplicantManager manager = new ApplicantManager(database.getApplicants());
        for (JobPosting jobPosting : jobPostings) {
            jobPosting.updateJobPosting(manager);
        }
    }
}
