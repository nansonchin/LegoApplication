package Model;

import DataAccess.DBModel;

/**
 * @author LOH XIN JIE
 */
public class AddressBook extends DBModel {

    private int addressId;
    private String addressName;
    private String addressPhone;
    private String addressNo;
    private String addressStreet;
    private String addressState;
    private String addressCity;
    private String addressPostcode;

    public AddressBook(int addressId, String addressName, String addressPhone, String addressNo, String addressStreet, String addressState, String addressCity, String addressPostcode) {
        super("addressbook");
        this.addressId = addressId;
        this.addressName = addressName;
        this.addressPhone = addressPhone;
        this.addressNo = addressNo;
        this.addressStreet = addressStreet;
        this.addressState = addressState;
        this.addressCity = addressCity;
        this.addressPostcode = addressPostcode;
    }

    public AddressBook(String addressName, String addressPhone, String addressNo, String addressStreet, String addressState, String addressCity, String addressPostcode) {
        super("addressbook");
        this.addressName = addressName;
        this.addressPhone = addressPhone;
        this.addressNo = addressNo;
        this.addressStreet = addressStreet;
        this.addressState = addressState;
        this.addressCity = addressCity;
        this.addressPostcode = addressPostcode;
    }

    public AddressBook(int addressId) {
        super("addressbook");
        this.addressId = addressId;
    }

    public AddressBook() {
        super("addressbook");
    }

    public int getAddressId() {
        return addressId;
    }

    public String getAddressName() {
        return addressName;
    }

    public String getAddressPhone() {
        return addressPhone;
    }

    public String getAddressNo() {
        return addressNo;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public String getAddressState() {
        return addressState;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public String getAddressPostcode() {
        return addressPostcode;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public void setAddressPhone(String addressPhone) {
        this.addressPhone = addressPhone;
    }

    public void setAddressNo(String addressNo) {
        this.addressNo = addressNo;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public void setAddressPostcode(String addressPostcode) {
        this.addressPostcode = addressPostcode;
    }
}
