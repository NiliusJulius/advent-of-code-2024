package com.niliusjulius.aoc.days;

import com.niliusjulius.aoc.util.Reader;

import java.util.*;

public class Day02 {

    public static void main(String[] args) {
        List<String> input = Reader.readLinesAsList("day02");

        System.out.println(part1(input));
        System.out.println(part2(input));
    }

    private static int part1(List<String> input) {
        int safe = 0;
        for (String report : input) {
            String[] points = report.split(" ");
            if (isSafe(points)) {
                safe++;
            }
        }
        return safe;
    }

    private static boolean isSafe(String[] points) {
        boolean increasing = Integer.parseInt(points[1]) >= Integer.parseInt(points[0]);
        for (int i = 1; i < points.length; i++) {
            if (Integer.parseInt(points[i]) > Integer.parseInt(points[i-1]) && increasing) {
                if (Math.abs(Integer.parseInt(points[i]) - Integer.parseInt(points[i-1])) > 3) {
                    return false;
                }
            } else if (Integer.parseInt(points[i]) < Integer.parseInt(points[i-1]) && !increasing) {
                if (Math.abs(Integer.parseInt(points[i]) - Integer.parseInt(points[i-1])) > 3) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    private static int part2(List<String> input) {
        int safe = 0;
        int index = 0;
        for (String report : input) {
            String[] points = report.split(" ");
            List<String> pointsList = new LinkedList<>(Arrays.asList(points));
            List<String> pointsList2 = new LinkedList<>(Arrays.asList(points));
            pointsList2.removeFirst();
            if (isSafe2(pointsList, false) || isSafe2(pointsList2, true)) {
                safe++;
//                System.out.println(index);
            }
            index++;
        }
        return safe;
    }

    private static boolean isSafe2(List<String> points, boolean removed) {
        boolean increasing = Integer.parseInt(points.get(1)) >= Integer.parseInt(points.get(0));
        for (int i = 1; i < points.size(); i++) {
            if (Integer.parseInt(points.get(i)) > Integer.parseInt(points.get(i-1)) && increasing) {
                if (Math.abs(Integer.parseInt(points.get(i)) - Integer.parseInt(points.get(i-1))) > 3) {
                    if (!removed) {
                        List<String> copy1 = new LinkedList<>(points);
                        List<String> copy2 = new LinkedList<>(points);
                        copy1.remove(i);
                        copy2.remove(i-1);
                        return isSafe2(copy1, true) || isSafe2(copy2, true);
                    } else {
                        return false;
                    }
                }
            } else if (Integer.parseInt(points.get(i)) < Integer.parseInt(points.get(i-1)) && !increasing) {
                if (Math.abs(Integer.parseInt(points.get(i)) - Integer.parseInt(points.get(i-1))) > 3) {
                    if (!removed) {
                        List<String> copy1 = new LinkedList<>(points);
                        List<String> copy2 = new LinkedList<>(points);
                        copy1.remove(i);
                        copy2.remove(i-1);
                        return isSafe2(copy1, true) || isSafe2(copy2, true);
                    } else {
                        return false;
                    }
                }
            } else {
                if (!removed) {
                    List<String> copy1 = new LinkedList<>(points);
                    List<String> copy2 = new LinkedList<>(points);
                    copy1.remove(i);
                    copy2.remove(i-1);
                    return isSafe2(copy1, true) || isSafe2(copy2, true);
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}

