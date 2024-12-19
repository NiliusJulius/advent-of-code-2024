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
            } else if ( i >= 2) {
                patterns.add(input.get(i));
            }
        }

        System.out.println(part1(towels, patterns));
        System.out.println(part2(towels, patterns));
    }

    private static long part1(List<String> towels, List<String> patterns) {
        long possibleCount = 0;
        for (String pattern : patterns) {
            if (countPatternOptions(towels, pattern) > 0) {
                possibleCount++;
            }
        }
        return possibleCount;
    }

    private static long part2(List<String> towels, List<String> patterns) {
        long possibleCount = 0;
        for (String pattern : patterns) {
            possibleCount += countPatternOptions(towels, pattern);
        }
        return possibleCount;
    }

    private static long countPatternOptions(List<String> towels, String pattern) {
        Map<Integer, Long> patternWays = new HashMap<>(Map.of(0, 1L));
        for (int i = 0; i < pattern.length(); i++) {
            for (String towel : towels) {
                int subStringEnd = i + towel.length();
                if (subStringEnd <= pattern.length() && pattern.startsWith(towel, i)) {
                    if (!patternWays.containsKey(i)) {
                        patternWays.put(i, 0L);
                    }
                    patternWays.put(subStringEnd, patternWays.containsKey(subStringEnd)
                            ? patternWays.get(subStringEnd) + patternWays.get(i)
                            : patternWays.get(i));
                }
            }
        }
        return patternWays.getOrDefault(pattern.length(), 0L);
    }
}

