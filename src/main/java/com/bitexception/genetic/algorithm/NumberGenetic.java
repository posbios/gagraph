package com.bitexception.genetic.algorithm;

import static java.lang.Math.random;
import java.util.Collection;
import java.util.Optional;

/**
 *
 * @author andoni
 */
public class NumberGenetic extends Genetic<Double> {

    public NumberGenetic() {
        super.crossoverProbability = 0;
        super.mutationProbability = 0.33333333333333333333333333;
        super.populationSize = 100;
        super.crossover = this::crossover;
        super.fitness = this::fitness;
        super.mutation = this::mutation;
        super.supplier = () -> supplier();
        super.termination = this::termination;
    }

    private Double expected = 1000d;
    private int count;

    private Double crossover(Double value1, Double value2) {
        return 0d;
    }

    private Double fitness(Double value) {
        return value / expected;
    }

    private Double mutation(Double value) {
        return value + (expected * .1 * random() - expected * .05);
    }

    private Double supplier() {
        return expected * .1 + expected * .2 * random();
    }

    private boolean termination(Collection<Double> doubles) {
        count++;
        System.out.println("-------new population-------");
        doubles.forEach((Double t) -> {
            System.out.println(t);
        });
        final Optional<Double> result;
        final boolean isTerminated;
        final boolean isResult;

        result = doubles.stream()
                .filter(value -> fitness(value) >= 1)
                .findAny();

        isTerminated = count == 3000;
        isResult = result.isPresent();

        if (isResult) {
            System.out.println("Count: " + count);
            System.out.println(result.get());
        } else if (isTerminated) {
            doubles.forEach(System.out::println);
        }

        return isTerminated || isResult;
    }

}
