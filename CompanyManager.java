import java.io.Serializable;
import java.util.ArrayList;

public class CompanyManager implements Serializable {

    private ArrayList<Company> Companies;

    CompanyManager(ArrayList<Company> a) {
        this.Companies = a;
    }

    ArrayList<Company> getCompanies() {
        return this.Companies;
    }

    boolean hasCompany(String username) {
        for (Company a : this.Companies) {
            if (a.getUserName().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }

    boolean hasCompany(int id) {
        for (Company a : this.Companies) {
            if (a.getId() == id) {
                return true;
            }
        }
        return false;
    }

    Company getCompany(String username) {
        for (Company a : this.Companies) {
            if (a.getUserName().equals(username)) {
                return a;
            }
        }
        return null;
    }

    Company getCompanybyID(Integer id) {
        for (Company a : this.Companies) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    void addCompany(Company a) {
        this.Companies.add(a);
    }
}
