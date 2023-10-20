package Model.PageModel;

/**
 * @author LOH XIN JIE
 */
import Model.*;
import java.util.*;

public class OrderHistoryModel {

    private AddressBook address;
    private Orders orders;
    private ArrayList<ProductOrders> polist;

    public OrderHistoryModel() {
        this.polist = new ArrayList<>();
    }

    public void addPolist(ProductOrders po) {
        polist.add(po);
    }

    public AddressBook getAddress() {
        return address;
    }

    public Orders getOrders() {
        return orders;
    }

    public ArrayList<ProductOrders> getPolist() {
        return polist;
    }

    public void setAddress(AddressBook address) {
        this.address = address;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public void setPolist(ArrayList<ProductOrders> polist) {
        this.polist = polist;
    }

    public class ProductOrders {

        private Product product;
        private Orderlist orderlist;
        private String rnrStatus;//rate and review status

        public Product getProduct() {
            return product;
        }

        public Orderlist getOrderlist() {
            return orderlist;
        }

        public String getRnrStatus() {
            return rnrStatus;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public void setOrderlist(Orderlist orderlist) {
            this.orderlist = orderlist;
        }

        public void setRnrStatus(String rnrStatus) {
            this.rnrStatus = rnrStatus;
        }
    }
}
