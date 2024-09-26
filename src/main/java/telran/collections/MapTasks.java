package telran.collections;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static void displayOccurrencesStream(String[] strings) {
        Arrays.stream(strings).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream().sorted((e1, e2) -> {
                    int res = Long.compare(e2.getValue(), e1.getValue());
                    return res == 0 ? e1.getKey().compareTo(e2.getKey()) : res;
                }).forEach(e -> System.out.printf("%s -> %d\n", e.getKey(), e.getValue()));
    }

    private static void displaySortedOccurancesMap(TreeMap<Long, TreeSet<String>> sortedOccurancesMap) {
        sortedOccurancesMap
                .forEach((occurancy, treeSet) -> treeSet.forEach(s -> System.out.printf("%s -> %d \n", s, occurancy)));
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

    public static Map<Integer, Integer[]> getGroupingByNumberOfDigits(int[][] array) {
        // Arrays.stream(array).flatMapToInt(Arrays::stream).boxed() refactoe to
        // streamOfNumbers
        Map<Integer, List<Integer>> map = streamOfNumbers(array)
                .collect(Collectors.groupingBy(n -> (Integer) (Integer.toString(n).length())));
        return map.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()
                .toArray(Integer[]::new)));
    }

    private static Stream<Integer> streamOfNumbers(int[][] array) {
        return Arrays.stream(array).flatMapToInt(Arrays::stream).boxed();
    }

    public static Map<Integer, Long> getDistributionByNumberOfDigits(int[][] array) {

        return streamOfNumbers(array).collect(Collectors.groupingBy(n -> Integer.toString(n).length(),
                Collectors.counting()));
    }

    public static void displayDigitsDistribution() {
        // 1_000_000 random numbers from 0 to Integer.MAX_VALUE created
        // Output should contain all digits (0 - 9) with counters of occurrences
        // sorted by descending order of occurrences
        // example:
        // 1 -> <counter of occurrences>
        // 2 -> <counter of occurrences>
        // ..............
        HashMap<Character, Long> digitsCounts = new HashMap<>();
        new Random().ints().limit(1000000).forEach(number -> {
            String numberString = String.valueOf(number);
            numberString.chars().mapToObj(c -> (char) c).forEach(digit -> {
                digitsCounts.merge(digit, 1l, Long::sum);
            });
        });
        System.out.println(digitsCounts);
    }

    public static ParenthesesMaps getParenthesesMaps(Character[][] openCloseParentheses) {
        Map<Character, Character> openCloseMap = new HashMap<>();
        Map<Character, Character> closeOpenMap = new HashMap<>();
        for(Character[] pair : openCloseParentheses) {
            openCloseMap.put(pair[0], pair[1]);
            closeOpenMap.put(pair[1], pair[0]);
        }
        return new ParenthesesMaps(openCloseMap, closeOpenMap);
    }
}
