public class UserHandler {

    UserHandler(){}

    // Ask for information when user pick login
    void login(Database d) {
        VerifyInput verify = new VerifyInput();
        System.out.println("Please select the type of account that you have or 6 to go back:(1: Applicant, 2: Interviewer, 3: HRCoordinator, 4: Company, 5: Referee)");
        int input = verify.getValidChoice(6);
        if (input == 6) {
            System.out.println("Canceled.");
            Main.Welcome(d);
        }
        switch (input) {
            case 1:
                ApplicantInterface ui = new ApplicantInterface(d);
                ui.login(d);
                break;
            case 2:
                InterviewerInterface ui2 = new InterviewerInterface(d);
                ui2.login(d);
                break;
            case 3:
                HRCoordinatorInterface ui3 = new HRCoordinatorInterface(d);
                ui3.login(d);
                break;
            case 4:
                CompanyInterface ui4 = new CompanyInterface(d);
                ui4.login(d);
                break;
            case 5:
                RefereeInterface ui5 = new RefereeInterface(d);
                ui5.login(d);
                break;
        }
    }

    // Ask for information when creating new account
    void createAccount(Database d) {
        VerifyInput verify = new VerifyInput();
        System.out.println("Please select the type of account that you want to create or 6 to go back: (1: Applicant, 2: Interviewer, 3: HRCoordinator, 4: Company, 5: Referee)");
        int input = verify.getValidChoice(6);
        if (input == 6) {
            System.out.println("Canceled.");
            Main.Welcome(d);
        }
        switch (input) {
            case 1:
                ApplicantInterface ui = new ApplicantInterface(d);
                ui.newAccount(d);
                break;
            case 2:
                InterviewerInterface ui2 = new InterviewerInterface(d);
                ui2.newAccount(d);
                break;
            case 3:
                HRCoordinatorInterface ui3 = new HRCoordinatorInterface(d);
                ui3.newAccount(d);
                break;
            case 4:
                CompanyInterface ui4 = new CompanyInterface(d);
                ui4.newAccount(d);
                break;
            case 5:
                RefereeInterface ui5 = new RefereeInterface(d);
                ui5.newAccount(d);
                break;
        }
    }
}
