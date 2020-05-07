package de.pioneo.pioutils;

import java.util.ArrayList;
import java.util.HashSet;

public class PioListUtils {

    /**
     * This method removes Duplicated Entries from a List and returns a non duplicated List
     *
     * @param arrayList given Arraylist
     * @return returns List with no duplicated entries
     */
    public static ArrayList<Object> removeDuplicatedEntries(ArrayList<Object> arrayList) {
        HashSet<Object> hashSet = new HashSet<>(arrayList);
        arrayList.clear();
        arrayList.addAll(hashSet);

        return arrayList;
    }
}
