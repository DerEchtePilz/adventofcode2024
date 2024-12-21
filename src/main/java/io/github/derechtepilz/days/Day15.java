package io.github.derechtepilz.days;

import io.github.derechtepilz.Common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day15 extends Common {

    private final String[] input = read("day15.txt").split("\n\n");
    private final char[][] shoppingCenter = Arrays.stream(input[0].split("\n")).map(String::toCharArray).toArray(char[][]::new);
    private final char[][] biggerShoppingCenter = new char[shoppingCenter.length][shoppingCenter[0].length * 2];
    private final List<Character> moves = new ArrayList<>();

    public Day15() {
        List<char[]> instructions = Arrays.stream(input[1].split("\n")).map(String::toCharArray).toList();
        for (char[] instruction : instructions) {
            for (char c : instruction) {
                moves.add(c);
            }
        }
        for (int x = 0; x < shoppingCenter.length; x++) {
            int y = 0;
            for (char c : shoppingCenter[x]) {
                String unit = switch (c) {
                    case '#' -> "##";
                    case 'O' -> "[]";
                    case '.' -> "..";
                    case '@' -> "@.";
                    default -> throw new IllegalStateException("Unexpected value: " + c);
                };
                biggerShoppingCenter[x][y] = unit.charAt((y++) % 2);
                biggerShoppingCenter[x][y] = unit.charAt((y++) % 2);
            }
        }
    }

    @Override
    public void solve(boolean part2) {
        if (!part2) {
            long result = getBoxCoordinates(false);
            System.out.println("Part 1: " + result);
        } else {
            long result = getBoxCoordinates(true);
            System.out.println("Part 2: " + result);
        }
    }

    private int canMove = 0;

    private long getBoxCoordinates(boolean considerBiggerCenter) {
        long result = 0;
        Pair<Integer> robot = findRobot(considerBiggerCenter);
        assert robot != null;

        int currX = robot.left();
        int currY = robot.right();

        int steps = 0;
        while (steps < moves.size()) {
            if (tryMove(robot, steps, considerBiggerCenter)) {
                moveRobotMarker(currX, currY, robot, considerBiggerCenter);
                currX = robot.left();
                currY = robot.right();
                steps++;
                continue;
            }
            if (canMove == -1) {
                steps++;
                continue;
            }
            tryMoveBoxesAndRobot(robot, steps, considerBiggerCenter);
            moveRobotMarker(currX, currY, robot, considerBiggerCenter);
            currX = robot.left();
            currY = robot.right();
            steps++;
        }
        for (int x = 0; x < getShoppingCenter(considerBiggerCenter).length; x++) {
            for (int y = 0; y < getShoppingCenter(considerBiggerCenter)[x].length; y++) {
                if (!considerBiggerCenter) {
                    if (getShoppingCenter(false)[x][y] == 'O') {
                        result += 100L * x + y;
                    }
                } else {
                    if (getShoppingCenter(true)[x][y] == '[') {
                        result += 100L * x + y;
                    }
                }
            }
        }
        return result;
    }

    private void moveRobotMarker(int x, int y, Pair<Integer> current, boolean biggerCenter) {
        getShoppingCenter(biggerCenter)[x][y] = '.';
        getShoppingCenter(biggerCenter)[current.left()][current.right()] = '@';
    }

    private void tryMoveBoxesAndRobot(Pair<Integer> robot, int steps, boolean considerBigger) {
        char direction = moves.get(steps);
        int x = robot.left();
        int y = robot.right();
        switch (direction) {
            case '^' -> x = x - 1;
            case '>' -> y = y + 1;
            case 'v' -> x = x + 1;
            case '<' -> y = y - 1;
        }
        if (!canMoveBoxes(x, y, x - robot.left(), y - robot.right(), considerBigger)) {
            return;
        }
        robot.left(x);
        robot.right(y);
    }

    private boolean canMoveBoxes(int x, int y, int dx, int dy, boolean considerBigger) {
        if (!considerBigger) {
            if (getShoppingCenter(false)[x + dx][y + dy] == '#') {
                return false;
            }
            if (getShoppingCenter(false)[x + dx][y + dy] == 'O') {
                canMoveBoxes(x + dx, y + dy, dx, dy, false);
            }
            if (getShoppingCenter(false)[x + dx][y + dy] == '.') {
                getShoppingCenter(false)[x + dx][y + dy] = 'O';
                getShoppingCenter(false)[x][y] = '.';
                return true;
            }
        } else {
            if (dx == 0) {
                if (getShoppingCenter(true)[x][y + dy] == '#') {
                    return false;
                }
                if (getShoppingCenter(true)[x][y + dy] == '[' || getShoppingCenter(true)[x][y + dy] == ']') {
                    canMoveBoxes(x + dx, y + dy, dx, dy, true);
                }
                if (getShoppingCenter(true)[x][y + dy] == '.') {
                    getShoppingCenter(true)[x][y + dy] = getShoppingCenter(true)[x][y];
                    getShoppingCenter(true)[x][y] = '.';
                    return true;
                }
            } else {
                if (!canMoveTree(x, y, dx, dy)) {
                    return false;
                }
                if (getShoppingCenter(true)[x][y] == '[' && getShoppingCenter(true)[x][y + 1] == ']'
                    && (getShoppingCenter(true)[x + dx][y] == '#' || getShoppingCenter(true)[x + dx][y + 1] == '#')) {
                    return false;
                }
                if (getShoppingCenter(true)[x][y - 1] == '[' && getShoppingCenter(true)[x][y] == ']'
                    && (getShoppingCenter(true)[x + dx][y - 1] == '#' || getShoppingCenter(true)[x + dx][y] == '#')) {
                    return false;
                }
                if (getShoppingCenter(true)[x][y] == '[' && getShoppingCenter(true)[x + dx][y] == '[') {
                    boolean left = canMoveBoxes(x + dx, y + dy, dx, dy, true);
                    boolean right = canMoveBoxes(x + dx, y + 1 + dy, dx, dy, true);
                    if (!(left && right)) {
                        return false;
                    }
                }
                if (getShoppingCenter(true)[x][y] == ']' && getShoppingCenter(true)[x + dx][y] == ']') {
                    boolean right = canMoveBoxes(x + dx, y + dy, dx, dy, true);
                    boolean left = canMoveBoxes(x + dx, y - 1 + dy, dx, dy, true);
                    if (!(left && right)) {
                        return false;
                    }
                }
                if (getShoppingCenter(true)[x][y] == '[' && getShoppingCenter(true)[x + dx][y] == ']') {
                    boolean left = canMoveBoxes(x + dx, y + dy, dx, dy, true);
                    boolean right = canMoveBoxes(x + dx, y + 1 + dy, dx, dy, true);
                    if (!(left && right)) {
                        return false;
                    }
                }
                if (getShoppingCenter(true)[x][y] == ']' && getShoppingCenter(true)[x + dx][y] == '[') {
                    boolean right = canMoveBoxes(x + dx, y + dy, dx, dy, true);
                    boolean left = canMoveBoxes(x + dx, y - 1 + dy, dx, dy, true);
                    if (!(left && right)) {
                        return false;
                    }
                }
                if (getShoppingCenter(true)[x][y] == '[' && getShoppingCenter(true)[x + dx][y] == '.'
                    && getShoppingCenter(true)[x][y + 1] == ']' && getShoppingCenter(true)[x + dx][y + 1] == '.') {
                    getShoppingCenter(true)[x + dx][y] = '[';
                    getShoppingCenter(true)[x + dx][y + 1] = ']';
                    getShoppingCenter(true)[x][y] = '.';
                    getShoppingCenter(true)[x][y + 1] = '.';
                    return true;
                }
                if (getShoppingCenter(true)[x][y] == ']' && getShoppingCenter(true)[x + dx][y] == '.'
                    && getShoppingCenter(true)[x][y - 1] == '[' && getShoppingCenter(true)[x + dx][y - 1] == '.') {
                    getShoppingCenter(true)[x + dx][y] = ']';
                    getShoppingCenter(true)[x + dx][y - 1] = '[';
                    getShoppingCenter(true)[x][y] = '.';
                    getShoppingCenter(true)[x][y - 1] = '.';
                    return true;
                }
            }
        }
        return false;
    }

    private boolean canMoveTree(int x, int y, int dx, int dy) {
        if (getShoppingCenter(true)[x][y] == '[' && getShoppingCenter(true)[x][y + 1] == ']') {
            // Current point on [
            if (getShoppingCenter(true)[x + dx][y] == '.' && getShoppingCenter(true)[x + dx][y + 1] == '.') {
                return true;
            }
            boolean left = getShoppingCenter(true)[x + dx][y + dy] == '.' || canMoveTree(x + dx, y + dy, dx, dy);
            boolean right = getShoppingCenter(true)[x + dx][y + 1 + dy] == '.' || canMoveTree(x + dx, y + 1 + dy, dx, dy);
            return left && right;
        } else if (getShoppingCenter(true)[x][y] == ']' && getShoppingCenter(true)[x][y - 1] == '[') {
            // Current point on ]
            if (getShoppingCenter(true)[x + dx][y] == '.' && getShoppingCenter(true)[x + dx][y - 1] == '.') {
                return true;
            }
            boolean left = getShoppingCenter(true)[x + dx][y - 1 + dy] == '.' || canMoveTree(x + dx, y - 1 + dy, dx, dy);
            boolean right = getShoppingCenter(true)[x + dx][y + dy] == '.' || canMoveTree(x + dx, y + dy, dx, dy);
            return left && right;
        } else if (getShoppingCenter(true)[x][y] == '.' && getShoppingCenter(true)[x][y + 1] == '.'
            || getShoppingCenter(true)[x][y] == '.' && getShoppingCenter(true)[x][y - 1] == '.') {
            return true;
        } else if (getShoppingCenter(true)[x][y - 1] == '#' || getShoppingCenter(true)[x][y] == '#' || getShoppingCenter(true)[x][y + 1] == '#') {
            return false;
        }
        throw new IllegalStateException("Unexpected figure in grid: " + getShoppingCenter(true)[x][y - 1] + getShoppingCenter(true)[x][y] + getShoppingCenter(true)[x][y + 1]);
    }

    private boolean tryMove(Pair<Integer> robot, int steps, boolean biggerCenter) {
        char nextMove = moves.get(steps);
        return switch (nextMove) {
            case '^' -> {
                int dx = robot.left() - 1;
                int y = robot.right();
                setMoveState(dx, y, biggerCenter);
                if (canMove == 0) {
                    robot.left(dx);
                }
                yield canMove == 0;
            }
            case '>' -> {
                int x = robot.left();
                int dy = robot.right() + 1;
                setMoveState(x, dy, biggerCenter);
                if (canMove == 0) {
                    robot.right(dy);
                }
                yield canMove == 0;
            }
            case 'v' -> {
                int dx = robot.left() + 1;
                int y = robot.right();
                setMoveState(dx, y, biggerCenter);
                if (canMove == 0) {
                    robot.left(dx);
                }
                yield canMove == 0;
            }
            case '<' -> {
                int x = robot.left();
                int dy = robot.right() - 1;
                setMoveState(x, dy, biggerCenter);
                if (canMove == 0) {
                    robot.right(dy);
                }
                yield canMove == 0;
            }
            default -> throw new IllegalArgumentException("Invalid move: " + nextMove);
        };
    }

    private void setMoveState(int x, int y, boolean biggerCenter) {
        if (!biggerCenter) {
            if (getShoppingCenter(false)[x][y] == '#') {
                canMove = -1;
            } else if (getShoppingCenter(false)[x][y] == 'O') {
                canMove = 1;
            } else {
                canMove = 0;
            }
        } else {
            if (getShoppingCenter(true)[x][y] == '#') {
                canMove = -1;
            } else if (getShoppingCenter(true)[x][y] == '[' || getShoppingCenter(true)[x][y] == ']') {
                canMove = 1;
            } else {
                canMove = 0;
            }
        }
    }

    private Pair<Integer> findRobot(boolean considerBigger) {
        for (int x = 0; x < getShoppingCenter(considerBigger).length; x++) {
            for (int y = 0; y < getShoppingCenter(considerBigger)[x].length; y++) {
                if (getShoppingCenter(considerBigger)[x][y] == '@') {
                    return new Pair<>(x, y);
                }
            }
        }
        return null;
    }

    private char[][] getShoppingCenter(boolean considerBigger) {
        if (!considerBigger) {
            return shoppingCenter;
        } else {
            return biggerShoppingCenter;
        }
    }

}
