package telran.collections;

import org.junit.jupiter.api.Test;

public class MapTasksTest {
@Test
void displayOccurancesTest() {
    String[] strings = {"lpm", "ab", "a", "c", "cb", "cb", "c", "lpm", "lpm"};
    MapTasks.displayOccurances(strings);
}
}
