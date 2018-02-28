package com.bitexception.genetic;

import com.bitexception.genetic.algorithm.SimpleGenetic;
import com.bitexception.genetic.feed.CryptoconcurrencyFeeder;
import java.text.MessageFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author andoni
 */
public class MainCrypto {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // loops of test
        int loops = 10;
        // this is the times population will live over the evolution graphics.
        int maxRangeCicles = 100;
        // a initial money of 1 is ok becouse we don't want to much higers values at the end
        double initialMoney = 100d;
        // no limit
        double expectedMoney = initialMoney * 100d;
//        double expectedMoney = Double.MAX_VALUE;
        // more population do better selection
        int population = 1000;
        // this don't let go good values on selection
        // use this for
        double crossover = .08d;
        // mutation probability
        double mutation = .1d;
        // Use the index as a trust value. 1 means no confidence, 0 means total confidence.
        double mutationRandomness = .03d;
        // this will be the inensifictaion of values over time. If 0 last is all and first is none. If 1 this is not applied.
        // use this for more risk, and disable for no risk.
        double evolutionIntesification = 0.15d;
        // time period for data
        Instant startDate = new GregorianCalendar(2017, 11, 10).toInstant();
        long numberOfDays = 90;
        // values
        CryptoconcurrencyFeeder.CONCURRENCY[] concurrencies = CryptoconcurrencyFeeder.CONCURRENCY.values();

        // main
        List<LinkedHashMap<Integer, Double>> result;
        SimpleGenetic simpleGenetic;

        result = new ArrayList<>();
        simpleGenetic = new SimpleGenetic(new CryptoconcurrencyFeeder(startDate, numberOfDays, concurrencies).getEnterprisesEvolutions(), population, crossover, mutation, mutationRandomness, maxRangeCicles, evolutionIntesification);

        // each loop is a selection over population
        for (int i = 0; i < loops; i++) {
            simpleGenetic.find(initialMoney, expectedMoney);
            result.add(simpleGenetic.result);
        }

        // sum of all results into only one map
        Map<Integer, Double> finals;
        
        finals = new LinkedHashMap<>();
        
        result.stream().forEach(value -> {
            value.forEach((key, currentValue) -> {
                double acumulated = finals.containsKey(key) ? finals.get(key) : 0d;
                finals.put(key, acumulated + currentValue);
            }
            );
        });

        // sort all sums
        Map<Integer, Double> sortedFinals;

        sortedFinals = sortHashMapByValues(finals);

        sortedFinals.forEach((key, value) -> {
            System.out.print(MessageFormat.format("{0}: {1, number, #.#####}; ", CryptoconcurrencyFeeder.coins.get(key).name(), value));
            System.out.println(MessageFormat.format("{0}: {1}", CryptoconcurrencyFeeder.coins.get(key).name(), String.valueOf(value)));
        });
    }

    public static <K, V> LinkedHashMap<K, V> sortHashMapByValues(Map<K, V> passedMap) {
        List mapKeys = new ArrayList<>(passedMap.keySet());
        List mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);
        Collections.reverse(mapValues);
        Collections.reverse(mapKeys);

        LinkedHashMap<K, V> sortedMap = new LinkedHashMap<>();

        Iterator<V> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            V val = valueIt.next();
            Iterator<K> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                K key = keyIt.next();
                V comp1 = passedMap.get(key);
                V comp2 = val;

                if (comp1.equals(comp2)) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }

        return sortedMap;
    }
}
