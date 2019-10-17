package counters;

public class VeryMagicCounter implements Counter {
    long val = 0;

    @Override
    public void increment() {
        val++;
    }

    @Override
    public long getValue() {
        return val;
    }
}
