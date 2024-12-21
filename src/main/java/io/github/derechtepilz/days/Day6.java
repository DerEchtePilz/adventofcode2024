package io.github.derechtepilz.days;

import io.github.derechtepilz.Common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day6 extends Common {

    private final char[][] map = Arrays.stream(read("day6.txt").split("\n")).map(String::toCharArray).toArray(char[][]::new);

    private Set<Pair<Integer>> path = null;

    @Override
    public void solve(boolean part2) {
        if (!part2) {
            int result = leaveMappedArea();
            System.out.println("Part 1: " + result);
        } else {
            int result = circleInMappedArea();
            System.out.println("Part 2: " + result);
        }
    }

    private int leaveMappedArea() {
        Pair<Integer> start = findGuard();
        Set<Pair<Integer>> path = walk(start);
        this.path = path;
        return path.size();
    }

    private int circleInMappedArea() {
        Pair<Integer> start = findGuard();
        return walkAddObstructions(start, path).size();
    }

    private Set<Pair<Integer>> walk(Pair<Integer> start) {
        Pair<Integer> current = start.clone();
        Set<Pair<Integer>> result = new HashSet<>();
        char direction = map[current.left()][current.right()];
        while (current.left() >= 0 && current.left() < map.length && current.right() >= 0 && current.right() < map[0].length) {
            result.add(new Pair<>(current.left(), current.right()));
            while (nextPosition(direction, current) == '#') {
                direction = turn(direction);
            }
            current = nextPoint(direction, current);
        }
        return result;
    }

    private Set<Pair<Integer>> walkAddObstructions(Pair<Integer> start, Set<Pair<Integer>> walkPath) {
        Set<Pair<Integer>> result = new HashSet<>();
        List<Pair<Integer>> walkCoords = new ArrayList<>(walkPath);
        for (Pair<Integer> toBeBlocked : walkCoords) {
            if (toBeBlocked.equals(start) || map[toBeBlocked.left()][toBeBlocked.right()] == '#') {
                continue;
            }

            map[toBeBlocked.left()][toBeBlocked.right()] = '#';

            Pair<Integer> current = start.clone();
            Map<Pair<Integer>, Set<Character>> facing = new HashMap<>();
            char direction = map[current.left()][current.right()];

            while (current.left() >= 0 && current.left() < map.length && current.right() >= 0 && current.right() < map[0].length) {
                Set<Character> rotations;
                if (facing.containsKey(current)) {
                    rotations = facing.get(current);
                    if (rotations.contains(direction)) {
                        result.add(new Pair<>(toBeBlocked.left(), toBeBlocked.right()));
                        break;
                    } else {
                        rotations.add(direction);
                    }
                } else {
                    rotations = new HashSet<>();
                    rotations.add(direction);
                }
                facing.put(current, rotations);
                while (nextPosition(direction, current) == '#') {
                    direction = turn(direction);
                }
                current = nextPoint(direction, current);
            }

            map[toBeBlocked.left()][toBeBlocked.right()] = '.';
        }
        return result;
    }

    private char nextPosition(char direction, Pair<Integer> current) {
        try {
            if (direction == '^') {
                return map[current.left() - 1][current.right()];
            }
            if (direction == '>') {
                return map[current.left()][current.right() + 1];
            }
            if (direction == 'v') {
                return map[current.left() + 1][current.right()];
            }
            return map[current.left()][current.right() - 1];
        } catch (ArrayIndexOutOfBoundsException | StringIndexOutOfBoundsException e) {
            return ' ';
        }
    }

    private Pair<Integer> nextPoint(char direction, Pair<Integer> current) {
        return switch (direction) {
            case '^' -> new Pair<>(current.left() - 1, current.right());
            case '>' -> new Pair<>(current.left(), current.right() + 1);
            case 'v' -> new Pair<>(current.left() + 1, current.right());
            case '<' -> new Pair<>(current.left(), current.right() - 1);
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        };
    }

    private char turn(char direction) {
        return switch (direction) {
            case '^' -> '>';
            case '>' -> 'v';
            case 'v' -> '<';
            case '<' -> '^';
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        };
    }

    private Pair<Integer> findGuard() {
        Pair<Integer> point = null;
        for (int x = 0; x < map.length && point == null; x++) {
            for (int y = 0; y < map[x].length; y++) {
                if (map[x][y] == '^') {
                    point = new Pair<>(x, y);
                    break;
                }
            }
        }
        return point;
    }

}