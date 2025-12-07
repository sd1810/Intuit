package org.example;

import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {

    private final BlockingQueue<Integer> queue;
    private final int items;
    private final int producerId;

    public Producer(BlockingQueue<Integer> queue, int items, int id) {
        this.queue = queue;
        this.items = items;
        this.producerId = id;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= items; i++) {
                int item = producerId * 1000 + i;
                queue.put(item);
                System.out.println("Producer " + producerId + " → produced: " + item);
                Thread.sleep(500);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}