package counters;

import org.openjdk.jmh.annotations.Threads;

import java.util.HashMap;
import java.util.Map;

public class MagicCounter implements Counter {

    public MagicCounter() {
        map  = new HashMap<>(16*100);
    }

    public MagicCounter(int threadNum) {
        map = new HashMap<>(100*threadNum);
    }

    private Map<Long, Long> map;

    @Override
    public void increment() {
        long t = Thread.currentThread().getId();
        if (map.containsKey(t)) {
            map.put(t,map.get(t)+1);
        } else {
            map.put(t,1L);
        }
    }

    @Override
    public long getValue() {
        long sum = 0;
        for (Long val:map.values()) {
            sum += val;
        }
        return sum;
    }
}
