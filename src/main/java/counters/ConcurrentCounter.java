package counters;

import java.util.concurrent.atomic.AtomicLong;

public class ConcurrentCounter implements Counter {

    private AtomicLong value = new AtomicLong(0);

    public void increment() {
            value.getAndIncrement();

    }

    public long getValue() {
        return value.get();
    }

}
