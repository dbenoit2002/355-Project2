package JavaCWE;

public class User {
    public static final int MAX_CART_SIZE = 1000;

    private String firstName;
    private String lastName;
    private String cart[]; //CWE-582
    private int numItems;
    private double balance;

    public User(String fname, String lname, double balance) {
        firstName = fname;
        lastName = lname;
        cart = new String[MAX_CART_SIZE];
        numItems = 0;
        this.balance = balance;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getNumItems() {
        return numItems;
    }

    public String getCart(int index) {
        return cart[index];
    }

    public double getBalance() {
        return balance;
    }

    public boolean addToCart(String item) {
        if(numItems != MAX_CART_SIZE) {
            cart[numItems] = item;
            numItems++;
            return true;
        }
        return false;
    }

    public boolean removeFromCart(String item) {
        for(int i = 0; i < numItems; i++) {
            if(cart[i] == item) {
                for(int j = i; j < numItems - 1; j++){
                    cart[j] = cart[j+1];
                }
                numItems--;
                return true;
            }
        }
        return false;
    }

    public void addToBalance(double num) {
        balance += num;
    }
    
    public void removeFromBalance(double num) {
        balance -= num;
    }
    
    public boolean equals(User user) {
        if(this.getClass() == user.getClass()) { //CWE-486
            if(this.firstName == user.firstName && this.lastName == user.lastName) {
                return true;
            }
        }
        return false;
    }
}
