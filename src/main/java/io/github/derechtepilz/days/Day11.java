package io.github.derechtepilz.days;

import io.github.derechtepilz.Common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day11 extends Common {

    private final List<Long> input = Arrays.stream(read("day11.txt").strip().split(" ")).map(Long::parseLong).toList();
    private final Map<String, Long> memo = new HashMap<>();

    @Override
    public void solve(boolean part2) {
        if (!part2) {
            long result = calculateStones(25);
            System.out.println("Part 1: " + result);
        } else {
            long result = calculateStones(75);
            System.out.println("Part 2: " + result);
        }
    }

    private long calculateStones(int blinks) {
        long result = 0;
        for (long l : input) {
            result += calculateStones(blinks, l);
        }
        return result;
    }

    private long calculateStones(long blinks, long number) {
        String key = blinks + "_" + number;

        if (memo.containsKey(key)) {
            return memo.get(key);
        }

        if (blinks == 0) {
            return 1;
        }
        if (number == 0) {
            long result = calculateStones(blinks - 1, 1);
            memo.put(key, result);
            return result;
        }
        long digits = (long) Math.log10(number) + 1;
        if (digits % 2 == 0) {
            long divisor = (long) Math.pow(10, digits / 2);
            long first = number / divisor;
            long second = number % divisor;
            long result = calculateStones(blinks - 1, first) + calculateStones(blinks - 1, second);
            memo.put(key, result);
            return result;
        }
        long result = calculateStones(blinks - 1, number * 2024);
        memo.put(key, result);
        return result;
    }

}
