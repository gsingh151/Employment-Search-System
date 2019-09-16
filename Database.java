
import java.util.ArrayList;
import java.io.*;

public class Database {
    private ArrayList<Company> companies;
    private ArrayList<HRCoordinator> coordinators;
    private ArrayList<Interviewer> interviewers;
    private ArrayList<Applicant> applicants;
    private ArrayList<JobPosting> jobPostings;
    private ArrayList<Referee> referees;

    Database() {
        companies = new ArrayList<>();
        coordinators = new ArrayList<>();
        interviewers = new ArrayList<>();
        applicants = new ArrayList<>();
        jobPostings = new ArrayList<>();
        referees = new ArrayList<>();
    }

    void load_from_file_companies() {
        //load info from company file
        try {
            File f = new File("Companies.txt");
            if (!f.exists()) {
                f.createNewFile();
            }
            if (f.length() != 0) {
                InputStream file = new FileInputStream(f.getName());
                InputStream inputStream = new BufferedInputStream(file);
                ObjectInput input = new ObjectInputStream(inputStream);

                companies = (ArrayList<Company>) input.readObject();
                file.close();
                inputStream.close();
                input.close();
            }
        } catch (IOException e) {
            System.out.println("Loading failed. IO");
        } catch (ClassNotFoundException e1) {
            System.out.println("Loading failed. ClassNotFound");
        }
    }

    void load_from_file_interviewers() {
        //load info from interviewers file
        try {
            File f = new File("Interviewers.txt");
            if (!f.exists()) {
                f.createNewFile();
            }
            if (f.length() != 0) {
                InputStream file = new FileInputStream(f.getName());
                InputStream inputStream = new BufferedInputStream(file);
                ObjectInput input = new ObjectInputStream(inputStream);

                interviewers = (ArrayList<Interviewer>) input.readObject();
                file.close();
                inputStream.close();
                input.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Loading failed. IO");
        } catch (ClassNotFoundException e1) {
            System.out.println("Loading failed. ClassNotFound");
        }
    }

    void load_from_file_applicants() {
        //load info from applicants file
        try {
            File f = new File("Applicants.txt");
            if (!f.exists()) {
                f.createNewFile();
            }
            if (f.length() != 0) {
                InputStream file = new FileInputStream(f.getName());
                InputStream inputStream = new BufferedInputStream(file);
                ObjectInput input = new ObjectInputStream(inputStream);

                applicants = (ArrayList<Applicant>) input.readObject();
                file.close();
                inputStream.close();
                input.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Loading failed. IO");
        } catch (ClassNotFoundException e1) {
            System.out.println("Loading failed. ClassNotFound");
        }
    }

    void load_from_file_coordinators() {
        //load info from coordinators file
        try {
            File f = new File("Coordinators.txt");
            if (!f.exists()) {
                f.createNewFile();
            }
            if (f.length() != 0) {
                InputStream file = new FileInputStream(f.getName());
                InputStream inputStream = new BufferedInputStream(file);
                ObjectInput input = new ObjectInputStream(inputStream);

                coordinators = (ArrayList<HRCoordinator>) input.readObject();
                file.close();
                inputStream.close();
                input.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Loading failed. IO");
        } catch (ClassNotFoundException e1) {
            System.out.println("Loading failed. ClassNotFound");
        }
    }

    void load_from_file_jobposting() {
        //Load job postings from a file
        try {
            File f = new File("jobPostingsFile.txt");
            if (!f.exists()) {
                f.createNewFile();
            }
            if (f.length() != 0) {
                InputStream file = new FileInputStream(f.getName());
                InputStream inputStream = new BufferedInputStream(file);
                ObjectInput input = new ObjectInputStream(inputStream);

                jobPostings = (ArrayList<JobPosting>) input.readObject();
                file.close();
                inputStream.close();
                input.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Loading failed. IO");
        } catch (ClassNotFoundException e1) {
            System.out.println("Loading failed. ClassNotFound");
        }
    }

    void load_from_file_referees() {
        //Load referees from a file
        try {
            File f = new File("Referees.txt");
            if (!f.exists()) {
                f.createNewFile();
            }
            if (f.length() != 0) {
                InputStream file = new FileInputStream(f.getName());
                InputStream inputStream = new BufferedInputStream(file);
                ObjectInput input = new ObjectInputStream(inputStream);

                referees = (ArrayList<Referee>) input.readObject();
                file.close();
                inputStream.close();
                input.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Loading failed. IO");
        } catch (ClassNotFoundException e1) {
            System.out.println("Loading failed. ClassNotFound");
        }
    }


    void saveAllCompanies(CompanyManager c) {
        ArrayList<Company> companies = c.getCompanies();
        save_to_file_companies(companies);
    }

    void saveAllApplicants(ApplicantManager a) {
        ArrayList<Applicant> applicants = a.getApplicants();
        save_to_file_applicants(applicants);
    }

    void saveAllInterviewers(InterviewerManager i) {
        ArrayList<Interviewer> interviewers = i.getInterviewers();
        save_to_file_interviewers(interviewers);

    }

    void saveAllHRCoordinators(HRCoordinatorManager hr) {
        ArrayList<HRCoordinator> coordinators = hr.getCoordinators();
        save_to_file_coordinators(coordinators);

    }

    void saveAllJobPostings(JobPostingManager jp) {
        ArrayList<JobPosting> jobPostings = jp.getJobPostings();
        save_to_file_jobposting(jobPostings);
    }

    void saveAllReferees(RefereeManager r) {
        ArrayList<Referee> referees = r.getReferees();
        save_to_file_referees(referees);
    }

    void save_to_file_referees(ArrayList<Referee> list) {
        //save and update new data to file
        try {
            File f = new File("Referees.txt");
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();

            FileOutputStream file = new FileOutputStream(f.getName());
            BufferedOutputStream outputStream = new BufferedOutputStream(file);
            ObjectOutput objectOutput = new ObjectOutputStream(outputStream);
            objectOutput.writeObject(list);
            objectOutput.close();
            file.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Saving failed.");
        }
    }

    void save_to_file_companies(ArrayList<Company> list) {
        //save and update new data to file
        try {
            File f = new File("Companies.txt");
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();

            FileOutputStream file = new FileOutputStream(f.getName());
            BufferedOutputStream outputStream = new BufferedOutputStream(file);
            ObjectOutput objectOutput = new ObjectOutputStream(outputStream);
            objectOutput.writeObject(list);
            objectOutput.close();
            file.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Saving failed.");
        }
    }

    void save_to_file_coordinators(ArrayList<HRCoordinator> list) {
        //Save to coordinators file
        try {
            File f = new File("Coordinators.txt");
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();

            FileOutputStream file = new FileOutputStream(f.getName());
            BufferedOutputStream outputStream = new BufferedOutputStream(file);
            ObjectOutput objectOutput = new ObjectOutputStream(outputStream);
            objectOutput.writeObject(list);
            objectOutput.close();
            file.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Saving failed.");
        }
    }

    void save_to_file_interviewers(ArrayList<Interviewer> list) {
        //Save to interviewers file
        try {
            File f = new File("Interviewers.txt");
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();

            FileOutputStream file = new FileOutputStream(f.getName());
            BufferedOutputStream outputStream = new BufferedOutputStream(file);
            ObjectOutput objectOutput = new ObjectOutputStream(outputStream);
            objectOutput.writeObject(list);
            objectOutput.close();
            file.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Saving failed.");
        }
    }

    void save_to_file_applicants(ArrayList<Applicant> list) {
        //Save to applicants file
        try {
            File f = new File("Applicants.txt");
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();

            FileOutputStream file = new FileOutputStream(f.getName());
            BufferedOutputStream outputStream = new BufferedOutputStream(file);
            ObjectOutput objectOutput = new ObjectOutputStream(outputStream);
            objectOutput.writeObject(list);
            objectOutput.close();
            file.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Saving failed.");
        }

    }

    void save_to_file_jobposting(ArrayList<JobPosting> list) {
        //Save job postings to a file
        try {
            File f = new File("jobPostingsFile.txt");
            if (f.exists()) {
                f.delete();
            }
            f.createNewFile();

            FileOutputStream file = new FileOutputStream(f.getName());
            BufferedOutputStream outputStream = new BufferedOutputStream(file);
            ObjectOutput objectOutput = new ObjectOutputStream(outputStream);
            objectOutput.writeObject(list);
            objectOutput.close();
            file.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Saving failed.");
        }
    }


    ArrayList<Applicant> getApplicants() {
        return applicants;
    }

    ArrayList<Company> getCompanies() {
        return companies;
    }

    ArrayList<HRCoordinator> getCoordinators() {
        return coordinators;
    }

    ArrayList<Interviewer> getInterviewers() {
        return interviewers;
    }

    ArrayList<JobPosting> getAllJobPostings() {
        return jobPostings;
    }

    ArrayList<Referee> getReferees() {
        return referees;
    }

}
