# Intuit Projects

This repository contains two Java projects demonstrating different programming concepts and patterns:

1. **Producer-Consumer Pattern** - A concurrent programming implementation
2. **Data Analysis** - A sales data analysis application

## Table of Contents

- [Overview](#overview)
- [Projects](#projects)
  - [Producer-Consumer Pattern](#producer-consumer-pattern)
  - [Data Analysis](#data-analysis)
- [Prerequisites](#prerequisites)
- [Building and Running](#building-and-running)
- [Project Structure](#project-structure)

## Overview

This multi-module Maven project showcases:
- **Concurrent Programming**: Thread-safe producer-consumer pattern implementation
- **Data Processing**: CSV parsing and sales data analysis

Both projects use Java 17 and Maven for build management.

## Projects

### Producer-Consumer Pattern

A Java implementation of the classic Producer-Consumer pattern using `BlockingQueue` for thread-safe communication between multiple producers and consumers.

#### Key Features

- **Thread-safe communication** using `BlockingQueue`
- **Synchronized coordination** between producers and consumers via `StartSignal`
- **Graceful shutdown** using sentinel values
- **Multiple producers and consumers** support
- **Thread-safe destination list** using `Collections.synchronizedList()`

#### Components

- **Producer**: Generates items and places them in a shared queue
- **Consumer**: Retrieves items from the queue and processes them
- **StartSignal**: Ensures consumers wait until at least one item is produced
- **Main**: Orchestrates the producer-consumer system with user input

#### Running the Producer-Consumer Project

```bash
  cd producer-consumer
  mvn compile
  java -cp target/classes org.example.Main
```

The application will prompt you for:
- Number of producers
- Number of consumers
- Number of items each producer should produce

#### Example Output

```
Enter number of producers: 4
Enter number of consumers: 2
Enter number of items each producer should produce: 2
Producer 1 → produced: 1001
Producer 1 → produced: 1002
Consumer 1 consumed → 1001
Consumer 2 consumed → 1002
...
```

For more details, see the [Producer-Consumer README](producer-consumer/README.md).

---

### Data Analysis

A comprehensive sales data analysis application that processes CSV files and generates various analytical insights.

#### Key Features

- **CSV Data Loading**: Robust CSV parsing with error handling
- **Sales Analytics**: Multiple analysis methods including:
  - Total sales by product line
  - Total sales by country
  - Average sales by deal size (Small, Medium, Large)
  - Orders per year
  - Revenue per customer
  - Best selling products by quantity

#### Running the Data Analysis Project

```bash
  # Navigate to the data-analysis module
  cd data-analysis
  
  # Compile
  mvn clean compile
  
  # Run the application
  java -cp target/classes org.example.Main
```

#### Example Output

```
Total Sales by Product Line:
{Trains=226243.47, Vintage Cars=1903150.84, Planes=975003.57, ...}

Total Sales by Country:
{USA=3627982.83, Singapore=288488.41, Philippines=94015.73, ...}

Average Sales by Deal Size:
{Small=2061.68, Medium=4398.43, Large=8293.75}

Orders Per Year:
{2003=1000, 2004=1345, 2005=478}

Revenue Per Customer (Top 10):
Euro Shopping Channel=912294.11
Mini Gifts Distributors Ltd.=654858.06
...
```

For more details, see the [Data Analysis README](data-analysis/README.md).

## Prerequisites

- **Java 17** or higher
- **Maven 3.6** or higher

## Building and Running

### Build All Projects

From the root directory:

```bash
  mvn clean compile
```

### Run Tests

```bash
  # Run all tests
  mvn test

  # Run tests for a specific module
  cd data-analysis
  mvn test
```

### Package Projects

```bash
  mvn package
```

## Project Structure

```
Intuit/
├── pom.xml                          # Parent POM with shared dependencies
├── README.md                        # This file
│
├── src/                             # Producer-Consumer project
│   ├── main/
│   │   └── java/
│   │       └── org/example/
│   │           ├── Main.java           # Main application
│   │           ├── Producer.java       # Producer implementation
│   │           ├── Consumer.java       # Consumer implementation
│   │           └── StartSignal.java    # Synchronization helper
│   └── test/
│       └── java/
│           └── org/example/
│               ├── ProducerTest.java   # Producer tests
│               ├── ConsumerTest.java   # Consumer tests
│               └── MainTest.java       # Main tests
│
└── data-analysis/                   # Data Analysis module
    ├── pom.xml                      # Module POM
    ├── README.md                    # Module documentation
    ├── src/
    │   ├── main/
    │   │   ├── java/
    │   │   │   └── org/example/
    │   │   │       ├── Main.java              # Main entry point
    │   │   │       ├── CSVLoader.java         # CSV file loader
    │   │   │       ├── SaleRecord.java        # Data model
    │   │   │       └── SalesAnalysis.java     # Analysis methods
    │   │   └── resources/
    │   │       └── sales_data_sample2.csv     # Sample data
    │   └── test/
    │       └── java/
    │           └── org/example/
    │               ├── CSVLoaderTest.java     # CSVLoader tests
    │               └── SalesAnalysisTest.java # SalesAnalysis tests
    └── target/                      # Compiled classes
```

## Dependencies

Both projects share common dependencies defined in the parent POM:

- **JUnit 5** (junit-jupiter 5.10.0): For unit testing
- **Maven Surefire Plugin**: For running tests

## Testing

Both projects include comprehensive unit tests:

- **Producer-Consumer**: Tests for Producer, Consumer, and Main classes
- **Data Analysis**: Tests for CSVLoader, SaleRecord, and SalesAnalysis classes

Run all tests:

```bash
  mvn test
```

## Technologies Used

- **Java 17**: Programming language
- **Maven**: Build and dependency management
- **JUnit 5**: Testing framework
- **Java Concurrency API**: For producer-consumer implementation
- **Java Streams API**: For data analysis operations

