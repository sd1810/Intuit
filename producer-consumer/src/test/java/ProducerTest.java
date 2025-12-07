import org.example.Producer;
import org.example.StartSignal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ProducerTest {

    private BlockingQueue<Integer> queue;
    private StartSignal signal;

    @BeforeEach
    void setUp() {
        queue = new ArrayBlockingQueue<>(100);
        signal = new StartSignal();
    }

    @Test
    void testProducerProducesCorrectNumberOfItems() throws InterruptedException {
        int items = 5;
        int producerId = 1;
        Producer producer = new Producer(queue, items, producerId, signal);

        Thread producerThread = new Thread(producer);
        producerThread.start();
        producerThread.join();

        assertEquals(items, queue.size(), "Queue should contain all produced items");
    }

    @Test
    void testProducerGeneratesCorrectItemValues() throws InterruptedException {
        int items = 3;
        int producerId = 2;
        Producer producer = new Producer(queue, items, producerId, signal);

        Thread producerThread = new Thread(producer);
        producerThread.start();
        producerThread.join();

        // Producer 2 should produce: 2001, 2002, 2003
        assertEquals(2001, queue.take());
        assertEquals(2002, queue.take());
        assertEquals(2003, queue.take());
    }

    @Test
    void testProducerSetsFirstItemProducedSignal() throws InterruptedException {
        int items = 1;
        int producerId = 1;
        Producer producer = new Producer(queue, items, producerId, signal);

        assertFalse(signal.firstItemProduced, "Signal should be false initially");

        Thread producerThread = new Thread(producer);
        producerThread.start();
        producerThread.join();

        assertTrue(signal.firstItemProduced, "Signal should be set to true after first item");
    }

    @Test
    void testMultipleProducersWithDifferentIds() throws InterruptedException {
        Producer producer1 = new Producer(queue, 2, 1, signal);
        Producer producer2 = new Producer(queue, 2, 2, signal);

        Thread thread1 = new Thread(producer1);
        Thread thread2 = new Thread(producer2);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        assertEquals(4, queue.size(), "Total items from both producers should be 4");
        
        // Verify items from both producers are present
        boolean found1001 = false, found1002 = false, found2001 = false, found2002 = false;
        while (!queue.isEmpty()) {
            int item = queue.take();
            if (item == 1001) found1001 = true;
            if (item == 1002) found1002 = true;
            if (item == 2001) found2001 = true;
            if (item == 2002) found2002 = true;
        }
        
        assertTrue(found1001 && found1002 && found2001 && found2002, 
                   "All items from both producers should be present");
    }
}

