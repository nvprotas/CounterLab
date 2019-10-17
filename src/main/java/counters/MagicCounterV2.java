package counters;

import java.util.ArrayList;
import java.util.Map;

public class MagicCounterV2 implements Counter {
    private int threads;
    private long val;
    private static int magicOffset = 12;

    private int[] ticket;
    private boolean[] entering;

    public MagicCounterV2() {
        this.threads = 16+magicOffset;
        this.ticket = new int[16+magicOffset];
        this.entering = new boolean[16+magicOffset];
    }

    public MagicCounterV2(int threads) {
        this.threads = threads+magicOffset;
        this.ticket = new int[this.threads];
        this.entering = new boolean[this.threads];
    }

    private void lock(int pid){ // thread ID
        entering[pid] = true;
        int max = 0;
        for (int i = 0; i < threads; i++) {
            int current = ticket[i];
            if (current > max) {
                max = current;
            }
        }
        ticket[pid] = 1 + max;
        entering[pid] = false;
        for (int i = 0; i < ticket.length; ++i) {
            if (i != pid) {
                while (entering[i]) { Thread.yield(); }
                while (ticket[i] != 0 && ( ticket[pid] > ticket[i]  ||
                        (ticket[pid] == ticket[i] && pid > i)))
                { Thread.yield(); }
            }
        }
    }

    private void unlock(int pid) {
        ticket[pid] = 0;
    }

    private Map<Long, Long> map;
    @Override
    public void increment() {
        lock((int) Thread.currentThread().getId());
        val++;
        unlock((int) Thread.currentThread().getId());
    }

    @Override
    public long getValue() {
        return val;
    }
}
