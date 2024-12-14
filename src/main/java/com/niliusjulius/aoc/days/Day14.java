package com.niliusjulius.aoc.days;

import com.niliusjulius.aoc.util.Reader;
import com.niliusjulius.aoc.util.traverse2d.Coordinate;
import com.niliusjulius.aoc.util.traverse2d.Grid;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14 {

    private static final int HEIGHT = 103;
    private static final int WIDTH = 101;

    public static void main(String[] args) {
        List<String> input = Reader.readLinesAsList("day14");
        Location[][] locations = new Location[WIDTH][HEIGHT];
        Grid<Location> grid = new Grid<>(locations);
        List<Robot> robots = new ArrayList<>();
        for (String s : input) {
            String regex = "-*[0-9]+";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(s);
            ArrayList<Integer> list = new ArrayList<>();
            while (matcher.find()) {
                list.add(Integer.parseInt(matcher.group()));
            }
            Coordinate location = new Coordinate(list.get(0), list.get(1));
            Coordinate velocity = new Coordinate(list.get(2), list.get(3));
            if (grid.get(location) != null) {
                grid.get(location).robots.add(new Robot(location, velocity));
            } else {
                Location loc = new Location();
                loc.robots.add(new Robot(location, velocity));
                grid.set(location, loc);
            }
            robots.add(new Robot(location, velocity));
        }

        System.out.println(part1(robots.stream().map(Robot::clone).toList()));
        System.out.println(part2(robots.stream().map(Robot::clone).toList()));
//        System.out.println(part2Pretty(grid, robots.stream().map(Robot::clone).toList()));
    }

    private static int part1(List<Robot> robots) {
        for (int i = 0; i < 100; i++) {
            for (Robot robot : robots) {
                moveRobot(robot);
            }
        }

        return determineSafety(robots);
    }

    private static int part2(List<Robot> robots) {
        int lowestSafety = 1000000000;
        int lowestSafetyIndex = 0;
        for (int i = 0; i < 10000; i++) {
            for (Robot robot : robots) {
                moveRobot(robot);
            }
            int safety = determineSafety(robots);
            if (safety < lowestSafety) {
                lowestSafety = safety;
                lowestSafetyIndex = i;
            }
        }
        return lowestSafetyIndex + 1;
    }

    private static int part2Pretty(Grid<Location> grid, List<Robot> robots) {
        int lowestSafety = 1000000000;
        int lowestSafetyIndex = 0;

        for (int k = 0; k < 10000; k++) {
            for (Robot robot : robots) {
                Location location = grid.get(robot.coordinate);
                location.robots.remove(robot);
                moveRobot(robot);
                Location newLocation = grid.get(robot.coordinate);
                if (newLocation != null) {
                    newLocation.robots.add(robot);
                } else {
                    newLocation = new Location();
                    newLocation.robots.add(robot);
                    grid.set(robot.coordinate, newLocation);
                }
            }

            int safety = determineSafety(robots);
            if (safety < lowestSafety) {
                System.out.println(k+1);
                grid.printWithDefault(".");
                lowestSafety = safety;
                lowestSafetyIndex = k;
            }
        }

        return lowestSafetyIndex;
    }

    private static void moveRobot(Robot robot) {
        int newX = robot.coordinate.x + robot.velocity.x;
        int newY = robot.coordinate.y + robot.velocity.y;
        robot.coordinate = new Coordinate(newX, newY).loopInBoundingBox(0, 0, WIDTH - 1, HEIGHT- 1);
    }

    private static int determineSafety(List<Robot> robots) {
        int q1 = 0;
        int q2 = 0;
        int q3 = 0;
        int q4 = 0;
        int xDivider = (WIDTH-1) / 2;
        int yDivider = (HEIGHT-1) / 2;
        for (Robot robot : robots) {
            if (robot.coordinate.x < xDivider) {
                if (robot.coordinate.y < yDivider) {
                    q1++;
                } else if (robot.coordinate.y > yDivider) {
                    q3++;
                }
            } else if (robot.coordinate.x > xDivider) {
                if (robot.coordinate.y < yDivider) {
                    q2++;
                } else if (robot.coordinate.y > yDivider) {
                    q4++;
                }
            }
        }
        return q1*q2*q3*q4;
    }

    static class Robot implements Cloneable {
        public Coordinate coordinate;
        public Coordinate velocity;

        public Robot(Coordinate coordinate, Coordinate velocity) {
            this.coordinate = coordinate;
            this.velocity = velocity;
        }

        @Override
        public Robot clone() {
            Robot robot;
            try {
                robot = (Robot) super.clone();
            } catch (CloneNotSupportedException ignored) {
                throw new IllegalStateException();
            }
            robot.coordinate = new Coordinate(coordinate.x, coordinate.y);
            robot.velocity = new Coordinate(velocity.x, velocity.y);
            return robot;
        }
    }

    static class Location {
        public List<Robot> robots = new ArrayList<>();
        @Override
        public String toString() {
            if (!robots.isEmpty()) {
                return "#";
            }
            return ".";
        }
    }
}

