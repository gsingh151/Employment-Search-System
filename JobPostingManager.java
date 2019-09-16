import java.io.Serializable;
import java.util.ArrayList;

public class JobPostingManager implements Serializable {

    private ArrayList<JobPosting> JobPostings;

    JobPostingManager(ArrayList<JobPosting> a) {
        this.JobPostings = a;
    }

    JobPosting getJobPosting(int id) {
        for (JobPosting a : this.JobPostings) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    void addJobPosting(JobPosting a) {
        this.JobPostings.add(a);
    }

    ArrayList<JobPosting> getJobPostings() {
        return this.JobPostings;
    }

    void printPostings() {
        for (JobPosting jp : this.JobPostings) {
            System.out.println(jp);
        }
    }


    /**
     * @param filter represents the filter
     *               Iterates through all Job Postings and prints out only those that pass through the filter
     */
    void printFilteredPostings(String filter) {
        for (JobPosting jp : this.JobPostings) {
            if (jp.passesFilter(filter)) {
                System.out.println("-----------------------------");
                System.out.println(jp);
            }
        }
    }
}
