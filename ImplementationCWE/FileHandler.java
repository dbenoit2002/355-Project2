package ImplementationCWE;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileHandler {
    private String filePath;
    public final String userFileName; //CWE-493
    private File userFile;

    public FileHandler() {
        filePath = "ImplementationCWE/data/";
        userFileName = "user.txt";
        userFile = new File(filePath + userFileName);
    }

    public User readUser() {
        try {
            Scanner scanner = new Scanner(userFile);
            String userName = scanner.nextLine();
            String password = scanner.nextLine();

            scanner.close();
            return new User(userName, password);
        } catch (FileNotFoundException e) {
            return null;
        }
    }
    public boolean writeUser(User writeUser) {
        try {
            userFile.delete();
            userFile.createNewFile();

            PrintWriter writer = new PrintWriter(userFile);
            writer.write(writeUser.getUserName());
            writer.println();
            writer.write(writeUser.getPassword());

            writer.close();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }
}
