import java.util.ArrayList;
import java.util.Scanner;

public class CompanyInterface {
    CompanyManager manager;
    JobPostingManager jp;

    CompanyInterface(Database d) {
        manager = new CompanyManager(d.getCompanies());
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
        while (!manager.hasCompany(input)) {
            System.out.println("This username does not exist. Please try again: (Type cancel to cancel action)");
            input = console.next();
            if (verify.cancel(input)) {
                this.cancel(d);
                return;
            }
        }
        Company a = manager.getCompany(input);
        System.out.println("Please enter your password:");
        input = verify.attemptPassword(a);
        if (verify.cancel(input)) {
            return;
        }
        System.out.println("Successfully logged in.");
        this.MainMenu(d, a, manager, jp);
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
        ArrayList<Integer> jobs = new ArrayList<>();
        ArrayList<Integer> hr = new ArrayList<>();
        ArrayList<Integer> interviewers = new ArrayList<>();
        Company a = new Company(name, password, jobs, hr, interviewers, manager, d);
        System.out.println("Account created. Please note that your username is: " + a.getUserName());
        System.out.println("Also note that your userID is: " + a.getId());
        System.out.println("Logging in...");
        this.MainMenu(d, a, manager, jp);
    }

    /**
     * Allows user to do certain actions based on user type
     */
    private void MainMenu(Database d, Company a, CompanyManager manager, JobPostingManager jp) {
        d.saveAllCompanies(manager);
        d.saveAllJobPostings(jp);
        VerifyInput verify = new VerifyInput();
        System.out.println("===Main Menu===");
        System.out.println();
        System.out.println("What would you like to do today?");
        System.out.println("1: Create a job posting, 2: logout");
        int input = verify.getValidChoice(2);
        switch (input) {
            case 1:
                a.makeJobPosting(jp);
                break;
            case 2:
                Main.Welcome(d);
                return;
        }
        this.MainMenu(d, a, manager, jp);
    }

    private void cancel(Database d) {
        System.out.println("Action canceled.");
        Main.Welcome(d);
    }
}
