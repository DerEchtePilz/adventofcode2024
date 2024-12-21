package io.github.derechtepilz.days;

import io.github.derechtepilz.Common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day17 extends Common {

    private long regA;
    private long regB;
    private long regC;
    private int pc = 0;
    private final List<Integer> instructions = new ArrayList<>();

    public Day17() {
        String[] input = read("day17.txt").split("\n");
        regA = Integer.parseInt(input[0].split(":")[1].strip());
        regB = Integer.parseInt(input[1].split(":")[1].strip());
        regC = Integer.parseInt(input[2].split(":")[1].strip());

        instructions.addAll(Arrays.stream(input[4].split(":")[1].strip().split(",")).map(Integer::parseInt).toList());
    }

    @Override
    public void solve(boolean part2) {
        if (!part2) {
            String result = determineProgramOutput();
            System.out.println("Part 1: " + result);
        } else {
            long result = 0;
            System.out.println("Part 2: " + result);
        }
    }

    private String determineProgramOutput() {
        StringBuilder output = new StringBuilder();
        while (pc < instructions.size()) {
             switch (instructions.get(pc)) {
                case 0 -> {
                    regA = regA / (long) Math.pow(2, switch (instructions.get(pc + 1)) {
                        case 0, 1, 2, 3 -> instructions.get(pc + 1);
                        case 4 -> regA;
                        case 5 -> regB;
                        case 6 -> regC;
                        default -> throw new IllegalStateException("Unexpected value: " + pc);
                    });
                    pc += 2;
                }
                case 1 -> {
                    regB = regB ^ instructions.get(pc + 1);
                    pc += 2;
                }
                case 2 -> {
                    regB = switch (instructions.get(pc + 1)) {
                        case 0, 1, 2, 3 -> instructions.get(pc + 1);
                        case 4 -> regA;
                        case 5 -> regB;
                        case 6 -> regC;
                        default -> throw new IllegalStateException("Unexpected value: " + pc);
                    } % 8;
                    pc += 2;
                }
                case 3 -> {
                    if (regA == 0) {
                        pc += 2;
                        break;
                    }
                    pc = instructions.get(pc + 1);
                }
                case 4 -> {
                    regB = regB ^ regC;
                    pc += 2;
                }
                case 5 -> {
                    long outResult = switch (instructions.get(pc + 1)) {
                        case 0, 1, 2, 3 -> instructions.get(pc + 1);
                        case 4 -> regA;
                        case 5 -> regB;
                        case 6 -> regC;
                        default -> throw new IllegalStateException("Unexpected value: " + pc);
                    } % 8;
                    appendToOutput(outResult, output);
                    pc += 2;
                }
                case 6 -> {
                    regB = regA / (long) Math.pow(2, switch (instructions.get(pc + 1)) {
                        case 0, 1, 2, 3 -> instructions.get(pc + 1);
                        case 4 -> regA;
                        case 5 -> regB;
                        case 6 -> regC;
                        default -> throw new IllegalStateException("Unexpected value: " + pc);
                    });
                    pc += 2;
                }
                case 7 -> {
                    regC = regA / (long) Math.pow(2, switch (instructions.get(pc + 1)) {
                        case 0, 1, 2, 3 -> instructions.get(pc + 1);
                        case 4 -> regA;
                        case 5 -> regB;
                        case 6 -> regC;
                        default -> throw new IllegalStateException("Unexpected value: " + pc);
                    });
                    pc += 2;
                }
            }
        }
        return output.toString();
    }

    private long findReplicator() {
        return -1;
    }

    private void appendToOutput(long output, StringBuilder builder) {
        if (builder.isEmpty()) {
            builder.append(output);
            return;
        }
        builder.append(",").append(output);
    }

}
