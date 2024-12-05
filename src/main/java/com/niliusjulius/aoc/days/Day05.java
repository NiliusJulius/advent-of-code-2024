package com.niliusjulius.aoc.days;

import com.niliusjulius.aoc.util.Reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day05 {

    public static void main(String[] args) {
        List<String> input = Reader.readLinesAsList("day05");
        Map<Integer, List<Integer>> ordering = new HashMap<>();
        List<List<Integer>> updates = new ArrayList<>();
        for (String line : input) {
            if (line.contains("|")) {
                String[] parts = line.split("\\|");
                if (ordering.containsKey(Integer.parseInt(parts[0]))) {
                    ordering.get(Integer.parseInt(parts[0])).add(Integer.parseInt(parts[1]));
                } else {
                    List<Integer> list = new ArrayList<>(List.of(Integer.parseInt(parts[1])));
                    ordering.put(Integer.parseInt(parts[0]), list);
                }
            } else if (line.contains(",")) {
                List<Integer> pages = new ArrayList<>();
                for (String part : line.split(",")) {
                    pages.add(Integer.parseInt(part));
                }
                updates.add(pages);
            }
        }

        System.out.println(part1(ordering, updates));
        System.out.println(part2(ordering, updates));
    }

    private static int part1(Map<Integer, List<Integer>> ordering, List<List<Integer>> updates) {
        int middleNumbers = 0;
        for (List<Integer> pages : updates) {
            if (isCorrectlyOrdered(ordering, pages)) {
                middleNumbers+= pages.get(pages.size() / 2);
            }
        }
        return middleNumbers;
    }

    private static boolean isCorrectlyOrdered(Map<Integer, List<Integer>> ordering, List<Integer> pages) {
        boolean correctOrder = true;
        List<Integer> alreadySeenPages = new ArrayList<>();
        for (Integer page : pages) {
            if (ordering.containsKey(page)) {
                for (Integer laterPage : ordering.get(page)) {
                    if (alreadySeenPages.contains(laterPage)) {
                        correctOrder = false;
                        break;
                    }
                }
            }
            alreadySeenPages.add(page);
        }
        return correctOrder;
    }

    private static int part2(Map<Integer, List<Integer>> ordering, List<List<Integer>> updates) {
        int middleNumbers = 0;
        List<List<Integer>> incorrectUpdates = new ArrayList<>();
        for (List<Integer> pages : updates) {
            if (!isCorrectlyOrdered(ordering, pages)) {
                incorrectUpdates.add(pages);
            }
        }

        for (List<Integer> pages : incorrectUpdates) {
            List<Integer> correct = testPages(ordering, pages);
            middleNumbers+= correct.get(pages.size() / 2);
        }
        return middleNumbers;
    }

    private static List<Integer> testPages(Map<Integer, List<Integer>> ordering, List<Integer> pages) {
        List<Integer> alreadySeenPages = new ArrayList<>();
        for (int i = 0; i < pages.size(); i++) {
            Integer page = pages.get(i);
            if (ordering.containsKey(page)) {
                for (Integer laterPage : ordering.get(page)) {
                    if (alreadySeenPages.contains(laterPage)) {
                        int pageNumber = pages.get(i);
                        pages.remove(i);
                        pages.add(alreadySeenPages.indexOf(laterPage), pageNumber);
                        return testPages(ordering, pages);
                    }
                }
            }
            alreadySeenPages.add(page);
        }
        return pages;
    }
}

