package JavaCWE;

import java.util.Vector;
import java.util.Random;
import java.util.Scanner;

public class GroceryStore {
    private String storeName;
    private Vector<InventoryItem> inventory;

    private static class InventoryItem { //CWE-492
        private String name;
        private int itemCount;

        public static int getRandomID()
        {
            Random rand = new Random();
            return rand.nextInt(1000);
        }

        public static final int id = getRandomID(); //CWE-500

        public InventoryItem(String name, int itemCount) {
            this.name = name;
            this.itemCount = itemCount;
        }
        public String getName() {
            return name;
        }
        private int getCount() {
            return itemCount;
        }
        public int getID() {
            return id;
        }
        private void updateCount(int count) {
            this.itemCount = count;
        }
    }

    public GroceryStore(String name) {
        storeName = name;
        inventory = new Vector<InventoryItem>();
    }
    
    public String getName() {
        return storeName;
    }
    public Vector<InventoryItem> getInventory() {
        return inventory;
    }
    public InventoryItem getItem(String name) {
        for(InventoryItem item : inventory) {
            if(item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }
    public void addItem(String name, int count) {
        for(InventoryItem item : inventory) {
            if(item.getName().equals(name)) {
                if(item.getCount() == count) { //no changes
                    return;
                }
                else {
                    item.updateCount(count); //update count
                    return;
                }
            }
        }
        InventoryItem item = new InventoryItem(name, count); //new item
        inventory.add(item);
    }
    public boolean updateItemCount(String name, int count) {
        for(InventoryItem item : inventory) {
            if(item.getName().equals(name)) {
                item.updateCount(count);
                return true;
            }
        }
        System.out.println("Could not find item " + name); //CWE-537 (current item count is not exposed)
        return false;
    }
    public boolean removeItem(String name) {
        for(InventoryItem item : inventory) {
            if(item.getName().equals(name)) {
                inventory.remove(item);
                return true;
            }
        }
        System.out.println("Could not find item " + name);
        return false;
    }
    public boolean findItemUsingID(int id) {
        for(InventoryItem item : inventory) {
            if(item.getID() == id) {
                System.out.println("Item Found! The name of this item is: " + item.getName());
                return true;
            }
        }
        System.out.println("Could not find item of ID: " + id);
        return false;
    }

    public static void main(String[] args)
    {
        boolean endFlag = false;
        Scanner scan = new Scanner(System.in);
        int userInput = -1;
        String itemName;
        int itemCount;
        GroceryStore gs;
        gs = new GroceryStore("Grocery Store");
        if(gs != null) // CWE-476
        {
            while(!endFlag) {
                System.out.println("Select an option: ");
                System.out.println("0: exit");
                System.out.println("1: Add an item");
                System.out.println("2: Edit the number of a specific item you have added");
                System.out.println("3: Remove an item from inventory");
                System.out.println("4: Get an item's ID");
                System.out.println("5: Find an item using it's ID number");

                userInput = scan.nextInt();
                if(userInput >= 0 && userInput <= 5)
                {
                    if(userInput == 0)
                    {
                        System.out.println("Goodbye");
                        endFlag = true;
                    }
                    else if(userInput == 1)
                    {
                        System.out.println("What will the item be called");
                    }
                }
                else
                {
                    System.out.println("Please enter a valid option");
                }
            }
        }
    }
}