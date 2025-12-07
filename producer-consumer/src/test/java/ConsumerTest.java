import org.example.Consumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ConsumerTest {

    private BlockingQueue<Integer> queue;
    private List<Integer> destination;

    @BeforeEach
    void setUp() {
        queue = new ArrayBlockingQueue<>(100);
        destination = Collections.synchronizedList(new ArrayList<>());
    }

    @Test
    void testConsumerWaitsForFirstItemProduced() throws InterruptedException {
        Consumer consumer = new Consumer(queue, destination, 1);
        
        Thread consumerThread = new Thread(consumer);
        consumerThread.start();
        
        // Give consumer time to start - it will block on queue.take() waiting for data
        Thread.sleep(100);
        
        // Consumer should be waiting (blocked on queue.take()), destination should be empty
        assertTrue(destination.isEmpty(), "Consumer should wait until first item is produced");

        // Add items to queue - this will wake up the consumer automatically
        queue.put(1001);
        queue.put(-1); // Exit signal
        
        consumerThread.join();
        
        assertEquals(1, destination.size(), "Consumer should have consumed one item");
        assertEquals(1001, destination.get(0));
    }

    @Test
    void testConsumerConsumesAllItems() throws InterruptedException {
        // Add items to queue
        queue.put(1001);
        queue.put(1002);
        queue.put(1003);
        queue.put(-1); // Exit signal
        
        Consumer consumer = new Consumer(queue, destination, 1);
        Thread consumerThread = new Thread(consumer);
        consumerThread.start();
        consumerThread.join();
        
        assertEquals(3, destination.size(), "Consumer should consume all items");
        assertTrue(destination.contains(1001));
        assertTrue(destination.contains(1002));
        assertTrue(destination.contains(1003));
    }

    @Test
    void testConsumerExits() throws InterruptedException {
        queue.put(-1); // Exit signal
        
        Consumer consumer = new Consumer(queue, destination, 1);
        Thread consumerThread = new Thread(consumer);
        consumerThread.start();
        consumerThread.join();
        
        assertTrue(destination.isEmpty(), "Consumer should exit without consuming -1");
        assertFalse(consumerThread.isAlive(), "Consumer thread should have exited");
    }

    @Test
    void testMultipleConsumers() throws InterruptedException {
        List<Integer> dest1 = Collections.synchronizedList(new ArrayList<>());
        List<Integer> dest2 = Collections.synchronizedList(new ArrayList<>());
        
        // Add items to queue
        queue.put(1001);
        queue.put(1002);
        queue.put(-1); // Exit for consumer 1
        queue.put(-1); // Exit for consumer 2
        
        Consumer consumer1 = new Consumer(queue, dest1, 1);
        Consumer consumer2 = new Consumer(queue, dest2, 2);
        
        Thread thread1 = new Thread(consumer1);
        Thread thread2 = new Thread(consumer2);
        
        thread1.start();
        thread2.start();
        
        thread1.join();
        thread2.join();
        
        // Both consumers should have consumed items (order may vary)
        int totalConsumed = dest1.size() + dest2.size();
        assertEquals(2, totalConsumed, "Both consumers together should consume 2 items");
    }
}

