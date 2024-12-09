package com.niliusjulius.aoc.days;

import com.niliusjulius.aoc.util.Reader;

import java.util.ArrayList;
import java.util.List;

public class Day09 {

    public static void main(String[] args) {
        String input = Reader.readLineAsString("day09");

        System.out.println(part1(input));
    }

    private static long part1(String input) {
        List<Integer> rearranged = new ArrayList<>();
        int index = 0;
        for (int i = 0; i < input.length(); i++) {
            int times = Integer.parseInt(String.valueOf(input.charAt(i)));
            Integer charToUse;
            if (i % 2 == 0) {
                charToUse = index;
                index++;
            } else {
                charToUse = null;
            }
            for (int j = 0; j < times; j++) {
                rearranged.add(charToUse);
            }
        }

        int lastNumberIndex = rearranged.size() - 1;
        List<Integer> compacted = new ArrayList<>();
        for (int i = 0; i <= lastNumberIndex; i++) {
            if (rearranged.get(i) == null) {
                while (rearranged.get(lastNumberIndex) == null) {
                    lastNumberIndex--;
                }
                compacted.add(rearranged.get(lastNumberIndex));
                lastNumberIndex--;
            } else {
                compacted.add(rearranged.get(i));
            }

        }
        long checksum = 0;
        for (int i = 0; i < compacted.size(); i++) {
            checksum += i * (long)compacted.get(i);
        }
        return checksum;
    }
}

