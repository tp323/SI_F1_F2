package isel.sisinf.grp02.presentation;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;

import java.lang.reflect.Field;
import java.util.*;

interface ArrayConvertFunction<T> {
    String[] toArray(T obj);
}

public class Table {
    private static String[][] makeInfoArray(int n_columns, String[] head, String[][] objs) {
        String[][] array = new String[3][];
        int i = 1;
        array[i] = head;
        i += 2;
        for(int l = 0; l <= 2; l++) {
            if(l != 1) {
                array[l] = new String[n_columns];
                for (int j = 0; j < n_columns; j++) {
                    array[l][j] = "-";
                }
            }
        }
        for (String[] obj : objs) {
            if (i >= array.length) array = expandArray2D(array);
            array[i] = obj;
            i++;
        }

        array = expandArray2D(array);
        array[i] = new String[n_columns];
        for(int j = 0; j < n_columns; j++) {
            array[i][j] = "-";
        }
        return array;
    }

    private static <T> String[][] makeInfoArray(int n_columns, List<T> objs, ArrayConvertFunction<T> func, Field[] objFields) {
        String[][] array = new String[3][];
        int i = 1;
        array[i] = new String[n_columns];
        for(int j = 0; j < n_columns; j++) {
            array[i][j] = objFields[j].getName();
        }
        i += 2;
        for(int l = 0; l <= 2; l++) {
            if(l != 1) {
                array[l] = new String[n_columns];
                for (int j = 0; j < n_columns; j++) {
                    array[l][j] = "-";
                }
            }
        }
        for (T obj : objs) {
            if (i >= array.length) array = expandArray2D(array);
            array[i] = func.toArray(obj);
            i++;
        }

        array = expandArray2D(array);
        array[i] = new String[n_columns];
        for(int j = 0; j < n_columns; j++) {
            array[i][j] = "-";
        }
        return array;
    }

    private static String[][] expandArray2D(String[][] array) {
        String[][] newArray = new String[array.length + 1][];

        for(int i = 0; i < array.length; i++) {
            newArray[i] = new String[array[i].length];
            System.arraycopy(array[i], 0, newArray[i], 0, array[i].length);
        }

        return newArray;
    }

    private static Field[] expandArray(Field[] array) {
        Field[] newArray = new Field[array.length + 1];

        System.arraycopy(array, 0, newArray, 0, array.length);

        return newArray;
    }

    static <T> void createTable(List<T> objs, Scanner in, ArrayConvertFunction<T> func) {
        System.out.println();
        if(objs.size() == 0 || objs.get(0) == null) {
            System.out.println("(empty)");
            System.out.print("Press enter to continue...");
            in.nextLine();
            return;
        }
        Field[] columnFields = getColumnFields(objs.get(0).getClass().getDeclaredFields());
        int n_columns = columnFields.length;
        String[][] array = makeInfoArray(n_columns, objs, func, columnFields);
        printTable(array, n_columns);
        System.out.print("Press enter to continue...");
        in.nextLine();
    }

    static void createTable(String[] head, Scanner in, String[][] objs) {
        System.out.println();
        if(objs == null || objs.length == 0 || head == null || head.length == 0) {
            System.out.println("(empty)");
            System.out.print("Press enter to continue...");
            in.nextLine();
            return;
        }
        int n_columns = head.length;
        String[][] array = makeInfoArray(n_columns, head, objs);
        printTable(array, n_columns);
        System.out.print("Press enter to continue...");
        in.nextLine();
    }

    private static void printTable(String[][] array, int n_columns) {
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

    private static Field[] getColumnFields(Field[] fields) {
        Field[] array = new Field[0];
        int j = 0;
        for (Field field : fields) {
            if (field.getAnnotation(Column.class) != null || field.getAnnotation(JoinColumn.class) != null) {
                if (j >= array.length) array = expandArray(array);
                array[j] = field;
                j++;
            }
        }
        return array;
    }
}