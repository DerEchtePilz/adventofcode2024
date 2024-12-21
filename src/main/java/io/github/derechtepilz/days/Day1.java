package io.github.derechtepilz.days;

import io.github.derechtepilz.Common;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class Day1 extends Common {

    private final String input = read("day1.txt");

    @Override
    public void solve(boolean part2) {
        if (input == null) {
            return;
        }
        List<String> stringPairs = Arrays.asList(input.split("\n"));
        stringPairs.removeIf(String::isEmpty);

        Queue<Integer> leftSide = new PriorityQueue<>();
        Queue<Integer> rightSide = new PriorityQueue<>();

        for (String stringPair : stringPairs) {
            String[] split = stringPair.split(" {3}");
            leftSide.add(Integer.parseInt(split[0]));
            rightSide.add(Integer.parseInt(split[1]));
        }

        if (!part2) {
            int result = sumDistances(leftSide, rightSide);
            System.out.println("Part 1: " + result);
        } else {
            int result = similarity(leftSide, rightSide);
            System.out.println("Part 2: " + result);
        }
    }

    private int sumDistances(Queue<Integer> leftSide, Queue<Integer> rightSide) {
        if (leftSide.isEmpty() && rightSide.isEmpty()) {
            return 0;
        }
        return Math.abs(rightSide.poll() - leftSide.poll()) + sumDistances(leftSide, rightSide);
    }

    private int similarity(Queue<Integer> leftSide, Queue<Integer> rightSide) {
        int similarityScore = 0;
        for (int left : leftSide) {
            int occurences = 0;
            for (int right : rightSide) {
                if (left == right) {
                    occurences++;
                }
            }
            similarityScore += (left * occurences);
        }
        return similarityScore;
    }

}
