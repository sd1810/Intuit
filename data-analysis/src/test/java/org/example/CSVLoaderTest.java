package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CSVLoaderTest {

    @TempDir
    Path tempDir;

    private Path testCsvFile;

    @BeforeEach
    void setUp() throws IOException {
        // Create a test CSV file
        testCsvFile = tempDir.resolve("test_sales.csv");
        String csvContent = """
                ORDERNUMBER,QUANTITYORDERED,PRICEEACH,ORDERLINENUMBER,SALES,ORDERDATE,STATUS,QTR_ID,MONTH_ID,YEAR_ID,PRODUCTLINE,MSRP,PRODUCTCODE,CUSTOMERNAME,PHONE,ADDRESSLINE1,ADDRESSLINE2,CITY,STATE,POSTALCODE,COUNTRY,TERRITORY,CONTACTLASTNAME,CONTACTFIRSTNAME,DEALSIZE
                10107,30,95.7,2,2871,2/24/2003 0:00,Shipped,1,2,2003,Motorcycles,95,S10_1678,Land of Toys Inc.,2125557818,897 Long Airport Avenue,,NYC,NY,10022,USA,NA,Yu,Kwai,Small
                10121,34,81.35,5,2765.9,5/7/2003 0:00,Shipped,2,5,2003,Motorcycles,95,S10_1678,Reims Collectables,26.47.1555,59 rue de l'Abbaye,,Reims,,51100,France,EMEA,Henriot,Paul,Small
                10134,41,94.74,2,3884.34,7/1/2003 0:00,Shipped,3,7,2003,Motorcycles,95,S10_1678,Lyon Souveniers,+33 1 46 62 7555,27 rue du Colonel Pierre Avia,,Paris,,75508,France,EMEA,Da Cunha,Daniel,Medium
                """;
        Files.writeString(testCsvFile, csvContent);
    }

    @Test
    void testLoadValidCsv() {
        List<SaleRecord> records = CSVLoader.load(testCsvFile.toString());
        
        assertNotNull(records);
        assertEquals(3, records.size());
        
        // Verify first record
        SaleRecord first = records.get(0);
        assertEquals(10107, first.orderNumber);
        assertEquals(30, first.quantityOrdered);
        assertEquals(95.7, first.priceEach, 0.01);
        assertEquals(2871, first.sales, 0.01);
        assertEquals("Motorcycles", first.productLine);
        assertEquals("USA", first.country);
        assertEquals("Small", first.dealSize);
    }

    @Test
    void testLoadSkipsHeader() {
        List<SaleRecord> records = CSVLoader.load(testCsvFile.toString());
        
        // Should skip header and only have data rows
        assertEquals(3, records.size());
        assertNotEquals("ORDERNUMBER", records.get(0).orderNumber);
    }

    @Test
    void testLoadEmptyFile() throws IOException {
        Path emptyFile = tempDir.resolve("empty.csv");
        Files.writeString(emptyFile, "ORDERNUMBER,QUANTITYORDERED\n");
        
        List<SaleRecord> records = CSVLoader.load(emptyFile.toString());
        assertTrue(records.isEmpty());
    }

    @Test
    void testLoadWithMissingFields() throws IOException {
        Path incompleteFile = tempDir.resolve("incomplete.csv");
        String csvContent = """
                ORDERNUMBER,QUANTITYORDERED,PRICEEACH,ORDERLINENUMBER,SALES,ORDERDATE,STATUS,QTR_ID,MONTH_ID,YEAR_ID,PRODUCTLINE,MSRP,PRODUCTCODE,CUSTOMERNAME,PHONE,ADDRESSLINE1,ADDRESSLINE2,CITY,STATE,POSTALCODE,COUNTRY,TERRITORY,CONTACTLASTNAME,CONTACTFIRSTNAME,DEALSIZE
                10107,30,95.7,2,2871,2/24/2003 0:00,Shipped,1,2,2003,Motorcycles,95,S10_1678,Land of Toys Inc.,2125557818,897 Long Airport Avenue,,NYC,NY,10022,USA,NA,Yu,Kwai,
                """;
        Files.writeString(incompleteFile, csvContent);
        
        List<SaleRecord> records = CSVLoader.load(incompleteFile.toString());
        assertEquals(1, records.size());
        // Deal size should be empty string, not null
        assertEquals("", records.get(0).dealSize);
    }
}

