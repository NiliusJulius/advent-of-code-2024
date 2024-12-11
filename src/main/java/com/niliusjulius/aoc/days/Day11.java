package com.niliusjulius.aoc.days;

import com.niliusjulius.aoc.util.Reader;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day11 {

    public static void main(String[] args) {
        String input = Reader.readLineAsString("day11");
        List<Long> stones = Arrays.stream(input.split(" ")).map(Long::parseLong).toList();
        Map<Long, Long> stoneCounts = new HashMap<>();
        for (Long stone : stones) {
            stoneCounts.put(stone, stoneCounts.getOrDefault(stone, 0L) + 1);
        }
        System.out.println(part1(stoneCounts));
        System.out.println(part2(stoneCounts));
    }

    private static long part1(Map<Long, Long> stonesCounts) {
        return countStones(stonesCounts, 25);
    }

    private static long part2(Map<Long, Long> stoneCounts) {
        return countStones(stoneCounts, 75);
    }

    private static long countStones(Map<Long, Long> stoneCounts, int blinks) {

        for (int i = 0; i < blinks; i++) {
            Map<Long, Long> nextCounts = new HashMap<>();
            for (Map.Entry<Long, Long> entry : stoneCounts.entrySet()) {
                List<Long> newStones = determineNewStones(entry.getKey());
                for (Long stone : newStones) {
                    nextCounts.put(stone, nextCounts.getOrDefault(stone, 0L) + entry.getValue());
                }
            }
            stoneCounts = nextCounts;
        }
        return stoneCounts.values().stream().mapToLong(l -> l).sum();
    }

    private static List<Long> determineNewStones(Long stone) {
        if (stone == 0) {
            return List.of(1L);
        } else if (String.valueOf(stone).length() % 2 == 0) {
            String toSplit = String.valueOf(stone);
            Long left = Long.parseLong(toSplit.substring(0, toSplit.length() / 2));
            Long right = Long.parseLong(toSplit.substring(toSplit.length() / 2));
            return List.of(left, right);
        } else {
            return List.of(stone * 2024);
        }
    }
}

