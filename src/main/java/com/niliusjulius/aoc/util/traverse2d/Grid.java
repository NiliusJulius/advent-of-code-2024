package com.niliusjulius.aoc.util.traverse2d;

import java.util.ArrayList;
import java.util.List;

public class Grid<T> {
    private T[][] grid;

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

    public Coordinate indexOf(Object o) {
        T[][] a = this.grid;
        if (o == null) {
            for (int i = 0; i < a.length; i++) {
                for (int j = 0; j < a[i].length; j++) {
                    if (a[i][j] == null) {
                        return new Coordinate(i, j);
                    }
                }
            }
        } else {
            for (int i = 0; i < a.length; i++) {
                for (int j = 0; j < a[i].length; j++) {
                    if (o.equals(a[i][j])) {
                        return new Coordinate(i, j);
                    }
                }
            }
        }
        return null;
    }

    public Coordinate indexOf(Object... o) {
        T[][] a = this.grid;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                for (Object obj : o) {
                    if (obj.equals(a[i][j])) {
                        return new Coordinate(i, j);
                    }
                }
            }
        }
        return null;
    }

    public List<Coordinate> findAll(Object o) {
        T[][] a = this.grid;
        List<Coordinate> result = new ArrayList<>();
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                if (o.equals(a[i][j])) {
                    result.add(new Coordinate(i, j));
                }
            }
        }
        return result;
    }

    public List<Coordinate> findAdjacent(Coordinate coordinate, Object o) {
        T[][] a = this.grid;
        List<Coordinate> result = new ArrayList<>();
        if (withinGrid(new Coordinate(coordinate.x+Direction.UP.coordinate.x, coordinate.y+Direction.UP.coordinate.y)) && a[coordinate.x+Direction.UP.coordinate.x][coordinate.y+Direction.UP.coordinate.y] == o) {
            result.add(new Coordinate(coordinate.x+Direction.UP.coordinate.x, coordinate.y+Direction.UP.coordinate.y));
        }
        if (withinGrid(new Coordinate(coordinate.x+Direction.RIGHT.coordinate.x, coordinate.y+Direction.RIGHT.coordinate.y)) && a[coordinate.x+Direction.RIGHT.coordinate.x][coordinate.y+Direction.RIGHT.coordinate.y] == o) {
            result.add(new Coordinate(coordinate.x+Direction.RIGHT.coordinate.x, coordinate.y+Direction.RIGHT.coordinate.y));
        }
        if (withinGrid(new Coordinate(coordinate.x+Direction.DOWN.coordinate.x, coordinate.y+Direction.DOWN.coordinate.y)) && a[coordinate.x+Direction.DOWN.coordinate.x][coordinate.y+Direction.DOWN.coordinate.y] == o) {
            result.add(new Coordinate(coordinate.x+Direction.DOWN.coordinate.x, coordinate.y+Direction.DOWN.coordinate.y));
        }
        if (withinGrid(new Coordinate(coordinate.x+Direction.LEFT.coordinate.x, coordinate.y+Direction.LEFT.coordinate.y)) && a[coordinate.x+Direction.LEFT.coordinate.x][coordinate.y+Direction.LEFT.coordinate.y] == o) {
            result.add(new Coordinate(coordinate.x+Direction.LEFT.coordinate.x, coordinate.y+Direction.LEFT.coordinate.y));
        }
        return result;
    }
}
