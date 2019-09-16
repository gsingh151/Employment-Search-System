
import java.io.*;
import java.util.*;

class Applicant extends User implements Serializable {
    private Date last_online;
    private String CV;
    private ApplicantHistory History;
    private ArrayList<File> letters;
    private ArrayList<Application> applications;
    private Mailbox mailbox;

    Applicant(String name1, String password1, ApplicantManager manager, Database d) {
        super(name1, password1);
        this.CV = "";
        this.History = new ApplicantHistory();
        this.letters = new ArrayList<>();
        this.applications = new ArrayList<>();
        this.mailbox = new Mailbox();

        manager.addApplicant(this);
        d.saveAllApplicants(manager);
    }

    Applicant(String name1, String password1, Date last_online1, String CV1, ApplicantHistory History1) {
        super(name1, password1);
        this.CV = CV1;
        this.History = History1;
        this.letters = new ArrayList<>();
        this.applications = new ArrayList<>();
        this.mailbox = new Mailbox();
    }

    /**
     * Method triggered when applicant logs in. Notifies user of all closed and filled job postings
     */
    void logged_in(JobPostingManager jb) {
        ArrayList<JobPosting> jobPostings = jb.getJobPostings();
        ArrayList<String> curr = History.getCurrentJobs();
        for (String i : curr) {
            for (JobPosting j : jobPostings) {
                if (j.getTitle().equalsIgnoreCase(i)) {
                    if (j.getStatus().equalsIgnoreCase("Closed")) {
                        System.out.println(j.getTitle() + " is closed.");
                    } else if (j.getStatus().equalsIgnoreCase("Filled")) {
                        System.out.println(j.getTitle() + " is filled.");
                    }
                }
            }

        }
    }

    /**
     * Allows applicant to view current applications in the program
     */
    void viewCurrentApplications(JobPostingManager jb) {
        // print all current applications and their status
        int id = 0;
        ArrayList<JobPosting> jobPostings = jb.getJobPostings();
        ArrayList<String> curr = History.getCurrentJobs();
        System.out.println("Current jobs: ");
        for (String i : curr) {
            for (JobPosting j : jobPostings) {
                if (j.getTitle().equalsIgnoreCase(i)) {
                    id = j.getId();
                    break;
                }
            }
            System.out.println(i);
            JobPosting job = jb.getJobPosting(id);
            System.out.println("Status: " + job.getStatus());
        }
    }

    /**
     * Apply to a job posting
     */
    void apply(JobPostingManager jb) {
        // find a job posting and apply
        ArrayList<String> curr = this.History.getCurrentJobs();
        ArrayList<Integer> current = new ArrayList<>();
        int count = 0;
        for (String i : curr) {
            String[] splited = i.split("\\s+");
            for (String j : splited) {
                if (count == 1) {
                    count = 0;
                    current.add(Integer.parseInt(j));
                }
                count++;
            }
        }

        System.out.println("Choose from jobs below or enter -1 to go back:");
        jb.printPostings();
        ArrayList<JobPosting> jobPostings = jb.getJobPostings();
        VerifyInput verifyInput = new VerifyInput();
        int num = 0;
        boolean valid = false;
        while (!valid) {
            num = verifyInput.getValidNumber();
            if (num == -1) {
                return;
            }
            for (JobPosting j : jobPostings) {
                if (num == j.getId()) {
                    valid = true;
                }
            }
        }

        if (current.contains(num)) {
            System.out.println("Cannot apply twice.");
            return;
        }
        System.out.println("Enter your cover:");
        Scanner input = new Scanner(System.in);
        String cover = input.nextLine();
        System.out.println("Enter your CV:");
        this.CV = input.nextLine();
        ArrayList<String> extras = new ArrayList<>();
        System.out.println("Enter extra documents if there is any: or none to skip");
        String str = input.nextLine();
        while (!str.equalsIgnoreCase("none")) {
            extras.add(str);
            System.out.println("Enter extra documents if there is any: or none to skip");
            str = input.nextLine();
        }
        Application application = new Application(this, cover, extras);
        applications.add(application);
        for (JobPosting i : jobPostings) {
            if (i.getId() == num) {
                if (i.getStatus().equalsIgnoreCase("Open")) {
                    this.History.apply(i.getTitle(), i.getId());
                    i.addApplicant(application);
                } else {
                    System.out.println("Cannot apply.");
                }
            }
        }


    }

    /**
     * Withdraw from a job posting
     */
    void withdraw(JobPostingManager jp) {
        // withdraw from a particular application.
        System.out.println("Enter the id for the job you want to withdraw or -1 to go back:");
        ArrayList<String> curr = this.History.getCurrentJobs();
        ArrayList<JobPosting> jobPostings = jp.getJobPostings();
        if (curr.size() == 0) {
            System.out.println("No current applications.");
            return;
        }

        HashMap<String, Integer> jobs = new HashMap<>();
        String title = "";
        int id = 0;
        int count = 0;
        VerifyInput verifyInput = new VerifyInput();
        for (String i : curr) {
            String[] splited = i.split("\\s+");
            for (String e : splited) {
                if (count == 0) {
                    title = e;
                    System.out.println("Job title: " + title);
                    count++;
                } else {
                    id = Integer.parseInt(e);
                    System.out.println("Job id: " + id);
                    count--;
                }
            }
            jobs.put(title, id);
        }
        Collection<Integer> job_ids = jobs.values();

        int remove_id = verifyInput.getValidNumber();

        if (remove_id == -1) {
            return;
        }

        while (!(job_ids.contains(remove_id))) {
            remove_id = verifyInput.getValidNumber();
        }
        Application temp = null;
        JobPosting jtemp = jobPostings.get(0); // this is to avoid a warning about producing a NullPointerException. The correct job posting WILL be found in this loop
        for (JobPosting j : jobPostings) {
            if (j.getId() == remove_id) {
                ArrayList<Application> ap = j.getCurrentApplications();
                jtemp = j;
                for (Application a : ap) {

                    if (a.getApplicant_id() == this.getId()) {
                        temp = a;
                    }
                }
            }
        }
        this.History.closeApplication(jtemp.getTitle(), jtemp.getId());
        this.applications.remove(temp);
        jtemp.removeApplicant(this.getId());


    }

    /**
     * View applicant's history
     */
    void viewHistory() {
        // view the history of the applicant
        if (this.History == null) {
            System.out.println("No history available.");
        } else {
            ArrayList<String> past = History.getPastJobs();
            ArrayList<String> curr = History.getCurrentJobs();
            System.out.println("Past jobs: ");
            for (String i : past) {
                System.out.println(i);
            }
            System.out.println("Current jobs: ");
            for (String i : curr) {
                System.out.println(i);
            }
        }
    }

    void addLetter(File file) {
        this.letters.add(file);
    }

    String getCV() {
        return this.CV;
    }

    ApplicantHistory getHistory() {
        return this.History;
    }

    void printReferenceLetters() {
        try {

            for (File file : this.letters) {
                BufferedReader reader = new BufferedReader(new FileReader(file.getName()));
                String line = reader.readLine();
                while (line != null) {
                    System.out.println(line);
                    line = reader.readLine();
                }
                reader.close();
            }
        } catch (IOException e) {
            System.out.println("IO Exception.");
        }
    }

    void mailOption() {
        this.mailbox.read();
    }

    /**
     * Use filters to search for job postings
     */
    void searchJobPostingByTags(JobPostingManager jmane) {
        System.out.println("Type the word you want to search by or cancel to go back:");
        Scanner console = new Scanner(System.in);
        String word = console.next();
        if (word.equalsIgnoreCase("cancel")) {
            return;
        }
        jmane.printFilteredPostings(word);
    }

    Mailbox getMailbox() {
        return this.mailbox;
    }

    ArrayList<Application> getApplications() {
        return this.applications;
    }
}

