package test;

import Implementing_Sorting_Techniques.SortArray;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SortArrayTest {
    private String filePath = "test_input.txt";
    private SortArray sa;

    private void createInputFile(List<Integer> data) {
        try {
            FileWriter writer = new FileWriter(filePath);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < data.size(); i++) {
                sb.append(data.get(i));
                if (i < data.size() - 1) {
                    sb.append(", ");
                }
            }
            writer.write(sb.toString());
            writer.close();
        } catch (IOException e) {
            System.err.println("Error creating test file: " + e.getMessage());
        }
    }

    private void deleteInputFile() {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

    private List<Integer> getFinalSortedArray(List<List<Integer>> result) {
        return result.isEmpty() ? null : result.getLast();
    }

    private long measureExecutionTime(Runnable function) {
        long startTime = System.nanoTime();
        function.run();
        long endTime = System.nanoTime();
        return endTime - startTime;
    }
    // Edge cases
    @Test
    public void testEmptyArray() {
        List<Integer> input = new ArrayList<>();
        createInputFile(input);
        sa = new SortArray(filePath);

        List<List<Integer>> simpleSortResult = sa.SimpleSort(true);
        List<List<Integer>> efficientSortResult = sa.EfficientSort(true);
        List<List<Integer>> nonComparisonSortResult = sa.NonComparisonSort(true);

        List<Integer> expected = new ArrayList<>();

        assertEquals(expected, getFinalSortedArray(simpleSortResult), "Simple Sort empty array test failed\n");
        assertEquals(expected, getFinalSortedArray(efficientSortResult), "Efficient Sort empty array test failed\n");
        assertEquals(expected, getFinalSortedArray(nonComparisonSortResult), "Non-Comparison Sort empty array test failed\n");

        deleteInputFile();
    }

    @Test
    public void testSingleElementArray() {
        List<Integer> input = Arrays.asList(42);
        createInputFile(input);
        sa = new SortArray(filePath);

        List<List<Integer>> simpleSortResult = sa.SimpleSort(true);
        List<List<Integer>> efficientSortResult = sa.EfficientSort(true);
        List<List<Integer>> nonComparisonSortResult = sa.NonComparisonSort(true);

        List<Integer> expected = Arrays.asList(42);

        assertEquals(expected, getFinalSortedArray(simpleSortResult), "Simple Sort single element test failed\n");
        assertEquals(expected, getFinalSortedArray(efficientSortResult), "Efficient Sort single element test failed\n");
        assertEquals(expected, getFinalSortedArray(nonComparisonSortResult), "Non-Comparison Sort single element test failed\n");

        deleteInputFile();
    }

    // Best, Worst, Average case tests for Simple Sort (Bubble Sort)
    @Test
    public void testSimpleSortBestCase() {
        // Best case for bubble sort: already sorted array
        List<Integer> input = new ArrayList<>();
        for(int i = 1; i <= 1000; i++) {
            input.add(i);
        }
        createInputFile(input);
        sa = new SortArray(filePath);

        long executionTime1 = measureExecutionTime(() -> sa.SimpleSort(true));
        long executionTime2 = measureExecutionTime(() -> sa.EfficientSort(true));
        long executionTime3 = measureExecutionTime(() -> sa.NonComparisonSort(true));

        System.out.println("for simple sort (bubble sort) best case:");
        System.out.println("Simple Sort (bubble sort) Execution Time: " + executionTime1 + " ns");
        System.out.println("Efficient Sort (merge sort) Execution Time: " + executionTime2 + " ns");
        System.out.println("Non-Comparison Sort (radix sort) Execution Time: " + executionTime3 + " ns\n");

        deleteInputFile();
    }

    @Test
    public void testSimpleSortWorstCase() {
        // Worst case for bubble sort: reverse sorted array
        List<Integer> input = new ArrayList<>();
        for(int i = 1000; i >= 1; i--) {
            input.add(i);
        }
        createInputFile(input);
        sa = new SortArray(filePath);

        long executionTime1 = measureExecutionTime(() -> sa.SimpleSort(true));
        long executionTime2 = measureExecutionTime(() -> sa.EfficientSort(true));
        long executionTime3 = measureExecutionTime(() -> sa.NonComparisonSort(true));

        System.out.println("for simple sort (bubble sort) worst case:");
        System.out.println("Simple Sort (bubble sort) Execution Time: " + executionTime1 + " ns");
        System.out.println("Efficient Sort (merge sort) Execution Time: " + executionTime2 + " ns");
        System.out.println("Non-Comparison Sort (radix sort) Execution Time: " + executionTime3 + " ns\n");

        deleteInputFile();
    }

    @Test
    public void testSimpleSortAverageCase() {
        // Average case: randomly ordered array
        List<Integer> input = new ArrayList<>();
        for(int i = 1; i <= 1000; i++) {
            input.add((int) (Math.random() * 1000) + 1);
        }
        createInputFile(input);
        sa = new SortArray(filePath);

        long executionTime1 = measureExecutionTime(() -> sa.SimpleSort(true));
        long executionTime2 = measureExecutionTime(() -> sa.EfficientSort(true));
        long executionTime3 = measureExecutionTime(() -> sa.NonComparisonSort(true));

        System.out.println("for simple sort (bubble sort) average case:");
        System.out.println("Simple Sort (bubble sort) Execution Time: " + executionTime1 + " ns");
        System.out.println("Efficient Sort (merge sort) Execution Time: " + executionTime2 + " ns");
        System.out.println("Non-Comparison Sort (radix sort) Execution Time: " + executionTime3 + " ns\n");

        deleteInputFile();
    }

    // Best, Worst, Average case tests for Efficient Sort (Merge Sort)
    // There's no true "best case" for merge sort as it's O(n log n) regardless
    @Test
    public void testEfficientSortBestCase() {
        // but using a sorted array for consistency
        System.out.println("There's no true \"best/worst case\" for merge sort as it's O(n log n) regardless" +
                           "\nbut for consistency:");
        List<Integer> input = new ArrayList<>();
        for(int i = 1; i <= 1000; i++) {
            input.add(i);
        }
        createInputFile(input);
        sa = new SortArray(filePath);

        long executionTime1 = measureExecutionTime(() -> sa.SimpleSort(true));
        long executionTime2 = measureExecutionTime(() -> sa.EfficientSort(true));
        long executionTime3 = measureExecutionTime(() -> sa.NonComparisonSort(true));

        System.out.println("for Efficient sort (merge sort) best case:");
        System.out.println("Simple Sort (bubble sort) Execution Time: " + executionTime1 + " ns");
        System.out.println("Efficient Sort (merge sort) Execution Time: " + executionTime2 + " ns");
        System.out.println("Non-Comparison Sort (radix sort) Execution Time: " + executionTime3 + " ns\n");

        deleteInputFile();

    }

    @Test
    public void testEfficientSortWorstCase() {
        // but using a reverse sorted array for consistency
        System.out.println("There's no true \"best/worst case\" for merge sort as it's O(n log n) regardless" +
                "\nbut for consistency:");
        List<Integer> input = new ArrayList<>();
        for(int i = 1000; i >= 0; i--) {
            input.add(i);
        }
        createInputFile(input);
        sa = new SortArray(filePath);

        long executionTime1 = measureExecutionTime(() -> sa.SimpleSort(true));
        long executionTime2 = measureExecutionTime(() -> sa.EfficientSort(true));
        long executionTime3 = measureExecutionTime(() -> sa.NonComparisonSort(true));

        System.out.println("for Efficient sort (merge sort) worst case:");
        System.out.println("Simple Sort (bubble sort) Execution Time: " + executionTime1 + " ns");
        System.out.println("Efficient Sort (merge sort) Execution Time: " + executionTime2 + " ns");
        System.out.println("Non-Comparison Sort (radix sort) Execution Time: " + executionTime3 + " ns\n");

        deleteInputFile();
    }

    @Test
    public void testEfficientSortAverageCase() {
        System.out.println("There's no true \"best/worst case\" for merge sort as it's O(n log n) regardless" +
                "\nbut for consistency:");
        // Average case: randomly ordered array
        List<Integer> input = new ArrayList<>();
        for(int i = 1; i <= 1000; i++) {
            input.add((int) (Math.random() * 1000) + 1);
        }
        createInputFile(input);
        sa = new SortArray(filePath);

        long executionTime1 = measureExecutionTime(() -> sa.SimpleSort(true));
        long executionTime2 = measureExecutionTime(() -> sa.EfficientSort(true));
        long executionTime3 = measureExecutionTime(() -> sa.NonComparisonSort(true));

        System.out.println("for Efficient sort (merge sort) average case:");
        System.out.println("Simple Sort (bubble sort) Execution Time: " + executionTime1 + " ns");
        System.out.println("Efficient Sort (merge sort) Execution Time: " + executionTime2 + " ns");
        System.out.println("Non-Comparison Sort (radix sort) Execution Time: " + executionTime3 + " ns\n");

        deleteInputFile();
    }

    // Best, Worst, Average case tests for Non-Comparison Sort (Radix Sort)
    @Test
    public void testNonComparisonBestSortCase() {
        // Best case for radix sort: small digit numbers with same number of digits
        List<Integer> input = new ArrayList<>();
        for(int i = 1; i <= 1000; i++) {
            input.add((int) (Math.random() * 10) - 5);
        }
        createInputFile(input);
        sa = new SortArray(filePath);

        long executionTime1 = measureExecutionTime(() -> sa.SimpleSort(true));
        long executionTime2 = measureExecutionTime(() -> sa.EfficientSort(true));
        long executionTime3 = measureExecutionTime(() -> sa.NonComparisonSort(true));

        System.out.println("for Non-Comparison sort (radix sort) best case:");
        System.out.println("Simple Sort (bubble sort) Execution Time: " + executionTime1 + " ns");
        System.out.println("Efficient Sort (merge sort) Execution Time: " + executionTime2 + " ns");
        System.out.println("Non-Comparison Sort (radix sort) Execution Time: " + executionTime3 + " ns\n");

        deleteInputFile();
    }

    @Test
    public void testNonComparisonWorstCase() {
        // Worst case for radix sort: numbers with widely varying digit counts
        List<Integer> input = new ArrayList<>();
        for(int i = 1; i <= 1000; i++) {
            input.add((int) (Math.random() * 100000000) - 50000000);
        }
        createInputFile(input);
        sa = new SortArray(filePath);

        long executionTime1 = measureExecutionTime(() -> sa.SimpleSort(true));
        long executionTime2 = measureExecutionTime(() -> sa.EfficientSort(true));
        long executionTime3 = measureExecutionTime(() -> sa.NonComparisonSort(true));

        System.out.println("for Non-Comparison sort (radix sort) worst case:");
        System.out.println("Simple Sort (bubble sort) Execution Time: " + executionTime1 + " ns");
        System.out.println("Efficient Sort (merge sort) Execution Time: " + executionTime2 + " ns");
        System.out.println("Non-Comparison Sort (radix sort) Execution Time: " + executionTime3 + " ns\n");

        deleteInputFile();
    }

    @Test
    public void testNonComparisonAverageCase() {
        // Average case: randomly ordered array with mixed digit counts
        List<Integer> input = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            input.add((int) (Math.random() * 1000) - 500);
        }
        createInputFile(input);
        sa = new SortArray(filePath);

        long executionTime1 = measureExecutionTime(() -> sa.SimpleSort(true));
        long executionTime2 = measureExecutionTime(() -> sa.EfficientSort(true));
        long executionTime3 = measureExecutionTime(() -> sa.NonComparisonSort(true));

        System.out.println("for Non-Comparison sort (radix sort) average case:");
        System.out.println("Simple Sort (bubble sort) Execution Time: " + executionTime1 + " ns");
        System.out.println("Efficient Sort (merge sort) Execution Time: " + executionTime2 + " ns");
        System.out.println("Non-Comparison Sort (radix sort) Execution Time: " + executionTime3 + " ns\n");

        deleteInputFile();
    }

    // Performance comparison tests with larger arrays
    @Test
    public void testPerformance1() {
        // Generate a large random array
        List<Integer> input = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            input.add((int)(Math.random() * 10000) - 5000); // Mix of positive and negative numbers
        }
        createInputFile(input);

        // Create 3 identical copies of the SortArray object
        SortArray saSimple = new SortArray(filePath);
        SortArray saEfficient = new SortArray(filePath);
        SortArray saNonComparison = new SortArray(filePath);

        // Measure execution times
        long simpleSortTime = measureExecutionTime(() -> saSimple.SimpleSort(true));
        long efficientSortTime = measureExecutionTime(() -> saEfficient.EfficientSort(true));
        long nonComparisonSortTime = measureExecutionTime(() -> saNonComparison.NonComparisonSort(true));

        System.out.println("Performance Comparison with 1000 random elements (-5000 to 5000):");
        System.out.println("Simple Sort Time: " + simpleSortTime + " ns");
        System.out.println("Efficient Sort Time: " + efficientSortTime + " ns");
        System.out.println("Non-Comparison Sort Time: " + nonComparisonSortTime + " ns\n");

        deleteInputFile();
    }

    @Test
    public void testPerformance2() {
        // Generate a large random array
        List<Integer> input = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            input.add((int)(Math.random() * 10000) - 5000); // Mix of positive and negative numbers
        }
        createInputFile(input);

        // Create 3 identical copies of the SortArray object
        SortArray saSimple = new SortArray(filePath);
        SortArray saEfficient = new SortArray(filePath);
        SortArray saNonComparison = new SortArray(filePath);

        // Measure execution times
        long simpleSortTime = measureExecutionTime(() -> saSimple.SimpleSort(true));
        long efficientSortTime = measureExecutionTime(() -> saEfficient.EfficientSort(true));
        long nonComparisonSortTime = measureExecutionTime(() -> saNonComparison.NonComparisonSort(true));

        System.out.println("Performance Comparison with 10000 random elements (-5000 to 5000):");
        System.out.println("Simple Sort Time: " + simpleSortTime + " ns");
        System.out.println("Efficient Sort Time: " + efficientSortTime + " ns");
        System.out.println("Non-Comparison Sort Time: " + nonComparisonSortTime + " ns\n");

        deleteInputFile();
    }

    @Test
    public void testPerformance3() {
        // Generate a large random array
        List<Integer> input = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            input.add((int)(Math.random() * 100000000) - 50000000); // Mix of positive and negative numbers
        }
        createInputFile(input);

        // Create 3 identical copies of the SortArray object
        SortArray saSimple = new SortArray(filePath);
        SortArray saEfficient = new SortArray(filePath);
        SortArray saNonComparison = new SortArray(filePath);

        // Measure execution times
        long simpleSortTime = measureExecutionTime(() -> saSimple.SimpleSort(true));
        long efficientSortTime = measureExecutionTime(() -> saEfficient.EfficientSort(true));
        long nonComparisonSortTime = measureExecutionTime(() -> saNonComparison.NonComparisonSort(true));

        System.out.println("Performance Comparison with 1000 random elements(-50000000 to 50000000):");
        System.out.println("Simple Sort Time: " + simpleSortTime + " ns");
        System.out.println("Efficient Sort Time: " + efficientSortTime + " ns");
        System.out.println("Non-Comparison Sort Time: " + nonComparisonSortTime + " ns\n");

        deleteInputFile();
    }

    @Test
    public void testPerformance4() {
        // Generate a large random array
        List<Integer> input = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            input.add((int)(Math.random() * 10) - 5); // Mix of positive and negative numbers
        }
        createInputFile(input);

        // Create 3 identical copies of the SortArray object
        SortArray saSimple = new SortArray(filePath);
        SortArray saEfficient = new SortArray(filePath);
        SortArray saNonComparison = new SortArray(filePath);

        // Measure execution times
        long simpleSortTime = measureExecutionTime(() -> saSimple.SimpleSort(true));
        long efficientSortTime = measureExecutionTime(() -> saEfficient.EfficientSort(true));
        long nonComparisonSortTime = measureExecutionTime(() -> saNonComparison.NonComparisonSort(true));

        System.out.println("Performance Comparison with 10000 random elements (-5 to 5):");
        System.out.println("Simple Sort Time: " + simpleSortTime + " ns");
        System.out.println("Efficient Sort Time: " + efficientSortTime + " ns");
        System.out.println("Non-Comparison Sort Time: " + nonComparisonSortTime + " ns\n");

        deleteInputFile();
    }
    @Test
    public void testPerformance5() {
        // Generate a nearly sorted array (good for bubble sort)
        List<Integer> input = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            input.add(i);
        }
        // Swap a few elements to make it nearly sorted
        for (int i = 0; i < 100; i++) {
            int pos1 = (int)(Math.random() * 5000);
            int pos2 = (int)(Math.random() * 5000);
            Collections.swap(input, pos1, pos2);
        }
        createInputFile(input);

        // Create 3 identical copies of the SortArray object
        SortArray saSimple = new SortArray(filePath);
        SortArray saEfficient = new SortArray(filePath);
        SortArray saNonComparison = new SortArray(filePath);

        // Measure execution times
        long simpleSortTime = measureExecutionTime(() -> saSimple.SimpleSort(true));
        long efficientSortTime = measureExecutionTime(() -> saEfficient.EfficientSort(true));
        long nonComparisonSortTime = measureExecutionTime(() -> saNonComparison.NonComparisonSort(true));

        System.out.println("Performance Comparison with 5000 nearly sorted elements:");
        System.out.println("Simple Sort Time: " + simpleSortTime + " ns");
        System.out.println("Efficient Sort Time: " + efficientSortTime + " ns");
        System.out.println("Non-Comparison Sort Time: " + nonComparisonSortTime + " ns\n");

        deleteInputFile();
    }

    @Test
    public void testPerformance6() {
        // Generate an array with many duplicates (good for radix sort)
        List<Integer> input = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            input.add((int)(Math.random() * 100) - 50); // Only 100 possible values
        }
        createInputFile(input);

        // Create 3 identical copies of the SortArray object
        SortArray saSimple = new SortArray(filePath);
        SortArray saEfficient = new SortArray(filePath);
        SortArray saNonComparison = new SortArray(filePath);

        // Measure execution times
        long simpleSortTime = measureExecutionTime(() -> saSimple.SimpleSort(true));
        long efficientSortTime = measureExecutionTime(() -> saEfficient.EfficientSort(true));
        long nonComparisonSortTime = measureExecutionTime(() -> saNonComparison.NonComparisonSort(true));

        System.out.println("Performance Comparison with 10000 elements with many duplicates (-50 to 50):");
        System.out.println("Simple Sort Time: " + simpleSortTime + " ns");
        System.out.println("Efficient Sort Time: " + efficientSortTime + " ns");
        System.out.println("Non-Comparison Sort Time: " + nonComparisonSortTime + " ns\n");

        deleteInputFile();
    }

    @Test
    public void testPerformance7() {
        // Generate an array with large numbers (many digits, challenging for radix sort)
        List<Integer> input = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            input.add((int)(Math.random() * Integer.MAX_VALUE)); // Large positive integers
        }
        createInputFile(input);

        // Create 3 identical copies of the SortArray object
        SortArray saSimple = new SortArray(filePath);
        SortArray saEfficient = new SortArray(filePath);
        SortArray saNonComparison = new SortArray(filePath);

        // Measure execution times
        long simpleSortTime = measureExecutionTime(() -> saSimple.SimpleSort(true));
        long efficientSortTime = measureExecutionTime(() -> saEfficient.EfficientSort(true));
        long nonComparisonSortTime = measureExecutionTime(() -> saNonComparison.NonComparisonSort(true));

        System.out.println("Performance Comparison with 5000 large integer elements:");
        System.out.println("Simple Sort Time: " + simpleSortTime + " ns");
        System.out.println("Efficient Sort Time: " + efficientSortTime + " ns");
        System.out.println("Non-Comparison Sort Time: " + nonComparisonSortTime + " ns\n");

        deleteInputFile();
    }

    @Test
    public void testPerformance8() {
        // Generate an array with a mix of positive and negative numbers with varying digit counts
        List<Integer> input = new ArrayList<>();
        for (int i = 0; i < 3000; i++) {
            int magnitude = (int) Math.pow(10, (int)(Math.random() * 6)); // 1, 10, 100, 1000, 10000, or 100000
            input.add((int)(Math.random() * magnitude) * (Math.random() > 0.5 ? 1 : -1));
        }
        createInputFile(input);

        // Create 3 identical copies of the SortArray object
        SortArray saSimple = new SortArray(filePath);
        SortArray saEfficient = new SortArray(filePath);
        SortArray saNonComparison = new SortArray(filePath);

        // Measure execution times
        long simpleSortTime = measureExecutionTime(() -> saSimple.SimpleSort(true));
        long efficientSortTime = measureExecutionTime(() -> saEfficient.EfficientSort(true));
        long nonComparisonSortTime = measureExecutionTime(() -> saNonComparison.NonComparisonSort(true));

        System.out.println("Performance Comparison with 3000 mixed-magnitude elements:");
        System.out.println("Simple Sort Time: " + simpleSortTime + " ns");
        System.out.println("Efficient Sort Time: " + efficientSortTime + " ns");
        System.out.println("Non-Comparison Sort Time: " + nonComparisonSortTime + " ns\n");

        deleteInputFile();
    }
}
