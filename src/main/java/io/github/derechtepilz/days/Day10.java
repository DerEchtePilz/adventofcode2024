package io.github.derechtepilz.days;

import io.github.derechtepilz.Common;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Day10 extends Common {

    private final char[][] input = Arrays.stream(read("day10.txt").split("\n")).map(String::toCharArray).toArray(char[][]::new);

    @Override
    public void solve(boolean part2) {
        if (!part2) {
            int result = findAllTrailheads(false);
            System.out.println("Part 1: " + result);
        } else {
            int result = findAllTrailheads(true);
            System.out.println("Part 2: " + result);
        }
    }

    private int findAllTrailheads(boolean part2) {
        int result = 0;
        for (int x = 0; x < input.length; x++) {
            for (int y = 0; y < input[x].length; y++) {
                if (input[x][y] != '0') {
                    continue;
                }
                if (!part2) {
                    result += findTrailheads('0', x, y).size();
                } else {
                    result += findDistinctTrails('0', x, y);
                }
            }
        }
        return result;
    }

    private Set<Pair<Integer>> findTrailheads(char toMatch, int x, int y) {
        if (toMatch == '9' && input[x][y] == '9') {
            return Set.of(new Pair<>(x, y));
        }
        if (toMatch != input[x][y]) {
            return Set.of();
        }
        Set<Pair<Integer>> trailheads = new HashSet<>();
        char next = next(toMatch);
        if ((x + 1) < input.length) {
            trailheads.addAll(findTrailheads(next, x + 1, y));
        }
        if ((x - 1) >= 0) {
            trailheads.addAll(findTrailheads(next, x - 1, y));
        }
        if ((y + 1) < input[x].length) {
            trailheads.addAll(findTrailheads(next, x, y + 1));
        }
        if ((y - 1) >= 0) {
            trailheads.addAll(findTrailheads(next, x, y - 1));
        }
        return trailheads;
    }

    private int findDistinctTrails(char toMatch, int x, int y) {
        if (toMatch == '9' && input[x][y] == '9') {
            return 1;
        }
        if (toMatch != input[x][y]) {
            return 0;
        }
        int result = 0;
        char next = next(toMatch);
        if ((x + 1) < input.length) {
            result += findDistinctTrails(next, x + 1, y);
        }
        if ((x - 1) >= 0) {
            result += findDistinctTrails(next, x - 1, y);
        }
        if ((y + 1) < input[x].length) {
            result += findDistinctTrails(next, x, y + 1);
        }
        if ((y - 1) >= 0) {
            result += findDistinctTrails(next, x, y - 1);
        }
        return result;
    }

    private char next(char toMatch) {
        return switch (toMatch) {
            case '0' -> '1';
            case '1' -> '2';
            case '2' -> '3';
            case '3' -> '4';
            case '4' -> '5';
            case '5' -> '6';
            case '6' -> '7';
            case '7' -> '8';
            case '8' -> '9';
            default -> throw new IllegalStateException("Unexpected value: " + toMatch);
        };
    }

}
