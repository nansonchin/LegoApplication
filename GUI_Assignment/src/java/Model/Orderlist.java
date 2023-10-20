package Model;

import DataAccess.DBModel;

/**
 * @author LOH XIN JIE
 */
public class Orderlist extends DBModel {

    private Orders order;
    private Product product;
    private int ordersQuantity;
    private double ordersSubprice;

    public Orderlist(Orders order, Product product, int ordersQuantity, double ordersSubprice) {
        super("orderlist");
        this.order = order;
        this.product = product;
        this.ordersQuantity = ordersQuantity;
        this.ordersSubprice = ordersSubprice;
    }

    public Orderlist(Orders order) {
        super("orderlist");
        this.order = order;
    }

    public Orderlist() {
        super("orderlist");
    }

    public Orders getOrder() {
        return order;
    }

    public Product getProduct() {
        return product;
    }

    public int getOrdersQuantity() {
        return ordersQuantity;
    }

    public double getOrdersSubprice() {
        return ordersSubprice;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setOrdersQuantity(int ordersQuantity) {
        this.ordersQuantity = ordersQuantity;
    }

    public void setOrdersSubprice(double ordersSubprice) {
        this.ordersSubprice = ordersSubprice;
    }

}
