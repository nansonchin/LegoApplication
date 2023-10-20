/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.*;
import DataAccess.DBTable;
import DataAccess.DbSet;
import DataAccess.Mapper.*;
import Model.AddressBook;
import Model.Cartlist;
import Model.PageModel.PaymentModel;
import Model.Product;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Acer
 */
public class PaymentController {

    DBTable dbTable = new DBTable();

    //get all cartlist show at summary cart
    public ArrayList<Cartlist> getCart() {
        try {
            return dbTable.getCartlist().getData(new CartlistMapper());
        } catch (SQLException ex) {
            return null;
        }
    }

    public ArrayList<Product> getCartProduct() {
        try {
            return dbTable.getProduct().getData(new ProductMapper());
        } catch (SQLException ex) {
            return null;
        }
    }

    public boolean addAddress(String name, String phone, String no, String street, String city, String state, String postcode) throws SQLException, Exception {
        return dbTable.AddressBook.Add(new AddressBookMapper(), new AddressBook(name, phone, no, street, state, city, postcode));
    }

    public boolean addOrder(Date ordersDate, String ordersPaymentType, double ordersTtlPrice, double ordersTax, double ordersDeliveryFee, double ordersExpressShipping, Member member, AddressBook address) throws SQLException, Exception {
        // Create a new Orders object with the given parameters
        Orders newOrder = new Orders(ordersDate, ordersPaymentType, ordersTtlPrice, ordersTax, ordersDeliveryFee, ordersExpressShipping, member, address);

        // Get a DbSet instance for the Orders table
        DbSet<Orders> ordersTable = new DbSet<>(new Orders());

        // Insert the new Order into the database using the DbSet's Add method
        return ordersTable.Add(new OrdersMapper(), newOrder);
    }

    public boolean addMemberAddress(int addressId, int memberId) throws SQLException, Exception {
        AddressBook address = new AddressBook(addressId);
        Member member = new Member(memberId);
        MemberAddress memberAddress = new MemberAddress(address, member);
        return dbTable.MemberAddress.Add(new MemberAddressMapper(), memberAddress);
    }

    public boolean addO(Date ordersDate, String ordersPaymentType, double ordersTtlPrice, double ordersTax, double ordersDeliveryFee, double ordersExpressShipping, int memberId, int addressId) throws SQLException, Exception {
        AddressBook address = new AddressBook(addressId);
        Member member = new Member(memberId);
        Orders order = new Orders(ordersDate, ordersPaymentType, ordersTtlPrice, ordersTax, ordersDeliveryFee, ordersExpressShipping, member, address);
        return dbTable.Orders.Add(new OrdersMapper(), order);
    }

    public boolean addOrderlist(int orderId, int productId, int ordersQuantity, double ordersSubprice) throws SQLException, Exception {
        Orders order = new Orders(orderId);
        Product product = new Product(productId);
        Orderlist ol = new Orderlist(order, product, ordersQuantity, ordersSubprice);
        return dbTable.Orderlist.Add(new OrderlistMapper(), ol);
    }

    public boolean addRateReview(String reviewText, int reviewRating, Date reviewDate, int productId, int memberId, int orderId) throws SQLException, Exception {
        Product product = new Product(productId);
        Member member = new Member(memberId);
        Orders order = new Orders(orderId);
        RateReview rate = new RateReview(reviewText, reviewRating, reviewDate, product, member, order);
        return dbTable.RateReview.Add(new RateReviewMapper(), rate);
    }

    public boolean addNew(String name, String phone, String no, String street, String city, String state, String postcode, int memberId) throws SQLException, Exception {
        AddressBook address = new AddressBook(name, phone, no, street, city, state, postcode);
        Member member = new Member(memberId);
        MemberAddress memberAddress = new MemberAddress(address, member);

        // Add address to the address book table
        boolean addressAdded = dbTable.AddressBook.Add(new AddressBookMapper(), address);

        // Add member and address relationship to the member address table
        boolean memberAddressAdded = dbTable.MemberAddress.Add(new MemberAddressMapper(), memberAddress);
//        dbTable.MemberAddress.Add(mapper, memberAddress);

        // Return true if both additions were successful
        return addressAdded && memberAddressAdded;
    }

    public boolean addMemberAddress1(String name, String phone, String no, String street, String city, String state, String postcode, int memberId) throws SQLException, Exception {
        AddressBook address = new AddressBook(name, phone, no, street, city, state, postcode);
        Member member = new Member(memberId);
        MemberAddress memberAddress = new MemberAddress(address, member);

        boolean memberAddressAdded = dbTable.MemberAddress.Add(new MemberAddressMapper(), memberAddress);

        return memberAddressAdded;
    }

    //calculate total
    // return clist and plist
    public static Map<String, List<?>> getCartAndProductLists(int memberId) throws SQLException {
        Map<String, List<?>> result = new HashMap<>();

        DBTable db = new DBTable();

        // Get cart id for the given member
        ArrayList<Object> condition = new ArrayList<>();
        condition.add(memberId);
        Cart cart = db.Cart.getData(new CartMapper(), condition, "SELECT * FROM CART WHERE member_id = ?").get(0);

        if (cart != null) {
            // Get cart list items
            List<Cartlist> cartList = db.Cartlist.getData(new CartlistMapper(), cart.getCartId());
            result.put("cartList", cartList);

            // Get product list items
            List<Product> productList = new ArrayList<>();
            for (Cartlist list : cartList) {
                Product product = db.Product.getData(new ProductMapper(), list.getProduct().getProductId()).get(0);
                if (product != null && product.getProductActive() != 0) {
                    productList.add(product);
                }
            }
            result.put("productList", productList);
        }

        return result;
    }

    public static ArrayList<Object> getAddressLists(int memberId) throws SQLException {
        ArrayList<Object> result = new ArrayList<>();

        DBTable db = new DBTable();
        ArrayList<Object> condition = new ArrayList<>();
        condition.add(memberId);

//        Cart cart = new DBTable().Cart.getData(new CartMapper(), condition, "SELECT * FROM CART WHERE member_id = ?").get(0);
        Member member = new DBTable().Member.getData(new MemberMapper(), condition, "SELECT * FROM MEMBER WHERE member_id = ?").get(0);

        if (member != null) {
            ArrayList<MemberAddress> mAddress = db.MemberAddress.getData(new MemberAddressMapper(), member.getMemberId());
            ArrayList<AddressBook> addressBook = new ArrayList<>();

            for (MemberAddress m : mAddress) {
                AddressBook a = db.AddressBook.getData(new AddressBookMapper(), m.getAddress().getAddressId()).get(0);
                if (a != null) {
                    addressBook.add(a);
                } else {
                    //no recod found
                }
            }

            result.add(mAddress);
            result.add(addressBook);
        }

        return result;
    }

    public static ArrayList<PaymentModel> getAddressItems(ArrayList<MemberAddress> memberAddresses, ArrayList<AddressBook> addressBooks) {
        ArrayList<PaymentModel> addressItems = new ArrayList<PaymentModel>();

        for (MemberAddress m : memberAddresses) {
            for (AddressBook a : addressBooks) {
                if (m.getAddress().getAddressId() == a.getAddressId()) {
                    PaymentModel addressItem = new PaymentModel(a);
                    addressItems.add(addressItem);
                }
            }
        }

        return addressItems;
    }

    public static ArrayList<PaymentModel> getCartItem(ArrayList<Cartlist> cart, ArrayList<Product> product) {
        ArrayList<PaymentModel> cartItems = new ArrayList<PaymentModel>();
        for (Cartlist c : cart) {
            for (Product p : product) {
                if (c.getProduct().getProductId() == p.getProductId()) {
                    PaymentModel cartItem = new PaymentModel(p, c.getCartQuantity());
//                    double total = c.getCartQuantity() * p.getProductPrice();
                    cartItems.add(cartItem);
                }
            }
        }

        return cartItems;
    }

//    public static double getDiscount(List<Product> productList, int productId) throws SQLException {
//        DBTable db = new DBTable();
//
//        ArrayList<Object> condition = new ArrayList<>();
//        condition.add(productId);
//
//        // Check if product has discount
//        List<Discount> discountList = db.Discount.getData(new DiscountMapper(), condition, "SELECT * FROM DISCOUNT WHERE product_id = ?");
//
//        if (!discountList.isEmpty()) {
//            // Get product price from productList
//            for (Product product : productList) {
//                if (product.getProductId() == productId) {
//                    double originalPrice = product.getProductPrice();
//
//                    // Apply discount
//                    Discount discount = discountList.get(0);
//                    double discountAmount = discount.getDiscountPercentage();
//                    double discountedPrice = (originalPrice * discountAmount) / 100;
//
//                    return discountedPrice;
//                }
//            }
//        }
//
//        // If product has no discount, return original price
//        for (Product product : productList) {
//            if (product.getProductId() == productId) {
//                return product.getProductPrice();
//            }
//        }
//
//        // If product not found, throw exception
//        throw new SQLException("Product not found in productList");
//    }
    public static double calculateGrandTotal(ArrayList<Cartlist> cart, ArrayList<Product> product, DBTable db) throws SQLException {
        double grandTotal = 0.0;

        for (Cartlist c : cart) {
            for (Product p : product) {
                if (c.getProduct().getProductId() == p.getProductId()) {
                    double originalPrice = p.getProductPrice();

                    Discount discount = DiscountController.getDiscount(db, p.getProductId()); // get the discount for the product

                    if (discount != null) {
                        double discountedPrice = DiscountController.getPrice(originalPrice, discount.getDiscountPercentage());
                        grandTotal += c.getCartQuantity() * discountedPrice;
                    } else {
                        grandTotal += c.getCartQuantity() * originalPrice;
                    }
                }
            }
        }

        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(grandTotal));
    }

    public static double calculateTax(double grandTotal) {
//        return grandTotal * 0.06;
        double tax = grandTotal * 0.06;
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.parseDouble(df.format(tax));
    }

    public static double calculateShippingCharge(String shippingMethod) {
        double shippingCharge = 0.00;

        if (shippingMethod != null && shippingMethod.equals("expressShipping")) {
            shippingCharge = 25.00;
        }

        return shippingCharge;
    }

    public static double calculateFinalTotal(double grandTotal, double tax, double shippingCharge, double deliveryFee) {
        double finalTotal = grandTotal + tax + shippingCharge + deliveryFee;
        finalTotal = Math.round(finalTotal * 100.0) / 100.0; // round to 2 decimal places
        return finalTotal;
    }

    public static String changeFormat(double number) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(number);
    }

    public static double calculateDeliveryFee(double grandTotal) {
        double delivery = 0.00;

        if (grandTotal < 200) {
            delivery = 25.00;
        }

        return delivery;
    }

}
