package org.example;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SalesAnalysis {

    // A. Total Sales by Product Line
    public static Map<String, Double> totalSalesByProductLine(List<SaleRecord> records) {
        return records.stream()
                .collect(Collectors.groupingBy(
                        r -> r.productLine,
                        Collectors.summingDouble(r -> r.sales)
                ));
    }

    // B. Total Sales by Country
    public static Map<String, Double> totalSalesByCountry(List<SaleRecord> records) {
        return records.stream()
                .collect(Collectors.groupingBy(
                        r -> r.country,
                        Collectors.summingDouble(r -> r.sales)
                ));
    }

    // C. Average Sales by Deal Size (Small, Medium, Large)
    public static Map<String, Double> avgSalesByDealSize(List<SaleRecord> records) {
        return records.stream()
                .collect(Collectors.groupingBy(
                        r -> r.dealSize,
                        Collectors.averagingDouble(r -> r.sales)
                ));
    }

    // D. Orders per Year
    public static Map<Integer, Long> ordersPerYear(List<SaleRecord> records) {
        return records.stream()
                .collect(Collectors.groupingBy(
                        r -> r.yearId,
                        Collectors.counting()
                ));
    }

    // E. Revenue per Customer
    public static Map<String, Double> revenuePerCustomer(List<SaleRecord> records) {
        return records.stream()
                .collect(Collectors.groupingBy(
                        r -> r.customerName,
                        Collectors.summingDouble(r -> r.sales)
                ));
    }

    // F. Best Selling Products by Quantity Ordered
    public static Map<String, Integer> bestSellingProducts(List<SaleRecord> records) {
        return records.stream()
                .collect(Collectors.groupingBy(
                        r -> r.productCode,
                        Collectors.summingInt(r -> r.quantityOrdered)
                ));
    }
}
