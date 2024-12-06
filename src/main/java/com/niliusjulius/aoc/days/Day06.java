package com.niliusjulius.aoc.days;

import com.niliusjulius.aoc.util.Reader;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Day06 {

    static class Guard {
        int x;
        int y;
        Direction direction;

        public Guard(int x, int y, Direction direction) {
            this.x = x;
            this.y = y;
            this.direction = direction;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof Guard)) return false;
            return this.x == ((Guard) o).x && this.y == ((Guard) o).y && this.direction == ((Guard) o).direction;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.x, this.y, this.direction);
        }
    }

    enum Direction {
        LEFT, RIGHT, UP, DOWN
    }

    public static void main(String[] args) {
        List<String> input = Reader.readLinesAsList("day06");
        int lineCount = input.size();
        int lineLength = input.getFirst().length();
        String[][] map = new String[lineCount][lineLength];
        Guard guard = null;
        for (int i = 0; i < lineCount; i++) {
            for (int j = 0 ; j < lineLength; j++) {
                if (input.get(i).charAt(j) == '^') {
                    guard = new Guard(i, j, Direction.UP);
                } else if (input.get(i).charAt(j) == '>') {
                    guard = new Guard(i, j, Direction.RIGHT);
                } else if (input.get(i).charAt(j) == '<') {
                    guard = new Guard(i, j, Direction.LEFT);
                } else if (input.get(i).charAt(j) == 'v') {
                    guard = new Guard(i, j, Direction.DOWN);
                }
                map[i][j] = input.get(i).substring(j, j+1);
            }
        }
        if (guard == null) {
            throw new RuntimeException("Guard not found");
        }

        System.out.println(part1(copyStringArray(map), new Guard(guard.x, guard.y, guard.direction), lineCount, lineLength));
        System.out.println(part2(map, guard, lineCount, lineLength));
    }

    private static String[][] copyStringArray(String[][] original) {
        String[][] newArray = new String[original.length][];
        for(int i = 0; i < original.length; i++)
        {
            String[] row = original[i];
            int length = row.length;
            newArray[i] = new String[length];
            System.arraycopy(row, 0, newArray[i], 0, length);
        }
        return newArray;
    }

    private static int part1(String[][] map, Guard guard, int lineCount, int lineLength) {
        do {
            if (guard.direction == Direction.UP) {
                if (map[guard.x - 1][guard.y].equals("#")) {
                    guard.direction = Direction.RIGHT;
                } else {
                    map[guard.x][guard.y] = "X";
                    guard.x--;
                }
            } else if (guard.direction == Direction.RIGHT) {
                if (map[guard.x][guard.y + 1].equals("#")) {
                    guard.direction = Direction.DOWN;
                } else {
                    map[guard.x][guard.y] = "X";
                    guard.y++;
                }
            } else if (guard.direction == Direction.LEFT) {
                if (map[guard.x][guard.y - 1].equals("#")) {
                    guard.direction = Direction.UP;
                } else {
                    map[guard.x][guard.y] = "X";
                    guard.y--;
                }
            } else if (guard.direction == Direction.DOWN) {
                if (map[guard.x + 1][guard.y].equals("#")) {
                    guard.direction = Direction.LEFT;
                } else {
                    map[guard.x][guard.y] = "X";
                    guard.x++;
                }
            }
        } while (guard.x > 0 && guard.y > 0 && guard.x < lineCount - 1 && guard.y < lineLength - 1);
        map[guard.x][guard.y] = "X";

        int visitedCount = 0;
        for (int i = 0; i < lineCount; i++) {
            for (int j = 0; j < lineLength; j++) {
                if (map[i][j].equals("X")) {
                    visitedCount++;
                }
            }
        }
        return visitedCount;
    }
    private static int part2(String[][] map, Guard guard, int lineCount, int lineLength) {
        int loopBlockerCount = 0;
        Guard intialGuard = new Guard(guard.x, guard.y, guard.direction);
        for (int i = 0; i < lineCount; i++) {
            for (int j = 0; j < lineLength; j++) {
                if (i == intialGuard.x && j == intialGuard.y) {
                    continue;
                }
                guard = new Guard(intialGuard.x, intialGuard.y, intialGuard.direction);
                Set<Guard> guardsVisitedBefore = new HashSet<>();
                String originalMapString = map[i][j];
                map[i][j] = "#";
                do {
                    if (guardsVisitedBefore.contains(guard)) {
                        loopBlockerCount++;
                        break;
                    }
                    if (guard.direction == Direction.UP) {
                        if (map[guard.x - 1][guard.y].equals("#")) {
                            guard.direction = Direction.RIGHT;
                        } else {
                            map[guard.x][guard.y] = "X";
                            guardsVisitedBefore.add(new Guard(guard.x, guard.y, guard.direction));
                            guard.x--;
                        }
                    } else if (guard.direction == Direction.RIGHT) {
                        if (map[guard.x][guard.y + 1].equals("#")) {
                            guard.direction = Direction.DOWN;
                        } else {
                            map[guard.x][guard.y] = "X";
                            guardsVisitedBefore.add(new Guard(guard.x, guard.y, guard.direction));
                            guard.y++;
                        }
                    } else if (guard.direction == Direction.LEFT) {
                        if (map[guard.x][guard.y - 1].equals("#")) {
                            guard.direction = Direction.UP;
                        } else {
                            map[guard.x][guard.y] = "X";
                            guardsVisitedBefore.add(new Guard(guard.x, guard.y, guard.direction));
                            guard.y--;
                        }
                    } else if (guard.direction == Direction.DOWN) {
                        if (map[guard.x + 1][guard.y].equals("#")) {
                            guard.direction = Direction.LEFT;
                        } else {
                            map[guard.x][guard.y] = "X";
                            guardsVisitedBefore.add(new Guard(guard.x, guard.y, guard.direction));
                            guard.x++;
                        }
                    }
                } while (guard.x > 0 && guard.y > 0 && guard.x < lineCount - 1 && guard.y < lineLength - 1);
                map[i][j] = originalMapString;
            }
        }
        return loopBlockerCount;
    }

}

