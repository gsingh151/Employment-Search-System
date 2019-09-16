
import java.io.File;
import java.io.Serializable;
import java.util.*;

class Referee extends User implements Serializable {

    private HashMap<Applicant, ArrayList<File>> referenceLetter;


    Referee(String name, String password, RefereeManager manager, Database d) {
        super(name, password);
        referenceLetter = new HashMap<>();
        manager.addReferee(this);
        d.saveAllReferees(manager);
    }

    HashMap<Applicant, ArrayList<File>> getReferenceLetter() {
        return this.referenceLetter;
    }

    /**
     * upload a file as a reference letter
     */
    void upLoadReferenceLetter(Database database) {
        try {
            Scanner input = new Scanner(System.in);
            System.out.println("Enter the applicant id that you recommend or -1 to go back:");
            int id = input.nextInt();
            if (id == -1) {
                return;
            }

            System.out.println("Enter your file path of the reference letter or cancel to go back:");
            String str = input.next();
            if (str.equalsIgnoreCase("cancel")) {
                return;
            }

            File file = new File(str);
            for (Map.Entry<Applicant, ArrayList<File>> entry : this.referenceLetter.entrySet()) {
                if (entry.getKey().getId() == id) {
                    entry.getValue().add(file);
                    entry.getKey().addLetter(file);
                    return;
                }
            }
            ArrayList<File> arrayList = new ArrayList<>();
            arrayList.add(file);
            ArrayList<Applicant> applicants = database.getApplicants();
            for (Applicant applicant : applicants) {
                if (applicant.getId() == id) {
                    applicant.addLetter(file);
                    this.referenceLetter.put(applicant, arrayList);
                    return;
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Wrong input please try again......");
            upLoadReferenceLetter(database);
        }
    }

    void seeDueDatesOfJobs(JobPostingManager jp) {
        ArrayList<JobPosting> arrayList = jp.getJobPostings();
        for (JobPosting jobPosting : arrayList) {
            System.out.println(jobPosting.getTitle() + " : " + jobPosting.getClosingDate());
        }
    }

}
