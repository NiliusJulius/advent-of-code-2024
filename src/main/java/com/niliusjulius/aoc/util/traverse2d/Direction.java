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

    public Direction turnLeft(Direction direction, boolean includeDiagonal) {
        return switch (direction) {
            case UP -> includeDiagonal ? LEFT_UP : LEFT;
            case RIGHT -> includeDiagonal ? RIGHT_UP : UP;
            case DOWN -> includeDiagonal ? RIGHT_DOWN : RIGHT;
            case LEFT -> includeDiagonal ? LEFT_DOWN : DOWN;
            case RIGHT_UP -> UP;
            case RIGHT_DOWN -> RIGHT;
            case LEFT_DOWN -> DOWN;
            case LEFT_UP -> LEFT;
        };
    }

    public Direction opposite() {
        return switch (this) {
            case UP -> DOWN;
            case RIGHT -> LEFT;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT_UP -> LEFT_DOWN;
            case RIGHT_DOWN -> LEFT_UP;
            case LEFT_UP -> RIGHT_DOWN;
            case LEFT_DOWN -> RIGHT_UP;
        };
    }
}
