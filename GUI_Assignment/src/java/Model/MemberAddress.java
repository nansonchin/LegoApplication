package Model;

import DataAccess.DBModel;

/**
 * @author LOH XIN JIE
 */
public class MemberAddress extends DBModel {

    private AddressBook address;
    private Member member;

    public MemberAddress(AddressBook address, Member member) {
        super("member_address");
        this.address = address;
        this.member = member;
    }

    public MemberAddress(Member member) {
        super("member_address");
        this.member = member;
    }

    public MemberAddress(AddressBook address) {
        super("member_address");
        this.address = address;
    }

    public MemberAddress() {
        super("member_address");
    }

    public AddressBook getAddress() {
        return address;
    }

    public Member getMember() {
        return member;
    }

    public void setAddress(AddressBook address) {
        this.address = address;
    }

    public void setMember(Member member) {
        this.member = member;
    }

}
