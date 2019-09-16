import java.io.*;

abstract class User implements Serializable {

    private int id;
    private String name;
    private String password;

    User(String name1, String password1) {
        this.name = name1;
        this.password = password1;
        try {
            File file = new File("UserID.txt");
            if (!file.exists()) {
                file.createNewFile();
                BufferedWriter output = new BufferedWriter(new FileWriter(file, false));
                output.write("0");
                output.close();
            } else if (file.length() == 0) {
                BufferedWriter output = new BufferedWriter(new FileWriter(file, false));
                output.write("0");
                output.close();
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            this.id = Integer.parseInt(reader.readLine());
            reader.close();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
            writer.write(String.valueOf(this.id + 1));
            writer.close();

        } catch (IOException e) {
            System.out.println("IO exception.");
        }
    }

    int getId() {
        return this.id;
    }

    void setId(int num) {
        this.id = num;
    }

    String getName() {
        return this.name;
    }

    String getUserName() {
        return this.name + this.id;
    }

    String getPassword() {
        return this.password;
    }

    void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public String toString() {
        return this.getName() + " " + this.getId();
    }

}
