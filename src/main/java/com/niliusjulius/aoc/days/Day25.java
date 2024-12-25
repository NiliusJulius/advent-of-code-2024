package com.niliusjulius.aoc.days;

import com.niliusjulius.aoc.util.Reader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Day25 {

    public static void main(String[] args) {
        List<String> input = Reader.readLinesAsList("day25");
        List<List<Integer>> keys = new ArrayList<>();
        List<List<Integer>> locks = new ArrayList<>();
        for (int i = 0; i < input.size(); i+=8) {
            boolean isKey = !input.get(i).equals("#####");

            List<Integer> columns = Arrays.asList(0, 0, 0, 0, 0);
            for (int j = i+1; j <= i+5; j++) {
                String row = input.get(j);
                for (int k = 0; k < row.length(); k++) {
                    char ch = row.charAt(k);
                    if (ch == '#') {
                        columns.set(k, columns.get(k) + 1);
                    }
                }
            }
            if (isKey) {
                keys.add(columns);
            } else {
                locks.add(columns);
            }
        }

        System.out.println(part1(locks, keys));
    }

    private static int part1(List<List<Integer>> locks, List<List<Integer>> keys) {
        int combinations = 0;
        for (List<Integer> lock : locks) {
            for (List<Integer> key : keys) {
                boolean foundMatch = true;
                for (int k = 0; k < lock.size(); k++) {
                    if (lock.get(k) + key.get(k) > 5) {
                        foundMatch = false;
                        break;
                    }
                }
                if (foundMatch) {
                    combinations++;
                }
            }
        }

        return combinations;
    }
}

