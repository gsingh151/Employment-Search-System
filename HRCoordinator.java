import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

class HRCoordinator extends User implements Serializable {
    private int company;

    HRCoordinator(String name1, String password1) {
        super(name1, password1);
    }

    HRCoordinator(String name1, String password1, int Company1, HRCoordinatorManager manager, Database d) {
        super(name1, password1);
        this.company = Company1;

        manager.addCoordinator(this);
        d.saveAllHRCoordinators(manager);

    }

    /**
     * Method called when HRCoordinator logs in. Checks if there are any job postings that must be manually filled and
     * then prompts the user to fill it.
     */
    void logged_in(CompanyManager c, JobPostingManager j, ApplicantManager a) {
        ArrayList<JobPosting> jobPostings = j.getJobPostings();
        for (JobPosting jobPosting : jobPostings) {
            if (jobPosting.getShouldManuallyFill()) {
                jobPosting.manuallyFill(a);
            }
        }
    }

    /**
     * Assign interviewers for a closed job posting. This action cannot be canceled.
     */
    void assignInterviewers(JobPostingManager jpm, ApplicantManager a, InterviewerManager interviewerManager, CompanyManager companyManager) {
        // assign interviewers from this company to all the applicants of a certain job posting at this company.
        JobPosting jobPosting = this.selectClosedPosting(jpm, companyManager);
        if (jobPosting == null) {
            return;
        }
        CompanyArchives archives = companyManager.getCompanybyID(company).getArchives();
        VerifyInput verifyInput = new VerifyInput();
        archives.printInterviewers(interviewerManager);
        for (Application application : jobPosting.getCurrentApplications()) {
            application.read(a);
            System.out.println("Enter an Interviewer's ID to assign to this application or -1 to go back.");
            int interviewer = verifyInput.getValidNumber();
            if (interviewer == -1) {
                return;
            }
            while (!archives.hasInterviewer(interviewer)) {
                System.out.println("Your company does not have that interviewer/ interviewer does not exist.");
                interviewer = verifyInput.getValidNumber();
                if (interviewer == -1) {
                    return;
                }
            }
            Interviewer interviewer1 = interviewerManager.getInterviewer(interviewer);
            interviewer1.assignApplicant(application.getId());
            System.out.println("Interviewer assigned to this application.");
        }
        System.out.println("All applications have been assigned to an interviewer.");
    }

    /**
     * Access applicant information for all applications in one job posting
     */
    void accessApplicantInfo(ApplicantManager a, JobPostingManager b, CompanyManager companyManager) {
        // access the information of an applicant (CV from applicant as well as cover letter).
        VerifyInput verify = new VerifyInput();
        Company company = companyManager.getCompanybyID(this.company);
        CompanyArchives archives = company.getArchives();
        ArrayList<Integer> Jps = new ArrayList<>();
        for (JobPosting jobPosting : b.getJobPostings()) {
            if (archives.hasJobPosting(jobPosting.getId())) {
                Jps.add(jobPosting.getId());
                System.out.println(jobPosting);
            }
        }
        System.out.println("Please enter id for the job posting to access its current applicants' information or -1 to go back.");
        int input = verify.getValidNumber();
        if (input == -1) {
            return;
        }
        while (!Jps.contains(input)) {
            System.out.println("This job posting is not made by your company/ does not exist." +
                    " Please try again.");
            input = verify.getValidNumber();
            if (input == -1) {
                return;
            }
        }
        JobPosting jobPosting = b.getJobPosting(input);
        jobPosting.readAllApplications(a);
    }

    /**
     * Access applicant information for the hired applicant of a filled job posting
     */
    void accessHiredApplicantInfo(ApplicantManager manager, JobPostingManager jobPostingManager, CompanyManager companyManager) {
        VerifyInput verify = new VerifyInput();
        Company company = companyManager.getCompanybyID(this.company);
        CompanyArchives archives = company.getArchives();
        ArrayList<Integer> Jps = new ArrayList<>();
        for (JobPosting jobPosting : jobPostingManager.getJobPostings()) {
            if (archives.hasJobPosting(jobPosting.getId()) && jobPosting.isFilled()) {
                Jps.add(jobPosting.getId());
                System.out.println(jobPosting);
            }
        }
        System.out.println("Please enter id for the job posting to access its hired applicant's information or -1 to go back.");
        int input = verify.getValidNumber();
        if (input == -1) {
            return;
        }
        while (!Jps.contains(input)) {
            System.out.println("This job posting is not a valid choice." +
                    " Please try again.");
            input = verify.getValidNumber();
            if (input == -1) {
                return;
            }
        }
        System.out.println("Selected job posting. Printing hired applicant information.");
        JobPosting jobPosting = jobPostingManager.getJobPosting(input);
        Application application = jobPosting.getApplicant(jobPosting.getHired_applicant());
        application.read(manager);
    }

    /**
     * uses user input to return the selected closed job posting. Return null if there are no open job postings.
     */
    private JobPosting selectClosedPosting(JobPostingManager jpm, CompanyManager companyManager) {
        VerifyInput verify = new VerifyInput();
        Company company = companyManager.getCompanybyID(this.company);
        CompanyArchives archives = company.getArchives();
        if (!archives.hasOpenPostings(jpm)) {
            System.out.println("Your company does not have any open job postings at the moment.");
            return null;
        }
        archives.printOpenPostings(jpm);
        System.out.println("Please enter job ID for which you want to assign interviewers to or -1 to go back");
        int input = verify.getValidNumber();
        if (input == -1) {
            return null;
        }
        while (!archives.hasJobPosting(input)) {
            System.out.println("Job posting not found. Please try again or -1 to go back.");
            input = verify.getValidNumber();
            if (input == -1) {
                return null;
            }
        }
        return jpm.getJobPosting(input);
    }

}
