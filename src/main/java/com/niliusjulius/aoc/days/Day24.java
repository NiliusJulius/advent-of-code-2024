package com.niliusjulius.aoc.days;

import com.niliusjulius.aoc.util.Reader;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Day24 {

    public static void main(String[] args) {
        List<String> input = Reader.readLinesAsList("day24");
        Map<String, Gate> wires = new TreeMap<>();
        boolean parsingWires = true;
       for (String line : input) {
           if (line.isEmpty()) {
               parsingWires = false;
               continue;
           }
           if (parsingWires) {
               String[] split = line.split(": ");
               wires.put(split[0], new Gate(split[0], Integer.parseInt(split[1]), null, null, null));
           } else {
               String[] split = line.split(" ");
               Gate gate = new Gate(split[4], null, split[1], split[0], split[2]);
               wires.put(split[4], gate);
           }
        }

        System.out.println(part1(wires));
        System.out.println(part2(wires));
    }

    private static long part2(Map<String, Gate> wires) {
        StringBuilder xValue = new StringBuilder();
        StringBuilder yValue = new StringBuilder();
        StringBuilder zValue = new StringBuilder();
        for (Map.Entry<String, Gate> entry : wires.entrySet()) {
            Gate gate = entry.getValue();
            if (gate.value == null) {
                gate.value = calculateValue(wires, gate);
            }
            if (gate.name.startsWith("x")) {
                xValue.append(gate.value);
            } else if (gate.name.startsWith("y")) {
                yValue.append(gate.value);
            } else if (gate.name.startsWith("z")) {
                zValue.append(gate.value);
            }
        }
        long x = Long.parseLong(xValue.reverse().toString(), 2);
        long y = Long.parseLong(yValue.reverse().toString(), 2);
        long correctZ = x + y;
        String binaryCorrectZ = Long.toBinaryString(correctZ);
//        System.out.println(xValue);
//        System.out.println(Long.parseLong(xValue.toString(), 2));
//        System.out.println(yValue);
//        System.out.println(Long.parseLong(yValue.toString(), 2));
        zValue.reverse();
//        System.out.println(zValue);
        return Long.parseLong(zValue.toString(), 2);
    }

    private static long part1(Map<String, Gate> wires) {
        StringBuilder zValue = new StringBuilder();
        for (Map.Entry<String, Gate> entry : wires.entrySet()) {
            if (!entry.getKey().startsWith("z")) {
                continue;
            }
            Gate gate = entry.getValue();
            if (gate.value == null) {
                gate.value = calculateValue(wires, gate);
            }
            zValue.append(gate.value);
        }
        return Long.parseLong(zValue.reverse().toString(), 2);
    }

    private static int calculateValue(Map<String, Gate> wires, Gate gate) {
        if (gate.value == null) {
            Gate input1 = wires.get(gate.input1);
            Gate input2 = wires.get(gate.input2);
            if (input1.value == null) {
                input1.value = calculateValue(wires, input1);
            }
            if (input2.value == null) {
                input2.value = calculateValue(wires, input2);
            }
            if (gate.operator.equals("AND")) {
                if (input1.value == 1 && input2.value == 1) {
                    return 1;
                }
            } else if (gate.operator.equals("OR")) {
                if (input1.value == 1 || input2.value == 1) {
                    return 1;
                }
            } else if (gate.operator.equals("XOR")) {
                if (!input1.value.equals(input2.value)) {
                    return 1;
                }
            }
        }
        return 0;
    }

    private static class Gate {
        String name;
        Integer value;
        String operator;
        String input1;
        String input2;

        public Gate(String name, Integer value, String operator, String input1, String input2) {
            this.name = name;
            this.value = value;
            this.operator = operator;
            this.input1 = input1;
            this.input2 = input2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Gate gate = (Gate) o;
            return name.equals(gate.name);
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }

        @Override
        public String toString() {
            return name;
        }
    }
}

