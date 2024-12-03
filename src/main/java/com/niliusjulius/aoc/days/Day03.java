package com.niliusjulius.aoc.days;

import com.niliusjulius.aoc.util.Reader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 {

    public static void main(String[] args) {
       String input = Reader.readWholeFileAsString("day03");

        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    private static int part1(String input) {
        String regex = "mul\\([0-9]{1,3},[0-9]{1,3}\\)";
        List<String> importantParts = retrieveImportantParts(input, regex);

        int multiplication = 0;
        for (String importantPart : importantParts) {
            multiplication += multiplyNumbers(importantPart);
        }
        return multiplication;
    }

    private static int part2(String input) {
        boolean enabled = true;
        String regex = "mul\\([0-9]{1,3},[0-9]{1,3}\\)|do\\(\\)|don\\'t\\(\\)";
        List<String> importantParts = retrieveImportantParts(input, regex);
        int multiplication = 0;
        for (String importantPart : importantParts) {
            if (importantPart.equals("do()")) {
                enabled = true;
            } else if (importantPart.equals("don't()")) {
                enabled = false;
            } else if (enabled) {
                multiplication += multiplyNumbers(importantPart);
            }
        }
        return multiplication;
    }

    private static List<String> retrieveImportantParts(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        ArrayList<String> list = new ArrayList<>();
        Matcher matcher = pattern.matcher(input);
        while(matcher.find()) {
            list.add(matcher.group());
        }
        return list;
    }

    private static int multiplyNumbers(String input) {
        String innerRegex = "[0-9]{1,3}";
        List<String> innerParts = retrieveImportantParts(input, innerRegex);
        return Integer.parseInt(innerParts.get(0)) * Integer.parseInt(innerParts.get(1));
    }
}

