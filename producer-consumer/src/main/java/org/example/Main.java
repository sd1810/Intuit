package org.example;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    private static int readPositiveInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(sc.nextLine().trim());

                if (value <= 0) {
                    System.out.println("Please enter a number greater than 0.");
                    continue;
                }
                return value;

            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);

        int producerCount = readPositiveInt(sc, "Enter number of producers: ");
        int consumerCount = readPositiveInt(sc, "Enter number of consumers: ");
        int items = readPositiveInt(sc, "Enter number of items each producer should produce: ");


        BlockingQueue<Integer> sharedQueue = new ArrayBlockingQueue<>(50);
        List<Integer> destination = Collections.synchronizedList(new ArrayList<>());

        StartSignal signal = new StartSignal();  // shared between all threads

        List<Thread> threads = new ArrayList<>();

        // Start producers
        for (int i = 1; i <= producerCount; i++) {
            threads.add(new Thread(new Producer(sharedQueue, items, i, signal)));
        }

        // Start consumers
        for (int i = 1; i <= consumerCount; i++) {
            threads.add(new Thread(new Consumer(sharedQueue, destination, i, signal)));
        }

        // Start all threads
        for (Thread t : threads) t.start();

        // Wait for only producer threads first
        for (int i = 0; i < producerCount; i++) {
            threads.get(i).join();
        }

        // Add -1 after producers finishes
        for (int i = 0; i < consumerCount; i++) {
            sharedQueue.put(-1);
        }

        // Wait for consumer threads
        for (Thread t : threads) t.join();

        System.out.println("\nFINAL DESTINATION LIST: " + destination);
    }
}