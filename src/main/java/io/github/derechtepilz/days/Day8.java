package io.github.derechtepilz.days;

import io.github.derechtepilz.Common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day8 extends Common {

    private final char[][] input = Arrays.stream(read("day8.txt").split("\n")).map(String::toCharArray).toArray(char[][]::new);

    private final Map<Character, List<Pair<Integer>>> antennas  = new HashMap<>();

    public Day8() {
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input[i].length; j++) {
                if (!isAlphaNumeric(input[i][j])) {
                    continue;
                }
                List<Pair<Integer>> seenAntennas;
                if (!antennas.containsKey(input[i][j])) {
                    seenAntennas = new ArrayList<>();
                } else {
                    seenAntennas = antennas.get(input[i][j]);
                }
                seenAntennas.add(new Pair<>(i, j));
                antennas.put(input[i][j], seenAntennas);
            }
        }
    }

    @Override
    public void solve(boolean part2) {
        if (!part2) {
            int result = findAntinodes(false);
            System.out.println("Part 1: " + result);
        } else {
            int result = findAntinodes(true);
            System.out.println("Part 2: " + result);
        }
    }

    private int findAntinodes(boolean part2) {
        Set<Pair<Integer>> antinodes = new HashSet<>();
        for (Character frequency : antennas.keySet()) {
            List<Pair<Integer>> presentAntennas = antennas.get(frequency);
            List<Pair<Integer>> processedAntennas = new ArrayList<>();
            for (Pair<Integer> antenna : presentAntennas) {
                for (Pair<Integer> dAntenna : presentAntennas) {
                    if (antenna.equals(dAntenna)) {
                        continue;
                    }
                    if (processedAntennas.contains(dAntenna)) {
                        continue;
                    }
                    antinodes.addAll(generateAntinodes(antenna, dAntenna, part2));
                }
                processedAntennas.add(antenna);
            }
        }
        return antinodes.size();
    }

    private Set<Pair<Integer>> generateAntinodes(Pair<Integer> antenna, Pair<Integer> dAntenna, boolean part2) {
        int dx = Math.abs(dAntenna.left() - antenna.left());
        int dy = dAntenna.right() - antenna.right();

        Pair<Integer> down = new Pair<>(dAntenna.left() + dx, dAntenna.right() + dy);
        Pair<Integer> up = new Pair<>(antenna.left() - dx, antenna.right() - dy);

        Set<Pair<Integer>> antinodes = new HashSet<>();
        if (!part2) {
            if (down.isInBounds(input.length, input[0].length)) {
                antinodes.add(down);
            }
            if (up.isInBounds(input.length, input[0].length)) {
                antinodes.add(up);
            }
        } else {
            antinodes.add(antenna);
            antinodes.add(dAntenna);

            while (down.isInBounds(input.length, input[0].length)) {
                antinodes.add(down);
                down = new Pair<>(down.left() + dx, down.right() + dy);
            }

            while (up.isInBounds(input.length, input[0].length)) {
                antinodes.add(up);
                up = new Pair<>(up.left() - dx, up.right() - dy);
            }
        }
        return antinodes;
    }

}
