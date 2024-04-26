package JavaCWE;

import java.util.Scanner;
import java.text.DecimalFormat;

public class GSMain {
    public void mainAddItem(Scanner scan, GroceryStore gs) {
        String itemName = "";
        int itemCount = 0;
        double itemPrice = 0;
        boolean returnFuncGS = false;
        System.out.println("What will the item be called?");
        itemName = scan.nextLine();
        System.out.println("How much of the item is in inventory?");
        itemCount = Integer.parseInt(scan.nextLine());
        System.out.println("How much is the item worth?");
        itemPrice = Double.parseDouble(scan.nextLine());
        returnFuncGS = gs.addItem(itemName, itemCount, itemPrice);
        if(returnFuncGS == true)
        {
            System.out.println("Item has been added successfully!");
        }
    }

    public void mainUpdateCount(Scanner scan, GroceryStore gs) {
        String itemName = "";
        int itemCount = 0;
        boolean returnFuncGS = false;
        System.out.println("Enter the item name: ");
        itemName = scan.nextLine();
        System.out.println("New inventory count: ");
        itemCount = Integer.parseInt(scan.nextLine());
        returnFuncGS = gs.updateItemCount(itemName, itemCount);
        if(returnFuncGS == true)
        {
            System.out.println("Item count has been updated successfully!");
        }
    }

    public void mainRemoveItem(Scanner scan, GroceryStore gs) {
        String itemName = "";
        boolean returnFuncGS = false;
        System.out.println("Enter the item name: ");
        itemName = scan.nextLine();
        returnFuncGS = gs.removeItem(itemName);
        if(returnFuncGS == true)
        {
            System.out.println("Item has been removed successfully!");
        }
    }

    public void mainUpdatePrice(Scanner scan, GroceryStore gs) {
        String itemName = "";
        double itemPrice = 0.0;
        boolean returnFuncGS = false;
        System.out.println("Enter the item name: ");
        itemName = scan.nextLine();
        System.out.println("New item price: ");
        itemPrice = Double.parseDouble(scan.nextLine());;
        returnFuncGS = gs.updateItemPrice(itemName, itemPrice);
        if(returnFuncGS == true)
        {
            System.out.println("Item price has been updated successfully!");
        }
    }

    public User mainCreateUser(Scanner scan)
    {
        String first = "";
        String last = "";
        float wallet;
        System.out.println("Enter first name: ");
        first = scan.nextLine();
        System.out.println("Enter last name: ");
        last = scan.nextLine();
        System.out.println("Enter your balance: ");
        wallet = Float.parseFloat(scan.nextLine());
        return new User(first, last, wallet);
    }

    public void mainAddToCart(Scanner scan, User user, GroceryStore gs)
    {
        String itemName;
        System.out.println("Here is " + gs.getName() + "'s selection");
        gs.printInventory();
        
        System.out.println("Enter the item you would like to add: ");
        itemName = scan.nextLine();

        if(gs.getItem(itemName) != null)
        {
            if(gs.getItemCount(itemName) >= 1)
            {
                user.addToCart(itemName);
                gs.updateItemCount(itemName, gs.getItemCount(itemName) - 1);
                System.out.println("Item has been successfully added to cart!");
            }
            else
            {
                throw new NullPointerException("Error: The item is out of stock"); // CWE-460
            }
        }
        else
        {
            System.out.println("Item cannot be found in " + gs.getName());
        }
    }

    public void mainRemoveFromCart(Scanner scan, User user, GroceryStore gs)
    {
        String itemName;
        boolean returnFuncUser;
        
        System.out.println("Enter the item you would like to remove: ");
        itemName = scan.nextLine();

        returnFuncUser = user.removeFromCart(itemName);
        if(returnFuncUser == true)
        {
            gs.updateItemCount(itemName, gs.getItemCount(itemName) + 1);
            System.out.println("Item has been successfully removed from cart!");
        }
        else
        {
            System.out.println("Item cannot be found in your cart");
        }
    }

    public void mainAddToBalance(Scanner scan, User user)
    {
        double addBalance;
        
        System.out.println("Enter the amount you would like to add: ");
        addBalance = Double.parseDouble(scan.nextLine());

        user.addToBalance(addBalance);
    }

    public void mainRemoveFromBalance(Scanner scan, User user)
    {
        double removeBalance;
        
        System.out.println("Enter the amount you would like to remove: ");
        removeBalance = Double.parseDouble(scan.nextLine());

        user.removeFromBalance(removeBalance);
    }

    public boolean userCheckout(User user, GroceryStore gs)
    {
        DecimalFormat df = new DecimalFormat("#.00");
        double total = 0;
        String printFormat;
        for(int i = 0; i < user.getNumItems(); i++)
        {
            total += gs.getItemPrice(user.getCart(i));
        }
        if(user.getBalance() > total)
        {
            user.removeFromBalance(total);
            System.out.println("Successfully checked out!");
            printFormat = df.format(user.getBalance());
            System.out.println("Amount remaining: $" + printFormat);
            return true;
        }

        System.out.println("Balance too low. Increase balance or remove some items from cart.");
        return false;
    }

    public static void main(String[] args)
    {
        GSMain main = new GSMain();
        boolean endFlagGS = false;
        Scanner scan = new Scanner(System.in);
        int userInputGS = -1;
        GroceryStore gs = null;
        User user = null;
        boolean endFlagUser = false;
        int userInputUser = -1;

        System.out.println("Enter the name of the grocery store: ");
        gs = new GroceryStore(scan.nextLine());
        if(main != null && gs != null) // CWE-476 & CWE-395
        {
            System.out.println("You must add at least one item now.");
            main.mainAddItem(scan, gs);
            while(!endFlagGS) 
            {
                System.out.println("Select an option to setup the grocery store: ");
                System.out.println("0: exit & move to user experience");
                System.out.println("1: Add another item");
                System.out.println("2: Update the number of a specific item in the store's inventory");
                System.out.println("3: Remove an item from the store's inventory");
                System.out.println("4: Update the price of a specific item in the store's inventory");
                System.out.println("5: Get the store ID number");
                System.out.println("6: Print inventory");

                userInputGS = Integer.parseInt(scan.nextLine());
                if(userInputGS >= 0 && userInputGS <= 6)
                {
                    //CWE-484
                    switch(userInputGS){

                        case 0:
                        
                            System.out.println("Goodbye");
                            endFlagGS = true;
                            break;
                        
                        case 1:
                        
                            main.mainAddItem(scan, gs);
                            break;
                        
                        case 2:
                        
                            main.mainUpdateCount(scan, gs);
                            break;
                        
                        case 3:
                        
                            main.mainRemoveItem(scan, gs);
                            break;
                        
                        case 4:
                        
                            main.mainUpdatePrice(scan, gs);
                            break;
                        
                        case 5:
                        
                            gs.printStoreID();
                            break;
                        
                        case 6:
                        
                            gs.printInventory();
                            break;
                        
                    }
                }
                else
                {
                    System.out.println("Please enter a valid option");
                }
            }
            user = main.mainCreateUser(scan);
            if(user != null) // CWE-476 & CWE-395
            {
                while(!endFlagUser) 
                {
                    System.out.println("Select an option: ");
                    System.out.println("0: checkout");
                    System.out.println("1: Add item to cart");
                    System.out.println("2: Remove item from cart");
                    System.out.println("3: Deposit to balance");
                    System.out.println("4: Withdraw from balance");

                    userInputUser = Integer.parseInt(scan.nextLine());
                    if(userInputUser >= 0 && userInputUser <= 4)
                    {
                        if(userInputUser == 0)
                        {
                            endFlagUser = main.userCheckout(user, gs); // CWE-382
                        }
                        else if(userInputUser == 1)
                        {
                            main.mainAddToCart(scan, user, gs);
                        }
                        else if(userInputUser == 2)
                        {
                            main.mainRemoveFromCart(scan, user, gs);
                        }
                        else if(userInputUser == 3)
                        {
                            main.mainAddToBalance(scan, user);
                        }
                        else if(userInputUser == 4)
                        {
                            main.mainRemoveFromBalance(scan, user);
                        }
                    }
                    else
                    {
                        System.out.println("Please enter a valid option");
                    }
                }
            }
        }
        scan.close();
    }
}   