package com.niliusjulius.aoc.days;

import com.niliusjulius.aoc.util.Reader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day01 {

    public static void main(String[] args) {
        List<String> input = Reader.readLinesAsList("day01");
        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        for (String line : input) {
            String[] temp = line.split(" {3}");
            left.add(Integer.parseInt(temp[0]));
            right.add(Integer.parseInt(temp[1]));
        }
        System.out.println(part1(left, right));
        System.out.println(part2(left, right));
    }

    private static int part1(List<Integer> left, List<Integer> right) {
        Collections.sort(left);
        Collections.sort(right);

        int distance = 0;
        for (int i = 0; i < left.size(); i++) {
            distance += Math.abs(right.get(i) - left.get(i));
        }
        return distance;
    }

    private static int part2(List<Integer> left, List<Integer> right) {
        int number = 0;
        for (Integer integer : left) {
            number += Collections.frequency(right, integer) * integer;
        }
        return number;
    }


}

