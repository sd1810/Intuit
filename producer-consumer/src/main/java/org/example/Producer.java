package org.example;

import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {

    private final BlockingQueue<Integer> queue;
    private final int items;
    private final int producerId;
    private final StartSignal signal;

    public Producer(BlockingQueue<Integer> queue, int items, int id, StartSignal signal) {
        this.queue = queue;
        this.items = items;
        this.producerId = id;
        this.signal = signal;
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= items; i++) {

                int item = producerId * 1000 + i;
                queue.put(item);
                System.out.println("Producer " + producerId + " → produced: " + item);

                // Signal consumers when the FIRST item is produced
                synchronized (signal.lock) {
                    if (!signal.firstItemProduced) {
                        signal.firstItemProduced = true;
                        signal.lock.notifyAll();  // wake all consumers IMMEDIATELY
                    }
                }
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}