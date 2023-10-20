package Model.PageModel;

/**
 * @author LOH XIN JIE
 */
import java.util.*;
import Model.*;

public class HomeModel {

    ArrayList<HotSales> hotSalesList;
    ArrayList<ProductNoRate> productHaventRate;
    ArrayList<DiscountProduct> discountList;

    public HomeModel() {
        this.hotSalesList = new ArrayList<>();
        this.productHaventRate = new ArrayList<>();
        this.discountList = new ArrayList<>();
    }

    public ArrayList<ProductNoRate> getProductHaventRate() {
        return productHaventRate;
    }

    public ArrayList<HotSales> getHotSalesList() {
        return hotSalesList;
    }

    public ArrayList<DiscountProduct> getDiscountList() {
        return discountList;
    }

    public void addProductHaventRate(HomeModel.ProductNoRate pnr) {
        productHaventRate.add(pnr);
    }

    public void addHotSales(HomeModel.HotSales hotSales) {
        hotSalesList.add(hotSales);
    }

    public void addDiscountList(DiscountProduct discount) {
        discountList.add(discount);
    }

    public void setHotSalesList(ArrayList<HotSales> hotSalesList) {
        this.hotSalesList = hotSalesList;
    }

    public void setDiscountList(ArrayList<DiscountProduct> discountList) {
        this.discountList = discountList;
    }

    public void setProductHaventRate(ArrayList<ProductNoRate> productHaventRate) {
        this.productHaventRate = productHaventRate;
    }

    //hot sales class
    public class HotSales {

        private int productSold;
        private Product product;
        private double rating;
        private Discount discount;

        public HotSales() {
        }

        public int getProductSold() {
            return productSold;
        }

        public Product getProduct() {
            return product;
        }

        public double getRating() {
            return rating;
        }

        public Discount getDiscount() {
            return discount;
        }

        public void setProductSold(int productSold) {
            this.productSold = productSold;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }

        public void setDiscount(Discount discount) {
            this.discount = discount;
        }
    }

    public class ProductNoRate {

        private Product product;
        private Date ordersDate;
        private int ordersId;
        private int quantityOrders;
        private double price;

        public ProductNoRate() {
        }

        public Product getProduct() {
            return product;
        }

        public Date getOrdersDate() {
            return ordersDate;
        }

        public int getQuantityOrders() {
            return quantityOrders;
        }

        public double getPrice() {
            return price;
        }

        public int getOrdersId() {
            return ordersId;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public void setOrdersDate(Date ordersDate) {
            this.ordersDate = ordersDate;
        }

        public void setQuantityOrders(int quantityOrders) {
            this.quantityOrders = quantityOrders;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public void setOrdersId(int ordersId) {
            this.ordersId = ordersId;
        }
    }

    public class DiscountProduct {

        private Discount discount;
        private Product product;
        private double rating;

        public DiscountProduct() {
        }

        public Discount getDiscount() {
            return discount;
        }

        public Product getProduct() {
            return product;
        }

        public double getRating() {
            return rating;
        }

        public void setDiscount(Discount discount) {
            this.discount = discount;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }
    }
}
