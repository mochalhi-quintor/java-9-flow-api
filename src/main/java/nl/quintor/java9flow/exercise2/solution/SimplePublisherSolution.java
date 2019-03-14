package nl.quintor.java9flow.exercise2.solution;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

public class SimplePublisherSolution implements Flow.Publisher<Integer> {

    private final List<Integer> integers;

    public SimplePublisherSolution(int n) {
        this.integers = new ArrayList<>();

        IntStream.rangeClosed(1, n).forEach(this.integers::add);
    }

    @Override
    public void subscribe(Flow.Subscriber<? super Integer> subscriber) {
        subscriber.onSubscribe(new SimpleSubscription(subscriber));
    }

    private class SimpleSubscription implements Flow.Subscription {

        private final Iterator<Integer> iterator;
        private final AtomicBoolean terminated;
        private Flow.Subscriber<? super Integer> subscriber;

        SimpleSubscription(Flow.Subscriber<? super Integer> subscriber) {
            this.subscriber = subscriber;
            this.iterator = integers.iterator();
            this.terminated = new AtomicBoolean(false);
        }

        @Override
        public void request(long n) {
            if (n <= 0) {
                subscriber.onError(new IllegalArgumentException("must request 1 or more items"));
            }

            for (long consumed = 0; consumed < n && iterator.hasNext() && !terminated.get(); consumed++) {
                subscriber.onNext(iterator.next());
            }

            if (!iterator.hasNext() && !terminated.getAndSet(true)) {
                subscriber.onComplete();
            }
        }

        @Override
        public void cancel() {
            terminated.set(true);
        }
    }
}
