# Producer-Consumer Pattern Implementation

A Java implementation of the classic Producer-Consumer pattern using `BlockingQueue` for thread-safe communication between multiple producers and consumers.

## Overview

This project demonstrates a concurrent programming pattern where:
- **Producers** generate items and place them in a shared queue
- **Consumers** retrieve items from the queue and process them
- A **StartSignal** mechanism ensures consumers wait until at least one item is produced before starting to consume

## Architecture

### Components

1. **Producer** (`Producer.java`)
   - Implements `Runnable` interface
   - Generates items with the formula: `producerId * 1000 + itemNumber`
   - Places items into a shared `BlockingQueue`
   - Signals consumers when the first item is produced

2. **Consumer** (`Consumer.java`)
   - Implements `Runnable` interface
   - Waits for the first item to be produced before starting consumption
   - Retrieves items from the queue and adds them to a destination list
   - Exits when it receives a sentinel value (-1)

4. **Main** (`Main.java`)
   - Orchestrates the producer-consumer system
   - Accepts user input for number of producers, consumers, and items per producer
   - Manages thread lifecycle and termination

## Features

- **Thread-safe communication** using `BlockingQueue`
- **Synchronized coordination** between producers and consumers
- **Graceful shutdown** using -1 values
- **Multiple producers and consumers** support
- **Thread-safe destination list** using `Collections.synchronizedList()`

## Requirements

- Java 17 or higher
- Maven 3.6 or higher

## Building the Project

```bash
  # Compile the project
  mvn compile

  # Run tests
  mvn test

  # Package the project
  mvn package

  # Run the application
  mvn exec:java -Dexec.mainClass="org.example.Main"
```

## Running the Application

1. Compile the project:
```bash
  mvn compile
```

2. Run the main class:
```bash
  java -cp target/classes org.example.Main
```

3. Enter the following when prompted:
   - Number of producers
   - Number of consumers
   - Number of items each producer should produce

### Example Run

```
Enter number of producers: 4
Enter number of consumers: 2
Enter number of items each producer should produce: 2
Producer 1 → produced: 1001
Producer 1 → produced: 1002
Producer 2 → produced: 2001
Producer 3 → produced: 3001
Producer 3 → produced: 3002
Producer 4 → produced: 4001
Producer 4 → produced: 4002
Producer 2 → produced: 2002
Consumer 1 consumed → 1001
Consumer 2 consumed → 2001
Consumer 1 consumed → 3001
Consumer 1 consumed → 1002
Consumer 2 consumed → 4001
Consumer 2 consumed → 3002
Consumer 1 consumed → 2002
Consumer 2 consumed → 4002
Consumer 1 exiting...
Consumer 2 exiting...

FINAL DESTINATION LIST: [1001, 2001, 3001, 4001, 1002, 2002, 3002, 4002]

```

## How It Works

1. **Initialization**: Main creates a shared `BlockingQueue` and a synchronized destination list
2. **Thread Creation**: Producer and consumer threads are created and started
3. **Producer Behavior**:
   - Produces items and adds them to the queue
4. **Consumer Behavior**:
   - Consumes items from the queue
   - Adds consumed items to the destination list
   - Exits when receiving sentinel value (-1)
5. **Termination**:
   - Main waits for all producers to finish
   - Sends sentinel values (-1) to signal consumers to exit
   - Waits for all consumers to finish
   - Displays the final destination list

## Testing

The project includes comprehensive unit tests using JUnit 5:

- **ProducerTest**: Tests producer functionality including item generation, signal setting, and multi-producer scenarios
- **ConsumerTest**: Tests consumer functionality including waiting behavior, consumption, and thread safety
- **MainTest**: Tests the main application flow with various configurations

Run tests with:
```bash
  mvn test
```

## Project Structure

```
Intuit/
├── pom.xml                          # Maven configuration
├── README.md                        # This file
├── src/
│   ├── main/
│   │   └── java/
│   │       └── org/
│   │           └── example/
│   │               ├── Main.java           # Main application
│   │               ├── Producer.java       # Producer implementation
│   │               ├── Consumer.java       # Consumer implementation
│   └── test/
│       └── java/
│           └── org/
│               └── example/
│                   ├── ProducerTest.java   # Producer tests
│                   ├── ConsumerTest.java   # Consumer tests
└── target/                         # Compiled classes (generated)
```

## Limitations

- Queue size is fixed at 50 items (hardcoded in Main.java)
- Item generation formula assumes producer IDs start at 1

## Future Enhancements

- Configurable queue size
- Better error handling and validation
- Logging framework integration
- Performance metrics and monitoring
- Configurable item generation strategies


