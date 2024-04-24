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
            this.itemCount = count;
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

    public Vector<InventoryItem> getInventory() {
        return inventory;
    }

    public int getID() {
        return storeID;
    }

    public void printStoreID() {
        System.out.println(storeName + "'s ID number is: " + storeID);
    }
    
    public InventoryItem getItem(String name) {
        for(InventoryItem item : inventory) {
            if(item.getName().equals(name)) {
                return item;
            }
        }
        return null;
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
        try{
        for(InventoryItem item : inventory) {
            if(item.getName().equals(name)) {
                item.updateCount(count);
                return true;
            }else{
                throw new NullPointerException("Error"); 
            }
        }
        }catch (NullPointerException e){
            System.out.println("Could not find item " + name);
        } //CWE-248 and CWE-396
        //CWE-537 (current item count is not exposed)
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
            System.out.println("Could not find item " + name);
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
        System.out.println("Could not find item " + name); //CWE-537 (current item count is not exposed)
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