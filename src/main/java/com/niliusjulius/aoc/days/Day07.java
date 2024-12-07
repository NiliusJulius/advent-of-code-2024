package com.niliusjulius.aoc.days;

import com.niliusjulius.aoc.util.Reader;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Long.parseLong;

public class Day07 {

    public static void main(String[] args) {
        List<String> input = Reader.readLinesAsList("day07");

        Map<Long, List<Long>> testCases = new HashMap<>();
        for (String testCase : input) {
            String[] testResult = testCase.split(":");
            List<Long> values = Arrays.stream(testResult[1].trim().split(" ")).map(Long::parseLong).toList();

            testCases.put(parseLong(testResult[0]), values);
        }
        System.out.println(part1(testCases));
        System.out.println(part2(testCases));
    }

    private static long part1(Map<Long,List<Long>> testCases) {
        long correctValuesSum = 0;

        for (Map.Entry<Long, List<Long>> entry : testCases.entrySet()) {
            long testResult = entry.getKey();
            List<Long> testValues = entry.getValue();

            if (test(testResult, testValues, 0, testValues.getFirst(), false)) {
                correctValuesSum += testResult;
            }
        }
        return correctValuesSum;
    }

    private static long part2(Map<Long,List<Long>> testCases) {
        long correct = 0;

        for (Map.Entry<Long, List<Long>> entry : testCases.entrySet()) {
            long testResult = entry.getKey();
            List<Long> testValues = entry.getValue();

            if (test(testResult, testValues, 0, testValues.getFirst(), true)) {
                correct += testResult;
            }
        }
        return correct;
    }

    private static boolean test(long testResult, List<Long> testValues, int index, long currentValue, boolean part2) {
        index++;
        if (index >= testValues.size()) {
            return currentValue == testResult;
        }

        long nextValue = testValues.get(index);
        boolean result = test(testResult, testValues, index, currentValue + nextValue, part2) ||
                test(testResult, testValues, index, currentValue * nextValue, part2);

        if (part2) {
            long newValue = parseLong(currentValue + "" + nextValue);
            result = result || test(testResult, testValues, index, newValue, part2);
        }

        return result;
    }
}

