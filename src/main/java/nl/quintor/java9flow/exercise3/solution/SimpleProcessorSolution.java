package nl.quintor.java9flow.exercise3.solution;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicBoolean;

public class SimpleProcessorSolution implements Flow.Processor<Integer, String> {
    private Flow.Subscription processorSubscription;

    private final List<String> items;
    private final AtomicBoolean allProcessed;
    private Throwable error;

    public SimpleProcessorSolution() {
        this.items = new ArrayList<>();
        this.allProcessed = new AtomicBoolean(false);
    }

    @Override
    public void subscribe(Flow.Subscriber<? super String> subscriber) {
        Flow.Subscription subscription = new SimpleSubscription(subscriber);
        subscriber.onSubscribe(subscription);
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        System.out.println("SimpleProcessor :: subscribed");
        this.processorSubscription = subscription;
        this.processorSubscription.request(1);
    }

    @Override
    public void onNext(Integer item) {
        System.out.println("SimpleProcessor :: item = [" + item + "]");

        this.items.add(Integer.toString(item));
        this.processorSubscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("SimpleProcessor :: throwable = [" + throwable + "]");
        this.error = throwable;
    }

    @Override
    public void onComplete() {
        System.out.println("SimpleProcessor :: complete");
        this.allProcessed.set(true);
    }

    private class SimpleSubscription implements Flow.Subscription {
        private Flow.Subscriber<? super String> subscriber;
        private final AtomicBoolean terminated;
        private int nextIndex;

        private SimpleSubscription(Flow.Subscriber<? super String> subscriber) {
            this.subscriber = subscriber;
            this.terminated = new AtomicBoolean(false);
            this.nextIndex = 0;
        }

        @Override
        public void request(long n) {
            if (n <= 0) {
                subscriber.onError(new IllegalArgumentException("must request 1 or more items"));
            }

            if (error != null) {
                subscriber.onError(error);
                terminated.set(true);
            }

            for (long consumed = 0; consumed < n && nextIndex < items.size() && !terminated.get(); consumed++) {
                subscriber.onNext(items.get(nextIndex++));
            }

            // Transmission is complete if all items have been sent and the publisher is done
            if (nextIndex >= items.size() && allProcessed.get() && !terminated.getAndSet(true)) {
                subscriber.onComplete();
            }
        }

        @Override
        public void cancel() {
            this.terminated.set(true);
        }
    }
}
