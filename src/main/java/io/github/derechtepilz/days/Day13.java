package io.github.derechtepilz.days;

import io.github.derechtepilz.Common;

import java.util.Arrays;
import java.util.List;

public class Day13 extends Common {

    private final List<Game> games = Arrays.stream(read("day13.txt").split("\n\n")).map(s -> {
        String[] stringArrGame = s.split("\n");
        long aX = Long.parseLong(stringArrGame[0].substring(stringArrGame[0].indexOf('X') + 1, stringArrGame[0].indexOf(',')));
        long aY = Long.parseLong(stringArrGame[0].substring(stringArrGame[0].indexOf('Y') + 1));

        long bX = Long.parseLong(stringArrGame[1].substring(stringArrGame[1].indexOf('X') + 1, stringArrGame[1].indexOf(',')));
        long bY = Long.parseLong(stringArrGame[1].substring(stringArrGame[1].indexOf('Y') + 1));

        long pX = Long.parseLong(stringArrGame[2].substring(stringArrGame[2].indexOf('X') + 2, stringArrGame[2].indexOf(',')));
        long pY = Long.parseLong(stringArrGame[2].substring(stringArrGame[2].indexOf('Y') + 2));

        Pair<Long> a = new Pair<>(aX, aY);
        Pair<Long> b = new Pair<>(bX, bY);
        Pair<Long> p = new Pair<>(pX, pY);

        return new Game(a, b, p);
    }).toList();

    @Override
    public void solve(boolean part2) {
        if (!part2) {
            long result = calculateRequiredCoins();
            System.out.println("Part 1: " + result);
        } else {
            long result = calculateMoreRequiredCoins();
            System.out.println("Part 2: " + result);
        }
    }

    private long calculateRequiredCoins() {
        long result = 0;
        for (Game game : games) {
            boolean found = false;
            for (int left = 0; left <= 100; left++) {
                for (int right = 0; right <= 100; right++) {
                    if (left * game.buttonA.left() + right * game.buttonB.left() == game.prize.left()
                        && left * game.buttonA.right() + right * game.buttonB.right() == game.prize.right()) {
                        result += left * 3 + right;
                        found = true;
                        break;
                    }
                }
                if (found) {
                    break;
                }
            }
        }
        return result;
    }

    private long calculateMoreRequiredCoins() {
        // https://www.reddit.com/r/adventofcode/comments/1hd7irq/2024_day_13_an_explanation_of_the_mathematics/
        long result = 0;
        for (Game game : games) {
            long priceX = game.prize.left() + 10000000000000L;
            long priceY = game.prize.right() + 10000000000000L;

            long det = (game.buttonA.left() * game.buttonB.right() - game.buttonA.right() * game.buttonB.left());

            long aFactor = (priceX * game.buttonB.right() - priceY * game.buttonB.left()) / det;
            long bFactor = (game.buttonA.left() * priceY - game.buttonA.right() * priceX) / det;

            if (aFactor * game.buttonA.left() + bFactor * game.buttonB.left() == priceX
                && aFactor * game.buttonA.right() + bFactor * game.buttonB.right() == priceY) {
                result += aFactor * 3 + bFactor;
            }
        }
        return result;
    }

    private record Game(Pair<Long> buttonA, Pair<Long> buttonB, Pair<Long> prize) {
        @Override
        public String toString() {
            return "Game={" +
                "buttonA=" + buttonA +
                ", buttonB=" + buttonB +
                ", prize=" + prize + "}";
        }
    }

}
