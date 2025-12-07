package org.example;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {

    private final BlockingQueue<Integer> queue;
    private final List<Integer> destination;
    private final int consumerId;

    public Consumer(BlockingQueue<Integer> queue, List<Integer> destination, int id) {
        this.queue = queue;
        this.destination = destination;
        this.consumerId = id;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // BlockingQueue.take() automatically blocks until data is available
                int item = queue.take();

                if (item == -1) {
                    System.out.println("Consumer " + consumerId + " exiting...");
                    break;
                }

                synchronized (destination) {
                    destination.add(item);
                }

                System.out.println("Consumer " + consumerId + " consumed → " + item);
                Thread.sleep(1500);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}