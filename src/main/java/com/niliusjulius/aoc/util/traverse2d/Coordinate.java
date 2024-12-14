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

    public boolean inBounds(int xMin, int xMax, int yMin, int yMax) {
        return x >= xMin && x <= xMax && y >= yMin && y <= yMax;
    }

    public Coordinate loopInBoundingBox(int minX, int minY, int maxX, int maxY) {
        if (x < minX ) {
            x = maxX - Math.abs(x - minX + 1);
        } else if (x > maxX ) {
            x = minX + (Math.abs(x - maxX) - 1);
        }
        if (y < minY ) {
            y = maxY - Math.abs(y - minY + 1);
        } else if (y > maxY ) {
            y = minY + (Math.abs(y - maxY) - 1);
        }
        return new Coordinate(x, y);
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
