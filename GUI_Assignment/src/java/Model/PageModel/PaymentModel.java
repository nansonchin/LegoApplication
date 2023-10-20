/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.PageModel;

import Model.*;

/**
 *
 * @author Acer
 */
public class PaymentModel {

    private Product product;
    private AddressBook addressBook;
    private int cartQuantity;

    public PaymentModel(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    
    public PaymentModel(Product product, int cartQuantity) {
        this.product = product;
        this.cartQuantity = cartQuantity;
    }

    public Product getProduct() {
        return product;
    }

    public AddressBook getAddressBook() {
        return addressBook;
    }

    public void setAddressBook(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(int cartQuantity) {
        this.cartQuantity = cartQuantity;
    }
    
    

}
