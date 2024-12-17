package com.niliusjulius.aoc.days;

import com.niliusjulius.aoc.util.Pair;
import com.niliusjulius.aoc.util.Reader;
import com.niliusjulius.aoc.util.traverse2d.Coordinate;
import com.niliusjulius.aoc.util.traverse2d.Direction;
import com.niliusjulius.aoc.util.traverse2d.Grid;

import java.util.*;

public class Day06 {

    public static void main(String[] args) {
        List<String> input = Reader.readLinesAsList("day06");
        Direction startDirection = Direction.UP;
        Grid<String> grid = new Grid<>(input
                .stream()
                .map(line -> Arrays.stream(line.split("")).toArray(String[]::new))
                .toArray(String[][]::new));
        Coordinate startLocation = grid.indexOf("^", ">", "v", "<");

        System.out.println(part1(grid, startLocation, startDirection));
        System.out.println(part2(grid, startLocation, startDirection));
    }

    private static int part1(Grid<String> grid, Coordinate startCoordinate, Direction startDirection) {
        Set<Map.Entry<Coordinate, Direction>> visited = findPath(grid, startCoordinate, startDirection);

        return (int) visited.stream().map(Map.Entry::getKey).distinct().count();
    }

    private static int part2(Grid<String> grid, Coordinate startingPos, Direction startDirection) {
        int obstructions = 0;
        List<Coordinate> visited = new ArrayList<>(findPath(grid, startingPos, startDirection).stream().map(Map.Entry::getKey).distinct().toList());
        visited.remove(startingPos);

        for (Coordinate visitedCoordinate : visited) {
            grid.set(visitedCoordinate, "#");
            try {
                findPath(grid, startingPos, startDirection);
            } catch (LoopException e) {
                obstructions++;
            } finally {
                grid.set(visitedCoordinate, ".");
            }
        }
        return obstructions;
    }

    public static Set<Map.Entry<Coordinate, Direction>> findPath(Grid<String> grid, Coordinate startPos, Direction startDirection) {
        Coordinate currentPos = startPos;
        Direction direction = startDirection;
        Set<Map.Entry<Coordinate, Direction>> visited = new HashSet<>();
        while (grid.withinGrid(currentPos)) {
            if (visited.contains(Pair.of(currentPos, direction))) {
                throw new LoopException();
            }
            visited.add(Pair.of(currentPos, direction));
            Coordinate nextPos = currentPos.nextCoordinate(direction);
            if (!grid.withinGrid(nextPos)) {
                break;
            }
            if (!grid.get(nextPos).equals("#")) {
                currentPos = nextPos;
            } else {
                direction = direction.turnRight(false);
            }
        }
        return visited;
    }

    private static class LoopException extends RuntimeException {}
}

