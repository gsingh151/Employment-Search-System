import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;

class Company extends User implements Serializable {
    private CompanyArchives archives;

    Company(String name1, String password1) {
        super(name1, password1);
        this.archives = new CompanyArchives();
    }

    Company(String name1, String password1, ArrayList<Integer> jp, ArrayList<Integer> hr, ArrayList<Integer> i, CompanyManager manager, Database d) {
        super(name1, password1);
        this.archives = new CompanyArchives(jp, hr, i);
        manager.addCompany(this);
        d.saveAllCompanies(manager);
    }

    /**
     * create and record a JobPosting with information given by the Company
     */
    void makeJobPosting(JobPostingManager jp) {
        Scanner console = new Scanner(System.in);
        VerifyInput verify = new VerifyInput();
        System.out.println("Please enter the position title or cancel to go back: ");
        String title = console.nextLine();
        if (title.equalsIgnoreCase("cancel")) {
            return;
        }
        System.out.println("Please enter the closing date in the following layout (MM/dd/yyyy):");
        Date date = verify.getValidDate();
        ArrayList<String> tags = new ArrayList<>();
        String unfiltered_order = this.getOrder();
        JobPosting jp1 = new JobPosting(title, date, unfiltered_order, tags);
        verifyTags(jp1);
        this.archives.recordJobPosting(jp1);
        jp.addJobPosting(jp1);
        System.out.println("Job posting created.");
    }

    private void verifyTags(JobPosting jp) {
        System.out.println("\nEnter 1 if you like to enter tags for this posting" + "\nEnter 2 to skip");
        VerifyInput verify = new VerifyInput();
        int num = verify.getValidNumber();
        switch (num) {
            case 1:
                getTags(jp);
                break;
            case 2:
                break;
        }

    }

    private void getTags(JobPosting j) {
        System.out.println("\nEnter a one word tag all lowercase with no space" + "\nEnter 'done' to finish");
        VerifyInput verify = new VerifyInput();
        String tag = verify.getValidTag();
        if (!tag.equals("done")) {
            j.addTag(tag);
            System.out.println("Tag has been added!");
            getTags(j);
        } else {
            System.out.println("Tags have been added and saved in your jobposting!");
        }

    }

    private String getOrder() {
        System.out.println("Please enter the type of interviews that would take place as well as its order \n" +
                "(For reference, one letter stands for one interview. p = phone interview, g = group interview \n" +
                "i = one-on-one in-person interview, t = take-home test, o = other (company specific interviews that" +
                "this program cannot currently specify to the applicants).\n " +
                "Entering any other letters will be ignored.");
        Scanner console = new Scanner(System.in);
        return console.next();
    }

    // getter methods
    CompanyArchives getArchives() {
        return this.archives;
    }
}
