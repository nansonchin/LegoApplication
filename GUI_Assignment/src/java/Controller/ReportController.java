/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DataAccess.DBaccess;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Yeet
 */
public class ReportController {

    private final static String[] SalesColumnName = {
    };

    private final static Map<String, String> SalesGroupByOption = new HashMap<String, String>() {
        {
            put("p.product_id", "p.product_id, p.product_name");
        }
    };

    private final static String[] SalesOrderByOption = {
        
        "total_units_sold",
        ""
    };

    String query;

    public ReportController(String dateFrom, String dateTo) {

            
            this.query = "SELECT p.product_id, p.product_name, SUM(ol.orders_quantity) AS total_units_sold, SUM(ol.orders_subprice) AS total_revenue "
                    + "FROM orders o "
                    + "JOIN orderlist ol ON o.orders_id = ol.orders_id "
                    + "JOIN product p on ol.product_id = p.product_id "
                    + "WHERE o.orders_date > '" + dateFrom + "' "
                    + "AND o.orders_date < '" + dateTo + "' "
                    + "GROUP BY p.product_id, p.product_name "
                    + "ORDER BY total_revenue";

    }

    public String getQuery() {
        return query;
    }

    public static String decodeSalesColumn(String column) {
        return decoder(column, ReportController.SalesColumnName);
    }

    public static String decodeSalesOrderBy(String groupby, String acs) {
        String input = decoder(groupby, ReportController.SalesOrderByOption);
        String prefix = acs;

        StringBuilder output = new StringBuilder();
        String[] values = input.split(",");
        for (int i = 0; i < values.length; i++) {
            output.append(values[i]).append(" ").append(prefix).append(",");
        }

        if (output.length() > 0) {
            output.setLength(output.length() - 1);
        }
        return output.toString();
    }

    private static String decoder(String column, Map<String, String> field) {
        if (column.equals("")) {
            return column;
        }
        String[] columnCode = decodeInput(column);
        StringBuilder sb = new StringBuilder();
        for (String code : columnCode) {
            String option = field.get(code);
            if (option != null) {
                sb.append(option.trim()).append(",");
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    public static String salesFormatDisplay(String inputStr) {
        Map<String, String> columnMap = new HashMap<>();
        columnMap.put("product_id", "Product Id");
        columnMap.put("product_name", "Product Name");
        columnMap.put("total_units_sold", "Total Units Sold");
        columnMap.put("total_revenue", "Total Revenue");

        String[] parts = inputStr.split(",");
        StringBuilder outputBuilder = new StringBuilder();
        boolean hasDesc = false;
        boolean hasOrderBy = false;
        for (String part : parts) {
            part = part.trim();
            if (part.startsWith("GROUP BY ")) {
                outputBuilder.append("GROUP BY ");
                part = part.substring(9);
            } else if (part.startsWith("ORDER BY ")) {
                outputBuilder.append("ORDER BY ");
                part = part.substring(9);
                hasOrderBy = true;
            }

            String[] columnParts = part.split("\\.");
            String columnName = columnParts[columnParts.length - 1];

            if (columnName.endsWith(" DESC")) {
                columnName = columnName.substring(0, columnName.length() - 5);
                hasDesc = true;
            }

            String formattedName = columnMap.getOrDefault(columnName, columnName);
            columnParts[columnParts.length - 1] = formattedName;

            if (columnParts.length == 2 && columnParts[0].equals("p")) {
                columnParts[0] = "";
            }

            outputBuilder.append(String.join(".", columnParts));
            outputBuilder.append(", ");
        }

        outputBuilder.setLength(outputBuilder.length() - 2);

        if (!hasDesc && hasOrderBy) {
            outputBuilder.append(" ASC");
        }

        return outputBuilder.toString().replace('.', ' ');
    }

    public static String decodeSalesGroupBy(Map<String, String> map) {
        String concatenatedKeys = "," + String.join(",", map.keySet());
        return decoder(concatenatedKeys, map);
    }

    private static String decoder(String column, String[] field) {
        if (column.equals("")) {
            return column;
        }
        String[] columnCode = ReportController.decodeInput(column);
        column = field[Integer.parseInt(columnCode[0]) - 1];
        for (int i = 1; i < columnCode.length; i++) {
            column += "," + field[Integer.parseInt(columnCode[i]) - 1];
        }
        return column;
    }

    public static String[] decodeInput(String input) {
        String[] parts = input.split(","); // split the input string at commas
        String[] output = Arrays.copyOfRange(parts, 1, parts.length);
        return output;
    }

    public static List<HashMap<String, Object>> reportSelector(String query) {
        return DBaccess.customizeSqlSelect(query);
    }

    public List<HashMap<String, Object>> salesReport() {
        return reportSelector(query);
    }

    public List<String> getDynamicColumnNames() {
        List<String> columnNames = new ArrayList<>();
        Pattern pattern = Pattern.compile("SELECT\\s+(.*?)\\s+FROM", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(query);

        if (matcher.find()) {
            String column_names = matcher.group(1);
            column_names = column_names.replaceAll("\\n\\s+", " ");
            for (String column : column_names.split(",")) {

                columnNames.add(nameToDisplay(column).toString().trim());
            }
        }

        return columnNames;
    }

    public static StringBuilder nameToDisplay(String column) {
        column = column.trim();
        if (column.contains(" AS ")) {
            column = column.split(" AS ")[1];
        } else {
            column = column.replaceAll("^[^.]*\\.", "");
        }
        column = column.replaceAll("_", " ");
        String[] words = column.split(" ");
        StringBuilder formattedColumn = new StringBuilder();
        for (String word : words) {
            formattedColumn.append(word.substring(0, 1).toUpperCase()).append(word.substring(1).toLowerCase()).append(" ");
        }
        return formattedColumn;
    }

    public static List<String> getSalesColumnNamesOption() {
        List<String> names = new ArrayList<>();
        for (String option : ReportController.SalesColumnName) {

            names.add(nameToDisplay(option).toString().trim());
        }

        return names;
    }

    public static List<String> getSalesGroupByNamesOption() {
        List<String> names = new ArrayList<>();
        for (String option : SalesGroupByOption.keySet()) {
            names.add(nameToDisplay(option).toString().trim());
        }
        return names;
    }

    public static List<String> getSalesOrderByNamesOption() {
        List<String> names = new ArrayList<>();
        for (String option : ReportController.SalesOrderByOption) {

            names.add(nameToDisplay(option).toString().trim());
        }

        return names;
    }
}
