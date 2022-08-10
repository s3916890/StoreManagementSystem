package lib.algorithm.sort;

import java.util.ArrayList;
import java.util.Collections;

public class ArrayListSorting {
    public static ArrayList<Long> sortAscending(ArrayList<Long> list){
        Collections.sort(list);

        return list;
    }

    public static ArrayList<Long> sortDescending(ArrayList<Long> list){
        list.sort(Collections.reverseOrder());

        return list;
    }
}
