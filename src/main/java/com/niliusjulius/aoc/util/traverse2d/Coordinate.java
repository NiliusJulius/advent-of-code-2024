package com.niliusjulius.aoc.util.traverse2d;

import java.util.Objects;

public class Coordinate {
    public int x;
    public int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate nextCoordinate(Direction direction) {
        return new Coordinate(this.x + direction.coordinate.x, this.y + direction.coordinate.y);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Coordinate)) return false;
        return this.x == ((Coordinate) o).x && this.y == ((Coordinate) o).y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }
}
