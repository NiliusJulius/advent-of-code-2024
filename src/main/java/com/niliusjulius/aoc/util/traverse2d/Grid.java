package com.niliusjulius.aoc.util.traverse2d;

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
            for (int i = 0; i < a.length; i++)
                for (int j = 0; j < a[i].length; j++) {
                    if (a[i][j] == null)
                        return new Coordinate(i, j);
                }
        } else {
            for (int i = 0; i < a.length; i++)
                for (int j = 0; j < a[i].length; j++) {
                    if (o.equals(a[i][j]))
                        return new Coordinate(i, j);
                }
        }
        return null;
    }

    public Coordinate indexOf(Object... o) {
        T[][] a = this.grid;
        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < a[i].length; j++) {
                for (Object obj : o) {
                    if (obj.equals(a[i][j]))
                        return new Coordinate(i, j);
                }
            }
        return null;
    }
}
