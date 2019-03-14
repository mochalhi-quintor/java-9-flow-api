package nl.quintor.java9flow.exercise4;

import org.reactivestreams.Publisher;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Thread.sleep;

/**
 * Exercise 4
 *
 * Use Project reactor to do the follow exercises.
 */
public class Reactor {

    /**
     * Make a Publisher that publishes the numbers 1 tot 10.
     * Make a Subscriber that prints out these numbers.
     */
    private static void ProduceTenAndPrint() {
        // TODO
    }

    /**
     * Make a Publisher that publishes the numbers 1 tot 10.
     * Convert the numbers to strings.
     * Make a Subscriber that prints out these numbers.
     */
    private static void ProduceTenAndPrintAsString() {
        // TODO
    }

    /**
     * Make a Publisher that publishes the numbers 1 to 10.
     * Make sure that only one item is published per second.
     *
     * Make two subscribers that will subscribe to the publisher.
     * The second subscriber should subscribe 5 seconds later than the first subscriber, use Thread.sleep(5000)
     *
     *      * Example output:
     *      * Subscriber 1 :: 1
     *      * Subscriber 1 :: 2
     *      * Subscriber 1 :: 3
     *      * Subscriber 1 :: 4
     *      * Subscriber 2 :: 4
     *      * ...
     *
     * The second subscriber does not get all the data!
     * Alter your implementation to make use of {@link ConnectableFlux} so that both streams get all the data.
     *
     *      * Example output:
     *      * Subscriber 1 :: 1
     *      * Subscriber 2 :: 1
     *      * Subscriber 1 :: 2
     *      * Subscriber 2 :: 2
     *      * Subscriber 1 :: 3
     *      * Subscriber 2 :: 3
     *      * ...
     */
    private static void ProduceTenWithMultipleSubscribers() {
        // TODO
    }

    /**
     * BONUS
     *
     * Use the list of words to print out the entire alphabet, one letter per second.
     * The output should be sorted and numbered!
     *
     * Example:
     * 1. a
     * 2. b
     * 3. c
     *
     * Hint:
     * {@link Flux#zipWith(Publisher)}.
     */
    private static void Bonus() throws InterruptedException {
        List<String> words = Stream.of(
                "the", "quick", "brown",
                "fox", "jumps", "over",
                "the", "lazy", "dog"
        ).collect(Collectors.toList());

        // TODO

        sleep(30000);
    }

    public static void main(String[] args) {

        ProduceTenAndPrint();
        ProduceTenAndPrintAsString();
        ProduceTenWithMultipleSubscribers();

//        Bonus();
    }
}
