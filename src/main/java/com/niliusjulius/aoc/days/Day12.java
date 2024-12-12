package com.niliusjulius.aoc.days;

import com.niliusjulius.aoc.util.Reader;
import com.niliusjulius.aoc.util.traverse2d.Coordinate;
import com.niliusjulius.aoc.util.traverse2d.Grid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day12 {

    public static void main(String[] args) {
        List<String> input = Reader.readLinesAsList("day12");
        Grid<String> grid = new Grid<>(input
                .stream()
                .map(line -> Arrays.stream(line.split("")).toArray(String[]::new))
                .toArray(String[][]::new));
        System.out.println(part1(grid));
        System.out.println(part2(grid));
    }

    private static int part1(Grid<String> grid) {
        List<List<Coordinate>> regions = determineRegions(grid);

        int fenceCost = 0;
        for (List<Coordinate> regionCoordinates : regions) {
            int areaSize =  regionCoordinates.size();
            int perimeter = 0;
            for (Coordinate regionCoordinate : regionCoordinates) {
                perimeter += 4 - grid.findAdjacent(regionCoordinate, grid.get(regionCoordinate)).size();
            }
            fenceCost += perimeter * areaSize;
        }
        return fenceCost;
    }

    private static int part2(Grid<String> grid) {
        List<List<Coordinate>> regions = determineRegions(grid);

        int fenceCost = 0;
        for (List<Coordinate> regionCoordinates : regions) {
            int areaSize =  regionCoordinates.size();
            int sides = 0;
            for (Coordinate regionCoordinate : regionCoordinates) {
                sides += grid.countCoordinateCorners(regionCoordinate);
            }
            fenceCost += sides * areaSize;
        }
        return fenceCost;
    }

    private static List<List<Coordinate>> determineRegions(Grid<String> grid) {
        List<Coordinate> alreadyInRegion = new ArrayList<>();
        List<List<Coordinate>> regions = new ArrayList<>();

        for (int i = 0; i < grid.height(); i++) {
            for (int j = 0; j < grid.length(); j++) {
                Coordinate currentCoordinate = new Coordinate(i, j);
                if (!alreadyInRegion.contains(currentCoordinate)) {
                    List<Coordinate> regionCoordinates = grid.findRegionAt(currentCoordinate, grid.get(currentCoordinate));
                    alreadyInRegion.addAll(regionCoordinates);
                    regions.add(regionCoordinates);
                }
            }
        }
        return regions;
    }
}

