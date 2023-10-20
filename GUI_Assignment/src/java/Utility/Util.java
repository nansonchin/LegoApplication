/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utility;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Yeet
 */
public class Util {
    public static Date stringToDate(String dateString, String format) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.parse(dateString);
    }
    
    public String[] parseWhereCondition(String where) {
        String sql = where;
        String[] result = new String[2];
        int start = sql.indexOf("WHERE") + 5;
        if (start == -1) {
            return null;
        }
        String condition = sql.substring(start).trim();
        String[] parts = condition.split("=");
        if (parts.length != 2) {
            return null;
        }
        result[0] = parts[0].trim();
        result[1] = parts[1].trim();
        return result;
    }
}
