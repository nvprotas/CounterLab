import counters.Counter;
import counters.ConcurrentCounter;
import counters.MutexCounter;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static org.junit.Assert.assertEquals;

public class MutexTest {
//    private static final Counter counter = new ConcurrentCounter();
    private static final Counter counter = new MutexCounter();

    @Test
    public void testSequentialExecution() throws ExecutionException, InterruptedException {
        ExecutorService executors = Executors.newFixedThreadPool(1000);

        int incrementCallsCount = 10000000;

        List<Future> futures = range(0, incrementCallsCount)
                .mapToObj(i -> executors.submit(incrementRunnable()))
                .collect(toList());
        for (Future future : futures) {
            future.get();
        }
        assertEquals("Oops! Smth is wrong!", incrementCallsCount, counter.getValue());
    }

    private static Runnable incrementRunnable(){
        return (counter::increment);
    }
}