package DataAccess;

/**
 * @author LOH XIN JIE
 */
public class DBModel {

    /**
     * DBModel is use to distinguish the Model can use in T <br/>
     * T Must be DBModel
     */
    public final String TABLENAME;

    public DBModel(String tableName) {
        this.TABLENAME = tableName;
    }

    public String getTABLENAME() {
        return TABLENAME;
    }
}
