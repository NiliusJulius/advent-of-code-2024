package com.niliusjulius.aoc.days;

import com.niliusjulius.aoc.util.Reader;

import java.util.*;

public class Day02 {

    public static void main(String[] args) {
        List<String> input = Reader.readLinesAsList("day02");

        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    private static int part1(List<String> input) {
        int safe = 0;
        for (String report : input) {
            List<Integer> points = Arrays.stream(report.split(" ")).map(Integer::parseInt).toList();
            if (isSafe(points)) {
                safe++;
            }
        }
        return safe;
    }

    private static boolean isSafe(List<Integer> points) {
        boolean increasing = points.get(1) >= points.get(0);
        for (int i = 1; i < points.size(); i++) {
            if (differenceTooLarge(points.get(i), points.get(i-1))) {
                return false;
            }
            if (increasing && points.get(i) <= points.get(i-1)) {
                return false;
            } else if (!increasing && points.get(i) >= points.get(i-1)) {
                return false;
            }
        }
        return true;
    }

    private static boolean differenceTooLarge(int a, int b) {
        return Math.abs(a-b) > 3;
    }

    private static int part2(List<String> input) {
        int safe = 0;
        for (String report : input) {
            List<Integer> points = Arrays.stream(report.split(" ")).map(Integer::parseInt).toList();
            List<Integer> pointsList = new LinkedList<>(points);
            List<Integer> pointsList2 = new LinkedList<>(points);
            pointsList2.removeFirst();
            if (isSafe2(pointsList, false) || isSafe2(pointsList2, true)) {
                safe++;
            }
        }
        return safe;
    }

    private static boolean checkSubsets(List<Integer> points, boolean removed, int i) {
            if (!removed) {
                List<Integer> copy1 = new LinkedList<>(points);
                List<Integer> copy2 = new LinkedList<>(points);
                copy1.remove(i);
                copy2.remove(i-1);
                return isSafe2(copy1, true) || isSafe2(copy2, true);
            } else {
                return false;
            }
    }

    private static boolean isSafe2(List<Integer> points, boolean removed) {
        boolean increasing = points.get(1) >= points.get(0);
        for (int i = 1; i < points.size(); i++) {
            if (increasing && points.get(i) > points.get(i-1)) {
                if (differenceTooLarge(points.get(i), points.get(i-1))) {
                    return checkSubsets(points, removed, i);
                }
            } else if (!increasing && points.get(i) < points.get(i-1)) {
                if (differenceTooLarge(points.get(i), points.get(i-1))) {
                    return checkSubsets(points, removed, i);
                }
            } else {
                return checkSubsets(points, removed, i);
            }
        }
        return true;
    }
}

