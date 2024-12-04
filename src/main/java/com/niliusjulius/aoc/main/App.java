package com.niliusjulius.aoc.main;

import com.niliusjulius.aoc.days.Day01;
import com.niliusjulius.aoc.days.Day02;
import com.niliusjulius.aoc.days.Day03;
import com.niliusjulius.aoc.days.Day04;
import com.niliusjulius.aoc.util.Downloader;

import java.io.OutputStream;
import java.io.PrintStream;
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
            exec(new DisplayData(1, "Red-Nosed Reports", Day02::main));
            exec(new DisplayData(1, "Mull It Over", Day03::main));
            exec(new DisplayData(1, "Ceres Search", Day04::main));
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
        MeasuringPrintStream measuringStream = new MeasuringPrintStream(System.out);
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

        public MeasuringPrintStream(OutputStream out) {
            super(out);
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
