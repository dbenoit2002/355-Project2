package JavaCWE;

import java.util.Vector;
import java.util.Random;

public class GroceryStore {
    private String storeName;
    private Vector<InventoryItem> inventory;

    public static int getRandomID()
    {
        Random rand = new Random();
        return rand.nextInt(1000);
    }

    public static final int storeID = getRandomID(); //CWE-500

    private static class InventoryItem { //CWE-492
        private String name;
        private int itemCount;
        private double price;

        public InventoryItem(String name, int itemCount, double price) {
            this.name = name;
            this.itemCount = itemCount;
            this.price = price;
        }
        public String getName() {
            return name;
        }
        private int getCount() {
            return itemCount;
        }
        private double getPrice() {
            return price;
        }
        private void updateCount(int count) {
            this.itemCount = count; // CWE-481
        }
        private void updatePrice(double itemPrice)
        {
            this.price = itemPrice;
        }
    }

    public GroceryStore(String name) {
        storeName = name;
        inventory = new Vector<InventoryItem>();
    }
    
    public String getName() {
        return storeName;
    }

    //CWE-375: Returning a Mutable Object to an Untrusted Caller, in this case the method returns a new Vector instance.
    // public Vector<InventoryItem> getInventory() {
    //     return new Vector<>(inventory); // CWE-495
    // }
    public Vector<InventoryItem> getInventory() {
        Vector<InventoryItem> copy = new Vector<>();
        for (InventoryItem item : inventory) {
            copy.add(new InventoryItem(item.getName(), item.getCount(), item.getPrice()));
        }

        return copy;
    }

    public int getID() {
        return storeID;
    }

    public void printStoreID() {
        System.out.println(storeName + "'s ID number is: " + storeID);
    }
    
    //CWE-502: The program does not perform deserialization of untrusted data,
    //thus avoiding vulnerabilities associated with deserialization of untrusted content.
    //No deserialization processes are implemented in the current sysytem.
    public InventoryItem getItem(String name) {
        for(InventoryItem item : inventory) {
            if(item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    public double getItemPrice(String name) {
        for(InventoryItem item : inventory) {
            if(item.getName().equals(name)) {
                return item.getPrice();
            }
        }
        return 0.0;
    }

    public int getItemCount(String name) {
        for(InventoryItem item : inventory) {
            if(item.getName().equals(name)) {
                return item.getCount();
            }
        }
        return -1;
    }

    public boolean addItem(String name, int count, double itemPrice) {
        for(InventoryItem item : inventory) {
            if(item.getName().equals(name)) {
                if(item.getCount() != count) {
                    item.updateCount(count); //update count
                }
                if(item.getPrice() != itemPrice) {
                    item.updatePrice(itemPrice); //update price
                }
                System.out.println("Item exists. Item has been updated with the new count and price you specified.");
                return false;
            }
        }
        InventoryItem item = new InventoryItem(name, count, itemPrice); //new item
        inventory.add(item);
        return true;
    }

    public boolean updateItemCount(String name, int count) {
        /* try{ */
        for(InventoryItem item : inventory) {
            if(item.getName().equals(name)) {
                item.updateCount(count);
                return true;
            }
        }
        /* }catch (NullPointerException e){
        } //CWE-248 and CWE-396
         */
        System.out.println("Could not find the specified item."); //CWE-209 - Avoiding exposure of sensitive information through error messages.
        return false;
    }

    public boolean removeItem(String name) {
        try{
            for(InventoryItem item : inventory) {
                if(item.getName().equals(name)) {
                    inventory.remove(item);
                    return true;
                }else{
                    throw new NullPointerException("Missing Error");
                }
            }
        }catch (NullPointerException e){
            System.out.println("Could not find the specified item."); //CWE-537, CWE-209 - Avoiding exposure of sensitive information through error messages.
        } //CWE-248 and CWE-396
        
        return false;
    }

    public boolean updateItemPrice(String name, double itemPrice)
    {
        for(InventoryItem item : inventory) {
            if(item.getName().equals(name)) {
                item.updatePrice(itemPrice);;
                return true;
            }
        }
        System.out.println("Could not find the specified item."); //CWE-209 - Avoiding exposure of sensitive information through error messages.
        return false;
    }
    
    public void printInventory() {
        int count = 1;
        for(InventoryItem item : inventory) {
            System.out.println("Item " + count + ": " + item.getName());
            System.out.println("Item " + count + "'s Inventory Count: " + item.getCount());
            System.out.println("Item " + count + "'s Price: " + item.getPrice());
            count++;
        }
    }
}
