package com.niliusjulius.aoc.util;

import com.niliusjulius.aoc.days.Day01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

public class Reader {

    public static String readLineAsString(String fileName) {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(Day01.class.getResourceAsStream("/" + fileName + ".txt")), StandardCharsets.UTF_8))) {
            return br.readLine();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static List<String> readLinesAsList(String fileName) {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(Day01.class.getResourceAsStream("/" + fileName + ".txt")), StandardCharsets.UTF_8))) {
            return br.lines().toList();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static String readWholeFileAsString(String fileName) {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(Day01.class.getResourceAsStream("/" + fileName + ".txt")), StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
