package JavaCWE;

import java.util.Scanner;

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

    public static void main(String[] args)
    {
        GSMain main = new GSMain();
        boolean endFlagGS = false;
        Scanner scan = new Scanner(System.in);
        int userInputGS = -1;
        GroceryStore gs = null;
        User user = null;

        System.out.println("Enter the name of the grocery store: ");
        gs = new GroceryStore(scan.nextLine());
        if(gs != null) // CWE-476
        {
            System.out.println("You must add at least one item now.");
            main.mainAddItem(scan, gs);
            while(!endFlagGS) {
                System.out.println("Select an option to setup the grocery store: ");
                System.out.println("0: exit & move to user experience");
                System.out.println("1: Add another item");
                System.out.println("2: Update the number of a specific item in the store's inventory");
                System.out.println("3: Remove an item from the store's inventory");
                System.out.println("4: Update the price of a specific item in the store's inventory");
                System.out.println("5: Get the store ID number");
                System.out.println("6: Print inventory");

                userInputGS = Integer.parseInt(scan.nextLine());
                if(userInputGS >= 0 && userInputGS <= 7)
                {
                    if(userInputGS == 0)
                    {
                        System.out.println("Goodbye");
                        endFlagGS = true;
                    }
                    else if(userInputGS == 1)
                    {
                        main.mainAddItem(scan, gs);
                    }
                    else if(userInputGS == 2)
                    {
                        main.mainUpdateCount(scan, gs);
                    }
                    else if(userInputGS == 3)
                    {
                        main.mainRemoveItem(scan, gs);
                    }
                    else if(userInputGS == 4)
                    {
                        main.mainUpdatePrice(scan, gs);
                    }
                    else if(userInputGS == 5)
                    {
                        gs.printStoreID();
                    }
                    else if(userInputGS == 6)
                    {
                        gs.printInventory();
                    }
                }
                else // CWE-478
                {
                    System.out.println("Please enter a valid option");
                }
            }
        }
        scan.close();
    }
}

    