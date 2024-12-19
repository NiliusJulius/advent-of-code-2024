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
    }

    private static int part1(List<String> towels, List<String> patterns) {
        int possibleCount = 0;
        Set<String> impossiblePatterns;
        for (String pattern : patterns) {
            impossiblePatterns = new HashSet<>();
            if (canMakePattern(towels, pattern, 0, impossiblePatterns)) {
                possibleCount++;
            }
        }

        return possibleCount;
    }

    private static boolean canMakePattern(List<String> towels, String pattern, int index, Set<String> impossiblePatterns) {
        String patternSub = pattern.substring(index);
        if (impossiblePatterns.contains(patternSub)) {
            return false;
        }
        boolean canMakePattern = false;
        for (String towel : towels) {
            if (patternSub.startsWith(towel)) {
                if (pattern.length() == index + towel.length()) {
                    return true;
                }
                if (canMakePattern(towels, pattern, index + towel.length(), impossiblePatterns)) {
                    return true;
                } else {
                    impossiblePatterns.add(patternSub);
                }
            }
        }
        return canMakePattern;
    }
}

