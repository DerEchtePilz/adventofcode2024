package io.github.derechtepilz.days;

import io.github.derechtepilz.Common;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Day16 extends Common {

    private final char[][] maze = Arrays.stream(read("day16.txt").split("\n")).map(String::toCharArray).toArray(char[][]::new);

    private final Set<Node> nodes = new HashSet<>();

    public Day16() {
        fillNodes();
    }

    @Override
    public void solve(boolean part2) {
        if (!part2) {
            long result = findLowestScore();
            System.out.println("Part 1: " + result);
        } else {
            fillNodes();
            long result = 0;
            System.out.println("Part 2: " + result);
        }
    }

    private void fillNodes() {
        for (int x = 0; x < maze.length; x++) {
            for (int y = 0; y < maze[x].length; y++) {
                if (maze[x][y] == '.') {
                    nodes.add(new Node(x, y));
                }
            }
        }
    }

    private long findLowestScore() {
        Set<Node> nextNodes = new HashSet<>();
        Node start = findStart();
        Node end = findEnd();
        nextNodes.add(start);
        nodes.add(start);
        nodes.add(end);
        while (!nextNodes.isEmpty()) {
            Node cheapest = null;
            for (Node node : nextNodes) {
                if (cheapest == null || node.costs() < cheapest.costs()) {
                    cheapest = node;
                }
            }
            nextNodes.remove(cheapest);
            nodes.remove(cheapest);
            if (cheapest.equals(end)) {
                break;
            }
            Set<Node> neighbours = new HashSet<>();
            for (Node node : nodes) {
                if (cheapest.isNeighbour(node)) {
                    neighbours.add(node);
                }
            }
            for (Node neighbour : neighbours) {
                Node previous = cheapest.from();
                int cheapestToNeighbourX = Math.abs(cheapest.x() - neighbour.x());
                int cheapestToNeighbourY = Math.abs(cheapest.y() - neighbour.y());
                int previousToCheapestX = previous == null ? 0 : Math.abs(cheapest.x() - previous.x());
                int previousToCheapestY = previous == null ? 1 : Math.abs(cheapest.y() - previous.y());
                long costs = previousToCheapestX == cheapestToNeighbourX && previousToCheapestY == cheapestToNeighbourY ? 1 : 1001;
                if (cheapest.costs() + costs < neighbour.costs()) {
                    neighbour.costsFrom(cheapest.costs() + costs, cheapest);
                    nextNodes.add(neighbour);
                }
            }
        }
        return end.costs();
    }

    private Node findStart() {
        Node start = null;
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                if (maze[i][j] == 'S') {
                    start = new Node(i, j);
                    start.costsFrom(0, null);
                    break;
                }
            }
            if (start != null) {
                break;
            }
        }
        return start;
    }

    private Node findEnd() {
        Node end = null;
        for (int x = 0; x < maze.length; x++) {
            for (int y = 0; y < maze[x].length; y++) {
                if (maze[x][y] == 'E') {
                    end = new Node(x, y);
                    break;
                }
            }
            if (end != null) {
                break;
            }
        }
        return end;
    }

}
