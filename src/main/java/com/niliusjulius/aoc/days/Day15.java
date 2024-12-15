package com.niliusjulius.aoc.days;

import com.niliusjulius.aoc.util.Reader;
import com.niliusjulius.aoc.util.traverse2d.Coordinate;
import com.niliusjulius.aoc.util.traverse2d.Direction;
import com.niliusjulius.aoc.util.traverse2d.Grid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day15 {

    public static void main(String[] args) {
        List<String> input = Reader.readLinesAsList("day15");
        List<String> gridLines = new ArrayList<>();
        List<String> moveLines = new ArrayList<>();
        for (String line : input) {
            if (line.startsWith("#")) {
                gridLines.add(line);
            } else if (line.isEmpty()) {
                // Do nothing.
                continue;
            } else {
                moveLines.add(line);
            }
        }
        String moves = String.join(" ", moveLines);
        Grid<String> grid = new Grid<>(gridLines
                .stream()
                .map(line -> Arrays.stream(line.split("")).toArray(String[]::new))
                .toArray(String[][]::new));
        Coordinate startLocation = grid.indexOf("@");
        grid.set(startLocation, ".");

        System.out.println(part1(grid, startLocation, moves));
        System.out.println(part2(gridLines, moves));
    }

    private static int part1(Grid<String> grid, Coordinate startLocation, String moves) {
        walkGrid(grid, startLocation, moves);
        return determineScore(grid, false);
    }

    private static int part2(List<String> gridLines, String moves) {
        List<String> fatGridLines = new ArrayList<>();
        for (String line : gridLines) {
            StringBuilder fatLine = new StringBuilder();
            for (char c : line.toCharArray()) {
                if (c == '#') {
                    fatLine.append("##");
                } else if (c == '@') {
                    fatLine.append("@.");
                } else if (c == '.') {
                    fatLine.append("..");
                } else if (c == 'O') {
                    fatLine.append("[]");
                }
            }
            fatGridLines.add(fatLine.toString());
        }
        Grid<String> grid = new Grid<>(fatGridLines
                .stream()
                .map(line -> Arrays.stream(line.split("")).toArray(String[]::new))
                .toArray(String[][]::new));
        Coordinate startLocation = grid.indexOf("@");
        grid.set(startLocation, ".");
        walkGrid(grid, startLocation, moves);
        return determineScore(grid, true);
    }

    private static void walkGrid(Grid<String> grid, Coordinate startLocation, String moves) {
        Coordinate currentPos = startLocation;
        for (int i = 0; i < moves.length(); i++) {
            char move = moves.charAt(i);
            Direction direction = determineDirection(move);
            if (direction == null) {
                continue;
            }
            String nextPosValue = grid.getValueInDirection(currentPos, direction);
            Coordinate nextPos = currentPos.nextCoordinate(direction);
            if (nextPosValue.equals(".")) {
                currentPos = nextPos;
            } else if (nextPosValue.equals("O")) {
                if (moveBoxes(grid, nextPos, direction)) {
                    currentPos = nextPos;
                }
            } else if (nextPosValue.equals("[") || nextPosValue.equals("]")) {
                if (moveFatBoxes(grid, nextPos, direction)) {
                    currentPos = nextPos;
                }
            }
        }
    }

    private static boolean moveFatBoxes(Grid<String> grid, Coordinate boxPos, Direction direction) {
        Coordinate nextPos = boxPos.nextCoordinate(direction);
        String nextPosValue = grid.getValueInDirection(boxPos, direction);
        String currentPosValue = grid.get(boxPos);

        if (direction == Direction.LEFT || direction == Direction.RIGHT) {
            Coordinate nextNextPos = nextPos.nextCoordinate(direction);
            String nextNextPosValue = grid.getValueInDirection(nextPos, direction);
            switch (nextNextPosValue) {
                case "." -> {
                    grid.set(nextNextPos, nextPosValue);
                    grid.set(nextPos, currentPosValue);
                    grid.set(boxPos, ".");
                    return true;
                }
                case "[", "]" -> {
                    if (moveFatBoxes(grid, nextPos, direction)) {
                        grid.set(boxPos, ".");
                        grid.set(nextPos, currentPosValue);
                        return true;
                    }
                }
                case "#" -> {
                    return false;
                }
            }
        } else {
            Coordinate otherBoxHalfPos;
            if (currentPosValue.equals("[")) {
                otherBoxHalfPos = boxPos.nextCoordinate(Direction.RIGHT);
            } else {
                otherBoxHalfPos = boxPos.nextCoordinate(Direction.LEFT);
            }
            String otherBoxHalfNextPosValue = grid.getValueInDirection(otherBoxHalfPos, direction);
            String otherBoxHalfCurrentPosValue = grid.get(otherBoxHalfPos);
            Coordinate otherBoxHalfNextPos = otherBoxHalfPos.nextCoordinate(direction);
            if (currentPosValue.equals(".")) {
                return true;
            }
            if (nextPosValue.equals(".") && otherBoxHalfNextPosValue.equals(".")) {
                grid.set(boxPos, ".");
                grid.set(nextPos, currentPosValue);
                grid.set(otherBoxHalfPos, ".");
                grid.set(otherBoxHalfNextPos, otherBoxHalfCurrentPosValue);
                return true;
            } else if (nextPosValue.equals("#") || otherBoxHalfNextPosValue.equals("#")) {
                return false;
            } else {
                if (currentPosValue.equals(nextPosValue)) {
                    if (moveFatBoxes(grid, nextPos, direction)) {
                        grid.set(boxPos, ".");
                        grid.set(nextPos, currentPosValue);
                        grid.set(otherBoxHalfPos, ".");
                        grid.set(otherBoxHalfNextPos, otherBoxHalfCurrentPosValue);
                        return true;
                    }
                } else {
                    if (canMoveBoxUpOrDown(grid, nextPos, direction) && canMoveBoxUpOrDown(grid, otherBoxHalfNextPos, direction)) {
                        moveFatBoxes(grid, nextPos, direction);
                        moveFatBoxes(grid, otherBoxHalfNextPos, direction);
                        grid.set(boxPos, ".");
                        grid.set(nextPos, currentPosValue);
                        grid.set(otherBoxHalfPos, ".");
                        grid.set(otherBoxHalfNextPos, otherBoxHalfCurrentPosValue);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean canMoveBoxUpOrDown(Grid<String> grid, Coordinate boxPos, Direction direction) {
        Coordinate nextPos = boxPos.nextCoordinate(direction);
        String nextPosValue = grid.getValueInDirection(boxPos, direction);
        String currentPosValue = grid.get(boxPos);
        Coordinate otherBoxHalfPos;
        if (currentPosValue.equals(".")) {
            return true;
        }
        if (currentPosValue.equals("[")) {
            otherBoxHalfPos = boxPos.nextCoordinate(Direction.RIGHT);
        } else {
            otherBoxHalfPos = boxPos.nextCoordinate(Direction.LEFT);
        }

        String otherBoxHalfNextPosValue = grid.getValueInDirection(otherBoxHalfPos, direction);
        Coordinate otherBoxHalfNextPos = otherBoxHalfPos.nextCoordinate(direction);
        if (nextPosValue.equals(".") && otherBoxHalfNextPosValue.equals(".")) {
            return true;
        } else if (nextPosValue.equals("#") || otherBoxHalfNextPosValue.equals("#")) {
            return false;
        } else {
            if (currentPosValue.equals(nextPosValue)) {
                return canMoveBoxUpOrDown(grid, nextPos, direction);
            } else {
                boolean canMove = true;
//                if (currentPosValue.equals("[") || currentPosValue.equals("]")) {
                    canMove = canMoveBoxUpOrDown(grid, nextPos, direction) && canMoveBoxUpOrDown(grid, otherBoxHalfNextPos, direction);
//                }
//                if (otherBoxHalfCurrentPosValue.equals("[") || otherBoxHalfCurrentPosValue.equals("]")) {
//                    canMove = canMoveBoxUpOrDown(grid, otherBoxHalfPos, direction);
//                }
                return canMove;
            }
        }
    }

    private static int determineScore(Grid<String> grid, boolean pt2) {
        int score = 0;
        List<Coordinate> boxes;
        if (!pt2) {
            boxes = grid.findAll("O");
        } else {
            boxes = grid.findAll("[");
        }
        for (Coordinate box : boxes) {
            score += 100 * box.x + box.y;
        }
        return score;
    }

    private static boolean moveBoxes(Grid<String> grid, Coordinate boxPos, Direction direction) {
        Coordinate nextPos = boxPos.nextCoordinate(direction);
        String nextPosValue = grid.getValueInDirection(boxPos, direction);
        switch (nextPosValue) {
            case "." -> {
                grid.set(boxPos, ".");
                grid.set(nextPos, "O");
                return true;
            }
            case "O" -> {
                if (moveBoxes(grid, nextPos, direction)) {
                    grid.set(boxPos, ".");
                    grid.set(nextPos, "O");
                    return true;
                }
            }
            case "#" -> {
                return false;
            }
        }
        return false;
    }

    private static Direction determineDirection(char move) {
        return switch (move) {
            case '^' -> Direction.UP;
            case '>' -> Direction.RIGHT;
            case 'v' -> Direction.DOWN;
            case '<' -> Direction.LEFT;
            default -> null;
        };
    }
}

