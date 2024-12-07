package com.niliusjulius.aoc.util.traverse2d;

public enum Direction {
    UP(new Coordinate(-1,0)),
    RIGHT(new Coordinate(0,1)),
    DOWN(new Coordinate(1,0)),
    LEFT(new Coordinate(0,-1)),
    RIGHT_UP(new Coordinate(-1,1)),
    RIGHT_DOWN(new Coordinate(1,1)),
    LEFT_UP(new Coordinate(-1,-1)),
    LEFT_DOWN(new Coordinate(1,-1));

    public final Coordinate coordinate;

    Direction(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Direction turnRight(Direction direction, boolean includeDiagonal) {
        return switch (direction) {
            case UP -> includeDiagonal ? RIGHT_UP : RIGHT;
            case RIGHT -> includeDiagonal ? RIGHT_DOWN : DOWN;
            case DOWN -> includeDiagonal ? LEFT_DOWN : LEFT;
            case LEFT -> includeDiagonal ? LEFT_UP : UP;
            case RIGHT_UP -> RIGHT;
            case RIGHT_DOWN -> DOWN;
            case LEFT_DOWN -> LEFT;
            case LEFT_UP -> UP;
        };
    }
}
