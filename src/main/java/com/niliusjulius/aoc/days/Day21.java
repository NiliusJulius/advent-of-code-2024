package com.niliusjulius.aoc.days;

import com.niliusjulius.aoc.util.Reader;
import com.niliusjulius.aoc.util.traverse2d.Coordinate;
import com.niliusjulius.aoc.util.traverse2d.Direction;
import com.niliusjulius.aoc.util.traverse2d.Grid;

import java.util.*;
import java.util.stream.Collectors;

public class Day21 {

    private static final Coordinate NUMERIC_START = new Coordinate(3, 2);
    private static final Coordinate DIRECTION_START = new Coordinate(0, 2);
    private static final Map<DepthMoveCost, Long> depthCostMap = new HashMap<>();

    public static void main(String[] args) {
        List<String> input = Reader.readLinesAsList("day21");
        String[][] numericalArray = new String[][] {{"7", "8", "9"}, {"4", "5", "6"}, {"1", "2", "3"}, {".", "0", "A"}};
        Grid<String> numericalGrid = new Grid<>(numericalArray);
        String[][] directionalArray = new String[][] {{".", "^", "A"}, {"<", "v", ">"}};
        Grid<String> directionalGrid = new Grid<>(directionalArray);

        System.out.println(part1(input, numericalGrid, directionalGrid));
        System.out.println(part2(input, numericalGrid, directionalGrid));
    }

    private static long part1(List<String> input, Grid<String> numericalGrid, Grid<String> directionalGrid) {
        return solve(input, numericalGrid, directionalGrid, 2);
    }

    private static long part2(List<String> input, Grid<String> numericalGrid, Grid<String> directionalGrid) {
        return solve(input, numericalGrid, directionalGrid, 25);
    }

    private static long solve(List<String> input, Grid<String> numericGrid, Grid<String> directionGrid, int robotsCount) {
        long result = 0;
        for (String line : input) {
            long best = Long.MAX_VALUE;
            List<String> robotMoves = determinePossibleMoves(line, NUMERIC_START, "", numericGrid);
            for (String move : robotMoves) {
                long count = countPushes(move, robotsCount, directionGrid);
                best = Math.min(best, count);
            }
            long startingNumber = Long.parseLong(line.substring(0, line.length() - 1));
            result += best * startingNumber;
        }
        return result;
    }

    private static List<String> determinePossibleMoves(String line, Coordinate start, String core, Grid<String> grid) {
        if (line.isEmpty()) {
            return List.of(core);
        }
        char c = line.charAt(0);
        List<String> collect = new ArrayList<>();
        for (Path path : shortestPaths(start, String.valueOf(c), grid)) {
            collect.addAll(determinePossibleMoves(line.substring(1), path.end, core + path.steps + "A", grid));
        }
        int minLength = collect.stream().mapToInt(String::length).min().orElseThrow();
        return collect.stream().filter(s -> s.length() == minLength).distinct().collect(Collectors.toList());
    }

    private static List<Path> shortestPaths(Coordinate start, String goal, Grid<String> grid) {
        if (isButton(start, grid) && grid.get(start).equals(goal)) {
            return List.of(new Path(start, ""));
        }
        Queue<Path> queue = new ArrayDeque<>(List.of(new Path(start, "")));
        List<Path> result = new ArrayList<>();
        int best = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            Path path = queue.poll();
            List<Coordinate> nextLocations = List.of(path.end.nextCoordinate(Direction.UP), path.end.nextCoordinate(Direction.DOWN),
                    path.end.nextCoordinate(Direction.LEFT), path.end.nextCoordinate(Direction.RIGHT));
            for (Coordinate nextLocation : nextLocations) {
                if (isButton(nextLocation, grid)) {
                    Path newPath = new Path(nextLocation, path.steps + determineMovesInDirection(path.end, nextLocation));
                    if (grid.get(nextLocation).equals(goal)) {
                        if (newPath.steps.length() < best) {
                            best = newPath.steps.length();
                            result.clear();
                            result.add(newPath);
                        } else if (newPath.steps.length() == best) {
                            result.add(newPath);
                        }
                    }else if (newPath.steps.length() < best) {
                        queue.add(newPath);
                    }
                }
            }
        }
        return result;
    }

    private static boolean isButton(Coordinate coordinate, Grid<String> grid) {
        return grid.withinGrid(coordinate) && !grid.get(coordinate).equals(".");
    }

    private static String determineMovesInDirection(Coordinate coordinate, Coordinate otherCoordinate) {
        if (coordinate.x < otherCoordinate.x) {
            return "v";
        } else if (coordinate.x > otherCoordinate.x) {
            return "^";
        } else if (coordinate.y < otherCoordinate.y) {
            return ">";
        } else if (coordinate.y > otherCoordinate.y) {
            return "<";
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static long countPushes(String move, int robots, Grid<String> grid) {
        if (robots == 0) {
            return move.length();
        }
        DepthMoveCost key = new DepthMoveCost(move, robots);
        if (depthCostMap.containsKey(key)) {
            return depthCostMap.get(key);
        }
        Coordinate position = DIRECTION_START;
        long sum = 0L;
        for (char c : move.toCharArray()) {
            long min = Long.MAX_VALUE;
            List<String> roads = shortestPaths(position, String.valueOf(c), grid).stream().map(p -> p.steps + "A").toList();
            for (String road : roads) {
                min = Long.min(min, countPushes(road, robots - 1, grid));
            }
            sum += min;
            position = grid.indexOf(String.valueOf(c));
        }
        depthCostMap.put(key, sum);
        return sum;
    }


    private record Path(Coordinate end, String steps) {
    }

    private record DepthMoveCost(String moves, int depth) {
    }
}

