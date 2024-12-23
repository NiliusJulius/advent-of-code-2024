package com.niliusjulius.aoc.days;

import com.niliusjulius.aoc.util.Reader;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day23 {

    public static void main(String[] args) {
        List<String> input = Reader.readLinesAsList("day23");
        List<String> computers = new ArrayList<>();
        Map<String, List<String>> connections  = new HashMap<>();
        Set<String> uniqueComputers = new HashSet<>();
        for (String connection : input) {
             String[] split = connection.split("-");
             computers.add(split[0]);
             computers.add(split[1]);
             uniqueComputers.add(split[0]);
             uniqueComputers.add(split[1]);
             if (!connections.containsKey(split[0])) {
                 connections.put(split[0], new ArrayList<>(List.of(split[1])));
             } else {
                 connections.get(split[0]).add(split[1]);
             }
            if (!connections.containsKey(split[1])) {
                connections.put(split[1], new ArrayList<>(List.of(split[0])));
            } else {
                connections.get(split[1]).add(split[0]);
            }
        }

        List<Node> nodes = new ArrayList<>();
        for (String connection : input) {
            String[] split = connection.split("-");
            if (nodes.contains(new Node(split[0], null))) {
                if (nodes.contains(new Node(split[1], null))) {
                    nodes.get(nodes.indexOf(new Node(split[0], null))).children.add(nodes.get(nodes.indexOf(new Node(split[1], null))));
                    nodes.get(nodes.indexOf(new Node(split[1], null))).children.add(nodes.get(nodes.indexOf(new Node(split[0], null))));
                } else {
                    Node firstNode = nodes.get(nodes.indexOf(new Node(split[0], null)));
                    Node secondNode = new Node(split[1], new ArrayList<>(List.of(firstNode)));
                    nodes.add(secondNode);
                    firstNode.children.add(secondNode);
                }
            } else {
                if (nodes.contains(new Node(split[1], null))) {
                    Node secondNode = nodes.get(nodes.indexOf(new Node(split[1], null)));
                    Node firstNode = new Node(split[0], new ArrayList<>(List.of(secondNode)));
                    secondNode.children.add(firstNode);
                    nodes.add(firstNode);
                } else {
                    Node firstNode = new Node(split[0], new ArrayList<>());
                    Node secondNode = new Node(split[1], new ArrayList<>(List.of(firstNode)));
                    firstNode.children.add(secondNode);
                    nodes.add(secondNode);
                    nodes.add(firstNode);
                }
            }
        }

        Map<String, Node> graph  = new HashMap<>();
        for (String computer : uniqueComputers) {
            graph.put(computer, nodes.get(nodes.indexOf(new Node(computer, null))));
        }

        System.out.println(part1(connections));
        System.out.println(part2(graph));
    }

    private static String part2(Map<String, Node> graph) {
        return findCliques(graph, new HashSet<>(), new HashSet<>(graph.keySet()))
                .stream()
                .max(Comparator.comparingInt(Set::size))
                .map(largestClique -> largestClique.stream().sorted().reduce((a, b) -> a + "," + b).orElse(""))
                .orElse("");
    }

    private static int part1(Map<String, List<String>> connections) {
        List<Triple> triples = new ArrayList<>();
        for ( Map.Entry<String, List<String>> entry : connections.entrySet()) {
            String computer = entry.getKey();
            List<String> connection = entry.getValue();
            for (String secondComputer : connection) {
                List<String> secondComputerConnections = connections.get(secondComputer);
                for (String thirdComputer : secondComputerConnections) {
                    if (computer.startsWith("t") || secondComputer.startsWith("t") || thirdComputer.startsWith("t")) {
                        if (hasConnectionTo(connections, thirdComputer, computer)) {
                            if (!triples.contains(new Triple(computer, secondComputer, thirdComputer))) {
                                triples.add(new Triple(computer, secondComputer, thirdComputer));
                            }
                        }
                    }
                }
            }
        }

        return triples.size();
    }

    private static List<Set<String>> findCliques(Map<String, Node> graph, Set<String> potential, Set<String> candidates) {
        if (candidates.isEmpty()) {
            return List.of(new HashSet<>(potential));
        }

        return new HashSet<>(candidates).stream().flatMap(candidate -> {
            candidates.remove(candidate);
            return findCliques(
                    graph,
                    Stream.concat(potential.stream(), Stream.of(candidate)).collect(Collectors.toSet()),
                    createIntersection(candidates, graph.get(candidate).children().stream().map(Node::name).toList())
            ).stream();
        }).toList();
    }

    private static Set<String> createIntersection(Set<String> one, List<String> two) {
        Set<String> intersection = new HashSet<>(one);
        intersection.retainAll(two);
        return intersection;
    }

    private static boolean hasConnectionTo(Map<String, List<String>> connections, String computer, String connection) {
        return connections.containsKey(computer) && connections.get(computer).contains(connection);
    }

    private record Triple(String first, String second, String third) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Triple triple = (Triple) o;
            return ((first.equals(triple.first) || first.equals(triple.second) || first.equals(triple.third)))
                    && (second.equals(triple.second) || second.equals(triple.first) || second.equals(triple.third))
                    && (third.equals(triple.third) || third.equals(triple.second) || third.equals(triple.first));
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second, third);
        }
    }

    private record Node(String name, List<Node> children) {

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return name.equals(node.name);
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

