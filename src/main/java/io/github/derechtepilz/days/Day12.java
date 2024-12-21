package io.github.derechtepilz.days;

import io.github.derechtepilz.Common;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class Day12 extends Common {

    //private final char[][] input = Arrays.stream(read("day12.txt").split("\n")).map(String::toCharArray).toArray(char[][]::new);
    private char[][] input = {
        {'C','X'},
        {'X','X'}
    };

    @Override
    public void solve(boolean part2) {
        if (!part2) {
            long result = countFences();
            System.out.println("Part 1: " + result);
        } else {
            long result = 0;
            System.out.println("Part 2: " + result);
        }
    }

    private long countFences() {
        long totalFences = 0;
        Set<Pair<Integer>> areaVisited = new HashSet<>();
        Set<Pair<Integer>> fenceVisited = new HashSet<>();
        for (int x = 0; x < input.length; x++) {
            for (int y = 0; y < input[x].length; y++) {
                Pair<Integer> current = new Pair<>(x, y);
                if (areaVisited.contains(current) || fenceVisited.contains(current)) {
                    continue;
                }
                char region = input[x][y];
                long area = findArea(region, x, y, areaVisited);
                long perimeter = findFences(region, x, y, fenceVisited);
                totalFences += area * perimeter;
            }
        }
        return totalFences;
    }

    private long findArea(char region, int x, int y, Set<Pair<Integer>> visited) {
        if (input[x][y] != region) {
            return 0;
        }
        Pair<Integer> current = new Pair<>(x, y);
        if (visited.contains(current)) {
            return 0;
        }
        visited.add(new Pair<>(x, y));
        long result = 1;
        if ((x + 1) < input.length) {
            result += findArea(region, x + 1, y, visited);
        }
        if ((y + 1) < input[x].length) {
            result += findArea(region, x, y + 1, visited);
        }
        if ((x - 1) >= 0) {
            result += findArea(region, x - 1, y, visited);
        }
        if ((y - 1) >= 0) {
            result += findArea(region, x, y - 1, visited);
        }
        return result;
    }

    private long findFences(char region, int x, int y, Set<Pair<Integer>> visited) {
        Queue<Pair<Integer>> queue = new LinkedList<>();
        queue.add(new Pair<>(x, y));
        long fences = 0;
        while (!queue.isEmpty()) {
            Pair<Integer> current = queue.poll();
            visited.add(current);
            if ((current.left() - 1) < 0 || input[current.left() - 1][current.right()] != region) {
                fences++;
            } else {
                Pair<Integer> neighbour = new Pair<>(current.left() - 1, current.right());
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    queue.add(neighbour);
                }
            }
            if ((current.left() + 1) >= input.length || input[current.left() + 1][current.right()] != region) {
                fences++;
            } else {
                Pair<Integer> neighbour = new Pair<>(current.left() + 1, current.right());
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    queue.add(neighbour);
                }
            }
            if ((current.right() - 1) < 0 || input[current.left()][current.right() - 1] != region) {
                fences++;
            } else {
                Pair<Integer> neighbour = new Pair<>(current.left(), current.right() - 1);
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    queue.add(neighbour);
                }
            }
            if ((current.right() + 1) >= input[current.left()].length || input[current.left()][current.right() + 1] != region) {
                fences++;
            } else {
                Pair<Integer> neighbour = new Pair<>(current.left(), current.right() + 1);
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    queue.add(neighbour);
                }
            }
        }
        return fences;
    }

    private long countCorners(Set<Pair<Integer>> region) {
        long sides = 0;

        return sides;
    }

}
