package Model;

import DataAccess.DBModel;

/**
 * @author LOH XIN JIE
 */
public class Cart extends DBModel {

    private int cartId;
    private Member member;

    public Cart(int cartId, Member member) {
        super("cart");
        this.cartId = cartId;
        this.member = member;
    }

    public Cart(Member member) {
        super("cart");
        this.member = member;
    }

    public Cart(int cartId) {
        super("cart");
        this.cartId = cartId;
    }

    public Cart() {
        super("cart");
    }

    public int getCartId() {
        return cartId;
    }

    public Member getMember() {
        return member;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public void setMember(Member member) {
        this.member = member;
    }

}
