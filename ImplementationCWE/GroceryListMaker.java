package ImplementationCWE;

import java.io.File;
import java.util.Scanner;

public class GroceryListMaker {
    public static void main(String args[]) {
        if(new GroceryListMaker().loginMenu() != null) {

        }
    }

    public User loginMenu() {
        boolean endFlag = false;
        Scanner scanner = new Scanner(System.in);
        int userInput;
        User curUser = null;
        FileHandler fileHandler = new FileHandler();
        
        while(!endFlag) {
            System.out.println("Select an option: ");
            System.out.println("0: Exit");
            System.out.println("1: Create an account");
            System.out.println("2: Login");
            try{
                userInput = Integer.parseInt(scanner.nextLine());
                if(userInput < 0 || userInput > 2) {
                    System.out.println("Error: Please enter a valid option");
                }
                else if(userInput == 0) {
                    endFlag = true;
                }
                else {
                    System.out.println("Enter a username: ");
                    String userName = scanner.nextLine();
                    System.out.println("Enter a password: ");
                    String password = scanner.nextLine();
                    if(userInput == 1) {
                        curUser = new User(userName, password);
                        fileHandler.writeUser(curUser);
                        return curUser;
                    }
                    else if(userInput == 2) {
                        User fileUser = fileHandler.readUser();
                        if(fileUser != null && fileUser.getUserName().equals(userName) &&
                           fileUser.getPassword().equals(password)) {
                            curUser = new User(userName, password);
                            return curUser;
                        } else {
                            System.out.println("Error: Incorrect user name or password");
                        }
                    }
                }
            } catch(NumberFormatException e) {
                System.out.println("Error: Please enter a number");
            }
        }
        scanner.close();
        return null;
    }
}
