package DataAccess;

import Model.*;

/**
 * @author LOH XIN JIE
 */
public class DBTable {

    public DbSet<Staff> Staff;
    public DbSet<AddressBook> AddressBook;
    public DbSet<Member> Member;
    public DbSet<MemberAddress> MemberAddress;
    public DbSet<Orders> Orders;
    public DbSet<Product> Product;
    public DbSet<Orderlist> Orderlist;
    public DbSet<Cart> Cart;
    public DbSet<Cartlist> Cartlist;
    public DbSet<Discount> Discount;
    public DbSet<RateReview> RateReview;
    public DbSet<ImageTable> ImageTable;

    public DBTable() {
        this.Staff = new DbSet<>(new Staff());
        this.AddressBook = new DbSet<>(new AddressBook());
        this.Member = new DbSet<>(new Member());
        this.MemberAddress = new DbSet<>(new MemberAddress());
        this.Orders = new DbSet<>(new Orders());
        this.Product = new DbSet<>(new Product());
        this.Orderlist = new DbSet<>(new Orderlist());
        this.Cart = new DbSet<>(new Cart());
        this.Cartlist = new DbSet<>(new Cartlist());
        this.Discount = new DbSet<>(new Discount());
        this.RateReview = new DbSet<>(new RateReview());
        this.ImageTable = new DbSet<>(new ImageTable());
    }

    public DbSet<Staff> getStaff() {
        return Staff;
    }

    public DbSet<AddressBook> getAddressBook() {
        return AddressBook;
    }

    public DbSet<Member> getMember() {
        return Member;
    }

    public DbSet<MemberAddress> getMemberAddress() {
        return MemberAddress;
    }

    public DbSet<Orders> getOrders() {
        return Orders;
    }

    public DbSet<Product> getProduct() {
        return Product;
    }

    public DbSet<Orderlist> getOrderlist() {
        return Orderlist;
    }

    public DbSet<Cart> getCart() {
        return Cart;
    }

    public DbSet<Cartlist> getCartlist() {
        return Cartlist;
    }

    public DbSet<Discount> getDiscount() {
        return Discount;
    }

    public DbSet<RateReview> getRateReview() {
        return RateReview;
    }

    public DbSet<ImageTable> getImageTable() {
        return ImageTable;
    }
}
