package io.github.derechtepilz.days;

import io.github.derechtepilz.Common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day2 extends Common {

    private final String input = read("day2.txt");

    @Override
    public void solve(boolean part2) {
        if (input == null) {
            return;
        }
        List<String> rawReports = new ArrayList<>(Arrays.asList(input.split("\n")));
        rawReports.removeIf(String::isEmpty);

        List<List<Integer>> reports = new ArrayList<>();
        for (String line : rawReports) {
            String[] parts = line.split(" ");
            List<Integer> report = new ArrayList<>();
            for (int i = 0; i < parts.length; i++) {
                report.add(Integer.parseInt(parts[i]));
            }
            reports.add(report);
        }

        int safeReports = countSafeReports(reports, part2);
        System.out.println((!part2 ? "Part 1: " : "Part 2: ") + safeReports);
    }

    private int countSafeReports(List<List<Integer>> reports, boolean withProblemDampener) {
        int unsafeReports = 0;
        if (withProblemDampener) {
            for (List<Integer> report : reports) {
                boolean containsValid = containsValid(report);
                unsafeReports = containsValid ? unsafeReports : unsafeReports + 1;
            }
        } else {
            for (List<Integer> report : reports) {
                unsafeReports = safe(report) ? unsafeReports : unsafeReports + 1;
            }
        }
        return reports.size() - unsafeReports;
    }

    private boolean containsValid(List<Integer> report) {
        int indexToRemove = 0;
        boolean containsValid = false;
        while (indexToRemove < report.size()) {
            List<Integer> modifiedReport = new ArrayList<>();
            for (int i = 0; i < report.size(); i++) {
                if (i == indexToRemove) {
                    continue;
                }
                modifiedReport.add(report.get(i));
            }
            containsValid = safe(modifiedReport) || containsValid;
            indexToRemove++;
        }
        return containsValid;
    }

    private boolean safe(List<Integer> report) {
        int up = 0;
        int down = 0;
        int previousReport = -1;
        for (int reportData : report) {
            if (previousReport == -1) {
                previousReport = reportData;
                continue;
            }
            int difference = Math.abs(reportData - previousReport);
            if (difference < 1 || difference > 3) {
                return false;
            }
            if (reportData == previousReport) {
                return false;
            }
            if (reportData > previousReport) {
                up++;
            }
            if (reportData < previousReport) {
                down++;
            }
            if (up >= 1 && down >= 1) {
                return false;
            }
            previousReport = reportData;
        }
        return true;
    }

}
