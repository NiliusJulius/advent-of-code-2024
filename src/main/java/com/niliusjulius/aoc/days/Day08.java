package com.niliusjulius.aoc.days;

import com.niliusjulius.aoc.util.Reader;
import com.niliusjulius.aoc.util.traverse2d.Coordinate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day08 {

    public static void main(String[] args) {
        List<String> input = Reader.readLinesAsList("day08");
        Map<Character, List<Coordinate>> antennas = new HashMap<>();
        int height = input.size();
        int length = input.getFirst().length();
        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);
                if (!(c == '.')) {
                    if (antennas.containsKey(c)) {
                        antennas.get(c).add(new Coordinate(i, j));
                    } else {
                        List<Coordinate> coordinates = new ArrayList<>();
                        coordinates.add(new Coordinate(i, j));
                        antennas.put(c, coordinates);
                    }
                }
            }
        }

        System.out.println(part1(antennas, height, length));
        System.out.println(part2(antennas, height, length));
    }

    private static int part1(Map<Character, List<Coordinate>> antenna, int height, int length) {
        List<Coordinate> antiNodes = new ArrayList<>();

        for (List<Coordinate> coordinates : antenna.values()) {
            for (int i = 0; i < coordinates.size(); i++) {
                for (int j = 0; j < coordinates.size(); j++) {
                    Coordinate ant1 = coordinates.get(i);
                    Coordinate ant2 = coordinates.get(j);
                    if (!ant1.equals(ant2)) {
                        int distanceX = ant1.x - ant2.x;
                        int distanceY = ant1.y - ant2.y;
                        Coordinate antiNode = new Coordinate(ant1.x + distanceX, ant1.y + distanceY);
                        if (antiNode.inBounds(0, height - 1, 0, length - 1)
                                && !antiNodes.contains(antiNode)) {
                            antiNodes.add(antiNode);
                        }
                    }
                }
            }
        }

        return antiNodes.size();
    }

    private static int part2(Map<Character, List<Coordinate>> antenna, int height, int length) {
        List<Coordinate> antiNodes = new ArrayList<>();

        for (List<Coordinate> coordinates : antenna.values()) {
            for (int i = 0; i < coordinates.size(); i++) {
                if (coordinates.size() > 1) {
                    if (!antiNodes.contains(coordinates.get(i))) {
                        antiNodes.add(coordinates.get(i));
                    }
                }
                for (int j = 0; j < coordinates.size(); j++) {
                    Coordinate ant1 = coordinates.get(i);
                    Coordinate ant2 = coordinates.get(j);
                    if (!ant1.equals(ant2)) {
                        int distanceX = ant1.x - ant2.x;
                        int distanceY = ant1.y - ant2.y;
                        addNewAntiNodes(antiNodes, height, length, distanceX, distanceY, ant1, 1);
                    }
                }
            }
        }

        return antiNodes.size();
    }

    private static void addNewAntiNodes(List<Coordinate> antiNodes, int height, int length, int distanceX, int distanceY,
                                       Coordinate ant1, int index) {
        Coordinate antiNode = new Coordinate(ant1.x + distanceX * index, ant1.y + distanceY * index);
        if (antiNode.inBounds(0, height - 1, 0, length - 1)) {
            if (!antiNodes.contains(antiNode)) {
                antiNodes.add(antiNode);
            }
            addNewAntiNodes(antiNodes, height, length, distanceX, distanceY, ant1, index+1);
        }
    }
}

