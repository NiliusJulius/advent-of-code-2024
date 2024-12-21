package com.niliusjulius.aoc.main;

import com.niliusjulius.aoc.days.*;
import com.niliusjulius.aoc.util.Downloader;

import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class App {
    private static final int CONSOLE_WIDTH = 80;

    public static void main(String[] args) {
        if (args.length > 0 && "download".equals(args[0])) {
            String sessionId = System.getenv("AOC_SESSION_ID");
            String year = System.getenv("AOC_YEAR");
            if (sessionId == null || sessionId.isBlank() || year == null || year.isBlank()) {
                System.err.println("Session ID and/or Year are not set correctly in environment variables AOC_SESSION_ID and YEAR.");
                System.exit(1);
            }
            new Downloader(sessionId).downloadInputs(year);
        } else {
            Instant start = Instant.now();
            exec(new DisplayData(1, "Historian Hysteria", Day01::main));
            exec(new DisplayData(2, "Red-Nosed Reports", Day02::main));
            exec(new DisplayData(3, "Mull It Over", Day03::main));
            exec(new DisplayData(4, "Ceres Search", Day04::main));
            exec(new DisplayData(5, "Print Queue", Day05::main));
            exec(new DisplayData(6, "Guard Gallivant", Day06::main));
            exec(new DisplayData(7, "Bridge Repair", Day07::main));
            exec(new DisplayData(8, "Resonant Collinearity", Day08::main));
            exec(new DisplayData(9, "Disk Fragmenter", Day09::main));
            exec(new DisplayData(10, "Hoof It", Day10::main));
            exec(new DisplayData(11, "Plutonian Pebbles", Day11::main));
            exec(new DisplayData(12, "Garden Groups", Day12::main));
            exec(new DisplayData(13, "Claw Contraption", Day13::main));
            exec(new DisplayData(14, "Restroom Redoubt", Day14::main));
            exec(new DisplayData(15, "Warehouse Woes", Day15::main));
            exec(new DisplayData(16, "Reindeer Maze", Day16::main));
            exec(new DisplayData(17, "Chronospatial Computer", Day17::main));
            exec(new DisplayData(18, "RAM Run", Day18::main));
            exec(new DisplayData(19, "Linen Layout", Day19::main));
            exec(new DisplayData(20, "Race Condition", Day20::main));
            exec(new DisplayData(21, "Keypad Conundrum", Day21::main));
            Instant end = Instant.now();
            System.out.println("so far:\t" + Duration.between(start, end).toMillis() + " ms...");
        }
    }

    private static void exec(DisplayData dd) {
        String title = String.format(" Day %02d: %s ", dd.number(), dd.name());
        StringBuilder titleLine = new StringBuilder();
        titleLine.append(repeat("#", (CONSOLE_WIDTH - title.length()) / 2));
        titleLine.append(title);
        titleLine.append(repeat("#", CONSOLE_WIDTH - titleLine.length()));
        System.out.println(titleLine);
        PrintStream save = System.out;
        MeasuringPrintStream measuringStream = new MeasuringPrintStream(System.out, false, StandardCharsets.UTF_8);
        System.setOut(measuringStream);
        try {
            Instant start = Instant.now();
            dd.main().accept(new String[]{});
            Instant end = Instant.now();
            System.setOut(save);
            System.out.println(repeat("-", CONSOLE_WIDTH));
            System.out.println(
                    "part1 time:\t" + Duration.between(start, measuringStream.times.getFirst()).toMillis() + " ms");
            System.out.println("part2 time:\t"
                    + Duration.between(measuringStream.times.getFirst(), measuringStream.times.getLast()).toMillis()
                    + " ms");
            System.out.println("full time:\t" + Duration.between(start, end).toMillis() + " ms");
            System.out.println(repeat("*", CONSOLE_WIDTH));
        } finally {
            System.setOut(save);
        }
    }

    private static String repeat(String data, int times) {
        return String.join("", Collections.nCopies(times, data));
    }

    record DisplayData(int number, String name, Consumer<String[]> main) {
    }

    private static class MeasuringPrintStream extends PrintStream {
        private final List<Instant> times = new ArrayList<>();

        public MeasuringPrintStream(OutputStream out, boolean autoFlush, Charset charset) {
            super(out, autoFlush, charset);
        }

        @Override
        public void println(int i) {
            times.add(Instant.now());
            super.println(i);
        }

        public void println(long L) {
            times.add(Instant.now());
            super.println(L);
        }

        public void println(double d) {
            times.add(Instant.now());
            super.println(d);
        }

        public void println(String s) {
            times.add(Instant.now());
            super.println(s);
        }

        public void println(Object o) {
            times.add(Instant.now());
            super.println(o);
        }
    }
}
