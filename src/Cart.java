import java.util.HashMap;
import java.util.Map;

public class Cart {
    private Map<Product, Integer> inCart = new HashMap<>();
    private double totalBasketPrice;

    
    public void addProduct(Product product, int amount) {
        for (Product currentProduct : inCart.keySet()) {
           if (currentProduct != null) {
               if (currentProduct.equals(product)){
                   Integer newAmount = inCart.get(currentProduct) + amount;
                   inCart.put(product, newAmount);
                   return;
               }
           }
        }
        inCart.put(product, amount);

    }

    //Getters and setters
    public Map<Product, Integer> getInCart() {
        return inCart;
    }
    public void setInCart(Map<Product, Integer> inCart) {
        this.inCart = inCart;
    }
    public double getTotalBasketPrice() {
        return totalBasketPrice;
    }
    public void setTotalBasketPrice(double totalBasketPrice) {
        this.totalBasketPrice = totalBasketPrice;
    }
}
