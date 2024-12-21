package io.github.derechtepilz.days;

import io.github.derechtepilz.Common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day5 extends Common {

    private final List<Pair<Integer>> printRules = new ArrayList<>();
    private final List<List<Integer>> updates = new ArrayList<>();

    public Day5() {
        String[] input = read("day5.txt").split("\n");
        int i = 0;
        while (!(input[i].isEmpty())) {
            String rule = input[i].trim();
            int key = Integer.parseInt(rule.split("\\|")[0]);
            int value = Integer.parseInt(rule.split("\\|")[1]);

            printRules.add(new Pair<>(key, value));
            i++;
        }
        i++;
        while (i < input.length) {
            String rawUpdate = input[i].trim();
            String[] raw = rawUpdate.split(",");
            List<Integer> update = new ArrayList<>();
            for (String s : raw) {
                update.add(Integer.parseInt(s));
            }
            updates.add(update);
            i++;
        }
    }

    @Override
    public void solve(boolean part2) {
        Pair<List<List<Integer>>> updates = getInValid();
        if (!part2) {
            int result = addMiddleNumbers(updates.left());
            System.out.println("Part 1: " + result);
        } else {
            int result = fixInvalidUpdates(updates.right());
            System.out.println("Part 2: " + result);
        }
    }

    private Pair<List<List<Integer>>> getInValid() {
        List<List<Integer>> validUpdates = new ArrayList<>();
        List<List<Integer>> invalidUpdates = new ArrayList<>();
        for (List<Integer> update : updates) {
            List<Integer> printedPages = new ArrayList<>();
            for (int page : update) {
                boolean canPrint = true;
                for (Pair<Integer> rule : printRules) {
                    if (rule.right() != page) {
                        continue;
                    }
                    if (!update.contains(rule.left())) {
                        continue;
                    }
                    if (!printedPages.contains(rule.left())) {
                        canPrint = false;
                        break;
                    }
                }
                if (canPrint) {
                    printedPages.add(page);
                }
            }
            if (printedPages.size() == update.size()) {
                validUpdates.add(update);
            } else {
                invalidUpdates.add(update);
            }
        }
        return new Pair<>(validUpdates, invalidUpdates);
    }

    private int fixInvalidUpdates(List<List<Integer>> updates) {
        for (List<Integer> update : updates) {
            int[] fixedUpdateArray = new int[update.size()];
            Arrays.fill(fixedUpdateArray, -1);
            for (int page : update) {
                int fixedIndex = 0;
                for (Pair<Integer> rule : printRules) {
                    if (rule.right() != page) {
                        continue;
                    }
                    if (!update.contains(rule.left())) {
                        continue;
                    }
                    fixedIndex++;
                }
                fixedUpdateArray[fixedIndex] = page;
            }
            for (int i = 0; i < fixedUpdateArray.length; i++) {
                update.set(i, fixedUpdateArray[i]);
            }
        }
        return addMiddleNumbers(updates);
    }

    private int addMiddleNumbers(List<List<Integer>> lists) {
        int result = 0;
        for (List<Integer> list : lists) {
            result += list.get((int) Math.floor(list.size() / 2.0));
        }
        return result;
    }
}
