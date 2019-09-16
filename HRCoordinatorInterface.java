import javax.xml.crypto.Data;
import java.util.Scanner;

public class HRCoordinatorInterface {
    private HRCoordinatorManager manager;
    private CompanyManager c;
    private JobPostingManager jp;
    private InterviewerManager i;
    private ApplicantManager a1;

    HRCoordinatorInterface(Database d) {
        manager = new HRCoordinatorManager(d.getCoordinators());
        c = new CompanyManager(d.getCompanies());
        jp = new JobPostingManager(d.getAllJobPostings());
        i = new InterviewerManager(d.getInterviewers());
        a1 = new ApplicantManager(d.getApplicants());
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
        while (!manager.hasCoordinator(input)) {
            System.out.println("This username does not exist. Please try again: (Type cancel to cancel action)");
            input = console.next();
            if (verify.cancel(input)) {
                this.cancel(d);
                return;
            }
        }
        HRCoordinator a = manager.getCoordinator(input);
        System.out.println("Please enter your password:");
        input = verify.attemptPassword(a);
        if (verify.cancel(input)) {
            return;
        }
        System.out.println("Successfully logged in.");
        a.logged_in(c, jp, a1);
        this.MainMenu(d, a, i, jp, a1, manager);
    }

    /**
     * Create a new account and redirect user to main menu
     */
    void newAccount(Database d) {
        HRCoordinatorManager manager = new HRCoordinatorManager(d.getCoordinators());
        CompanyManager c = new CompanyManager(d.getCompanies());
        JobPostingManager jp = new JobPostingManager(d.getAllJobPostings());
        InterviewerManager i = new InterviewerManager(d.getInterviewers());
        ApplicantManager a1 = new ApplicantManager(d.getApplicants());
        Scanner console = new Scanner(System.in);
        VerifyInput verify = new VerifyInput();
        System.out.println("Please enter your first name cancel to stop:");
        String name = console.next();
        if (verify.cancel(name)) {
            this.cancel(d);
            return;
        }
        System.out.println("Please enter your password:");
        String password = verify.getValidPassword();
        System.out.println("Please enter the id of your company:");
        int c_id;
        boolean br = true;
        HRCoordinator a = null;
        while (br) {
            c_id = verify.getValidNumber();
            if (c.hasCompany(c_id)) {
                br = false;
                Company company = c.getCompanybyID(c_id);
                a = new HRCoordinator(name, password, c_id, manager, d);
                company.getArchives().recordHRCoordinator(a);
            } else {
                System.out.println("Company does not exist, please try again.");
            }
        }
        System.out.println("Account created. Please note that your username is: " + a.getUserName());
        System.out.println("Also note that your userID is: " + a.getId());
        System.out.println("Logging in...");
        a.logged_in(c, jp, a1);
        this.MainMenu(d, a, i, jp, a1, manager);
    }

    /**
     * Allows user to do certain actions based on user type
     */
    private void MainMenu(Database d, HRCoordinator a, InterviewerManager i, JobPostingManager jp, ApplicantManager a1, HRCoordinatorManager manager) {
        d.saveAllHRCoordinators(manager);
        d.saveAllInterviewers(i);
        d.saveAllJobPostings(jp);
        d.saveAllApplicants(a1);
        VerifyInput verify = new VerifyInput();
        CompanyManager companyManager = new CompanyManager(d.getCompanies());
        System.out.println("===Main Menu===");
        System.out.println();
        System.out.println("What would you like to do today?");
        System.out.println("1: Assign interviewers to applicants for a job posting, 2: View applicant info for all applicants in a job posting");
        System.out.println("3: View a hired applicant's info, 4: logout");
        int input = verify.getValidChoice(4);
        switch (input) {
            case 1:
                a.assignInterviewers(jp, a1, i, companyManager);
                break;
            case 2:
                a.accessApplicantInfo(a1, jp, companyManager);
                break;
            case 3:
                a.accessHiredApplicantInfo(a1, jp, companyManager);
                break;
            case 4:
                Main.Welcome(d);
                return;
        }
        this.MainMenu(d, a, i, jp, a1, manager);
    }

    // cancel action
    private void cancel(Database d) {
        System.out.println("Action canceled.");
        Main.Welcome(d);
    }
}
