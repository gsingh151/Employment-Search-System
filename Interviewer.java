import java.io.Serializable;
import java.util.Scanner;
import java.util.ArrayList;


class Interviewer extends User implements Serializable {
    private int Company;
    private ArrayList<Integer> AssignedApplicants;


    Interviewer(String name1, String password1, int Company1, InterviewerManager manager, Database d) {
        super(name1, password1);
        this.Company = Company1;
        this.AssignedApplicants = new ArrayList<>();

        manager.addInterviewer(this);
        d.saveAllInterviewers(manager);
    }

    void logged_in() {
        System.out.println("Hello.");
    }

    /**
     * print a list of applicants the interviewer must interview from each job posting.
     *
     * @param cmane   CompanyManager
     * @param jmane   JobPostingManager
     * @param appmane ApplicantManager
     */
    void applicantsToInterview(CompanyManager cmane, JobPostingManager jmane, ApplicantManager appmane) {

        Company mycomp = cmane.getCompanybyID(this.Company);

        for (Integer i : mycomp.getArchives().getJobPostings()) {
            JobPosting jobpost = jmane.getJobPosting(i);
            System.out.println("Job title: " + jobpost.getTitle());
            ArrayList<Integer> ids = new ArrayList<>();

            for (Integer id : getCurrentApplicants(jobpost)) {
                if (-1 != this.AssignedApplicants.indexOf(id)) {
                    ids.add(id);
                }
            }
            this.printApplicants(ids, appmane, jobpost);

        }
    }


    /**
     * allow applicant to progress to next round. CAN ONLY recommend one applicant at a time for each round of a
     * particular job posting.
     *
     * @param cmane CompanyManager
     * @param jmane JobPostingManager
     * @param amane ApplicantManager
     */
    void recommendOrRejectApplicant(CompanyManager cmane, JobPostingManager jmane, ApplicantManager amane) {
        int jobpostid;
        int userid;
        Company mycomp = cmane.getCompanybyID(this.Company);
        printJobpostings(cmane, jmane);

        while (true) {
            System.out.println("Type the name of the jobposting you want to access... or cancel to go back");
            Scanner console = new Scanner(System.in);
            String name = console.next();
            if (name.equalsIgnoreCase("cancel")) {
                return;
            }
            jobpostid = this.searchPost(name, mycomp, jmane);
            if (jobpostid == -1) {
                System.out.println("Invalid input. Try again.");
            } else {
                break;
            }
        }

        JobPosting jobpost = jmane.getJobPosting(jobpostid);
        this.printApplicants(getCurrentApplicants(jobpost), amane, jobpost);

        while (true) {
            System.out.println("Type the name of the applicants you want to recommend or reject... or cancel to go back");
            Scanner console = new Scanner(System.in);
            String username = console.next();
            if (username.equalsIgnoreCase("cancel")) {
                return;
            }
            userid = searchUser(username, jobpostid, jmane, amane);
            if (userid == -1) {
                System.out.println("Invalid input. Try again.");
            } else {
                break;
            }
        }


        while (true) {
            System.out.println("Type recommend or reject... or cancel to go back");
            Scanner console = new Scanner(System.in);
            String decision = console.next();
            if (decision.equalsIgnoreCase("cancel")) {
                System.out.println("Action canceled.");
                return;
            }
            if (decision.equalsIgnoreCase("recommend")) {
                goToNextRoundOrNot(jobpost, userid, true, amane);
                break;
            } else if (decision.equalsIgnoreCase("reject")) {
                goToNextRoundOrNot(jobpost, userid, false, amane);
                break;
            }
            System.out.println("Invalid input. Try again.");
        }

        System.out.println("Recommended/Rejected successfully");
    }


    void assignApplicant(int id) {
        this.AssignedApplicants.add(id);
    }

    //------------------Helper---------------------
    private void printJobpostings(CompanyManager cmane, JobPostingManager jmane) {
        Company mycomp = cmane.getCompanybyID(this.Company);

        for (Integer i : mycomp.getArchives().getJobPostings()) {
            System.out.println("Job title: " + jmane.getJobPosting(i).getTitle());
        }
    }

    private void printApplicants(ArrayList<Integer> ids, ApplicantManager amane, JobPosting post) {
        for (Integer i : ids) {
            for (Applicant a : amane.getApplicants()) {
                if (a.getId() == i) {
                    System.out.println("Applicant info: ");
                    System.out.println("-" + a.getUserName());

                    System.out.println("Application info: ");
                    System.out.println("-CV: " + a.getCV());
                    System.out.println("-Reference letters: ");
                    a.printReferenceLetters();
                    System.out.println("-Cover Letter: ");
                    System.out.println(this.printCoverLetterOfThis(post, a));
                    System.out.println("-Extra documents: ");
                    printExtraOfThis(post, a);
                }
            }
        }
    }

    private String printCoverLetterOfThis(JobPosting post, Applicant a) {
        for (Application app : post.getCurrentApplications()) {
            if (app.getApplicant_id() == a.getId()) {
                return app.getCover_letter();
            }
        }
        return "Cover letter not found.";
    }

    private void printExtraOfThis(JobPosting post, Applicant a) {
        for (Application app : post.getCurrentApplications()) {
            if (app.getApplicant_id() == a.getId()) {
                for (String string : app.getExtraDocuments()) {
                    System.out.println(string);
                }
            }
        }
    }

    private int searchPost(String name, Company mycomp, JobPostingManager jmane) {
        for (Integer i : mycomp.getArchives().getJobPostings()) {
            if (name.equals(jmane.getJobPosting(i).getTitle())) {
                return i;
            }
        }
        return -1;
    }

    private int searchUser(String username, int jobpostid, JobPostingManager jmane, ApplicantManager amane) {
        JobPosting post = jmane.getJobPosting(jobpostid);
        for (Integer i : getCurrentApplicants(post)) {
            if (amane.hasApplicant(username)) {
                if (i == (amane.getApplicant(username).getId())) {
                    return i;
                }
            }
        }
        return -1;
    }

    private ArrayList<Integer> getCurrentApplicants(JobPosting post) {
        ArrayList<Integer> currentApplicantsId = new ArrayList<>();
        for (Application a : post.getCurrentApplications()) {
            currentApplicantsId.add(a.getApplicant_id());
        }
        return currentApplicantsId;
    }

    private void goToNextRoundOrNot(JobPosting post, int userid, boolean go, ApplicantManager amane) {
        ArrayList<Application> applicationList = post.getCurrentApplications();
        boolean rejected = false;
        Application application = null;
        for (Application a : applicationList) {
            if (a.getApplicant_id() == userid) {
                application = a;
                if (go) {
                    a.acceptedToNextRound(amane, post);
                } else {
                    a.rejected(amane, post);
                    rejected = true;
                }
                break;
            }
        }

        if (rejected) {
            Applicant applicant = amane.getApplicant(userid);
            applicant.getHistory().closeApplication(post.getTitle(), post.getId());
            applicant.getApplications().remove(application);
            post.removeApplicant(userid);
        }
    }
}
