package com.niliusjulius.aoc.days;

import com.niliusjulius.aoc.util.Reader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day17 {

    public static void main(String[] args) {
        List<String> input = Reader.readLinesAsList("day17");
        int a = Integer.parseInt(input.get(0).split(" ")[2]);
        int b = Integer.parseInt(input.get(1).split(" ")[2]);
        int c = Integer.parseInt(input.get(2).split(" ")[2]);
        String programLine = input.get(4).split(" ")[1];
        List<Integer> program = Arrays.stream(programLine.split(",")).map(Integer::parseInt).toList();

        System.out.println(part1(program, a, b, c));
    }

    private static String part1(List<Integer> program, int a, int b, int c) {
        int programLength = program.size();
        int programIndex = 0;
        List<Integer> output = new ArrayList<>();
        while (programIndex < programLength) {
            int opCode = program.get(programIndex);
            if (opCode == 0) {
                int combo = getComboValue(program.get(programIndex + 1), a, b, c);
                a = (int) (a / Math.pow(2, combo));
                programIndex += 2;
            } else if (opCode == 1) {
                b = b ^ program.get(programIndex + 1);
                programIndex += 2;
            } else if (opCode == 2) {
                int combo = getComboValue(program.get(programIndex + 1), a, b, c);
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
                int combo = getComboValue(program.get(programIndex + 1), a, b, c);
                output.add(combo % 8);
                programIndex += 2;
            } else if (opCode == 6) {
                int combo = getComboValue(program.get(programIndex + 1), a, b, c);
                b = (int) (a / Math.pow(2, combo));
                programIndex += 2;
            } else if (opCode == 7) {
                int combo = getComboValue(program.get(programIndex + 1), a, b, c);
                c = (int) (a / Math.pow(2, combo));
                programIndex += 2;
            }
        }

        return output.stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    private static int getComboValue(int value, int a, int b, int c) {
        return switch (value) {
            case 0,1,2,3 -> value;
            case 4 -> a;
            case 5 -> b;
            case 6 -> c;
            default -> 7;
        };
    }
}

