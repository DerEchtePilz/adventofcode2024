package io.github.derechtepilz.days;

import io.github.derechtepilz.Common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day14 extends Common {

    private final List<Pair<Pair<Integer>>> allRobots;

    private final int width = 101;
    private final int height = 103;

    public Day14() {
        allRobots = new ArrayList<>(Arrays.stream(read("day14.txt").split("\n")).map(posVel -> {
            int posX = Integer.parseInt(posVel.substring(posVel.indexOf('=') + 1, posVel.indexOf(',')));
            int posY = Integer.parseInt(posVel.substring(posVel.indexOf(',') + 1, posVel.indexOf(' ')));

            int velX = Integer.parseInt(posVel.substring(posVel.lastIndexOf('=') + 1, posVel.lastIndexOf(',')));
            int velY = Integer.parseInt(posVel.substring(posVel.lastIndexOf(',') + 1));

            Pair<Integer> position = new Pair<>(posX, posY);
            Pair<Integer> velocity = new Pair<>(velX, velY);
            return new Pair<>(position, velocity);
        }).toList());
    }

    @Override
    public void solve(boolean part2) {
        if (!part2) {
            long result = teleportRobots(100);
            System.out.println("Part 1: " + result);
        } else {
            long result = findEasterEgg();
            System.out.println("Part 2: " + result);
        }
    }

    private long findEasterEgg() {
        long lowestSafetyRating = Integer.MAX_VALUE;
        long result = 0;
        for (int i = 0; i < 10403; i++) {
            long safetyRating = teleportRobots(i);
            if (safetyRating < lowestSafetyRating) {
                lowestSafetyRating = safetyRating;
                result = i;
            }
        }
        return result;
    }

    private long teleportRobots(int turns) {
        int[] quadrants = new int[4];
        int midX = width / 2;
        int midY = height / 2;
        for (Pair<Pair<Integer>> robot : allRobots) {
            Pair<Integer> position = robot.left();
            Pair<Integer> velocity = robot.right();

            int finalX = (position.left() + velocity.left() * turns) % width;
            int finalY = (position.right() + velocity.right() * turns) % height;

            if (finalX < 0) {
                finalX = finalX + width;
            }
            if (finalY < 0) {
                finalY = finalY + height;
            }

            if (finalX > midX && finalY < midY) {
                quadrants[0]++;
            }
            if (finalX < midX && finalY < midY) {
                quadrants[1]++;
            }
            if (finalX < midX && finalY > midY) {
                quadrants[2]++;
            }
            if (finalX > midX && finalY > midY) {
                quadrants[3]++;
            }
        }
        return (long) quadrants[0] * quadrants[1] * quadrants[2] * quadrants[3];
    }

}
