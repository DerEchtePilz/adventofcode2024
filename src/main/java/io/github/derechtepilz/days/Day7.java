package io.github.derechtepilz.days;

import io.github.derechtepilz.Common;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day7 extends Common {

    private final String[] input = read("day7.txt").split("\n");

    private final Map<Long, List<Long>> allNumbers = new HashMap<>();

    public Day7() {
        for (String line : input) {
            String[] parts = line.split(":");
            allNumbers.put(Long.parseLong(parts[0]), getNumbersFromString(parts[1]));
        }
    }

    @Override
    public void solve(boolean part2) {
        if (!part2) {
            long result = addCorrectResults(false);
            System.out.println("Part 1: " + result);
        } else {
            long result = addCorrectResults(true);
            System.out.println("Part 2: " + result);
        }
    }

    private long addCorrectResults(boolean concat) {
        long result = 0;
        for (long equationRes : allNumbers.keySet()) {
            List<Long> numbers = allNumbers.get(equationRes);
            long first = numbers.getFirst();
            long allPossibilities = tryAllPossibilities(equationRes, first, numbers, 1, concat);
            result = allPossibilities == equationRes ? result + allPossibilities : result;
        }
        return result;
    }

    private long tryAllPossibilities(long goal, long result, List<Long> numbers, int index, boolean concat) {
        if (index >= numbers.size()) {
            return result;
        }
        long next = numbers.get(index);
        long plus = tryAllPossibilities(goal, result + next, numbers, index + 1, concat);
        long mul = tryAllPossibilities(goal, result * next, numbers, index + 1, concat);
        long conc = -1;
        if (concat) {
            conc = tryAllPossibilities(goal, Long.parseLong(result + "" + next), numbers, index + 1, true);
        }
        if (plus == goal || mul == goal || conc == goal) {
            return goal;
        } else {
            return -1;
        }
    }

    private List<Long> getNumbersFromString(String numbersAsString) {
        return Arrays.stream(numbersAsString.strip().split(" ")).map(Long::parseLong).toList();
    }

}
