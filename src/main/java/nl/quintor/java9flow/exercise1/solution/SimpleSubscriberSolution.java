package nl.quintor.java9flow.exercise1.solution;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

import static java.lang.Thread.sleep;

public class SimpleSubscriberSolution implements Flow.Subscriber<Integer> {
    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        System.out.println("SimpleSubscriber :: subscribed");

        this.subscription.request(1);
    }

    @Override
    public void onNext(Integer item) {
        System.out.println("SimpleSubscriber :: item = [" + item + "]");
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("SimpleSubscriber :: throwable = [" + throwable + "]");
    }

    @Override
    public void onComplete() {
        System.out.println("SimpleSubscriber :: completed");
    }

    public static void main(String[] args) throws InterruptedException {
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();

        publisher.subscribe(new SimpleSubscriberSolution());
        publisher.submit(1);
        publisher.submit(2);
        publisher.submit(3);
        publisher.submit(4);
        publisher.submit(5);
        publisher.close();

        sleep(1000);
    }
}
