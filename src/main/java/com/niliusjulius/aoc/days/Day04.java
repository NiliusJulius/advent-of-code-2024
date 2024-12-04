package com.niliusjulius.aoc.days;

import com.niliusjulius.aoc.util.Reader;

import java.util.List;

public class Day04 {

    enum Direction {
        LEFT, RIGHT, UP, DOWN,
        LEFT_UP, RIGHT_UP, LEFT_DOWN, RIGHT_DOWN
    }

    public static void main(String[] args) {
        List<String> input = Reader.readLinesAsList("day04");
        int lineCount = input.size();
        int lineLength = input.getFirst().length();
        System.out.println(part1(input, lineCount, lineLength));
        System.out.println(part2(input, lineCount, lineLength));
    }

    private static int part1(List<String> input, int lineCount, int lineLength) {
        int wordCount = 0;
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                if (input.get(i).charAt(j) == 'X') {
                    for (Direction direction : Direction.values()) {
                        if (findWord(input, "XMAS", 1, i, j, direction, lineCount, lineLength)) {
                            wordCount++;
                        }
                    }
                }
            }
        }
        return wordCount;
    }

    private static boolean findWord(List<String> input, String word, int index, int i, int j, Direction direction, int lineCount, int lineLength) {
        if (index >= word.length()) {
            return true;
        }

        char nextLetter = word.charAt(index);
        if (direction.equals(Direction.UP) && i > 0 && input.get(i - 1).charAt(j) == nextLetter) {
            return findWord(input, word, index + 1, i - 1, j, Direction.UP, lineCount, lineLength);
        } else if (direction.equals(Direction.LEFT) && j > 0 && input.get(i).charAt(j - 1) == nextLetter) {
            return findWord(input, word, index + 1, i, j - 1, Direction.LEFT, lineCount, lineLength);
        } else if (direction.equals(Direction.DOWN) && i < lineCount - 1 && input.get(i + 1).charAt(j) == nextLetter) {
            return findWord(input, word, index + 1, i + 1, j, Direction.DOWN, lineCount, lineLength);
        } else if (direction.equals(Direction.RIGHT) && j < lineLength - 1 && input.get(i).charAt(j + 1) == nextLetter) {
            return findWord(input, word, index + 1, i, j + 1, Direction.RIGHT, lineCount, lineLength);
        } else if (direction.equals(Direction.LEFT_UP) && i > 0 && j > 0 && input.get(i - 1).charAt(j - 1) == nextLetter) {
            return findWord(input, word, index + 1, i - 1, j - 1, Direction.LEFT_UP, lineCount, lineLength);
        } else if (direction.equals(Direction.LEFT_DOWN) && i < lineCount - 1 && j > 0 && input.get(i + 1).charAt(j - 1) == nextLetter) {
            return findWord(input, word, index + 1, i + 1, j - 1, Direction.LEFT_DOWN, lineCount, lineLength);
        } else if (direction.equals(Direction.RIGHT_DOWN) && i < lineCount - 1 && j < lineLength - 1 && input.get(i + 1).charAt(j + 1) == nextLetter) {
            return findWord(input, word, index + 1, i + 1, j + 1, Direction.RIGHT_DOWN, lineCount, lineLength);
        } else if (direction.equals(Direction.RIGHT_UP) && i > 0 && j < lineLength - 1 && input.get(i - 1).charAt(j + 1) == nextLetter) {
            return findWord(input, word, index + 1, i - 1, j + 1, Direction.RIGHT_UP, lineCount, lineLength);
        }
        return false;
    }

    private static int part2(List<String> input, int lineCount, int lineLength) {
        int wordCount = 0;
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                if (input.get(i).charAt(j) == 'A') {
                    if (findMas(input, i, j, lineCount, lineLength)) {
                        wordCount++;
                    }
                }
            }
        }
        return wordCount;
    }

    private static boolean findMas(List<String> input,int i, int j, int lineCount, int lineLength) {
        int masCount = 0;
        if (i == 0 || i == lineCount - 1 || j == 0 || j == lineLength - 1) {
            return false;
        }
        if ((input.get(i - 1).charAt(j - 1) == 'M' && input.get(i + 1).charAt(j + 1) == 'S')
                || (input.get(i - 1).charAt(j - 1) == 'S' && input.get(i + 1).charAt(j + 1) == 'M')) {
            masCount++;
        }

        if ((input.get(i - 1).charAt(j + 1) == 'M' && input.get(i + 1).charAt(j - 1) == 'S')
                || (input.get(i - 1).charAt(j + 1) == 'S' && input.get(i + 1).charAt(j - 1) == 'M')) {
            masCount++;
        }

        return masCount == 2;
    }
}

