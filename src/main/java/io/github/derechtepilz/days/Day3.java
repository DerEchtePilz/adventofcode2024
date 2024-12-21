package io.github.derechtepilz.days;

import io.github.derechtepilz.Common;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day3 extends Common {

    private final String input = read("day3.txt");

    private final Pattern mulPattern = Pattern.compile("mul\\(\\d{1,3},\\d{1,3}\\)");
    private final Pattern mulDoDontPattern = Pattern.compile("(mul\\(\\d{1,3},\\d{1,3}\\))|(do\\(\\)|don't\\(\\))");

    @Override
    public void solve(boolean part2) {
        int result;
        if (!part2) {
            result = performMultiplications();
            System.out.println("Part 1: " + result);
        } else {
            result = considerOnlyEnabled();
            System.out.println("Part 2: " + result);
        }
    }

    private int performMultiplications() {
        Matcher matcher = mulPattern.matcher(input);

        List<String> instructions = new ArrayList<>();

        while (matcher.find()) {
            instructions.add(matcher.group());
        }

        return multiplyAll(instructions);
    }

    private int considerOnlyEnabled() {
        Matcher matcher = mulDoDontPattern.matcher(input);

        List<String> instructions = new ArrayList<>();

        String instruction;
        boolean canAdd = true;
        while (matcher.find()) {
            instruction = matcher.group();
            if (instruction.equals("don't()")) {
                canAdd = false;
                continue;
            }
            if (instruction.equals("do()")) {
                canAdd = true;
                continue;
            }
            if (instruction.startsWith("mul") && canAdd) {
                instructions.add(instruction);
            }
        }

        return multiplyAll(instructions);
    }

    private int multiplyAll(List<String> instructions) {
        int result = 0;
        for (String instruction : instructions) {
            instruction = instruction.replace("mul(", "").replace(")", "");
            String[] numbers = instruction.split(",");
            result += Integer.parseInt(numbers[0]) * Integer.parseInt(numbers[1]);
        }
        return result;
    }

}
