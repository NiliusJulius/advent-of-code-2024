package com.niliusjulius.aoc.days;

import com.niliusjulius.aoc.util.Reader;
import com.niliusjulius.aoc.util.traverse2d.Coordinate;
import com.niliusjulius.aoc.util.traverse2d.Grid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day20 {

    public static final int MIN_TIME_SAVED = 100;

    public static void main(String[] args) {
        List<String> input = Reader.readLinesAsList("day20");
        Grid<String> grid = new Grid<>(input
                .stream()
                .map(line -> Arrays.stream(line.split("")).toArray(String[]::new))
                .toArray(String[][]::new));
        Coordinate startLocation = grid.indexOf("S");
        grid.set(startLocation, ".");
        Coordinate endLocation = grid.indexOf("E");
        grid.set(endLocation, ".");
        List<Coordinate> path = getPath(grid, startLocation, endLocation);

        System.out.println(part1(path));
        System.out.println(part2(path));
    }

    private static long part1(List<Coordinate> path) {
        return findCheatCount(path, 2);
    }

    private static long part2(List<Coordinate> path) {
        return findCheatCount(path, 20);
    }

    private static long findCheatCount(List<Coordinate> path, int cheatTime) {
        long cheatOptions = 0;

        for (int i = 0; i < path.size(); i++) {
            for (int j = i + MIN_TIME_SAVED; j < path.size(); j++) {
                Coordinate start = path.get(i);
                Coordinate end = path.get(j);
                int distance = start.distance(end);
                if (j - i - distance >= MIN_TIME_SAVED && distance <= cheatTime) {
                    cheatOptions++;
                }
            }
        }
        return cheatOptions;
    }

    private static List<Coordinate> getPath(Grid<String> grid, Coordinate start, Coordinate end) {
        List<Coordinate> path = new ArrayList<>();
        Coordinate current = start;
        while (!current.equals(end)) {
            path.add(current);
            List<Coordinate> nextOptions = grid.findAdjacent(current, ".");
            for (Coordinate next : nextOptions) {
                if (!path.contains(next)) {
                    current = next;
                }
            }
        }
        path.add(end);
        return path;
    }

}

