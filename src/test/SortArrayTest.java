package src.test;

import Implementing_Sorting_Techniques.SortArray;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SortArrayTest {
    private String filePath = "/media/braamostafa/Stuff/learning/engineering/year 2/semester 2/DSA/labs/lab 1/code/Implementing-Sorting-Techniques/input.txt";
    private SortArray sa;

    public SortArrayTest () {
        this.sa = new SortArray(filePath);
    }

    private List<Integer> getFinalSortedArray(List<List<Integer>> result) {
        return result.isEmpty() ? null : result.getLast();
    }

    @Test
    public void testSimpleSort() {
        List<List<Integer>> result= sa.SimpleSort(true);
        List<Integer> finalSortedArray = getFinalSortedArray(result);
        List<Integer> expected = new ArrayList<>(Arrays.asList(-15, -5, -3, 0, 7, 10, 20));
        assertEquals(expected, finalSortedArray, "Simple Sort Test Failed");
    }
    @Test
    public void testEfficientSort() {
        List<List<Integer>> result = sa.EfficientSort(true);
        List<Integer> finalSortedArray = getFinalSortedArray(result);
        List<Integer> expected = List.of(-15, -5, -3, 0, 7, 10, 20);
        assertEquals(expected, finalSortedArray, "EfficientSort Test Failed");
    }

    @Test
    public void testNonComparisonSort() {
        List<List<Integer>> result = sa.NonComparisonSort(true);
        List<Integer> finalSortedArray = getFinalSortedArray(result);
        List<Integer> expected = List.of(-15, -5, -3, 0, 7, 10, 20);
        assertEquals(expected, finalSortedArray, "NonComparisonSort Test Failed");
    }
}
