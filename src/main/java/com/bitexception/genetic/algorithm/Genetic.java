package com.bitexception.genetic.algorithm;

import static java.lang.Math.random;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import java.util.stream.Stream;
import static java.util.Collections.shuffle;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

/**
 *
 * @author andoni
 * @param <T>
 */
public class Genetic<T> {
    public BiFunction<T, T, T> crossover;
    public double crossoverProbability;
    public ToDoubleFunction<T> fitness;
    public Function<T, T> evolution;
    public Function<T, T> mutation;
    public double mutationProbability;
    public int populationSize;
    public Supplier<T> supplier;
    public Predicate<Collection<T>> termination;
    public List<T> initialPopulation;
    
    

    public void find() {
        List<T> population;
        
        if (null == initialPopulation) {
            initialPopulation = Stream.generate(supplier).parallel()
                .limit(populationSize)
                .collect(toList());
        }
        
        population = initialPopulation;

        // Iteration
        while (!termination.test(population)) {
            // Selection
            population = selection(population);
            // Crossover
            crossover(population);
            // Evolution
            evolution(population);
            // Mutation
            mutation(population);
        }
    }

    public abstract class TotalIntConsumer implements IntConsumer {
        public double totalFitness = 0d;

        @Override
        public abstract void accept(int i);
    }

    // get the random matches using probability obtained checking how many letters are ok
    private List<T> selection(List<T> population) {

        final double[] fitnesses = population.parallelStream()
                .mapToDouble(fitness)
                .toArray();
        TotalIntConsumer total = new TotalIntConsumer() {
            @Override
            public void accept(int i) {
                totalFitness = totalFitness + Math.abs(fitnesses[i]);
            }
        };
        
        IntStream.range(0, fitnesses.length).parallel().forEach(total);
        double totalFitness = total.totalFitness;
//        double totalFitness = DoubleStream.of(fitnesses).parallel().sum();

        double sum = 0;
        final double[] probabilities = new double[fitnesses.length];
        for (int i = 0; i < fitnesses.length; i++) {
            sum += fitnesses[i] / totalFitness;
            probabilities[i] = sum;
        }
        probabilities[probabilities.length - 1] = 1;

        return range(0, probabilities.length).mapToObj(i -> {
            int index = Arrays.binarySearch(probabilities, random());
            if (index < 0) {
                index = -(index + 1);
            }
            return population.get(index);
        }).collect(toList());
    }

    // from random words cross letters
    private void crossover(List<T> population) {
        final int[] indexes = range(0, population.size())
                .filter(i -> random() < crossoverProbability)
                .toArray();

        shuffle(Arrays.asList(indexes));

        for (int i = 0; i < indexes.length / 2; i++) {
            final int index1 = indexes[2 * i];
            final int index2 = indexes[2 * i + 1];
            final T value1 = population.get(index1);
            final T value2 = population.get(index2);
            population.set(index1, crossover.apply(value1, value2));
            population.set(index2, crossover.apply(value2, value1));
        }
    }

    private void evolution(List<T> population) {
        IntStream.range(0, population.size()).parallel().forEach((i) -> {
            population.set(i, evolution.apply(population.get(i)));
        });
    }
    
    private void mutation(List<T> population) {
        IntStream.range(0, population.size()).parallel().forEach((i) -> {
            if (random() < mutationProbability) {
                population.set(i, mutation.apply(population.get(i)));
            }
        });
    }
}
