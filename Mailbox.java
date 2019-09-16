import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;


class Mailbox implements Serializable {

    private ArrayList<String> messages;

    Mailbox() {
        this.messages = new ArrayList<>();
    }

    /**
     * Clear mailbox
     */
    private void clear() {
        this.messages = new ArrayList<>();
        System.out.println("All messages have been cleared.");
    }

    /**
     * Receive a message from the notification system
     * @param message is that message
     */
    void enter(String message) {
        this.messages.add(message);
    }

    /**
     * Reads all messages and then asks user to if he/she wants them to be deleted
     */
    void read() {
        VerifyInput verify = new VerifyInput();
        System.out.println("You have " + this.messages.size() + " messages.");
        System.out.println();
        for (String message : this.messages) {
            System.out.println(message);
            System.out.println();
        }
        System.out.println("Would you like to clear all messages? (1: Yes, 2: No)");
        int input = verify.getValidChoice(2);
        if (input == 1) {
            this.clear();
        }
    }
}

