package ImplementationCWE;

import java.util.HashMap;

public class User {
    private String userName;
    private String password;
    private HashMap<String, Integer> groceryList;
    //CWE-131 is prevented by using a Hashmap instead of a standard buffer
    
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.groceryList = new HashMap<String,Integer>();
    }

    public String getUserName() {
        return userName;
    }
    public String getPassword() {
        return password;
    }
    public HashMap<String, Integer> getGroceryList() {
        return groceryList;
    }
    public void addItem(String item) {
        Integer curCount = groceryList.get(item);
        if(curCount != null) {
            groceryList.put(item, curCount + 1);
        }
        else {
            groceryList.put(item, 1);
        }
    }
    public void addItem(String item, Integer count) {
        Integer curCount = groceryList.get(item);
        if(curCount != null) {
            groceryList.put(item, curCount + count);
        }
        else {
            groceryList.put(item, count);
        }
    }
    public boolean removeItem(String item) {
        Integer curCount = groceryList.get(item);
        if(curCount != null) {
            groceryList.remove(item);
            return true;
        }
        else {
            return false;
        }
    }
    public boolean removeItem(String item, Integer count) {
        Integer curCount = groceryList.get(item);
        if(curCount != null) {
            if(curCount == count) {
                groceryList.remove(item);
                return true;
            }
            else if(curCount > count) {
                groceryList.put(item, curCount - count);
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }
}
