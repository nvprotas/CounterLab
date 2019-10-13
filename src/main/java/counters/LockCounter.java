package counters;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockCounter implements Counter {

    volatile private long value;
    Lock lock = new ReentrantLock();

    @Override
    public void increment() {
        lock.lock();
        try {
            value++;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public long getValue() {
        return value;
    }
}
