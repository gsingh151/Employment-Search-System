import java.io.Serializable;

class InterviewFormat implements Serializable {

    private String DEFAULT_ORDER = "pi";
    private String order;
    private char roundType;
    private int currentRound;

    InterviewFormat(String unfiltered_order) {
        this.order = this.getValidOrder(unfiltered_order);
        roundType = this.order.charAt(0);
        currentRound = 1;
    }

    // PUBLIC METHODS

    void display() {
        System.out.println("Job posting round order: ");
        this.printOrder();
        System.out.println("Job posting is currently on round " + this.currentRound);
    }

    // METHODS THAT ONLY JOBPOSTING SHOULD CALL

    /**
     * Go to next round for a job posting
     */
    void nextRound() {
        roundType = this.order.charAt(currentRound);
        currentRound++;
    }

    boolean atLastRound() {
        return currentRound == order.length();
    }

    // PRIVATE METHODS

    private String getValidOrder(String unfiltered) {
        char p = 'p';
        char t = 't';
        char g = 'g';
        char i = 'i';
        char o = 'o';
        String temp = unfiltered.toLowerCase();
        String filtered = "";
        for (int x = 0; x < temp.length(); x++) {
            char c = temp.charAt(x);
            if (c == p || c == t || c == g || c == i || c == o) {
                filtered = filtered.concat(String.valueOf(c));
            }
        }
        if (filtered.equals("")) {
            filtered = DEFAULT_ORDER;
        }
        return filtered;
    }

    private void printOrder() {
        for (int x = 0; x < this.order.length(); x++) {
            char c = this.order.charAt(x);
            switch (c) {
                case 'p':
                    System.out.println("Phone interview");
                    break;
                case 't':
                    System.out.println("Take-home test");
                    break;
                case 'g':
                    System.out.println("Group interview");
                    break;
                case 'i':
                    System.out.println("One-on-one in-person interview");
                    break;
                case 'o':
                    System.out.println("Other. Please contact hiring company for more details.");
                    break;
            }
        }
    }

    String printSummary() {
        int counts[] = new int[4];
        String summary = "\nInterviews: ";

        for (int k = 0; k < order.length(); k++) {
            switch (order.charAt(k)) {
                case 'p':
                    counts[0]++;
                    break;
                case 't':
                    counts[1]++;
                    break;
                case 'g':
                    counts[2]++;
                    break;
                case 'i':
                    counts[3]++;
                    break;
            }
        }
        summary += counts[0];
        summary += " phone interview, ";
        summary += counts[1];
        summary += " take-home test, ";
        summary += counts[2];
        summary += " group interview, and ";
        summary += counts[3];
        summary += " one-on-one in-person interview";
        return summary;
    }
}
