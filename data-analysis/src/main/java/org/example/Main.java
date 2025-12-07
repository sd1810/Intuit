package org.example;

import java.util.List;
import java.util.Map;


public class Main {
    public static void main(String[] args) {
        List<SaleRecord> records = CSVLoader.load("/Users/sakshid7/Sakshi Documents/Projects/Intuit/data-analysis/src/main/resources/sales_data_sample2.csv");

        System.out.println("Total Sales by Product Line:");
        System.out.println(SalesAnalysis.totalSalesByProductLine(records));

        System.out.println("\nTotal Sales by Country:");
        System.out.println(SalesAnalysis.totalSalesByCountry(records));

        System.out.println("\nAverage Sales by Deal Size:");
        System.out.println(SalesAnalysis.avgSalesByDealSize(records));

        System.out.println("\nOrders Per Year:");
        System.out.println(SalesAnalysis.ordersPerYear(records));

        System.out.println("\nRevenue Per Customer (Top 10):");
        SalesAnalysis.revenuePerCustomer(records).entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(10)
                .forEach(System.out::println);

        System.out.println("\nBest Selling Products:");
        SalesAnalysis.bestSellingProducts(records).entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(1)
                .forEach(System.out::println);
    }
}