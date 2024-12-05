package com.niliusjulius.aoc.days;

import com.niliusjulius.aoc.util.Reader;

import java.util.*;

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
//                    List<Integer> list = new ArrayList<>(Integer.parseInt(parts[1]));
                    List<Integer> list = new ArrayList<>();
                    list.add(Integer.parseInt(parts[1]));
                    ordering.put(Integer.parseInt(parts[0]), list);
                }
            } else if (line.contains(",")) {
                List<Integer> pages = new LinkedList<>();
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
            boolean correctOrder = true;
            List<Integer> includedPages = new ArrayList<>();
            for (Integer page : pages) {
                if (ordering.containsKey(page)) {
                    for (Integer laterPage : ordering.get(page)) {
                        if (includedPages.contains(laterPage)) {
                            correctOrder = false;
                            break;
                        }
                    }
                }
                includedPages.add(page);
            }
            if (correctOrder) {
                middleNumbers+= pages.get(pages.size() / 2);
            }
        }

        return middleNumbers;
    }

    private static int part2(Map<Integer, List<Integer>> ordering, List<List<Integer>> updates) {
        int middleNumbers = 0;
        List<List<Integer>> incorrectPages = new ArrayList<>();
        for (List<Integer> pages : updates) {
            boolean correctOrder = true;
            List<Integer> includedPages = new ArrayList<>();
            for (Integer page : pages) {
                if (ordering.containsKey(page)) {
                    for (Integer laterPage : ordering.get(page)) {
                        if (includedPages.contains(laterPage)) {
                            correctOrder = false;
                            break;
                        }
                    }
                }
                includedPages.add(page);
            }
            if (!correctOrder) {
                incorrectPages.add(pages);
            }
        }

        for (List<Integer> pages : incorrectPages) {
            List<Integer> correct = testPages(ordering, pages);
            middleNumbers+= correct.get(pages.size() / 2);
        }

        return middleNumbers;
    }

    private static List<Integer> testPages(Map<Integer, List<Integer>> ordering, List<Integer> pages) {
        List<Integer> includedPages = new ArrayList<>();
        for (int i = 0; i < pages.size(); i++) {
            Integer page = pages.get(i);
            if (ordering.containsKey(page)) {
                for (Integer laterPage : ordering.get(page)) {
                    if (includedPages.contains(laterPage)) {
//                        for (int j = 0; j < includedPages.indexOf(laterPage); j++) {
                        int pageNumber = pages.get(i);
                        pages.remove(i);
                        pages.add(includedPages.indexOf(laterPage), pageNumber);
                        return testPages(ordering, pages);
//                        }
                    }
                }
            }
            includedPages.add(page);
        }
        return pages;
    }
}

