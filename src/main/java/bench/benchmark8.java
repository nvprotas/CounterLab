package bench;

import counters.ConcurrentCounter;
import counters.Counter;
import counters.LockCounter;
import counters.MutexCounter;
import org.openjdk.jmh.annotations.*;

public class benchmark8 {

    public static final int THREADS_NUMBER = 8;
    public static final int FORKS_NUMBER = 4;
    public static final int MEASURMENT_ITERATIONS_NUMBER = 4;
    public static final int WARMUP_ITERATIONS_NUMBER = 1;
    public static final int WARMUPS_NUMBER = 1;

    @State(Scope.Benchmark)
    public static class ConcurrentState {

        @TearDown(Level.Trial)
        public void doTearDown() {
            System.out.println("Doing TearDown: " + concurrentCounter.getValue());
        }

        Counter concurrentCounter = new ConcurrentCounter();
    }

    @State(Scope.Benchmark)
    public static class MutexState {

        @TearDown(Level.Trial)
        public void doTearDown() {
            System.out.println("Doing TearDown: " + mutexCounter.getValue());
        }

        Counter mutexCounter = new MutexCounter();
    }

    @State(Scope.Benchmark)
    public static class LockState {

        @TearDown(Level.Trial)
        public void doTearDown() {
            System.out.println("Doing TearDown: " + lockCounter.getValue());
        }

        Counter lockCounter = new LockCounter();
    }


    @Benchmark
    @BenchmarkMode({Mode.SampleTime, Mode.Throughput})
    @Fork(value = FORKS_NUMBER, warmups = WARMUPS_NUMBER)
    @Warmup(iterations = WARMUP_ITERATIONS_NUMBER)
    @Measurement(iterations = MEASURMENT_ITERATIONS_NUMBER)
    @Group("Mutex")
    @GroupThreads(THREADS_NUMBER)
    public void measureMutexCounter(MutexState state){
        state.mutexCounter.increment();
    }

    @Benchmark
    @BenchmarkMode({Mode.SampleTime, Mode.Throughput})
    @Fork(value = FORKS_NUMBER, warmups = WARMUPS_NUMBER)
    @Warmup(iterations = WARMUP_ITERATIONS_NUMBER)
    @Measurement(iterations = MEASURMENT_ITERATIONS_NUMBER)
    @Group("Concurrent")
    @GroupThreads(THREADS_NUMBER)
    public void measureConcurrentCounter(ConcurrentState state){
        state.concurrentCounter.increment();
    }

    @Benchmark
    @BenchmarkMode({Mode.SampleTime, Mode.Throughput})
    @Fork(value = FORKS_NUMBER, warmups = WARMUPS_NUMBER)
    @Warmup(iterations = WARMUP_ITERATIONS_NUMBER)
    @Measurement(iterations = MEASURMENT_ITERATIONS_NUMBER)
    @Group("Lock")
    @GroupThreads(THREADS_NUMBER)
    public void measureConcurrentCounter(LockState state){
        state.lockCounter.increment();
    }
}
