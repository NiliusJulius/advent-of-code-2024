package com.niliusjulius.aoc.days;

import com.niliusjulius.aoc.util.Reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day22 {

    public static void main(String[] args) {
        List<String> input = Reader.readLinesAsList("day22");
        Map<Long, Long> secretCounts = new HashMap<>();
        for (String line : input) {
            Long secret = Long.parseLong(line);
            secretCounts.put(secret, secretCounts.getOrDefault(secret, 0L) + 1);
        }

        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    private static long part1(List<String> input) {
        List<List<Long>> allSecrets = getAllSecrets(input);
        long sum = 0;
        for (List<Long> secrets : allSecrets) {
            sum += secrets.getLast();
        }
        return sum;
    }

    private static int part2(List<String> input) {
        List<List<Long>> allSecrets = getAllSecrets(input);
        List<List<Integer>> lastNumberList = changeToLastNumber(allSecrets);

        Map<String, Integer> lastFourValueMap = new HashMap<>();
        for (List<Integer> lastNumbers : lastNumberList) {
            List<String> alreadyVisited = new ArrayList<>();
            for (int i = 4; i < lastNumbers.size(); i++) {
                int last4Dif = lastNumbers.get(i - 3) - lastNumbers.get(i - 4);
                int last3Dif = lastNumbers.get(i - 2) - lastNumbers.get(i - 3);
                int last2Dif = lastNumbers.get(i - 1) - lastNumbers.get(i - 2);
                int last1Dif = lastNumbers.get(i) - lastNumbers.get(i - 1);
                String last4String = "" + last4Dif + last3Dif + last2Dif + last1Dif;
                if (!alreadyVisited.contains(last4String)) {
                    lastFourValueMap.put(last4String, lastFourValueMap.getOrDefault(last4String, 0) + lastNumbers.get(i));
                    alreadyVisited.add(last4String);
                }
            }
        }
        return lastFourValueMap.values().stream().max(Integer::compareTo).orElseThrow();
    }

    private static List<List<Integer>> changeToLastNumber(List<List<Long>> allSecrets) {
        List<List<Integer>> result = new ArrayList<>();
        for (List<Long> secrets : allSecrets) {
            List<Integer> newSecrets = new ArrayList<>();
            for (long secret : secrets) {
                newSecrets.add(determineLastNumber(secret));
            }
            result.add(newSecrets);
        }
        return result;
    }

    private static List<List<Long>> getAllSecrets(List<String> input) {
        List<List<Long>> allSecrets = new ArrayList<>();
        for (String line : input) {
            long secret = Long.parseLong(line);
            allSecrets.add(getMonkeySecrets(secret));
        }
        return allSecrets;
    }

    private static List<Long> getMonkeySecrets(long secret) {
        List<Long> result = new ArrayList<>();
        result.add(secret);
        for (int i = 0; i < 2000; i++) {
            secret = determineNewSecret(secret);
            result.add(secret);
        }
        return result;
    }

    private static int determineLastNumber(long number) {
        if (number < 10) {
            return (int) number;
        }
        long modNumber = number / 10 * 10;
        return (int) (number % modNumber);
    }

    private static Long determineNewSecret(Long secret) {
        long value = secret * 64;
        secret = (secret ^ value) % 16777216;

        value = secret / 32;
        secret = (secret ^ value) % 16777216;

        value = secret * 2048;
        secret = (secret ^ value) % 16777216;

        return secret;
    }
}

