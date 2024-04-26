package ImplementationCWE;

import java.util.HashMap;

public class User {
    private String userName;
    private String password;
    private HashMap<String, Integer> groceryList;

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
    public void addItem(String item, Integer count) {
        Integer curCount = groceryList.get(item);
        if(curCount != null) {
            groceryList.put(item, curCount + count);
        }
        else {
            groceryList.put(item, count);
        }
    }
    public boolean deleteItem(String item) {
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
                groceryList.put(item, curCount - count); // CWE-787
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
    public boolean divideItem(String item, Integer divideNum) {
        if(divideNum > 0) //CWE-369
        {
            Integer curCount = groceryList.get(item);
            if(curCount != null) {
                if(divideNum != 1) {
                    groceryList.put(item, curCount / divideNum);
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
        return false;
    }
}
