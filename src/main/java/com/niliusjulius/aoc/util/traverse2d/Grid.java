package com.niliusjulius.aoc.util.traverse2d;

import java.util.ArrayList;
import java.util.List;

import static com.niliusjulius.aoc.util.traverse2d.Direction.*;

public class Grid<T> {
    private final T[][] grid;

    public Grid(T[][] grid) {
        this.grid = grid;
    }

    public boolean withinGrid(Coordinate coordinate) {
        return coordinate.x >= 0 && coordinate.y >= 0 && coordinate.x < grid.length && coordinate.y < grid[0].length;
    }

    public T get(Coordinate coordinate) {
        return grid[coordinate.x][coordinate.y];
    }
    
    public T getValueInDirection(Coordinate coordinate, Direction direction) {
        return grid[coordinate.x+direction.coordinate.x][coordinate.y+direction.coordinate.y];
    }

    public void set(Coordinate coordinate, T value) {
        grid[coordinate.x][coordinate.y] = value;
    }

    public int height() { 
        return grid.length; 
    }

    public int length() { 
        return grid[0].length; 
    }

    public Coordinate indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (grid[i][j] == null) {
                        return new Coordinate(i, j);
                    }
                }
            }
        } else {
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    if (o.equals(grid[i][j])) {
                        return new Coordinate(i, j);
                    }
                }
            }
        }
        return null;
    }

    public Coordinate indexOf(Object... o) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                for (Object obj : o) {
                    if (obj.equals(grid[i][j])) {
                        return new Coordinate(i, j);
                    }
                }
            }
        }
        return null;
    }

    public List<Coordinate> findAll(Object o) {
        List<Coordinate> result = new ArrayList<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (o.equals(grid[i][j])) {
                    result.add(new Coordinate(i, j));
                }
            }
        }
        return result;
    }

    public List<Coordinate> findAdjacent(Coordinate coordinate, Object o) {
        List<Coordinate> result = new ArrayList<>();
        Coordinate nextCoordinate = coordinate.nextCoordinate(UP);
        if (withinGrid(nextCoordinate)
                && getValueInDirection(coordinate, UP).equals(o)) {
            result.add(nextCoordinate);
        }
        nextCoordinate = coordinate.nextCoordinate(RIGHT);
        if (withinGrid(nextCoordinate)
                && getValueInDirection(coordinate, RIGHT).equals(o)) {
            result.add(nextCoordinate);
        }
        nextCoordinate = coordinate.nextCoordinate(DOWN);
        if (withinGrid(nextCoordinate)
                && getValueInDirection(coordinate, DOWN).equals(o)) {
            result.add(nextCoordinate);
        }
        nextCoordinate = coordinate.nextCoordinate(LEFT);
        if (withinGrid(nextCoordinate)
                && getValueInDirection(coordinate, LEFT).equals(o)) {
            result.add(nextCoordinate);
        }
        return result;
    }

    public List<Coordinate> findNotAdjacent(Coordinate coordinate, Object o) {
        List<Coordinate> result = new ArrayList<>();
        Coordinate nextCoordinate = coordinate.nextCoordinate(UP);
        if (!withinGrid(nextCoordinate)
                || !getValueInDirection(coordinate,UP).equals(o)) {
            result.add(nextCoordinate);
        }
        nextCoordinate = coordinate.nextCoordinate(RIGHT);
        if (!withinGrid(nextCoordinate)
                || !getValueInDirection(coordinate, RIGHT).equals(o)) {
            result.add(nextCoordinate);
        }
        nextCoordinate = coordinate.nextCoordinate(DOWN);
        if (!withinGrid(nextCoordinate)
                || !getValueInDirection(coordinate, DOWN).equals(o)) {
            result.add(nextCoordinate);
        }
        nextCoordinate = coordinate.nextCoordinate(LEFT);
        if (!withinGrid(nextCoordinate)
                || !getValueInDirection(coordinate, LEFT).equals(o)) {
            result.add(nextCoordinate);
        }
        return result;
    }

    public List<Coordinate> findRegionAt(Coordinate coordinate, Object o) {
        List<Coordinate> result = new ArrayList<>();
        result.add(coordinate);
        findRegionAt(coordinate, o, result);
        return result;
    }

    private void findRegionAt(Coordinate coordinate, Object o, List<Coordinate> visited) {
        List<Coordinate> adjacentSameValue = findAdjacent(coordinate, o);
        for (Coordinate adjacent : adjacentSameValue) {
            if (!visited.contains(adjacent)) {
                visited.add(adjacent);
                findRegionAt(adjacent, o, visited);
            }
        }
    }

    public int countCoordinateCorners(Coordinate coordinate) {
        int cornerCount = 0;
        T regionValue = grid[coordinate.x][coordinate.y];
        Coordinate nextCoordinate = coordinate.nextCoordinate(UP);
        Coordinate nextCoordinate2 = coordinate.nextCoordinate(RIGHT);
        if ((!withinGrid(nextCoordinate)
                || !getValueInDirection(coordinate, UP).equals(regionValue))
                && (!withinGrid(nextCoordinate2)
                || !getValueInDirection(coordinate, RIGHT).equals(regionValue))) {
            cornerCount++;
        }
        nextCoordinate = coordinate.nextCoordinate(DOWN);
        nextCoordinate2 = coordinate.nextCoordinate(RIGHT);
        if ((!withinGrid(nextCoordinate)
                || !getValueInDirection(coordinate, DOWN).equals(regionValue))
                && (!withinGrid(nextCoordinate2)
                || !getValueInDirection(coordinate, RIGHT).equals(regionValue))) {
            cornerCount++;
        }
        nextCoordinate = coordinate.nextCoordinate(DOWN);
        nextCoordinate2 = coordinate.nextCoordinate(LEFT);
        if ((!withinGrid(nextCoordinate)
                || !getValueInDirection(coordinate, DOWN).equals(regionValue))
                && (!withinGrid(nextCoordinate2)
                || !getValueInDirection(coordinate, LEFT).equals(regionValue))) {
            cornerCount++;
        }
        nextCoordinate = coordinate.nextCoordinate(UP);
        nextCoordinate2 = coordinate.nextCoordinate(LEFT);
        if ((!withinGrid(nextCoordinate)
                || !getValueInDirection(coordinate, UP).equals(regionValue))
                && (!withinGrid(nextCoordinate2)
                || !getValueInDirection(coordinate, LEFT).equals(regionValue))) {
            cornerCount++;
        }
        nextCoordinate = coordinate.nextCoordinate(RIGHT_UP);
        nextCoordinate2 = coordinate.nextCoordinate(UP);
        Coordinate nextCoordinate3 = coordinate.nextCoordinate(RIGHT);
        if ((withinGrid(nextCoordinate)
                && !getValueInDirection(coordinate, RIGHT_UP).equals(regionValue))
                && ((withinGrid(nextCoordinate2)
                && getValueInDirection(coordinate, UP).equals(regionValue)))
                && ((withinGrid(nextCoordinate3)
                && getValueInDirection(coordinate, RIGHT).equals(regionValue)))) {
            cornerCount++;
        }
        nextCoordinate = coordinate.nextCoordinate(RIGHT_DOWN);
        nextCoordinate2 = coordinate.nextCoordinate(DOWN);
        nextCoordinate3 = coordinate.nextCoordinate(RIGHT);
        if ((withinGrid(nextCoordinate)
                && !getValueInDirection(coordinate, RIGHT_DOWN).equals(regionValue))
                && ((withinGrid(nextCoordinate2)
                && getValueInDirection(coordinate, DOWN).equals(regionValue)))
                && ((withinGrid(nextCoordinate3)
                && getValueInDirection(coordinate, RIGHT).equals(regionValue)))) {
            cornerCount++;
        }
        nextCoordinate = coordinate.nextCoordinate(LEFT_UP);
        nextCoordinate2 = coordinate.nextCoordinate(UP);
        nextCoordinate3 = coordinate.nextCoordinate(LEFT);
        if ((withinGrid(nextCoordinate)
                && !getValueInDirection(coordinate, LEFT_UP).equals(regionValue))
                && ((withinGrid(nextCoordinate2)
                && getValueInDirection(coordinate, UP).equals(regionValue)))
                && ((withinGrid(nextCoordinate3)
                && getValueInDirection(coordinate, LEFT).equals(regionValue)))) {
            cornerCount++;
        }
        nextCoordinate = coordinate.nextCoordinate(LEFT_DOWN);
        nextCoordinate2 = coordinate.nextCoordinate(DOWN);
        nextCoordinate3 = coordinate.nextCoordinate(LEFT);
        if ((withinGrid(nextCoordinate)
                && !getValueInDirection(coordinate, LEFT_DOWN).equals(regionValue))
                && ((withinGrid(nextCoordinate2)
                && getValueInDirection(coordinate, DOWN).equals(regionValue)))
                && ((withinGrid(nextCoordinate3)
                && getValueInDirection(coordinate, LEFT).equals(regionValue)))) {
            cornerCount++;
        }
        return cornerCount;
    }

    public void print() {
        printWithDefault(" ");
    }

    public void printWithDefault(String defaultString) {
        for (T[] row : grid) {
            for (T field : row) {
                if (field != null) {
                    System.out.print(field);
                } else {
                    System.out.print(defaultString);
                }
            }
            System.out.println();
        }
    }
}
