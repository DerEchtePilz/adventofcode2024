package io.github.derechtepilz.days;

import io.github.derechtepilz.Common;

public class Day4 extends Common {

    private final String[] input = read("day4.txt").split("\n");

    @Override
    public void solve(boolean part2) {
        if (!part2) {
            int result = countXmax();
            System.out.println("Part 1: " + result);
        } else {
            int result = countX_Mas();
            System.out.println("Part 2: " + result);
        }
    }

    private int countXmax() {
        int result = 0;
        for (int x = 0; x < input.length; x++) {
            for (int y = 0; y < input[x].length(); y++) {
                result += searchXmax('X', x, y, 1, 0);
                result += searchXmax('X', x, y, 0, 1);
                result += searchXmax('X', x, y, -1, 0);
                result += searchXmax('X', x, y, 0, -1);

                result += searchXmax('X', x, y, 1, 1);
                result += searchXmax('X', x, y, 1, -1);
                result += searchXmax('X', x, y, -1, 1);
                result += searchXmax('X', x, y, -1, -1);
            }
        }
        return result;
    }

    private int countX_Mas() {
        int result = 0;
        for (int x = 0; x < input.length; x++) {
            for (int y = 0; y < input[x].length(); y++) {
                char current = input[x].charAt(y);
                if (current != 'A') {
                    continue;
                }
                int tx = x - 1;
                int bx = x + 1;
                int ly = y - 1;
                int ry = y + 1;
                if (x < 1 || x > input.length - 2) {
                    continue;
                }
                if (y < 1 || y > input[x].length() - 2) {
                    continue;
                }
                if ((input[tx].charAt(ly) == 'M' && input[bx].charAt(ry) == 'S'
                    || input[tx].charAt(ly) == 'S' && input[bx].charAt(ry) == 'M')
                    && (input[bx].charAt(ly) == 'M' && input[tx].charAt(ry) == 'S'
                    || input[bx].charAt(ly) == 'S' && input[tx].charAt(ry) == 'M')
                ) {
                    result++;
                }
            }
        }
        return result;
    }

    private int searchXmax(char toMatch, int x, int y, int dx, int dy) {
        if (toMatch == 'S' && input[x].charAt(y) == toMatch) {
            return 1;
        }
        if (input[x].charAt(y) != toMatch) {
            return 0;
        }
        int nx = x + dx;
        int ny = y + dy;
        if (nx >= input.length || nx < 0) {
            return 0;
        }
        if (ny >= input[nx].length() || ny < 0) {
            return 0;
        }
        return searchXmax(switch (toMatch) {
            case 'S' -> 'X';
            case 'X' -> 'M';
            case 'M' -> 'A';
            case 'A' -> 'S';
            default -> throw new IllegalArgumentException("Illegal argument: " + toMatch);
        }, nx, ny, dx, dy);
    }

}
