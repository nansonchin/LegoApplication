/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DataAccess.DBTable;
import DataAccess.DBaccess;
import DataAccess.Mapper.AddressBookMapper;
import DataAccess.Mapper.CartMapper;
import DataAccess.Mapper.CartlistMapper;
import DataAccess.Mapper.MemberAddressMapper;
import DataAccess.Mapper.MemberMapper;
import Model.AddressBook;
import Model.Cart;
import Model.Cartlist;
import Model.Member;
import Model.MemberAddress;
import Model.Product;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Yeet
 */
public class MemController {

    public ArrayList<Member> getMems(String search) throws SQLException {
        DBTable dbTable = new DBTable();
            if (search == null) {
                return dbTable.getMember().getData(new MemberMapper());
            } else {
                ArrayList<Member> sfgs = dbTable.getMember().getData(new MemberMapper());
                    ArrayList<Member> members = new ArrayList<>();
                for (int i = 0; i < sfgs.size(); i++) {
                        if (Integer.toString(sfgs.get(i).getMemberId()).contains(search) || sfgs.get(i).getMemberName().toLowerCase().contains(search.toLowerCase())) {
                            members.add(sfgs.get(i));
                        }
                }
                return members;
            }
    }

    public boolean dltMem(int id) throws SQLException {
            DBTable dbTable = new DBTable();

            ArrayList<Member> members = dbTable.Member.getData(new MemberMapper(), id);
            Member member = new Member();
            if (members.size() == 1) {
                member = members.get(0);
            }
            ArrayList<MemberAddress> memAdds = dbTable.MemberAddress.getData(new MemberAddressMapper(), member.getMemberId());
            ArrayList<AddressBook> addressbooks = new ArrayList<>();

            if(DBaccess.customizeSqlSelect("SELECT * FROM member m INNER JOIN orders o ON m.member_id = o.member_id WHERE m.member_id = "+member.getMemberId()+"").isEmpty() == false){
                return false;
            }
            
            for (MemberAddress memAdd : memAdds) {
                ArrayList<AddressBook> faslkdfl = dbTable.AddressBook.getData(new AddressBookMapper(), memAdd.getAddress().getAddressId());
                if (faslkdfl.size() == 1) {
                    addressbooks.add(dbTable.AddressBook.getData(new AddressBookMapper(), faslkdfl.get(0).getAddressId()).get(0));
                }
                dbTable.MemberAddress.Delete(new MemberAddressMapper(), memAdd);
            }

            for (AddressBook addressbook : addressbooks) {
                dbTable.AddressBook.Delete(new AddressBookMapper(), addressbook);
            }
            
            List<HashMap<String, Object>> carts = DBaccess.customizeSqlSelect("SELECT cart_id FROM cart WHERE member_id = " + id);
            
            for(HashMap<String, Object> cart : carts){
                List<HashMap<String, Object>> cartlists = DBaccess.customizeSqlSelect("SELECT cart_id, product_id FROM cartlist WHERE cart_id = " + cart.get("CART_ID"));
                if(cartlists != null){
                for(HashMap<String, Object> cartlist : cartlists){
                    Cartlist cl = new Cartlist();
                    cl.setCart(new Cart(Integer.parseInt((String)cartlist.get("CART_ID"))));
                    cl.setProduct(new Product(Integer.parseInt((String)cartlist.get("PRODUCT_ID"))));
                    dbTable.Cartlist.Delete(new CartlistMapper(), cl);
                }
                }
                String cid = cart.get("CART_ID").toString();
                int cartid = Integer.parseInt((String)cid);
                dbTable.Cart.Delete(new CartMapper(), new Cart(cartid));
            }
            
            return dbTable.Member.Delete(new MemberMapper(), member);
       
    }
    
    public boolean dltMem(String id) throws SQLException {
        return dltMem(Integer.parseInt(id));
    }
}
