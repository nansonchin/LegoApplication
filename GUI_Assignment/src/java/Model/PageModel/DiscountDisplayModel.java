package Model.PageModel;

/**
 * @author LOH XIN JIE
 */
import Model.*;

public class DiscountDisplayModel {

    private Discount discount;
    private Product product;
    private String status;

    public DiscountDisplayModel() {
    }

    public Discount getDiscount() {
        return discount;
    }

    public Product getProduct() {
        return product;
    }

    public String getStatus() {
        return status;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
