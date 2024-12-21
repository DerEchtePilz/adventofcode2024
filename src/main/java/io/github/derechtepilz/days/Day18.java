package io.github.derechtepilz.days;

import io.github.derechtepilz.Common;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day18 extends Common {

    private final int mapSize = 71;
    private final int fallenBytes = 1024;

    private final char[][] map = new char[mapSize][mapSize];
    private final List<Pair<Integer>> bytes = Arrays.stream(read("day18.txt").split("\n")).map(value -> {
        int first = Integer.parseInt(value.split(",")[0].strip());
        int second = Integer.parseInt(value.split(",")[1].strip());
        return new Pair<>(second, first);
    }).toList();

    @Override
    public void solve(boolean part2) {
        if (!part2) {
            long result = simulate(fallenBytes).costs();
            System.out.println("Part 1: " + result);
        } else {
            String result = "";
            for (int i = fallenBytes; i < bytes.size(); i++) {
                if (simulate(i + 1).from() == null) {
                    Pair<Integer> blocking = bytes.get(i);
                    result = blocking.right() + "," + blocking.left();
                    break;
                }
            }
            System.out.println("Part 2: " + result);
        }
    }

    private Node simulate(int amount) {
        for (int i = 0; i < amount; i++) {
            Pair<Integer> pair = bytes.get(i);
            map[pair.left()][pair.right()] = '#';
        }
        Set<Node> nodes = new HashSet<>();

        for (int x = 0; x < mapSize; x++) {
            for (int y = 0; y < mapSize; y++) {
                if (x == mapSize - 1 && y == mapSize - 1) {
                    continue;
                }
                if (map[x][y] != '#') {
                    nodes.add(new Node(x, y));
                }
            }
        }

        Set<Node> nextNodes = new HashSet<>();
        Set<Node> visited = new HashSet<>();
        Node start = new Node(0, 0);
        start.costsFrom(0, null);
        Node end = new Node(mapSize - 1, mapSize - 1);
        nodes.add(end);
        nextNodes.add(start);
        while (!nextNodes.isEmpty()) {
            //visualizePathSearch(nextNodes, visited);
            Node cheapest = null;
            for (Node node : nextNodes) {
                if (cheapest == null || node.costs() < cheapest.costs()) {
                    cheapest = node;
                }
            }
            nextNodes.remove(cheapest);
            nodes.remove(cheapest);
            visited.add(cheapest);

            if (cheapest.equals(end)) {
                break;
            }
            Set<Node> neighbours = new HashSet<>();
            for (Node node : nodes) {
                if (cheapest.isNeighbour(node) && !visited.contains(node)) {
                    neighbours.add(node);
                }
            }
            for (Node neighbour : neighbours) {
                if (cheapest.costs() + 1 < neighbour.costs()) {
                    neighbour.costsFrom(cheapest.costs() + 1, cheapest);
                    nextNodes.add(neighbour);
                }
            }
        }
        visualizationId = 0;
        cycle++;
        return end;
    }

    int cycle = 0;
    int visualizationId = 0;

    private void visualizePathSearch(Set<Node> nodes, Set<Node> visited) {
        char[][] visualization = new char[mapSize][mapSize];
        for (int x = 0; x < mapSize; x++) {
            System.arraycopy(map[x], 0, visualization[x], 0, mapSize);
        }
        for (Node node : visited) {
            visualization[node.x()][node.y()] = '.';
        }
        for (Node node : nodes) {
            visualization[node.x()][node.y()] = 'C';
        }
        BufferedImage image = new BufferedImage(visualization[0].length * 100, visualization.length * 100, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < visualization.length; x++) {
            for (int y = 0; y < visualization[0].length; y++) {
                for (int ix = x * 100; ix < (x + 1) * 100; ix++) {
                    for (int iy = y * 100; iy < (y + 1) * 100; iy++) {
                        if (visualization[x][y] == '#') {
                            image.setRGB(iy, ix, Color.BLACK.getRGB());
                        } else if (visualization[x][y] == '.') {
                            image.setRGB(iy, ix, Color.WHITE.getRGB());
                        } else if (visualization[x][y] == 'C') {
                            image.setRGB(iy, ix, Color.CYAN.getRGB());
                        } else {
                            image.setRGB(iy, ix, Color.ORANGE.getRGB());
                        }
                    }
                }
            }
        }
        try {
            File file = new File("visualization" + cycle);
            if (!file.exists()) {
                file.mkdir();
            }
            ImageIO.write(image, "png", new File(file, "visualization" + visualizationId + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        visualizationId++;
    }

}
