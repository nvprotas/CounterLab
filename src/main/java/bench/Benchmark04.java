package bench;

import org.openjdk.jmh.annotations.*;
import counters.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.SampleTime)
@Fork(value = 2, warmups = 1)
@Warmup(iterations = 3) //3
@Measurement(iterations = 5) //5
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class Benchmark04 {

    public static final int THREADS_NUMBER = 4;

    @State(Scope.Benchmark)
    public static class ConcurrentState {

        @TearDown(Level.Iteration)
        public void doTearDown() {
            System.out.println("Doing TearDown: " + concurrentCounter.getValue());
        }

        Counter concurrentCounter = new ConcurrentCounter();
    }

    @State(Scope.Benchmark)
    public static class MutexState {

        @TearDown(Level.Iteration)
        public void doTearDown() {
            System.out.println("Doing TearDown: " + mutexCounter.getValue());
        }

        Counter mutexCounter = new MutexCounter();
    }

    @State(Scope.Benchmark)
    public static class LockState {

        @TearDown(Level.Iteration)
        public void doTearDown() {
            System.out.println("Doing TearDown: " + lockCounter.getValue());
        }

        Counter lockCounter = new LockCounter();
    }

    @State(Scope.Benchmark)
    public static class MagicState {

        @TearDown(Level.Iteration)
        public void doTearDown() {
            System.out.println("Doing TearDown: " + magicCounter.getValue());
        }

        Counter magicCounter = new MagicCounter();
    }

    @State(Scope.Benchmark)
    public static class Magic2State {

        @TearDown(Level.Iteration)
        public void doTearDown() {
            System.out.println("Doing TearDown: " + magic2Counter.getValue());
        }

        Counter magic2Counter = new MagicCounterV2();
    }

    @Benchmark
    @Group("Mutex")
    @GroupThreads(THREADS_NUMBER)
    public void measureMutexCounter(MutexState state){
        state.mutexCounter.increment();
    }

    @Benchmark
    @Group("Concurrent")
    @GroupThreads(THREADS_NUMBER)
    public void measureConcurrentCounter(ConcurrentState state){
        state.concurrentCounter.increment();
    }

    @Benchmark
    @Group("Lock")
    @GroupThreads(THREADS_NUMBER)
    public void measureLockCounter(LockState state){
        state.lockCounter.increment();
    }

    @Benchmark
    @Group("Magic")
    @GroupThreads(THREADS_NUMBER)
    public void measureMagicCounter(MagicState state){
        state.magicCounter.increment();
    }

    @Benchmark
    @Group("Magic2")
    @GroupThreads(THREADS_NUMBER)
    public void measureMagic2Counter(Magic2State state){
        state.magic2Counter.increment();
    }
}
