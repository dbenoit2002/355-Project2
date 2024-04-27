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
        //CWE-15: External control of System or Configuration Setting
        //CWE-73: External Control of File Name or path
        filePath = "ImplementationCWE/data/"; //Fixed path to prevent external manipulation
        userFileName = "user.txt"; //Fixed file name to prevent external manipulation
        userFile = new File(filePath + sanitizeFileName(userFileName));
    }
    //CWE-115: Sanitize the file name to make sure the file name is interpreted correctly
    private String sanitizeFileName(String fileName) {
        return fileName.replaceAll("[^a-zA-Z0-9_.]", "");
    }

    public User readUser() {
        try {
            Scanner scanner = new Scanner(userFile);
            String userName = scanner.nextLine();
            String password = scanner.nextLine();

            scanner.close();
            return new User(userName, password);
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
            return null;
        }
    }
    public boolean writeUser(User writeUser) {
        try {
            PrintWriter writer = new PrintWriter(userFile);
            writer.println(writeUser.getUserName());
            writer.println(writeUser.getPassword());
            writer.close();
            
            return true;
        } catch (IOException e) {
            System.out.println("Error: Unable to write to file.");
            return false;
        }
    }
}
