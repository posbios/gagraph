package com.bitexception.genetic.algorithm;

import com.bitexception.genetic.MainCrypto;
import com.bitexception.genetic.feed.CryptoconcurrencyFeeder;
import static java.lang.Math.random;
import static java.lang.System.arraycopy;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import static java.lang.Math.round;
import static java.lang.System.out;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

/**
 *
 * @author andoni
 */
public class SimpleGenetic extends Genetic<double[]> {

    private final double[][] monthFluxs;
    private final double maxCicles;
    private final double mutationRandomness;
    private final double evolutionIntesification;
    private double nCicles;
    private double initialAmount;
    private double expected;
    public LinkedHashMap<Integer, Double> result;

    public SimpleGenetic(double[][] monthsFluxes, int populationSize, double crossoverProbability, double mutationProbability, double mutationRandomness, double maxCicles, double evolutionIntesification) {
        super.populationSize = populationSize;
        super.crossoverProbability = crossoverProbability;
        super.mutationProbability = mutationProbability;
        this.mutationRandomness = mutationRandomness;
        this.evolutionIntesification = evolutionIntesification;
        super.crossover = this::crossover;
        super.fitness = this::fitness;
        super.mutation = this::mutation;
        super.evolution = this::evolution;
        super.supplier = () -> supplier();
        super.termination = this::termination;
        this.monthFluxs = monthsFluxes;
        this.maxCicles = monthsFluxes[0].length * maxCicles;
        this.nCicles = 0;
    }

    private double[] crossover(double[] value1, double[] value2) {
        final int i = (int) round(random() * value1.length);
        final double[] result = new double[value1.length];
        arraycopy(value1, 0, result, 0, i);
        arraycopy(value2, i, result, i, value2.length - i);
        return result;
    }

    private double fitness(double[] values) {
        return Arrays.stream(values).parallel().max().getAsDouble();
    }

    private double[] evolution(double[] values) {
        double[] c = new double[values.length];
        
        IntStream.range(0, values.length).parallel()
                .forEach((i) -> {
                    double indexIncrementCoeficient;
                    double currentEvolutionCoeficient;
                    double monthLength = new Double(monthFluxs[i].length);
                    Double test = Double.valueOf(nCicles % monthLength);

                    indexIncrementCoeficient = monthFluxs[i][test.intValue()];

                    currentEvolutionCoeficient = 1 + test / monthLength * evolutionIntesification;

                    c[i] = values[i] + values[i] * indexIncrementCoeficient * currentEvolutionCoeficient;
                });
        
        return c;
    }
    
    private double[] mutation(double[] values) {
        double[] c = new double[values.length];
        
        IntStream.range(0, values.length).parallel()
                .forEach((i) -> {
                    double mutation;

                    mutation = mutationRandomness * random() + (-mutationRandomness * .5d);

                    c[i] = values[i] + values[i] * mutation;
                });
        
        return c;
    }

    private double[] supplier() {
        double[] result;
        result = new double[monthFluxs.length];

        for (int i = 0; i < monthFluxs.length; i++) {
            result[i] = initialAmount;
        }

        return result;
    }

    private boolean termination(Collection<double[]> doubles) {
        nCicles++;
        out.println(MessageFormat.format("{0,number,00000}/{1,number,00000}", nCicles, maxCicles));

        final Optional<double[]> result;
        final boolean isTerminated;
        final boolean isResult;

        result = doubles.parallelStream()
                .filter(value -> fitness(value) >= expected)
                .findAny();

        isTerminated = nCicles == maxCicles;
        isResult = result.isPresent();

        if (isResult || isTerminated) {
            double[] best;

            best = new double[1];
            if (isResult) {
                best = result.get();
            } else if (isTerminated) {
                best = doubles.stream().max((double[] t, double[] t1) -> Double.compare(fitness(t), fitness(t1))).get();
            }
            
            final double sum;
            sum = DoubleStream.of(best).sum();

            HashMap<Integer, Double> map = new HashMap<>();
            for (int i = 0; i < best.length; i++) {
                double k = best[i] / sum;
                map.put(i, k);
            }
            LinkedHashMap<Integer, Double> sortedMap = MainCrypto.sortHashMapByValues(map);

            this.result = sortedMap;
        }

        return isTerminated || isResult;
    }

    public void find(double initialAmount, double expected) {
        this.nCicles = 0;
        this.expected = expected;
        this.initialAmount = initialAmount;
        
        find();
    }
}
