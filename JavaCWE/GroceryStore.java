package JavaCWE;

import java.util.HashMap;

public class GroceryStore {
    private String storeName;
    private HashMap<String, Integer> inventory;

    public GroceryStore(String name) {
        storeName = name;
        inventory = new HashMap<String,Integer>();
    }
    
    public String getName() {
        return storeName;
    }
    public HashMap<String, Integer> getInventory() {
        return inventory;
    }
    public boolean addItem(String name, Integer count) {
        if(inventory.get(name) == null) {
            inventory.put(name, count);
            return true;
        }
        return false;
    }
    public boolean updateItemCount(String name, Integer count) {
        if(inventory.get(name) != null) {
            inventory.put(name, count);
            return true;
        }
        return false;
    }
    public boolean removeItem(String name) {
        return (inventory.remove(name) != null);
    }
}