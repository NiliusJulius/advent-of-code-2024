package com.niliusjulius.aoc.days;

import com.niliusjulius.aoc.util.Reader;
import com.niliusjulius.aoc.util.traverse2d.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day13 {

    public static void main(String[] args) {
        List<String> input = Reader.readLinesAsList("day13");
        List<Coordinate> buttonAs = new ArrayList<>();
        List<Coordinate> buttonBs = new ArrayList<>();
        List<Coordinate> prizes = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            String regex = "[0-9]+";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(input.get(i));
            ArrayList<Integer> list = new ArrayList<>();
            while(matcher.find()) {
                list.add(Integer.parseInt(matcher.group()));
            }
            int lineMod = i % 4;
            if (lineMod == 0) {
                buttonAs.add(new Coordinate(list.get(0), list.get(1)));
            } else if (lineMod == 1) {
                buttonBs.add(new Coordinate(list.get(0), list.get(1)));
            } else if (lineMod == 2) {
                prizes.add(new Coordinate(list.get(0), list.get(1)));
            }
        }

        System.out.println(part1(buttonAs, buttonBs, prizes, false));
        System.out.println(part1(buttonAs, buttonBs, prizes, true));
    }

    private static long part1(List<Coordinate> buttonAs, List<Coordinate> buttonBs, List<Coordinate> prizes, boolean part2) {
        long tokenCount = 0;
        for (int i = 0; i < buttonAs.size(); i++) {
            long prizeX = prizes.get(i).x;
            long prizeY = prizes.get(i).y;
            if (part2) {
                prizeX += 10000000000000L;
                prizeY += 10000000000000L;
            }

            long[] solved = solveEquation(buttonAs.get(i).x, buttonAs.get(i).y, buttonBs.get(i).x, buttonBs.get(i).y,
                    prizeX, prizeY);
            if (solved != null) {
                tokenCount += solved[0] * 3;
                tokenCount += solved[1];
            }
        }

        return tokenCount;
    }

    public static long[] solveEquation(long xa, long xb, long ya, long yb, long pa, long pb) {
        long solve = xa * yb - xb * ya;

        long x = pa * yb - ya * pb;
        long y = xa * pb - pa * xb;
        if (x % solve == 0 && y % solve == 0) {
            return new long[]{x / solve, y / solve};
        }
        return null;
    }
}

