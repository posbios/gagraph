package com.bitexception.genetic.algorithm;

import static java.lang.Math.random;
import static java.lang.System.arraycopy;
import java.util.Collection;
import java.util.Optional;
import static java.util.stream.IntStream.range;
import static java.lang.Math.round;
import static java.util.Arrays.copyOf;

/**
 *
 * @author andoni
 */
public class NameGenetic extends Genetic<char[]> {

    public NameGenetic() {
        super.crossoverProbability = 0.5;
        super.mutationProbability = 0.1;
        super.populationSize = ALPHABET.length * 3;
        super.crossover = this::crossover;
        super.fitness = this::fitness;
        super.mutation = this::mutation;
        super.supplier = () -> supplier(expected.length);
        super.termination = this::termination;
    }

    private char[] expected = "Andoni".toCharArray();
    private char[] ALPHABET = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZabcdefghijklmnñopqrstuvwxyz ,.;:?!".toCharArray();
    private int count;

    public void find(char[] expected) {
        this.expected = expected;
        super.supplier = () -> supplier(expected.length);
        super.find();
    }

    private char[] crossover(char[] value1, char[] value2) {
        final int i = (int) round(random() * value1.length);
        final char[] result = new char[value1.length];
        arraycopy(value1, 0, result, 0, i);
        arraycopy(value2, i, result, i, value2.length - i);
        return result;
    }

    private double fitness(char[] value) {
        return range(0, value.length)
                .filter(i -> value[i] == expected[i])
                .count();
    }

    private char[] mutation(char[] value) {
        final char[] result = copyOf(value, value.length);
        for (int i = 0; i < 2; i++) {
            int letter = (int) round(random() * (ALPHABET.length - 1));
            int location = (int) round(random() * (value.length - 1));
            result[location] = ALPHABET[letter];
        }
        return result;
    }

    private char[] supplier(int length) {
        final char[] result = new char[length];
        for (int i = 0; i < length; i++) {
            int letter = (int) round(random() * (ALPHABET.length - 1));
            result[i] = ALPHABET[letter];
        }
        return result;
    }

    private boolean termination(Collection<char[]> chars) {
        System.out.println("-------new population-------");
        chars.forEach((char[] t) -> {
            System.out.println(t);
        });
        count++;
        final Optional<char[]> result = chars.stream()
                .filter(value -> round(fitness(value)) == expected.length)
                .findAny();
        if (result.isPresent()) {
            System.out.println("Count: " + count);
            System.out.println(result.get());
            return true;
        }
        final boolean terminated = count == 3000;
        if (terminated) {
            chars.forEach(System.out::println);
        }
        return terminated;
    }

    public void find(String arrate) {
        this.expected = arrate.toCharArray();
        super.find();
    }

}
