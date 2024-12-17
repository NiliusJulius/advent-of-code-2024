package com.niliusjulius.aoc.days;

import com.niliusjulius.aoc.util.Reader;
import com.niliusjulius.aoc.util.traverse2d.Coordinate;
import com.niliusjulius.aoc.util.traverse2d.Direction;
import com.niliusjulius.aoc.util.traverse2d.Grid;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.IntStream.range;

public class Day16 {

    public static void main(String[] args) {
        List<String> input = Reader.readLinesAsList("day16");
        Direction startDirection = Direction.RIGHT;
        Grid<String> grid = new Grid<>(input
                .stream()
                .map(line -> Arrays.stream(line.split("")).toArray(String[]::new))
                .toArray(String[][]::new));
        Coordinate startLocation = grid.indexOf("S");
        Coordinate endLocation = grid.indexOf("E");
        grid.set(endLocation, ".");
        grid.set(startLocation, ".");

        System.out.println(part1(grid, startLocation, startDirection, endLocation));
        System.out.println(part2(grid, startLocation, startDirection, endLocation));
    }

    private static long part1(Grid<String> grid, Coordinate startLocation, Direction startDirection, Coordinate endLocation) {
        Map<VisitedLocation, Long> costMap = createCostMap(grid, startLocation, startDirection);
        return determineCost(costMap, endLocation);
    }

    private static long part2(Grid<String> grid, Coordinate startLocation, Direction startDirection, Coordinate endLocation) {
        Map<VisitedLocation, Long> costMap = createCostMap(grid, startLocation, startDirection);
        long minScore = determineCost(costMap, endLocation);
        Set<VisitedLocation> visitedLocations = costMap.keySet().stream()
                .filter(visitedLocation -> visitedLocation.location.equals(endLocation) && costMap.get(visitedLocation) == minScore)
                .collect(Collectors.toSet());
        Set<VisitedLocation> path = findCheapestPath(grid, visitedLocations, costMap);
        return path.stream().map(VisitedLocation::location).distinct().count();
    }

    private static long determineCost(Map<VisitedLocation, Long> costMap, Coordinate endLocation) {
        long lowestCost = Long.MAX_VALUE;
        for (Map.Entry<VisitedLocation, Long> entry : costMap.entrySet()) {
            VisitedLocation location = entry.getKey();
            long cost = entry.getValue();
            if (location.location.equals(endLocation) && cost < lowestCost) {
                lowestCost = cost;
            }
        }
        return lowestCost;
    }

    private static Set<VisitedLocation> findCheapestPath(Grid<String> grid, Set<VisitedLocation> visitedLocations, Map<VisitedLocation, Long> costMap) {
        LinkedList<VisitedLocation> path = new LinkedList<>(visitedLocations);
        while (!path.isEmpty()) {
            VisitedLocation currentLocation = path.poll();
            findNextLocation(visitedLocations, path, currentLocation, costMap, grid);
        }
        return visitedLocations;
    }

    private static void findNextLocation(Set<VisitedLocation> visitedLocations, LinkedList<VisitedLocation> path,
                                                         VisitedLocation currentLocation, Map<VisitedLocation, Long> costMap,
                                                         Grid<String> grid) {
        long currentScore = costMap.get(currentLocation);
        range(0,2).forEach(left -> {
            Direction previousDirection;
        if (left == 0) {
            previousDirection = currentLocation.direction.turnLeft(false);
        } else {
            previousDirection = currentLocation.direction.turnRight(false);
        }
            VisitedLocation previousLocation = new VisitedLocation(currentLocation.location, previousDirection, 0);
            if (costMap.getOrDefault(previousLocation, Long.MAX_VALUE) == currentScore - 1000 && visitedLocations.add(previousLocation)) {
                path.add(previousLocation);
            }
        });
        Coordinate previousLocationCoordinate = currentLocation.location.nextCoordinate(currentLocation.direction.opposite());
        if (grid.get(previousLocationCoordinate).equals(".")) {
            VisitedLocation previousLocation = new VisitedLocation(previousLocationCoordinate, currentLocation.direction, 0);
            if (costMap.getOrDefault(previousLocation, Long.MAX_VALUE) == currentScore - 1 && visitedLocations.add(previousLocation)) {
                path.add(previousLocation);
            }
        }
    }

    private static Map<VisitedLocation, Long> createCostMap(Grid<String> grid, Coordinate startLocation, Direction startDirection) {
        PriorityQueue<VisitedLocation> queue = new PriorityQueue<>(Comparator.comparingLong(visitedLocation -> visitedLocation.score));
        queue.add(new VisitedLocation(startLocation, startDirection, 0));

        Map<VisitedLocation, Long> costMap = new HashMap<>();
        while (!queue.isEmpty()) {
            VisitedLocation currentLocation = queue.poll();
            findNextLocation(costMap, queue, currentLocation, grid);
        }
        return costMap;
    }

    private static void findNextLocation(Map<VisitedLocation, Long> costMap, PriorityQueue<VisitedLocation> queue,
                                                               VisitedLocation currentLocation, Grid<String> grid) {
        if (costMap.getOrDefault(currentLocation, Long.MAX_VALUE) <= currentLocation.score) {
            return;
        }
        costMap.put(currentLocation, currentLocation.score);
        Coordinate nextLocation = currentLocation.location.nextCoordinate(currentLocation.direction);
        if (grid.get(nextLocation).equals(".")) {
            VisitedLocation nextVisitedLocation = new VisitedLocation(nextLocation, currentLocation.direction, currentLocation.score + 1);
            queue.add(nextVisitedLocation);
        }
        queue.add(new VisitedLocation(currentLocation.location, currentLocation.direction.turnLeft(false),
                currentLocation.score + 1000));
        queue.add(new VisitedLocation(currentLocation.location, currentLocation.direction.turnRight(false),
                currentLocation.score + 1000));
    }

    private record VisitedLocation(Coordinate location, Direction direction, long score) {
        @Override
        public boolean equals(Object o) {
            VisitedLocation visitedLocation = (VisitedLocation) o;
            return Objects.equals(location, visitedLocation.location) && direction == visitedLocation.direction;
        }

        @Override
        public int hashCode() {
            return Objects.hash(location, direction);
        }
    }
}

