package org.example;

public class SaleRecord {

    public final int orderNumber;
    public final int quantityOrdered;
    public final double priceEach;
    public final int orderLineNumber;
    public final double sales;
    public final String orderDate;
    public final String status;
    public final int quarterId;
    public final int monthId;
    public final int yearId;
    public final String productLine;
    public final double msrp;
    public final String productCode;
    public final String customerName;
    public final String country;
    public final String territory;
    public final String dealSize;

    public SaleRecord(String[] fields) {

        // Fix: Use safe parsing for fields that may be empty
        this.orderNumber = parseIntSafe(fields[0]);
        this.quantityOrdered = parseIntSafe(fields[1]);
        this.priceEach = parseDoubleSafe(fields[2]);
        this.orderLineNumber = parseIntSafe(fields[3]);
        this.sales = parseDoubleSafe(fields[4]);

        this.orderDate = safe(fields[5]);
        this.status = safe(fields[6]);
        this.quarterId = parseIntSafe(fields[7]);
        this.monthId = parseIntSafe(fields[8]);
        this.yearId = parseIntSafe(fields[9]);

        this.productLine = safe(fields[10]);
        this.msrp = parseDoubleSafe(fields[11]);
        this.productCode = safe(fields[12]);
        this.customerName = safe(fields[13]);

        // Some rows may not have all fields (empty state, address, postal)
        this.country = getSafe(fields, 20);
        this.territory = getSafe(fields, 21);
        this.dealSize = getSafe(fields, 24);
    }


    private String safe(String s) {
        return (s == null) ? "" : s;
    }

    private String getSafe(String[] arr, int index) {
        return (index < arr.length) ? arr[index].trim() : "";
    }

    private int parseIntSafe(String s) {
        if (s == null || s.isEmpty()) return 0;
        return Integer.parseInt(s);
    }

    private double parseDoubleSafe(String s) {
        if (s == null || s.isEmpty()) return 0.0;
        return Double.parseDouble(s);
    }
}