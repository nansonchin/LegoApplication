package Model.PageModel;

/**
 * @author LOH XIN JIE
 */
import Model.*;
import java.util.*;

public class ViewSaleRecordModel {

    private Product product;
    private ArrayList<MemberDetail> mdList;
    private double ttlPrice;
    private int itemSold;

    public ViewSaleRecordModel() {
        this.mdList = new ArrayList<>();
    }

    public void addMdList(MemberDetail md) {
        this.mdList.add(md);
    }

    public Product getProduct() {
        return product;
    }

    public ArrayList<MemberDetail> getMdList() {
        return mdList;
    }

    public double getTtlPrice() {
        return ttlPrice;
    }

    public int getItemSold() {
        return itemSold;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setMdList(ArrayList<MemberDetail> mdList) {
        this.mdList = mdList;
    }

    public void setTtlPrice(double ttlPrice) {
        this.ttlPrice = ttlPrice;
    }

    public void setItemSold(int itemSold) {
        this.itemSold = itemSold;
    }

    public class MemberDetail {

        private Member member;
        private Orderlist orderlist;
        private AddressBook address;

        public Member getMember() {
            return member;
        }

        public Orderlist getOrderlist() {
            return orderlist;
        }

        public AddressBook getAddress() {
            return address;
        }

        public void setMember(Member member) {
            this.member = member;
        }

        public void setOrderlist(Orderlist orderlist) {
            this.orderlist = orderlist;
        }

        public void setAddress(AddressBook address) {
            this.address = address;
        }
    }
}
