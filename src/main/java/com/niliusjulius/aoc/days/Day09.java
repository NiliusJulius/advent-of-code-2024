package com.niliusjulius.aoc.days;

import com.niliusjulius.aoc.util.Reader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day09 {

    public static void main(String[] args) {
        String input = Reader.readLineAsString("day09");

        System.out.println(part1(input));
        System.out.println(part2(input));
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
                while (rearranged.get(lastNumberIndex) == null) {
                    lastNumberIndex--;
                }
            } else {
                compacted.add(rearranged.get(i));
            }

        }
        long checksum = 0;
        for (int i = 0; i < compacted.size(); i++) {
            checksum += (long)i * (long)compacted.get(i);
        }
        return checksum;
    }

    private static long part2(String input) {
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
        List<Integer> compacted = new ArrayList<>(rearranged);
        for (int i = lastNumberIndex; i >= 0; i--) {
            boolean moved = false;
            while (compacted.get(lastNumberIndex) == null) {
                lastNumberIndex--;
            }
            int lastNumberEnd = lastNumberIndex;
            int lastNumber = compacted.get(lastNumberIndex);
            while (lastNumberEnd > 0 && compacted.get(lastNumberEnd-1) != null && compacted.get(lastNumberEnd-1) == lastNumber) {
                lastNumberEnd--;
            }
            if (lastNumberEnd <= 0) {
                break;
            }
            int lastNumberLength = lastNumberIndex - lastNumberEnd + 1;

            for (int j = 0; j < lastNumberEnd; j++) {
                if (compacted.get(j) == null) {
                    int gapStart = j;
                    int gapEnd = gapStart;
                    while (compacted.get(gapEnd+1) == null) {
                        gapEnd++;
                    }
                    int gapLength = gapEnd - gapStart + 1;
                    if (gapLength >= lastNumberLength) {
                        for (int k = gapStart; k < gapStart + lastNumberLength; k++) {
                            compacted.set(k, lastNumber);
                        }
                        for (int k = lastNumberEnd; k <= lastNumberIndex; k++) {
                            compacted.set(k, null);
                        }
                        moved = true;
                    }
                }
                if (moved) {
                    break;
                }
            }
            lastNumberIndex = lastNumberEnd - 1;
        }
        long checksum = 0;
        for (int i = 0; i < compacted.size(); i++) {
            if (compacted.get(i) != null) {
                checksum += (long) i * (long) compacted.get(i);
            }
        }
        return checksum;
    }
}

