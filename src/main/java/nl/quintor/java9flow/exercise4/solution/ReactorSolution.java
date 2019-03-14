package nl.quintor.java9flow.exercise4.solution;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Thread.sleep;

public class ReactorSolution {

    private static void ProduceTenAndPrint() {
        Flux.range(1, 10)                       // Publisher
                .subscribe(System.out::println);           // Subscriber
    }

    private static void ProduceTenAndPrintAsString() {
        Flux.range(1, 10)                       // Publisher
                .map(integer -> Integer.toString(integer)) // "Processor" (in Reactor eigenlijk nog steeds de Publisher)
                .subscribe(System.out::println);           // Subscriber
    }

    private static void ProduceTenWithMultipleSubscribers() throws InterruptedException {
        ConnectableFlux<Long> tenNumbers = Flux.interval(Duration.ofMillis(500))
                .take(10)
                .publish();

        tenNumbers.subscribe(num -> System.out.println("Subscriber 1 :: " + num));
        sleep(5000);

        tenNumbers.subscribe(num -> System.out.println("Subscriber 2 :: " + num));
        tenNumbers.connect();
        sleep(5000);
    }

    private static void Bonus() throws InterruptedException {
        List<String> words = Stream.of(
                "the", "quick", "brown",
                "fox", "jumps", "over",
                "the", "lazy", "dog"
        ).collect(Collectors.toList());

        Flux<String> letters = Flux.fromIterable(words)
                .flatMap(s -> Flux.just(s.split("")))
                .distinct()
                .sort();

        Flux<Long> numbers = Flux.interval(Duration.ofSeconds(1))
                .map(aLong -> aLong + 1);

        letters.zipWith(numbers, (word, num) -> String.format("%d. %s", num, word))
                .subscribe(System.out::println);

        sleep(27000);
    }

    public static void main(String[] args) throws InterruptedException {
        ProduceTenAndPrint();
        ProduceTenAndPrintAsString();
        ProduceTenWithMultipleSubscribers();

        Bonus();
    }
}
