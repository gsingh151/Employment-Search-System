import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class VerifyInput {

    // prompts the user to enter a valid choice
    int getValidChoice(int choices) {
        int input = this.getValidNumber();
        while (input <= 0 || input > choices) {
            System.out.println("Please select a number between 1 and " + choices + ":");
            input = this.getValidNumber();
        }
        return input;
    }

    // prompts the user to type a number until they do. Return the first int they input
    int getValidNumber() {
        while (true) {
            Scanner console = new Scanner(System.in);
            try {
                return console.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("This is not a valid number. Please try again:");
            }
        }
    }

    // prompts the user to type a String in the layout "MM/dd/yyyy" until they do. Convert that string into a date and
    // return it

    // prompts the user to type a String in the layout "MM/dd/yyyy" until they do. Convert that string into a date and
    // return it
    Date getValidDate() {
        Scanner console = new Scanner(System.in);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        sdf.setLenient(false);
        while (true) {
            try {
                String input = console.next();
                return sdf.parse(input);
            } catch (ParseException e) {
                System.out.println("Invalid input. Please enter in this format (MM/dd/yyyy)");
            }
        }
    }

    // prompts the user to type a valid password until they do.
    String getValidPassword() {
        Scanner console = new Scanner(System.in);
        String input = console.next();
        while (input.length() < 3) {
            System.out.println("Password must be at least 3 characters long.");
            System.out.println("Please try again:");
            input = console.next();
        }
        return input;
    }

    String getValidTag() {
        Scanner scan = new Scanner(System.in);
        String tag = scan.next();
        tag = tag.strip();
        tag = tag.toLowerCase();
        return tag;
    }

    // prompts the user to enter the password to their account
    String attemptPassword(User a) {
        Scanner console = new Scanner(System.in);
        String password = console.next();
        while (!a.getPassword().equals(password)) {
            System.out.println("Incorrect password. Please try again:");
            password = console.next();
        }
        return password;
    }

    // prompts the user to type their username
    String getUser() {
        Scanner console = new Scanner(System.in);
        String input = console.next();
        while (input.contains(" ")) {
            System.out.println("Please enter a valid username!");
        }
        return input;

    }

    // checks if action should be canceled
    boolean cancel(String input) {
        return (input.equals("cancel"));
    }

}
