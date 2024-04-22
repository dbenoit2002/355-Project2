package JavaCWE;

import java.util.Vector;

public class GroceryStore {
    private String storeName;
    private Vector<InventoryItem> inventory;

    private static class InventoryItem { //CWE-492
        private String name;
        private int itemCount;

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
}