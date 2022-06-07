package isel.sisinf.grp02.presentation;

import java.util.Collections;
import java.util.Scanner;

public class Table {
    static void createTableHead() {

    }

    static void createTable(String[][] array, Scanner in) {
        System.out.println();
        if(array.length == 0) {
            System.out.println("(empty)");
            System.out.print("Press enter to continue...");
            in.nextLine();
            return;
        }
        int n_columns = array[0].length;
        for(int i = 0; i < array.length; i++) {
            int[] columnSizes = columnSizesCalculation(array, n_columns);

            for(int j = 0; j < array[i].length; j++) {
                String fittingString;
                if(array[i][j].length() < columnSizes[j] && (i == 0 || i == 2 || i == array.length - 1)) {
                    fittingString = array[i][j] + String.join("",
                            Collections.nCopies(columnSizes[j] - array[i][j].length(), "-"));
                }
                else if(array[i][j].length() < columnSizes[j]) {
                    fittingString = array[i][j] + String.join("",
                            Collections.nCopies(columnSizes[j] - array[i][j].length(), " "));
                }
                else fittingString = array[i][j];
                if(j == 0) System.out.print("| " + fittingString);
                else System.out.print(" | " + fittingString);
            }
            System.out.println(" |\n");
        }
        System.out.print("Press enter to continue...");
        in.nextLine();
    }

    private static int[] columnSizesCalculation(String[][] table, int n_columns) {
        int[] columnSizes = new int[n_columns];

        for(int i = 0; i < n_columns; i++) {
            for (String[] strings : table) {
                if (columnSizes[i] < strings[i].length()) columnSizes[i] = strings[i].length();
            }
        }

        return columnSizes;
    }
}
