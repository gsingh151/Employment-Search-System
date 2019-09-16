import java.io.Serializable;
import java.util.Scanner;

class RefereeInterface extends UserInterface implements Serializable {

    private RefereeManager manager;
    private JobPostingManager jobPostingManager;

    RefereeInterface(Database d) {
        manager = new RefereeManager(d.getReferees());
        jobPostingManager = new JobPostingManager(d.getAllJobPostings());
    }

    /**
     * Logs in and redirect user to main menu
     */
    void login(Database d) {
        Scanner console = new Scanner(System.in);
        VerifyInput verify = new VerifyInput();
        System.out.println("Please enter your username or type cancel to cancel action");
        String input = console.next();
        if (verify.cancel(input)) {
            this.cancel(d);
            return;
        }
        while (!manager.hasReferee(input)) {
            System.out.println("This username does not exist. Please try again: (Type cancel to cancel action)");
            input = console.next();
            if (verify.cancel(input)) {
                this.cancel(d);
                return;
            }
        }
        Referee referee = manager.getRefereeByUsername(input);
        System.out.println("Please enter your password:");
        input = verify.attemptPassword(referee);
        if (verify.cancel(input)) {
            return;
        }
        System.out.println("Successfully logged in.");
        this.MainMenu(d, referee, manager, jobPostingManager);

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
        Referee referee = new Referee(name, password, manager, d);
        System.out.println("Account created. Please note that your username is: " + referee.getUserName());
        System.out.println("Also note that your userID is: " + referee.getId());
        System.out.println("Logging in...");

        this.MainMenu(d, referee, manager, jobPostingManager);
    }

    /**
     * Allows user to do certain actions based on user type
     */
    private void MainMenu(Database d, Referee r, RefereeManager refereeManager, JobPostingManager jp) {
        d.saveAllReferees(refereeManager);
        VerifyInput verify = new VerifyInput();
        System.out.println("===Main Menu===");
        System.out.println();
        System.out.println("What would you like to do today?");
        System.out.println("1: Upload a reference letter for a applicant, 2: See due dates of jobs 3: logout");
        int input = verify.getValidChoice(3);
        switch (input) {
            case 1:
                r.upLoadReferenceLetter(d);
                break;
            case 2:
                r.seeDueDatesOfJobs(jp);
                break;
            case 3:
                Main.Welcome(d);
                return;
        }
        this.MainMenu(d, r, refereeManager, jp);

    }

    private void cancel(Database d) {
        System.out.println("Action canceled.");
        Main.Welcome(d);
    }
}
