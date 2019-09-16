import java.io.Serializable;
import java.util.ArrayList;

public class CompanyArchives implements Serializable {
    private ArrayList<Integer> JobPostings;
    private ArrayList<Integer> HRCoordinators;
    private ArrayList<Integer> Interviewers;

    CompanyArchives() {
        this.JobPostings = new ArrayList<>();
        this.HRCoordinators = new ArrayList<>();
        this.Interviewers = new ArrayList<>();
    }

    CompanyArchives(ArrayList<Integer> jp, ArrayList<Integer> hr, ArrayList<Integer> i) {
        this.JobPostings = jp;
        this.HRCoordinators = hr;
        this.Interviewers = i;
    }

    // add the JobPosting to the Company's archives
    void recordJobPosting(JobPosting jp) {
        this.JobPostings.add(jp.getId());
    }

    // register an HRCoordinator into the Company's archives
    void recordHRCoordinator(HRCoordinator hr) {
        this.HRCoordinators.add(hr.getId());
    }

    // register an Interviewer into the Company's archives
    void recordInterviewer(Interviewer i) {
        this.Interviewers.add(i.getId());
    }

    // returns true only if id is contained in this.JobPostings
    boolean hasJobPosting(Integer id) {
        return this.JobPostings.contains(id);
    }

    boolean hasInterviewer(Integer id) {
        return this.Interviewers.contains(id);
    }

    void printOpenPostings(JobPostingManager jobPostingManager) {
        for (Integer x : this.JobPostings) {
            JobPosting jobPosting = jobPostingManager.getJobPosting(x);
            if (jobPosting.isOpen()) {
                System.out.println(jobPosting);
            }
        }
    }

    void printInterviewers(InterviewerManager interviewerManager) {
        for (Integer x : this.Interviewers) {
            Interviewer interviewer = interviewerManager.getInterviewer(x);
            System.out.println("-------------------------------");
            System.out.println("Interviewer id: " + interviewer);
        }
    }

    boolean hasOpenPostings(JobPostingManager jobPostingManager) {
        for (Integer x : this.JobPostings) {
            JobPosting jobPosting = jobPostingManager.getJobPosting(x);
            if (jobPosting.isOpen()) {
                return true;
            }
        }
        return false;
    }

    // getter methods
    ArrayList<Integer> getJobPostings() {
        return this.JobPostings;
    }
}
