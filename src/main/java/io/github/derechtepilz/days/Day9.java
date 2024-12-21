package io.github.derechtepilz.days;

import io.github.derechtepilz.Common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day9 extends Common {

    private final String input = read("day9.txt").strip();

    @Override
    public void solve(boolean part2) {
        if (!part2) {
            long result = calcFilesystemChecksum();
            System.out.println("Part 1: " + result);
        } else {
            long result = reduceMemoryFragmentation();
            System.out.println("Part 2: " + result);
        }
    }

    private long calcFilesystemChecksum() {
        List<Integer> memory = new ArrayList<>();
        int processing = 0;
        int cell = 0;
        for (Character c : input.toCharArray()) {
            processing++;
            int memCell = getInt(c);
            if (processing % 2 == 0) {
                for (int i = 0; i < memCell; i++) {
                    memory.add(-1);
                }
            } else {
                for (int i = 0; i < memCell; i++) {
                    memory.add(cell);
                }
                cell++;
            }
        }
        int index = 0;
        while (memory.contains(-1)) {
            while (index < memory.size() && memory.get(index) != -1) {
                index++;
            }
            if (index >= memory.size()) {
                break;
            }
            memory.set(index, memory.getLast());
            memory.removeLast();
            index++;
            while (memory.getLast() == -1) {
                memory.removeLast();
            }
        }
        long result = 0;
        for (long i = 0; i < memory.size(); i++) {
            result += i * ((long) memory.get((int) i));
        }
        return result;
    }

    private long reduceMemoryFragmentation() {
        List<Integer> memory = new ArrayList<>();
        Map<Integer, Integer> freeBlocks = new HashMap<>();
        Map<Integer, Integer> filledBlocks = new HashMap<>();
        Map<Integer, Pair<Integer>> blocks = new HashMap<>();
        int processing = 0;
        int cell = 0;
        for (Character c : input.toCharArray()) {
            processing++;
            int cellSize = getInt(c);
            if (processing % 2 == 0) {
                if (cellSize != 0) {
                    freeBlocks.put(memory.size(), cellSize);
                }
                for (int i = 0; i < cellSize; i++) {
                    memory.add(-1);
                }
            } else {
                if (cellSize != 0) {
                    filledBlocks.put(cell, cellSize);
                    blocks.put(cell, new Pair<>(memory.size(), memory.size() + cellSize - 1));
                }
                for (int i = 0; i < cellSize; i++) {
                    memory.add(cell);
                }
                cell++;
            }
        }
        cell--;

        int maxFreeBlockSize = 0;
        for (int freeBlockSize : freeBlocks.values()) {
            if (freeBlockSize > maxFreeBlockSize) {
                maxFreeBlockSize = freeBlockSize;
            }
        }

        int end = memory.size() - 1;
        int blockStart = -1;
        int blockEnd = -1;
        while (cell >= 0) {
            for (int start = 0; start <= memory.size() - 1; start++) {
                while (blockStart == -1) {
                    if (memory.get(end) != cell) {
                        end--;
                        continue;
                    }
                    if (blockEnd == -1) {
                        blockEnd = end;
                    }
                    if (end - 1 < 0 || memory.get(end - 1) != cell) {
                        blockStart = end;
                    }
                    end--;
                }
                if (start >= blockStart) {
                    blockStart = -1;
                    blockEnd = -1;
                    cell--;
                    break;
                }
                if (!freeBlocks.containsKey(start)) {
                    continue;
                }
                int freeSize = freeBlocks.get(start);
                int blockSize = blockEnd - blockStart + 1;
                if (blockSize > freeSize) {
                    continue;
                }
                freeBlocks.remove(start);
                int newFreeBlocks = 0;
                int j = start + blockSize;
                while (memory.get(j) == -1) {
                    newFreeBlocks++;
                    j++;
                }
                if (newFreeBlocks > 0) {
                    freeBlocks.put(start + blockSize, newFreeBlocks);
                }
                for (int i = start; i < start + blockSize; i++) {
                    memory.set(i, cell);
                }
                for (int i = blockStart; i <= blockEnd; i++) {
                    memory.set(i, -1);
                }
                blockStart = -1;
                blockEnd = -1;
                cell--;
                start = 0;
            }
        }
        long result = 0;
        for (long i = 0; i < memory.size(); i++) {
            if (memory.get((int) i) == -1) {
                continue;
            }
            result += i * ((long) memory.get((int) i));
        }
        return result;
    }
}
