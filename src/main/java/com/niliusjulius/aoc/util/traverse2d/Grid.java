package com.niliusjulius.aoc.util.traverse2d;

import java.util.ArrayList;
import java.util.List;

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

    public void set(Coordinate coordinate, T value) {
        grid[coordinate.x][coordinate.y] = value;
    }

    public int height() { return grid.length; }

    public int length() { return grid[0].length; }

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
        if (withinGrid(new Coordinate(coordinate.x+Direction.UP.coordinate.x, coordinate.y+Direction.UP.coordinate.y))
                && grid[coordinate.x+Direction.UP.coordinate.x][coordinate.y+Direction.UP.coordinate.y].equals(o)) {
            result.add(new Coordinate(coordinate.x+Direction.UP.coordinate.x, coordinate.y+Direction.UP.coordinate.y));
        }
        if (withinGrid(new Coordinate(coordinate.x+Direction.RIGHT.coordinate.x, coordinate.y+Direction.RIGHT.coordinate.y))
                && grid[coordinate.x+Direction.RIGHT.coordinate.x][coordinate.y+Direction.RIGHT.coordinate.y].equals(o)) {
            result.add(new Coordinate(coordinate.x+Direction.RIGHT.coordinate.x, coordinate.y+Direction.RIGHT.coordinate.y));
        }
        if (withinGrid(new Coordinate(coordinate.x+Direction.DOWN.coordinate.x, coordinate.y+Direction.DOWN.coordinate.y))
                && grid[coordinate.x+Direction.DOWN.coordinate.x][coordinate.y+Direction.DOWN.coordinate.y].equals(o)) {
            result.add(new Coordinate(coordinate.x+Direction.DOWN.coordinate.x, coordinate.y+Direction.DOWN.coordinate.y));
        }
        if (withinGrid(new Coordinate(coordinate.x+Direction.LEFT.coordinate.x, coordinate.y+Direction.LEFT.coordinate.y))
                && grid[coordinate.x+Direction.LEFT.coordinate.x][coordinate.y+Direction.LEFT.coordinate.y].equals(o)) {
            result.add(new Coordinate(coordinate.x+Direction.LEFT.coordinate.x, coordinate.y+Direction.LEFT.coordinate.y));
        }
        return result;
    }

    public List<Coordinate> findNotAdjacent(Coordinate coordinate, Object o) {
        List<Coordinate> result = new ArrayList<>();
        if (!withinGrid(new Coordinate(coordinate.x+Direction.UP.coordinate.x, coordinate.y+Direction.UP.coordinate.y))
                || !grid[coordinate.x+Direction.UP.coordinate.x][coordinate.y+Direction.UP.coordinate.y].equals(o)) {
            result.add(new Coordinate(coordinate.x+Direction.UP.coordinate.x, coordinate.y+Direction.UP.coordinate.y));
        }
        if (!withinGrid(new Coordinate(coordinate.x+Direction.RIGHT.coordinate.x, coordinate.y+Direction.RIGHT.coordinate.y))
                || !grid[coordinate.x+Direction.RIGHT.coordinate.x][coordinate.y+Direction.RIGHT.coordinate.y].equals(o)) {
            result.add(new Coordinate(coordinate.x+Direction.RIGHT.coordinate.x, coordinate.y+Direction.RIGHT.coordinate.y));
        }
        if (!withinGrid(new Coordinate(coordinate.x+Direction.DOWN.coordinate.x, coordinate.y+Direction.DOWN.coordinate.y))
                || !grid[coordinate.x+Direction.DOWN.coordinate.x][coordinate.y+Direction.DOWN.coordinate.y].equals(o)) {
            result.add(new Coordinate(coordinate.x+Direction.DOWN.coordinate.x, coordinate.y+Direction.DOWN.coordinate.y));
        }
        if (!withinGrid(new Coordinate(coordinate.x+Direction.LEFT.coordinate.x, coordinate.y+Direction.LEFT.coordinate.y))
                || !grid[coordinate.x+Direction.LEFT.coordinate.x][coordinate.y+Direction.LEFT.coordinate.y].equals(o)) {
            result.add(new Coordinate(coordinate.x+Direction.LEFT.coordinate.x, coordinate.y+Direction.LEFT.coordinate.y));
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
        if ((!withinGrid(new Coordinate(coordinate.x+Direction.UP.coordinate.x, coordinate.y+Direction.UP.coordinate.y))
                || !grid[coordinate.x+Direction.UP.coordinate.x][coordinate.y+Direction.UP.coordinate.y].equals(regionValue))
                && (!withinGrid(new Coordinate(coordinate.x+Direction.RIGHT.coordinate.x, coordinate.y+Direction.RIGHT.coordinate.y))
                || !grid[coordinate.x+Direction.RIGHT.coordinate.x][coordinate.y+Direction.RIGHT.coordinate.y].equals(regionValue))) {
            cornerCount++;
        }
        if ((!withinGrid(new Coordinate(coordinate.x+Direction.DOWN.coordinate.x, coordinate.y+Direction.DOWN.coordinate.y))
                || !grid[coordinate.x+Direction.DOWN.coordinate.x][coordinate.y+Direction.DOWN.coordinate.y].equals(regionValue))
                && (!withinGrid(new Coordinate(coordinate.x+Direction.RIGHT.coordinate.x, coordinate.y+Direction.RIGHT.coordinate.y))
                || !grid[coordinate.x+Direction.RIGHT.coordinate.x][coordinate.y+Direction.RIGHT.coordinate.y].equals(regionValue))) {
            cornerCount++;
        }
        if ((!withinGrid(new Coordinate(coordinate.x+Direction.DOWN.coordinate.x, coordinate.y+Direction.DOWN.coordinate.y))
                || !grid[coordinate.x+Direction.DOWN.coordinate.x][coordinate.y+Direction.DOWN.coordinate.y].equals(regionValue))
                && (!withinGrid(new Coordinate(coordinate.x+Direction.LEFT.coordinate.x, coordinate.y+Direction.LEFT.coordinate.y))
                || !grid[coordinate.x+Direction.LEFT.coordinate.x][coordinate.y+Direction.LEFT.coordinate.y].equals(regionValue))) {
            cornerCount++;
        }
        if ((!withinGrid(new Coordinate(coordinate.x+Direction.UP.coordinate.x, coordinate.y+Direction.UP.coordinate.y))
                || !grid[coordinate.x+Direction.UP.coordinate.x][coordinate.y+Direction.UP.coordinate.y].equals(regionValue))
                && (!withinGrid(new Coordinate(coordinate.x+Direction.LEFT.coordinate.x, coordinate.y+Direction.LEFT.coordinate.y))
                || !grid[coordinate.x+Direction.LEFT.coordinate.x][coordinate.y+Direction.LEFT.coordinate.y].equals(regionValue))) {
            cornerCount++;
        }
        if ((withinGrid(new Coordinate(coordinate.x+Direction.RIGHT_UP.coordinate.x, coordinate.y+Direction.RIGHT_UP.coordinate.y))
                && !grid[coordinate.x+Direction.RIGHT_UP.coordinate.x][coordinate.y+Direction.RIGHT_UP.coordinate.y].equals(regionValue))
                && ((withinGrid(new Coordinate(coordinate.x+Direction.UP.coordinate.x, coordinate.y+Direction.UP.coordinate.y))
                && grid[coordinate.x+Direction.UP.coordinate.x][coordinate.y+Direction.UP.coordinate.y].equals(regionValue)))
                && ((withinGrid(new Coordinate(coordinate.x+Direction.RIGHT.coordinate.x, coordinate.y+Direction.RIGHT.coordinate.y))
                && grid[coordinate.x+Direction.RIGHT.coordinate.x][coordinate.y+Direction.RIGHT.coordinate.y].equals(regionValue)))) {
            cornerCount++;
        }
        if ((withinGrid(new Coordinate(coordinate.x+Direction.RIGHT_DOWN.coordinate.x, coordinate.y+Direction.RIGHT_DOWN.coordinate.y))
                && !grid[coordinate.x+Direction.RIGHT_DOWN.coordinate.x][coordinate.y+Direction.RIGHT_DOWN.coordinate.y].equals(regionValue))
                && ((withinGrid(new Coordinate(coordinate.x+Direction.DOWN.coordinate.x, coordinate.y+Direction.DOWN.coordinate.y))
                && grid[coordinate.x+Direction.DOWN.coordinate.x][coordinate.y+Direction.DOWN.coordinate.y].equals(regionValue)))
                && ((withinGrid(new Coordinate(coordinate.x+Direction.RIGHT.coordinate.x, coordinate.y+Direction.RIGHT.coordinate.y))
                && grid[coordinate.x+Direction.RIGHT.coordinate.x][coordinate.y+Direction.RIGHT.coordinate.y].equals(regionValue)))) {
            cornerCount++;
        }
        if ((withinGrid(new Coordinate(coordinate.x+Direction.LEFT_UP.coordinate.x, coordinate.y+Direction.LEFT_UP.coordinate.y))
                && !grid[coordinate.x+Direction.LEFT_UP.coordinate.x][coordinate.y+Direction.LEFT_UP.coordinate.y].equals(regionValue))
                && ((withinGrid(new Coordinate(coordinate.x+Direction.UP.coordinate.x, coordinate.y+Direction.UP.coordinate.y))
                && grid[coordinate.x+Direction.UP.coordinate.x][coordinate.y+Direction.UP.coordinate.y].equals(regionValue)))
                && ((withinGrid(new Coordinate(coordinate.x+Direction.LEFT.coordinate.x, coordinate.y+Direction.LEFT.coordinate.y))
                && grid[coordinate.x+Direction.LEFT.coordinate.x][coordinate.y+Direction.LEFT.coordinate.y].equals(regionValue)))) {
            cornerCount++;
        }
        if ((withinGrid(new Coordinate(coordinate.x+Direction.LEFT_DOWN.coordinate.x, coordinate.y+Direction.LEFT_DOWN.coordinate.y))
                && !grid[coordinate.x+Direction.LEFT_DOWN.coordinate.x][coordinate.y+Direction.LEFT_DOWN.coordinate.y].equals(regionValue))
                && ((withinGrid(new Coordinate(coordinate.x+Direction.DOWN.coordinate.x, coordinate.y+Direction.DOWN.coordinate.y))
                && grid[coordinate.x+Direction.DOWN.coordinate.x][coordinate.y+Direction.DOWN.coordinate.y].equals(regionValue)))
                && ((withinGrid(new Coordinate(coordinate.x+Direction.LEFT.coordinate.x, coordinate.y+Direction.LEFT.coordinate.y))
                && grid[coordinate.x+Direction.LEFT.coordinate.x][coordinate.y+Direction.LEFT.coordinate.y].equals(regionValue)))) {
            cornerCount++;
        }
        return cornerCount;
    }

    public void print() {
        for (T[] row : grid) {
            for (T field : row) {
                if (field != null) {
                    System.out.print(field);
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
