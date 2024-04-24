package ImplementationCWE;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class FileHandler {
    private String filePath;
    private String userFileName;
    private File userFile;

    public FileHandler() {
        filePath = "data/";
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

            FileOutputStream writer = new FileOutputStream(userFile);
            writer.write(writeUser.getUserName().getBytes(), 0 , writeUser.getUserName().length());
            writer.write(writeUser.getPassword().getBytes(), 0, writeUser.getPassword().length());

            writer.close();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }
}
