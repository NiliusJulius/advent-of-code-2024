package com.niliusjulius.aoc.days;

import com.niliusjulius.aoc.util.Reader;
import com.niliusjulius.aoc.util.traverse2d.Coordinate;
import com.niliusjulius.aoc.util.traverse2d.Grid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Day18 {

    private static final int HEIGHT = 71;
    private static final int WIDTH = 71;

    public static void main(String[] args) {
        List<String> input = Reader.readLinesAsList("day18");
        List<Coordinate> coordinates = new ArrayList<>();
        for (String coordinate : input) {
            String[] coord = coordinate.split(",");
             coordinates.add(new Coordinate(Integer.parseInt(coord[1]),Integer.parseInt(coord[0])));
        }

        System.out.println(part1(coordinates));
        System.out.println(Objects.requireNonNull(part2(coordinates)).toStringReverse());
    }

    private static Integer part1(List<Coordinate> coordinates) {
        Grid<String> grid = createGrid();
        fillGrid(coordinates, grid);

        return grid.findCheapestPath(new Coordinate(0,0), new Coordinate(HEIGHT-1,WIDTH-1), List.of("."));
    }

    private static Coordinate part2(List<Coordinate> coordinates) {
        int indexStart = 1024;
        int indexEnd = coordinates.size();
        int index = (indexEnd - indexStart) / 2 + indexStart;
        while (indexStart < indexEnd && indexStart != index && indexEnd != index) {
            Grid<String> grid = createGrid();
            for (int i = 0; i < index; i++) {
                grid.set(coordinates.get(i), "#");
            }
            if (grid.findCheapestPath(new Coordinate(0,0), new Coordinate(HEIGHT-1,WIDTH-1), List.of(".")) != null) {
                indexStart = index;
            } else {
                indexEnd = index;
            }
            index = (indexEnd - indexStart) / 2 + indexStart;
        }

        return coordinates.get(index);
    }

    private static Grid<String> createGrid() {
        String[][] locations = new String[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++) {
            Arrays.fill(locations[i], ".");
        }
        return new Grid<>(locations);
    }

    private static void fillGrid(List<Coordinate> coordinates, Grid<String> grid) {
        for (int i = 0; i < 1024; i++) {
            grid.set(coordinates.get(i), "#");
        }
    }
}

