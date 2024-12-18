package com.niliusjulius.aoc.util.traverse2d;

import java.util.Objects;

public record Point<Coordinate, T>(Coordinate coordinate, T value) {

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point<?,?>)) return false;
        return this.coordinate.equals(((Point<?,?>)o).coordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.coordinate);
    }
}
