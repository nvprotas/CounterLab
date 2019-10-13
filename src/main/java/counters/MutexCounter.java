package counters;

public class MutexCounter implements Counter {

    volatile private long value;

    public void increment() {
        synchronized (this) {
            value++;
        }
    }

    public long getValue() {
        return value;
    }
}
