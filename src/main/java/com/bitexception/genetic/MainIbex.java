package com.bitexception.genetic;

import com.bitexception.genetic.algorithm.SimpleGenetic;
import com.bitexception.genetic.feed.Ibex35WebFeeder;
import com.bitexception.genetic.feed.Ibex35WebFeeder.RANGE;
import java.text.MessageFormat;
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
public class MainIbex {

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
        RANGE range = RANGE.THREE_MONTH;
        // values
        Ibex35WebFeeder.ENTERPRISE[] enterprises = Ibex35WebFeeder.ENTERPRISE.values();
        
        SimpleGenetic test = new SimpleGenetic(new Ibex35WebFeeder(enterprises).getEnterprisesEvolutions(range), population, crossover, mutation, mutationRandomness, maxRangeCicles, evolutionIntesification);
        
        List<LinkedHashMap<Integer, Double>> result = new ArrayList<>();

        for (int i = 0; i < loops; i++) {
            test.find(initialMoney, expectedMoney);
            result.add(test.result);
        }

        Map<Integer, Double> finals = new LinkedHashMap<>();
        Map<Integer, Double> finals2;

        result.stream().forEach(value -> {
            value.forEach((key, value2) -> {
                double test2 = finals.containsKey(key) ? finals.get(key) : 0d;
                finals.put(key, test2 + value2);
            }
            );
        });

        finals2 = sortHashMapByValues(finals);

        finals2.forEach((key, value) -> {
            System.out.println(MessageFormat.format("{0}: {1, number, #.#####}", Ibex35WebFeeder.banks.get(key).name(), value));
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
