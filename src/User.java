import java.util.InputMismatchException;
import java.util.Scanner;

public abstract class User implements Comparable<User>{
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private boolean bought;
    private Cart basket;
    private double spent;
    private int boughtCounter;


    //Constructor
    public User(String firstName, String lastName, String userName, String password) {
        this.basket = new Cart();
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.bought = false;
        this.boughtCounter = 0;
        this.spent = 0;
    }

    //getters and setters

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName.matches(".*[0-9].*")) {
            System.out.println("The first name cant contain digits ");
        } else {
            this.firstName = firstName;
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (firstName.matches(".*[0-9].*")) {
            System.out.println("The last name cant contain ");
        } else {
            this.lastName = lastName;
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(password.length() < 6) {
            System.out.println("Min password len 6 chars.");
        } else {
            this.password = password; }
    }

    public Cart getBasket() {
        return basket;
    }

    public void setBasket(Cart basket) {
        this.basket = basket;
    }

    //Functions

    public void addToBasket(Product currentProduct) {
        Scanner intScanner = new Scanner(System.in);
        int amount;
        try {
            do {
                System.out.println("How many products do you want? ");
                amount = intScanner.nextInt();
            }
            while (amount <= 0);
            basket.addProduct(currentProduct, amount);

        } catch (InputMismatchException e) {
            System.out.println("Invalid amount of products");
            intScanner.nextLine();
        }
    }

    public void printCart(Options options) {
        double price;
        double totalPrice;
        double totalCartPrice = 0;

        for (Product product : getBasket().getInCart().keySet()) {

            int value = getBasket().getInCart().get(product);
            if (options == Options.OP_1 || options == Options.OP_2) {
                price = product.getDiscountPrice();

            }
            else {
                price = product.getPrice();
            }
            totalPrice = price*value;
            System.out.println("Unit: " + product.getAbout() + "; unit price: " + price + "$; total units: " + value + "; total price: " + totalPrice + "$");

            totalCartPrice+=totalPrice;
            getBasket().setTotalBasketPrice(totalCartPrice);

        }
    }

    public boolean isBought() {
        return bought;
    }

    public void setBought(boolean bought) {
        this.bought = bought;
    }

    public String toString(){
        return this.firstName + " " + this.lastName;
    }

    public double getSpent() {
        return spent;
    }

    public void setSpent(double spent) {
        this.spent = spent;
    }

    public int getBoughtCounter() {
        return boughtCounter;
    }

    public void setBoughtCounter(int boughtCounter) {
        this.boughtCounter = boughtCounter;
    }

    public int compareTo(User other) {
        double currentUserSpent = this.getSpent();
        double otherUserSpent = other.getSpent();

        return Double.compare(currentUserSpent, otherUserSpent);
    }

}
