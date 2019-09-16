import java.util.Scanner;

public class InterviewerInterface {

    private InterviewerManager manager;
    private CompanyManager c;
    private JobPostingManager jp;
    private ApplicantManager am;

    InterviewerInterface(Database d) {
        manager = new InterviewerManager(d.getInterviewers());
        c = new CompanyManager(d.getCompanies());
        jp = new JobPostingManager(d.getAllJobPostings());
        am = new ApplicantManager(d.getApplicants());
    }

    /**
     * Logs in and redirect user to main menu
     */
    void login(Database d) {
        Scanner console = new Scanner(System.in);
        VerifyInput verify = new VerifyInput();
        System.out.println("Please enter your username or cancel to stop:");
        String input = console.next();
        if (verify.cancel(input)) {
            this.cancel(d);
            return;
        }
        while (!manager.hasInterviewer(input)) {
            System.out.println("This username does not exist. Please try again: (Type cancel to cancel action)");
            input = console.next();
            if (verify.cancel(input)) {
                this.cancel(d);
                return;
            }
        }
        Interviewer a = manager.getInterviewer(input);
        System.out.println("Please enter your password:");
        input = verify.attemptPassword(a);
        if (verify.cancel(input)) {
            return;
        }
        System.out.println("Successfully logged in.");
        a.logged_in();
        this.MainMenu(d, a, am, jp, c, manager);
    }

    /**
     * Create a new account and redirect user to main menu
     */
    void newAccount(Database d) {
        Scanner console = new Scanner(System.in);
        VerifyInput verify = new VerifyInput();
        System.out.println("Please enter your first name or cancel to stop:");
        String name = console.next();
        if (verify.cancel(name)) {
            this.cancel(d);
            return;
        }
        System.out.println("Please enter your password (must be at least 3 characters long):");
        String password = verify.getValidPassword();
        System.out.println("Please enter your company id:");
        int c_id;
        boolean br = true;
        Interviewer a = null;
        while (br) {
            c_id = verify.getValidNumber();
            if (c.hasCompany(c_id)) {
                br = false;
                a = new Interviewer(name, password, c_id, manager, d);
                Company company = c.getCompanybyID(c_id);
                company.getArchives().recordInterviewer(a);
            } else {
                System.out.println("Company does not exist, please try again.");
            }
        }
        System.out.println("Account created. Please note that your username is: " + a.getUserName());
        System.out.println("Also note that your userID is: " + a.getId());
        System.out.println("Logging in...");
        a.logged_in();
        this.MainMenu(d, a, am, jp, c, manager);
    }

    /**
     * Allows user to do certain actions based on user type
     */
    private void MainMenu(Database d, Interviewer a, ApplicantManager am, JobPostingManager jp, CompanyManager c, InterviewerManager manager) {
        d.saveAllInterviewers(manager);
        d.saveAllApplicants(am);
        d.saveAllJobPostings(jp);
        d.saveAllCompanies(c);
        VerifyInput verify = new VerifyInput();
        System.out.println("===Main Menu===");
        System.out.println();
        System.out.println("What would you like to do today?");
        System.out.println("1: View all applicants to interview, 2: Recommend or reject applicant");
        System.out.println("3: logout");
        int input = verify.getValidChoice(3);
        switch (input) {
            case 1:
                a.applicantsToInterview(c, jp, am);
                break;
            case 2:
                a.recommendOrRejectApplicant(c, jp, am);
                break;
            case 3:
                Main.Welcome(d);
                return;
        }
        this.MainMenu(d, a, am, jp, c, manager);
    }

    // cancel action
    private void cancel(Database d) {
        System.out.println("Action canceled.");
        Main.Welcome(d);
    }
}
