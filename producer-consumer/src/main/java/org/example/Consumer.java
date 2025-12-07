package org.example;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {

    private final BlockingQueue<Integer> queue;
    private final List<Integer> destination;
    private final int consumerId;
    private final StartSignal signal;

    public Consumer(BlockingQueue<Integer> queue, List<Integer> destination, int id, StartSignal signal) {
        this.queue = queue;
        this.destination = destination;
        this.consumerId = id;
        this.signal = signal;
    }

    @Override
    public void run() {
        try {
            // Consumers wait here until any producer produces ONE item
            synchronized (signal.lock) {
                while (!signal.firstItemProduced) {
                    signal.lock.wait();
                }
            }

            // Now consumers can start consuming normally
            while (true) {
                int item = queue.take();

                if (item == -1) {
                    System.out.println("Consumer " + consumerId + " exiting...");
                    break;
                }

                synchronized (destination) {
                    destination.add(item);
                }

                System.out.println("Consumer " + consumerId + " consumed → " + item);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}