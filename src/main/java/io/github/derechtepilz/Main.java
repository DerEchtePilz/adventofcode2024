package io.github.derechtepilz;

import io.github.derechtepilz.days.Day1;
import io.github.derechtepilz.days.Day10;
import io.github.derechtepilz.days.Day11;
import io.github.derechtepilz.days.Day12;
import io.github.derechtepilz.days.Day13;
import io.github.derechtepilz.days.Day14;
import io.github.derechtepilz.days.Day15;
import io.github.derechtepilz.days.Day16;
import io.github.derechtepilz.days.Day17;
import io.github.derechtepilz.days.Day18;
import io.github.derechtepilz.days.Day2;
import io.github.derechtepilz.days.Day3;
import io.github.derechtepilz.days.Day4;
import io.github.derechtepilz.days.Day5;
import io.github.derechtepilz.days.Day6;
import io.github.derechtepilz.days.Day7;
import io.github.derechtepilz.days.Day8;
import io.github.derechtepilz.days.Day9;

public class Main {
    public static void main(String[] args) {
        measureExecutionTime(() -> {
            System.out.println("Tag 1:");
            Day1 day1 = new Day1();
            day1.solve(false);
            day1.solve(true);
        });
        measureExecutionTime(() -> {
            System.out.println("Tag 2:");
            Day2 day2 = new Day2();
            day2.solve(false);
            day2.solve(true);
        });
        measureExecutionTime(() -> {
            System.out.println("Tag 3:");
            Day3 day3 = new Day3();
            day3.solve(false);
            day3.solve(true);
        });
        measureExecutionTime(() -> {
            System.out.println("Tag 4:");
            Day4 day4 = new Day4();
            day4.solve(false);
            day4.solve(true);
        });
        measureExecutionTime(() -> {
            System.out.println("Tag 5:");
            Day5 day5 = new Day5();
            day5.solve(false);
            day5.solve(true);
        });
        measureExecutionTime(() -> {
            System.out.println("Tag 6:");
            Day6 day6 = new Day6();
            day6.solve(false);
            day6.solve(true);
        });
        measureExecutionTime(() -> {
            Day7 day7 = new Day7();
            System.out.println("Tag 7:");
            day7.solve(false);
            day7.solve(true);
        });
        measureExecutionTime(() -> {
            System.out.println("Tag 8:");
            Day8 day8 = new Day8();
            day8.solve(false);
            day8.solve(true);
        });
        measureExecutionTime(() -> {
            System.out.println("Tag 9:");
            Day9 day9 = new Day9();
            day9.solve(false);
            day9.solve(true);
        });
        measureExecutionTime(() -> {
            System.out.println("Tag 10:");
            Day10 day10 = new Day10();
            day10.solve(false);
            day10.solve(true);
        });
        measureExecutionTime(() -> {
            System.out.println("Tag 11:");
            Day11 day11 = new Day11();
            day11.solve(false);
            day11.solve(true);
        });
        measureExecutionTime(() -> {
            System.out.println("Tag 12:");
            Day12 day12 = new Day12();
            day12.solve(false);
            day12.solve(true);
        });
        measureExecutionTime(() -> {
            System.out.println("Tag 13:");
            Day13 day13 = new Day13();
            day13.solve(false);
            day13.solve(true);
        });
        measureExecutionTime(() -> {
            System.out.println("Tag 14:");
            Day14 day14 = new Day14();
            day14.solve(false);
            day14.solve(true);
        });
        measureExecutionTime(() -> {
            System.out.println("Tag 15:");
            Day15 day15 = new Day15();
            day15.solve(false);
        });
        measureExecutionTime(() -> {
            System.out.println("Tag 16:");
            Day16 day16 = new Day16();
            day16.solve(false);
        });
        measureExecutionTime(() -> {
            System.out.println("Tag 17:");
            Day17 day17 = new Day17();
            day17.solve(false);
        });
        measureExecutionTime(() -> {
            System.out.println("Tag 18:");
            Day18 day18 = new Day18();
            day18.solve(false);
            // day18.solve(true);
        });
    }

    private static void measureExecutionTime(Runnable day) {
        long startTime = System.nanoTime();
        day.run();
        long endTime = System.nanoTime();
        System.out.println("Execution time: " + (endTime - startTime) / 1000.0 + " microseconds");
    }

}
