package nl.quintor.java9flow.exercise2.solution;

import org.reactivestreams.tck.TestEnvironment;
import org.reactivestreams.tck.flow.FlowPublisherVerification;

import java.util.concurrent.Flow;

public class SimplePublisherSolutionTest extends FlowPublisherVerification<Integer> {

    public SimplePublisherSolutionTest() {
        super(new TestEnvironment());
    }

    @Override
    public Flow.Publisher<Integer> createFlowPublisher(long elements) {
        return new SimplePublisherSolution((int) elements);
    }

    @Override
    public Flow.Publisher<Integer> createFailedFlowPublisher() {
        return null;
    }
}