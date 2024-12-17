package com.niliusjulius.aoc.days;

import com.niliusjulius.aoc.util.Reader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day17 {

    public static void main(String[] args) {
        List<String> input = Reader.readLinesAsList("day17");
        long a = Long.parseLong(input.get(0).split(" ")[2]);
        long b = Long.parseLong(input.get(1).split(" ")[2]);
        long c = Long.parseLong(input.get(2).split(" ")[2]);
        String programLine = input.get(4).split(" ")[1];
        List<Integer> program = Arrays.stream(programLine.split(",")).map(Integer::parseInt).toList();

        System.out.println(part1(program, a, b, c));
        System.out.println(part2(program));
    }

    private static String part1(List<Integer> program, long a, long b, long c) {
        List<Integer> output = determineOutput(program, a, b, c);
        return output.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    private static String part2(List<Integer> program) {
        long a = 0;
        long b = 0;
        long c = 0;
        List<Long> workingValues = new ArrayList<>();
        recurse(program, a, b, c, workingValues, program.size() - 1);
        return workingValues.stream().min(Long::compareTo).orElse(0L).toString();
    }

    private static void recurse(List<Integer> program, long a, long b, long c, List<Long> workingValues, int programIndex) {
        for (int j = 0; j < 8; j++) {
            List<Integer> output = determineOutput(program, a+j, b, c);
            if (output.getFirst().equals(program.get(programIndex))) {
                if (output.size() == program.size()) {
                    workingValues.add(a+j);
                } else {
                    recurse(program, (a+j) * 8, b, c, workingValues, programIndex - 1);
                }
            }
        }
    }

    private static List<Integer> determineOutput(List<Integer> program, long a, long b, long c) {
        int programLength = program.size();
        int programIndex = 0;
        List<Integer> output = new ArrayList<>();
        while (programIndex < programLength) {
            int opCode = program.get(programIndex);
            if (opCode == 0) {
                long combo = getComboValue(program.get(programIndex + 1), a, b, c);
                a = (long) (a / Math.pow(2, combo));
                programIndex += 2;
            } else if (opCode == 1) {
                b = b ^ program.get(programIndex + 1);
                programIndex += 2;
            } else if (opCode == 2) {
                long combo = getComboValue(program.get(programIndex + 1), a, b, c);
                b = combo % 8;
                programIndex += 2;
            } else if (opCode == 3) {
                if (a != 0) {
                    programIndex = program.get(programIndex + 1);
                } else {
                    programIndex += 2;
                }
            } else if (opCode == 4) {
                b = b ^ c;
                programIndex += 2;
            } else if (opCode == 5) {
                long combo = getComboValue(program.get(programIndex + 1), a, b, c);
                output.add((int) (combo % 8));
                programIndex += 2;
            } else if (opCode == 6) {
                long combo = getComboValue(program.get(programIndex + 1), a, b, c);
                b = (long) (a / Math.pow(2, combo));
                programIndex += 2;
            } else if (opCode == 7) {
                long combo = getComboValue(program.get(programIndex + 1), a, b, c);
                c = (long) (a / Math.pow(2, combo));
                programIndex += 2;
            }
        }
        return output;
    }

    private static long getComboValue(int value, long a, long b, long c) {
        return switch (value) {
            case 0,1,2,3 -> value;
            case 4 -> a;
            case 5 -> b;
            case 6 -> c;
            default -> 7;
        };
    }
}

