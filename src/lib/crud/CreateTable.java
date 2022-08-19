package lib.crud;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CreateTable {
    private static final String HORIZONTAL_SEPARATOR = "-";
    private static String verticalSeparator;
    private static String joinSeparator;
    private static String[] headers;
    private static ArrayList<String[]> rows = new ArrayList<>();
    private static boolean rightAlign;

    public CreateTable() {
        setShowVerticalLines(false);
    }

    public void setRightAlign(boolean rightAlign) {
        this.rightAlign = rightAlign;
    }

    public static void setShowVerticalLines(boolean showVerticalLines) {
        verticalSeparator = showVerticalLines ? "|" : "";
        joinSeparator = showVerticalLines ? "+" : " ";
    }

    public static void setHeaders(String... headers) {
        CreateTable.headers = headers;
    }

    public static void addRow(String... cells) {
        rows.add(cells);
    }

    public static ArrayList<String[]> render() {
        int[] maxWidths = headers != null ?
                Arrays.stream(headers).mapToInt(String::length).toArray() : null;

        for (String[] cells : rows) {
            if (maxWidths == null) {
                maxWidths = new int[cells.length];
            }
            if (cells.length != maxWidths.length) {
                throw new IllegalArgumentException("Sorry, the number of row-cells and headers should be consistent ");
            }
            for (int i = 0; i < cells.length; i++) {
                maxWidths[i] = Math.max(maxWidths[i], cells[i].length());
            }
        }

        if (headers != null) {
            printLine(maxWidths);
            printRow(headers, maxWidths);
            printLine(maxWidths);
        }
        for (String[] cells : rows) {
            printRow(cells, maxWidths);
        }
        if (headers != null) {
            printLine(maxWidths);
        }

        return rows;
    }

    private static void printLine(int[] columnWidths) {
        for (int i = 0; i < columnWidths.length; i++) {
            String line = String.join("", Collections.nCopies(columnWidths[i] +
                    verticalSeparator.length() + 1, HORIZONTAL_SEPARATOR));
            System.out.print(joinSeparator + line + (i == columnWidths.length - 1 ? joinSeparator : ""));
        }
        System.out.println();
    }

    private static void printRow(String[] cells, int[] maxWidths) {
        for (int i = 0; i < cells.length; i++) {
            String s = cells[i];
            String verStrTemp = i == cells.length - 1 ? verticalSeparator : "";
            if (rightAlign) {
                System.out.printf("%s %" + maxWidths[i] + "s %s", verticalSeparator, s, verStrTemp);
            } else {
                System.out.printf("%s %-" + maxWidths[i] + "s %s", verticalSeparator, s, verStrTemp);
            }
        }
        System.out.println();
    }

    public static String[] getHeaders() {
        return headers;
    }

    public static ArrayList<String[]> getRows() {
        return rows;
    }

    public static void setRows(ArrayList<String[]> rows) {
        CreateTable.rows = rows;
    }
}
