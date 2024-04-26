package ImplementationCWE;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//CWE-546 (No suspicious comments exist)

public class GroceryListMaker {
    private static final int groceryListPrintSize = 10;
    private static final Pattern passwordPattern = Pattern.compile("^(?=.*[a-zA-Z\\d])(?=.*[@#$%^&+=]).{8,50}$");

    public static void main(String args[]) {
        GroceryListMaker driver = new GroceryListMaker();
        User user = driver.loginMenu();
        if(user != null) {
            driver.groceryListMenu(user);
        }
    }

    public User loginMenu() {
        boolean endFlag = false;
        Scanner scanner = new Scanner(System.in);
        int userInput;
        User curUser = null;
        final FileHandler fileHandler = new FileHandler(); //CWE-493
        
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
                    System.out.println("Goodbye");
                    endFlag = true;
                }
                else {
                    //CWE-749
                    System.out.println("Enter a username (must be at most 16 characters and consist of only lowercase letters, uppercase letters, and numbers): ");
                    String userName = scanner.nextLine();
                    System.out.println("Enter a password (must be between 8 and 32 characters and must consist of only lowercase letters, uppercase letters, numbers, and the following special characters: !@#$%^&*_+.,?): ");
                    String password = scanner.nextLine();

                    Matcher passwordMatcher = passwordPattern.matcher(password);      //CWE-521
                    if(passwordMatcher.matches()) {
                        if(userInput == 1) {
                            curUser = new User(userName, password);
                            fileHandler.writeUser(curUser);
                            System.out.println("Account successfully created!");
                        }
                        else if(userInput == 2) {
                            User fileUser = fileHandler.readUser();   //CWE-259
                            if(fileUser != null && fileUser.getUserName().equals(userName) &&
                            fileUser.getPassword().equals(password)) {
                                curUser = new User(userName, password);
                                return curUser;
                            } else {
                                System.out.println("Error: Incorrect username or password");
                            }
                        }
                    }
                    else {
                        System.out.println("Error: Incorrect username or password");
                    }
                }
            } catch(NumberFormatException e) {
                System.out.println("Error: Please enter a number");
            }
        }
        scanner.close();
        return null;
    }

    public void groceryListMenu(User curUser) {
        boolean endFlag = false;
        Scanner scanner = new Scanner(System.in);
        int userInput;

        while(!endFlag) {
            System.out.println("Select an option: ");
            System.out.println("0: Exit");
            System.out.println("1: Print grocery list");
            System.out.println("2: Add item");
            System.out.println("3: Delete item");
            System.out.println("4: Remove multiple items");
            System.out.println("5: Divide item count");
            try{
                userInput = Integer.parseInt(scanner.nextLine());
                if(userInput < 0 || userInput > 5) {
                    System.out.println("Error: Please enter a valid option");
                }
                else if(userInput == 0) {
                    System.out.println("Goodbye");
                    endFlag = true;
                }
                else if(userInput == 1){
                    int count = 0;
                    String[] groceryListItems = new String[groceryListPrintSize];
                    int[] groceryListCounts = new int[groceryListPrintSize];
                    if(curUser.getGroceryList().size() <= groceryListPrintSize) //CWE-193
                    {
                        for(String item : curUser.getGroceryList().keySet()) {
                            groceryListItems[count] = item;
                            groceryListCounts[count] = curUser.getGroceryList().get(item);
                            
                            count++;
                        }
                        if(count != 0)
                        {
                            for(int i = 0; i < count; i++)
                            {
                                System.out.println(i+1 + ": " + groceryListItems[i] + " - " + groceryListCounts[i]);
                            }
                        }
                        else
                        {
                            System.out.println("Error: Grocery list is empty");
                        }
                    }
                    else
                    {
                        System.out.println("Error: Grocery list is too big to print. Please remove items from your list.");
                    }
                }
                else {
                    System.out.println("Enter the name of the item: ");
                    String item = scanner.nextLine();
                    if(userInput == 2) {
                        System.out.println("Enter the number of items to add: ");
                        try {
                            int itemCount = Integer.parseInt(scanner.nextLine());
                            curUser.addItem(item, itemCount);
                            System.out.println("Successfully added item");
                        } catch (NumberFormatException e) {
                            System.out.println("Error: Please enter a number");
                        }
                    }
                    else if(userInput == 3) {
                        if(curUser.deleteItem(item)) {
                            System.out.println("Successfully deleted item");
                        }
                        else {
                            System.out.println("Error: Could not delete item");
                        }
                    }
                    else if(userInput == 4) {
                        System.out.println("Enter the number of items to delete: ");
                        try {
                            int numItems = Integer.parseInt(scanner.nextLine());
                            if(curUser.removeItem(item, numItems)) {
                                System.out.println("Successfully removed item(s)");
                            }
                            else {
                                System.out.println("Error: Could not remove item(s)");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Error: Please enter a number");
                        }
                    }
                    else if(userInput == 5)
                    {
                        System.out.println("Enter the number to divide your item by: ");
                        try {
                            int divideNum = Integer.parseInt(scanner.nextLine());
                            if(curUser.divideItem(item, divideNum)) {
                                System.out.println("Successfully removed item(s)");
                            }
                            else {
                                System.out.println("Error: Could not remove item(s)");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Error: Please enter a number");
                        }
                    }
                }
            } catch(NumberFormatException e) {
                System.out.println("Error: Please enter a number");
            }
        }
        scanner.close();
    }
}
//CWE-561