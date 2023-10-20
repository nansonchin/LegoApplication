package DataAccess.Mapper;

import DataAccess.DBModel;
import java.sql.*;

/**
 * @author LOH XIN JIE
 */
public abstract class RowMapper<T extends DBModel> {

    public final String TABLE_PRIMARY;

    public RowMapper(String tablePrimary) {
        //SET PRIMARY KEY COL NAME
        this.TABLE_PRIMARY = tablePrimary;
    }

    public abstract T mapRow(ResultSet result) throws SQLException;

    public abstract PreparedStatement prepareAdd(Connection conn, T t) throws SQLException;

    public abstract PreparedStatement prepareUpdate(Connection conn, T t) throws SQLException;

    public abstract PreparedStatement prepareDelete(Connection conn, T t) throws SQLException;

    //Staff col name
    public static final String STAFF_ID = "staff_id";
    public static final String STAFF_NAME = "staff_name";
    public static final String STAFF_PASS = "staff_pass";
    public static final String STAFF_IC = "staff_ic";
    public static final String STAFF_PHNO = "staff_phone";
    public static final String STAFF_EMAIL = "staff_email";
    public static final String STAFF_BIRTHDATE = "staff_birthdate";

    //address col name
    public static final String ADDRESS_ID = "address_id";
    public static final String ADDRESS_NAME = "address_name";
    public static final String ADDRESS_PHONE = "address_phone";
    public static final String ADDRESS_NO = "address_no";
    public static final String ADDRESS_STREET = "address_street";
    public static final String ADDRESS_STATE = "address_state";
    public static final String ADDRESS_CITY = "address_city";
    public static final String ADDRESS_POSTCODE = "address_postcode";

    //member col name
    public static final String MEMBER_ID = "member_id";
    public static final String MEMBER_NAME = "member_name";
    public static final String MEMBER_PASS = "member_pass";

    //orders col name
    public static final String ORDERS_ID = "orders_id";
    public static final String ORDERS_DATE = "orders_date";
    public static final String ORDERS_PAYMENT_TYPE = "orders_payment_type";
    public static final String ORDERS_TTL_PRICE = "orders_ttlprice";
    public static final String ORDERS_TAX = "orders_tax";
    public static final String ORDERS_DELIVERY_FEE = "orders_delivery_fee";
    public static final String ORDERS_EXPRESS_SHIP = "orders_express_shipping";

    //product col name
    public static final String PRODUCT_ID = "product_id";
    public static final String PRODUCT_NAME = "product_name";
    public static final String PRODUCT_DESC = "product_desc";
    public static final String PRODUCT_PRICE = "product_price";
    public static final String PRODUCT_ACTIVE = "product_active";

    //orderlist col name
    public static final String ORDERS_QTY = "orders_quantity";
    public static final String ORDERS_SUBPRICE = "orders_subprice";

    //cart col name
    public static final String CART_ID = "cart_id";

    //carlist col name
    public static final String CART_QTY = "cart_quantity";

    //discount col name
    public static final String DISCOUNT_ID = "discount_id";
    public static final String DISCOUNT_PERCENTAGE = "discount_percentage";
    public static final String DISCOUNT_START_DATE = "discount_start_date";
    public static final String DISCOUNT_END_DATE = "discount_end_date";

    //ratereview col name
    public static final String REVIEW_ID = "review_id";
    public static final String REVIEW_TEXT = "review_text";
    public static final String REVIEW_RATING = "review_rating";
    public static final String REVIEW_DATE = "review_date";

    //imagetable
    public static final String IMAGE_ID = "image_id";
    public static final String TRANS_ID = "trans_id";
    public static final String IMAGE_NAME = "image_name";
    public static final String IMAGE_CONTENT = "image_contenttype";
    public static final String IMAGE = "image";
}
