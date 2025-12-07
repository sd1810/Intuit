package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SalesAnalysisTest {

    private List<SaleRecord> testRecords;

    @BeforeEach
    void setUp() {
        testRecords = List.of(
            createRecord(10107, 30, 2871.0, 2003, "Motorcycles", "USA", "Small", "Customer A", "S10_1678"),
            createRecord(10121, 34, 2765.9, 2003, "Motorcycles", "France", "Small", "Customer B", "S10_1678"),
            createRecord(10134, 41, 3884.34, 2003, "Motorcycles", "France", "Medium", "Customer A", "S10_1678"),
            createRecord(10145, 45, 3746.7, 2004, "Classic Cars", "USA", "Medium", "Customer C", "S12_1108"),
            createRecord(10156, 20, 1500.0, 2004, "Classic Cars", "USA", "Large", "Customer C", "S12_1108"),
            createRecord(10167, 25, 2000.0, 2004, "Vintage Cars", "UK", "Large", "Customer D", "S18_1129")
        );
    }

    private SaleRecord createRecord(int orderNumber, int quantity, double sales, int year,
                                   String productLine, String country, String dealSize,
                                   String customerName, String productCode) {
        String[] fields = {
            String.valueOf(orderNumber),
            String.valueOf(quantity),
            String.valueOf(sales / quantity),
            "1",
            String.valueOf(sales),
            "1/1/2003 0:00",
            "Shipped",
            "1",
            "1",
            String.valueOf(year),
            productLine,
            "100",
            productCode,
            customerName,
            "1234567890",
            "Address",
            "",
            "City",
            "State",
            "12345",
            country,
            "NA",
            "Last",
            "First",
            dealSize
        };
        return new SaleRecord(fields);
    }

    @Test
    void testTotalSalesByProductLine() {
        Map<String, Double> result = SalesAnalysis.totalSalesByProductLine(testRecords);
        
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(9521.24, result.get("Motorcycles"), 0.01); // 2871 + 2765.9 + 3884.34
        assertEquals(5246.7, result.get("Classic Cars"), 0.01); // 3746.7 + 1500.0
        assertEquals(2000.0, result.get("Vintage Cars"), 0.01);
    }

    @Test
    void testTotalSalesByCountry() {
        Map<String, Double> result = SalesAnalysis.totalSalesByCountry(testRecords);
        
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(8117.7, result.get("USA"), 0.01); // 2871 + 3746.7 + 1500.0
        assertEquals(6650.24, result.get("France"), 0.01); // 2765.9 + 3884.34
        assertEquals(2000.0, result.get("UK"), 0.01);
    }

    @Test
    void testAvgSalesByDealSize() {
        Map<String, Double> result = SalesAnalysis.avgSalesByDealSize(testRecords);
        
        assertNotNull(result);
        assertEquals(3, result.size());
        
        // Small: (2871 + 2765.9) / 2 = 2818.45
        assertEquals(2818.45, result.get("Small"), 0.01);
        
        // Medium: (3884.34 + 3746.7) / 2 = 3815.52
        assertEquals(3815.52, result.get("Medium"), 0.01);
        
        // Large: (1500.0 + 2000.0) / 2 = 1750.0
        assertEquals(1750.0, result.get("Large"), 0.01);
    }

    @Test
    void testOrdersPerYear() {
        Map<Integer, Long> result = SalesAnalysis.ordersPerYear(testRecords);
        
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(3L, result.get(2003));
        assertEquals(3L, result.get(2004));
    }

    @Test
    void testRevenuePerCustomer() {
        Map<String, Double> result = SalesAnalysis.revenuePerCustomer(testRecords);
        
        assertNotNull(result);
        assertEquals(4, result.size());
        assertEquals(6755.34, result.get("Customer A"), 0.01); // 2871 + 3884.34
        assertEquals(2765.9, result.get("Customer B"), 0.01);
        assertEquals(5246.7, result.get("Customer C"), 0.01); // 3746.7 + 1500.0
        assertEquals(2000.0, result.get("Customer D"), 0.01);
    }

    @Test
    void testBestSellingProducts() {
        Map<String, Integer> result = SalesAnalysis.bestSellingProducts(testRecords);
        
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(105, result.get("S10_1678")); // 30 + 34 + 41
        assertEquals(65, result.get("S12_1108")); // 45 + 20
        assertEquals(25, result.get("S18_1129"));
    }

    @Test
    void testTotalSalesByProductLineWithEmptyList() {
        Map<String, Double> result = SalesAnalysis.totalSalesByProductLine(List.of());
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testTotalSalesByCountryWithEmptyList() {
        Map<String, Double> result = SalesAnalysis.totalSalesByCountry(List.of());
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testAvgSalesByDealSizeWithEmptyList() {
        Map<String, Double> result = SalesAnalysis.avgSalesByDealSize(List.of());
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testOrdersPerYearWithEmptyList() {
        Map<Integer, Long> result = SalesAnalysis.ordersPerYear(List.of());
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testRevenuePerCustomerWithEmptyList() {
        Map<String, Double> result = SalesAnalysis.revenuePerCustomer(List.of());
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testBestSellingProductsWithEmptyList() {
        Map<String, Integer> result = SalesAnalysis.bestSellingProducts(List.of());
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}

