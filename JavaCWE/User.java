package JavaCWE;

import java.util.Vector;

public class User {
    private String firstName;
    private String lastName;
    private Vector<String> cart;
    private float balance;

    public User(String fname, String lname, float balance) {
        firstName = fname;
        lastName = lname;
        cart = new Vector<String>();
        this.balance = balance;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public Vector<String> getCart() {
        return cart;
    }
    public float getBalance() {
        return balance;
    }
    public void addToCart(String item) {
        cart.add(item);
    }
    public boolean removeFromCart(String item) {
        if(cart.remove(item)) {
            return true;
        }
        return false;
    }
    public void addToBalance(float num) {
        balance += num;
    }
    public void removeFromBalance(float num) {
        balance -= num;
    }
}
