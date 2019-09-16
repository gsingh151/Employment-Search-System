import java.util.Scanner;

public class ApplicantInterface extends UserInterface {

    ApplicantManager manager;
    JobPostingManager jp;

    ApplicantInterface(Database d) {
        manager = new ApplicantManager(d.getApplicants());
        jp = new JobPostingManager(d.getAllJobPostings());
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
        while (!manager.hasApplicant(input)) {
            System.out.println("This username does not exist. Please try again: (Type cancel to cancel action)");
            input = console.next();
            if (verify.cancel(input)) {
                this.cancel(d);
                return;
            }
        }
        Applicant a = manager.getApplicant(input);
        System.out.println("Please enter your password:");
        input = verify.attemptPassword(a);
        if (verify.cancel(input)) {
            return;
        }
        System.out.println("Successfully logged in.");
        a.logged_in(jp);
        this.MainMenu(d, a, jp, manager);
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
        System.out.println("Please enter your password:");
        String password = verify.getValidPassword();
        Applicant a = new Applicant(name, password, manager, d);
        System.out.println("Account created. Please note that your username is: " + a.getUserName());
        System.out.println("Also note that your userID is: " + a.getId());
        System.out.println("Logging in...");
        a.logged_in(jp);
        this.MainMenu(d, a, jp, manager);
    }

    /**
     * Allows user to do certain actions based on user type
     */
    private void MainMenu(Database d, Applicant a, JobPostingManager jp, ApplicantManager manager) {
        d.saveAllApplicants(manager);
        d.saveAllJobPostings(jp);
        VerifyInput verify = new VerifyInput();
        System.out.println("===Main Menu===");
        System.out.println();
        System.out.println("What would you like to do today?");
        System.out.println("1: View current applications, 2: Apply for a job");
        System.out.println("3: Withdraw from an application, 4: View your history");
        System.out.println("5: Search job postings by tags, 6: Mailbox, 7: logout");
        int input = verify.getValidChoice(7);
        switch (input) {
            case 1:
                a.viewCurrentApplications(jp);
                break;
            case 2:
                a.apply(jp);
                break;
            case 3:
                a.withdraw(jp);
                break;
            case 4:
                a.viewHistory();
                break;
            case 5:
                a.searchJobPostingByTags(jp);
                break;
            case 6:
                a.mailOption();
                break;
            case 7:
                Main.Welcome(d);
                return;
        }
        this.MainMenu(d, a, jp, manager);
    }

    // cancel action
    private void cancel(Database d) {
        System.out.println("Action canceled.");
        Main.Welcome(d);
    }
}
