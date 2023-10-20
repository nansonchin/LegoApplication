package Model;

import DataAccess.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author LOH XIN JIE
 */
public class Staff extends DBModel {

    private int staffId;
    private String staffName;
    private String staffPass;
    private String staffIc;
    private String staffPhNo;
    private String staffEmail;
    private Date staffBirthdate;

    public Staff(int staffId, String staffName, String staffPass, String staffIc, String staffPhNo, String staffEmail, Date staffBirthdate) {
        super("staff");
        this.staffId = staffId;
        this.staffName = staffName;
        this.staffPass = staffPass;
        this.staffIc = staffIc;
        this.staffPhNo = staffPhNo;
        this.staffEmail = staffEmail;
        this.staffBirthdate = staffBirthdate;
    }
    
    public Staff(String staffName, String staffPass, String staffIc, String staffPhNo, String staffEmail, Date staffBirthdate) {
        super("staff");
        this.staffName = staffName;
        this.staffPass = staffPass;
        this.staffIc = staffIc;
        this.staffPhNo = staffPhNo;
        this.staffEmail = staffEmail;
        this.staffBirthdate = staffBirthdate;
    }

    public Staff(int staffId) {
        super("staff");
        this.staffId = staffId;
    }

    public Staff() {
        super("staff");
    }

    public int getStaffId() {
        return staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public String getStaffPass() {
        return staffPass;
    }

    public String getStaffIc() {
        return staffIc;
    }

    public String getStaffPhNo() {
        return staffPhNo;
    }

    public String getStaffEmail() {
        return staffEmail;
    }

    public Date getStaffBirthdate() {
        return staffBirthdate;
    }
    
    public String getDisplayFormatBirthdate(){
        return new SimpleDateFormat("dd/MM/yyyy").format(staffBirthdate);
    }
    public String getEditFormatBirthdate(){
        return new SimpleDateFormat("yyyy-MM-dd").format(staffBirthdate);
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public void setStaffPass(String staffPass) {
        this.staffPass = staffPass;
    }

    public void setStaffIc(String staffIc) {
        this.staffIc = staffIc;
    }

    public void setStaffPhNo(String staffPhNo) {
        this.staffPhNo = staffPhNo;
    }

    public void setStaffEmail(String staffEmail) {
        this.staffEmail = staffEmail;
    }

    public void setStaffBirthdate(Date staffBirthdate) {
        this.staffBirthdate = staffBirthdate;
    }
}
