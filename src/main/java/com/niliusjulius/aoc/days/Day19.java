package com.niliusjulius.aoc.days;

import com.niliusjulius.aoc.util.Reader;

import java.util.*;

public class Day19 {

    public static void main(String[] args) {
        List<String> input = Reader.readLinesAsList("day19");
        List<String> towels = new ArrayList<>();
        List<String> patterns = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            if (i == 0) {
                towels = Arrays.stream(input.get(i).split(", ")).toList();
            } else if (i == 1) {
                // do nothing
            } else {
                patterns.add(input.get(i));
            }
        }

        System.out.println(part1(towels, patterns));
        System.out.println(part2(towels, patterns));
    }

    private static int part1(List<String> towels, List<String> patterns) {
        int possibleCount = 0;
        Set<String> impossiblePatterns;
        for (String pattern : patterns) {
            impossiblePatterns = new HashSet<>();
            int ways = canMakePattern(towels, pattern, 0, impossiblePatterns);
            if (ways > 0) {
                possibleCount++;
            }
        }

        return possibleCount;
    }

    private static long part2(List<String> towels, List<String> patterns) {
        long possibleCount = 0;
        Set<String> impossiblePatterns;
        for (String pattern : patterns) {
            impossiblePatterns = new HashSet<>();
            int ways = canMakePattern(towels, pattern, 0, impossiblePatterns);
            possibleCount += ways;
        }

        return possibleCount;
    }

    private static int canMakePattern(List<String> towels, String pattern, int index, Set<String> impossiblePatterns) {
        int ways = 0;
        String patternSub = pattern.substring(index);
        if (impossiblePatterns.contains(patternSub)) {
            return 0;
        }
        for (String towel : towels) {
            if (patternSub.startsWith(towel)) {
                if (pattern.length() == index + towel.length()) {
                    ways++;
                } else {
                    int howManyWays = canMakePattern(towels, pattern, index + towel.length(), impossiblePatterns);
                    if (howManyWays > 0) {
                        ways += howManyWays;
                    } else {
                        impossiblePatterns.add(patternSub);
                    }
                }
            }
        }
        return ways;
    }
}

