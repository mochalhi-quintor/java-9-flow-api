package nl.quintor.java9flow.exercise2;

import java.util.concurrent.Flow;

/**
 * Exercise 2
 *
 * Create a synchronous implementation of {@link Flow.Publisher} that:
 *      Publishes the integers 1 to n.
 *
 * You will have to create your own implementation of {@link Flow.Subscription} as well.
 *
 * After this works, implement SimplePublisherTest and verify how good your implementation is against the TCK!
 */
public class SimplePublisher {

    /**
     * Creates a new SimplePublisher that publishes n items. This constructor
     * is required to run the TCK tests.
     *
     * @param n the amount of items to publish
     */
    private SimplePublisher(int n) {
    }

    public static void main(String[] args) {
    }
}
