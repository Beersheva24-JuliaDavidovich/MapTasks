package telran.collections;

import java.util.*;

public class MapTasks {
public static void displayOccurances(String[] strings) {
    // input {"lpm", "ab", "a", "c", "cb", "cb", "c", "lpm", "lpm"}
        // output:
        // lpm -> 3
        // c -> 2
        // cb -> 2
        // a -> 1
        // ab -> 1
    HashMap<String, Long> occurancesMap = getMapOccurances(strings);
    TreeMap<Long, TreeSet<String>> sortedOccurancesMap = getSortOccurancesMap(occurancesMap);
    displaySortedOccurancesMap(sortedOccurancesMap);
}

private static void displaySortedOccurancesMap(TreeMap<Long, TreeSet<String>> sortedOccurancesMap) {
    sortedOccurancesMap.forEach((occurancy, treeSet) -> 
    treeSet.forEach(s -> System.out.printf("%s -> %d \n", s, occurancy)));
}

private static TreeMap<Long, TreeSet<String>> getSortOccurancesMap(HashMap<String, Long> occurancesMap) {
    TreeMap<Long, TreeSet<String>> result = new TreeMap<>(Comparator.reverseOrder());
    occurancesMap.entrySet().forEach(e -> result.computeIfAbsent(e.getValue(), 
    k -> new TreeSet<>()).add(e.getKey()));
    return result;
}

private static HashMap<String, Long> getMapOccurances(String[] strings) {
    HashMap<String, Long> result = new HashMap<>();
    Arrays.stream(strings).forEach(s -> result.merge(s, 1l, Long::sum));
    return result; 
}
}
