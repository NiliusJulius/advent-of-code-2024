package com.niliusjulius.aoc.days;

import com.niliusjulius.aoc.util.Reader;
import com.niliusjulius.aoc.util.traverse2d.Coordinate;
import com.niliusjulius.aoc.util.traverse2d.Grid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day10 {

    public static void main(String[] args) {
        List<String> input = Reader.readLinesAsList("day10");
        Grid<Integer> grid = new Grid<>(input
                .stream()
                .map(line -> Arrays.stream(line.split("")).map(Integer::parseInt).toArray(Integer[]::new))
                .toArray(Integer[][]::new));
        List<Coordinate> trailHeads = grid.findAll(0);

        System.out.println(part1(grid, trailHeads));
        System.out.println(part2(grid, trailHeads));
    }

    private static int part1(Grid<Integer> grid, List<Coordinate> trailHeads) {
        int trailScored = 0;
        for (Coordinate head : trailHeads) {
            trailScored += findTrailEnds(grid, List.of(head), 0, new ArrayList<>(), false).size();
        }
        return trailScored;
    }

    private static int part2(Grid<Integer> grid, List<Coordinate> trailHeads) {
        int trailScored = 0;
        for (Coordinate head : trailHeads) {
            trailScored += findTrailEnds(grid, List.of(head), 0, new ArrayList<>(), true).size();
        }
        return trailScored;
    }

    private static List<Coordinate> findTrailEnds(Grid<Integer> grid, List<Coordinate> trailPositions, int currentHeight, List<Coordinate> trailEnds, boolean pt2) {
        for (Coordinate coordinate : trailPositions) {
            List<Coordinate> adjacentPathOptions = grid.findAdjacent(coordinate, currentHeight + 1);
            if (currentHeight + 1 == 9) {
                for (Coordinate adjacent : adjacentPathOptions) {
                    if (!pt2) {
                        if (!trailEnds.contains(adjacent)) {
                            trailEnds.add(adjacent);
                        }
                    } else {
                        trailEnds.add(adjacent);
                    }
                }
            } else {
                trailEnds = findTrailEnds(grid, adjacentPathOptions, currentHeight + 1, trailEnds, pt2);
            }
        }
        return trailEnds;
    }
}

