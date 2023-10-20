package Model;

import DataAccess.DBModel;

/**
 * @author LOH XIN JIE
 */
public class Cartlist extends DBModel {

    private Cart cart;
    private Product product;
    private int cartQuantity;

    public Cartlist(Cart cart, Product product, int cartQuantity) {
        super("cartlist");
        this.cart = cart;
        this.product = product;
        this.cartQuantity = cartQuantity;
    }

    public Cartlist(Cart cart) {
        super("cartlist");
        this.cart = cart;
    }

    public Cartlist() {
        super("cartlist");
    }

    public Cart getCart() {
        return cart;
    }

    public Product getProduct() {
        return product;
    }

    public int getCartQuantity() {
        return cartQuantity;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setCartQuantity(int cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

}
