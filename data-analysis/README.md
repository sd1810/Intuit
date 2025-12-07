# Data Analysis Project

A Java-based sales data analysis application that processes CSV sales data and provides various analytical insights including sales by product line, country, deal size, and customer metrics.

## Overview

This project analyzes sales data from CSV files and generates comprehensive reports on:
- Total sales by product line
- Total sales by country
- Average sales by deal size (Small, Medium, Large)
- Orders per year
- Revenue per customer
- Best selling products by quantity

## Project Structure

```
data-analysis/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/example/
│   │   │       ├── Main.java              # Main entry point
│   │   │       ├── CSVLoader.java         # CSV file loader
│   │   │       ├── SaleRecord.java        # Data model for sales records
│   │   │       └── SalesAnalysis.java     # Analysis methods
│   │   └── resources/
│   │       └── sales_data_sample2.csv     # Sample sales data
│   └── test/
│       └── java/
│           └── org/example/
│               ├── CSVLoaderTest.java     # Tests for CSVLoader
│               └── SalesAnalysisTest.java # Tests for SalesAnalysis
├── pom.xml                                # Maven configuration
└── README.md                              # This file
```

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Building the Project

To compile the project:

```bash
  mvn clean compile
```

## Running the Application

To run the main application:

1. Compile the project:
```bash
  mvn compile
```

2. Run the main class:
```bash
  java -cp target/classes org.example.Main
```

## Running Tests

To run all unit tests:

```bash
  mvn test
```


## Features

### 1. CSV Data Loading (`CSVLoader`)
- Loads sales data from CSV files
- Handles CSV parsing with proper field separation
- Skips header row automatically
- Robust error handling for file operations

### 2. Sales Record Model (`SaleRecord`)
- Represents a single sales record
- Handles missing or empty fields gracefully
- Safe parsing for numeric and string fields
- Supports all CSV columns including order details, customer info, and geographic data

### 3. Sales Analysis (`SalesAnalysis`)
Provides the following analytical methods:

- **`totalSalesByProductLine()`**: Calculates total sales grouped by product line
- **`totalSalesByCountry()`**: Calculates total sales grouped by country
- **`avgSalesByDealSize()`**: Calculates average sales for Small, Medium, and Large deals
- **`ordersPerYear()`**: Counts the number of orders per year
- **`revenuePerCustomer()`**: Calculates total revenue per customer
- **`bestSellingProducts()`**: Identifies best selling products by total quantity ordered

## CSV File Format

The expected CSV format includes the following columns:

```
ORDERNUMBER, QUANTITYORDERED, PRICEEACH, ORDERLINENUMBER, SALES, 
ORDERDATE, STATUS, QTR_ID, MONTH_ID, YEAR_ID, PRODUCTLINE, MSRP, 
PRODUCTCODE, CUSTOMERNAME, PHONE, ADDRESSLINE1, ADDRESSLINE2, 
CITY, STATE, POSTALCODE, COUNTRY, TERRITORY, CONTACTLASTNAME, 
CONTACTFIRSTNAME, DEALSIZE
```

## Example Output

When running the application, you'll see output like:

```
Total Sales by Product Line:
{Trains=226243.47, Vintage Cars=1903150.84, Planes=975003.57, Ships=714437.13, Motorcycles=1166388.34, Trucks and Buses=1127789.84, Classic Cars=3919615.66}

Total Sales by Country:
{USA=3627982.83, Singapore=288488.41, Philippines=94015.73, Japan=188167.81, Switzerland=117713.56, Spain=1215686.92, Canada=224078.56, Sweden=210014.21, Austria=202062.53, Belgium=108412.62, Norway=307463.7, Ireland=57756.43, UK=478880.46, Finland=329581.91, Denmark=245637.15, Italy=374674.31, Australia=630623.1, France=1110916.52, Germany=220472.09}

Average Sales by Deal Size:
{Small=2061.6828003120127, Medium=4398.433699421966, Large=8293.753248407644}

Orders Per Year:
{2003=1000, 2004=1345, 2005=478}

Revenue Per Customer (Top 10):
Euro Shopping Channel=912294.11
Mini Gifts Distributors Ltd.=654858.06
"Australian Collectors, Co."=200995.41
Muscle Machine Inc=197736.94
La Rochelle Gifts=180124.9
"Dragon Souveniers, Ltd."=172989.68
Land of Toys Inc.=164069.44
The Sharp Gifts Warehouse=160010.27
"AV Stores, Co."=157807.81
"Anna's Decorations, Ltd"=153996.13

Best Selling Products:
S18_3232=1774

```

## Testing

The project includes comprehensive unit tests covering:

- CSV loading with various scenarios (valid files, empty files, missing fields)
- SaleRecord creation and field parsing
- All analysis methods with sample data
- Edge cases (empty lists, null values, missing fields)

## Configuration

The main class currently uses a hardcoded path to the CSV file. To use a different file, modify the path in `Main.java`:

```java
List<SaleRecord> records = CSVLoader.load("path/to/your/file.csv");
```

## Error Handling

The application includes robust error handling:
- File not found exceptions are caught and wrapped in RuntimeException
- Missing or empty CSV fields are handled gracefully (defaulting to 0 for numbers, empty string for text)
- Array index out of bounds is prevented with safe field access

