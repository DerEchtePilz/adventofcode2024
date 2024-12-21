package io.github.derechtepilz;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Common {

    public static class Node {
        private final int x;
        private final int y;
        private long costs = Long.MAX_VALUE;
        private Node from;

        public Node(final int x, final int y) {
            this.x = x;
            this.y = y;
        }

        public int x() {
            return x;
        }

        public int y() {
            return y;
        }

        public long costs() {
            return costs;
        }

        public void costsFrom(long costs, Node from) {
            this.costs = costs;
            this.from = from;
        }

        public Node from() {
            return from;
        }

        public boolean isNeighbour(Node target) {
            if (target.x + 1 == x && target.y == y) {
                return true;
            }
            if (target.x - 1 == x && target.y == y) {
                return true;
            }
            if (target.x == x && target.y + 1 == y) {
                return true;
            }
            if (target.x == x && target.y - 1 == y) {
                return true;
            }
            return false;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Node other)) {
                return false;
            }
            return x == other.x && y == other.y && costs == other.costs;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Node{" + "x=" + x + ", y=" + y + ", costs=" + costs + '}';//", from=" + from + '}';
        }
    }

    public static class Pair<T> implements Cloneable {

        private T left;
        private T right;

        public Pair(T left, T right) {
            this.left = left;
            this.right = right;
        }

        public T left() {
            return left;
        }

        public T right() {
            return right;
        }

        public void left(T newLeft) {
            this.left = newLeft;
        }

        public void right(T newRight) {
            this.right = newRight;
        }

        public int lengthi() {
            if (!(left instanceof Number l && right instanceof Number r)) {
                throw new UnsupportedOperationException("This method is only available for numeric pairs!");
            }
            int length = 0;
            if (!(l instanceof Integer li && r instanceof Integer ri)) {
                throw new UnsupportedOperationException("This method is only available for integer pairs!");
            }
            for (int i = li; i < ri; i++) {
                length++;
            }
            return length;
        }

        public boolean isInBounds(int xSize, int ySize) {
            if (!(left instanceof Integer x)) return false;
            if (!(right instanceof Integer y)) return false;
            return x >= 0 && x < xSize && y >= 0 && y < ySize;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Pair<?> other)) {
                return false;
            }
            return Objects.equals(other.left, left) && Objects.equals(other.right, right);
        }

        @Override
        public int hashCode() {
            return Objects.hash(left, right);
        }

        @Override
        public String toString() {
            return "Pair[left=" + left + ", right=" + right + "]";
        }

        @Override
        public Pair<T> clone() {
            try {
                return (Pair<T>) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public abstract void solve(boolean part2);

    public String read(String fileName) {
        try {;
            BufferedReader reader = new BufferedReader(new FileReader("resources/" + fileName));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            return builder.toString();
        } catch (IOException e) {
            // throw  new IllegalArgumentException("Something went wrong!");
            return null;
        }
    }

    public int getInt(char c) {
        return Integer.parseInt(String.valueOf(c));
    }

    public long getLong(char c) {
        return Long.parseLong(String.valueOf(c));
    }

    public boolean isAlphaNumeric(char c) {
        return 'a' <= c && c <= 'z' || 'A' <= c && c <= 'Z' || '0' <= c && c <= '9';
    }

}
