package Implementing_Sorting_Techniques;

import java.util.Arrays;

public class RadixSort {


    static void countingSort(int array[], int size, int place) {
        int[] output = new int[size];
        int[] count = new int[10];
        Arrays.fill(count, 0);

        for (int i = 0; i < size; i++)
            count[(array[i] / place) % 10]++;

        for (int i = 1; i < 10; i++)
            count[i] += count[i - 1];

        for (int i = size - 1; i >= 0; i--) {
            output[count[(array[i] / place) % 10] - 1] = array[i];
            count[(array[i] / place) % 10]--;
        }

        for (int i = 0; i < size; i++)
            array[i] = output[i];
    }


    static int getMax(int array[], int n) {
        int max = array[0];
        for (int i = 1; i < n; i++)
            if (array[i] > max)
                max = array[i];
        return max;
    }


    static int getMin(int array[], int n) {
        int min = array[0];
        for (int i = 1; i < n; i++)
            if (array[i] < min)
                min = array[i];
        return min;
    }


    static int[] radix_sort(int arr[], boolean flag) {
        int size = arr.length;
        int min = getMin(arr, size);

        if (min < 0) {
            for (int i = 0; i < size; i++)
                arr[i] += (-1 * min);
        }

        radix_sortutil(arr, flag);

        if (min < 0) {
            for (int i = 0; i < size; i++)
                arr[i] = arr[i] + min;
        }

        return arr;
    }


    static int[] radix_sortutil(int arr[], boolean flag) {
        int size = arr.length;
        int max = getMax(arr, size);

        for (int place = 1; Math.abs(max / place) > 0; place *= 10) {
            countingSort(arr, size, place);
            if (flag)
                printArray(arr);
        }

        return arr;
    }

    // Swap function
    static void swap(int arr[], int i1, int i2) {
        int temp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = temp;
    }

    static void printArray(int arr[]) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }


    public static void main(String[] args) {
        int arr[] = {170, 45, 75, 90, 802, 24, 2, 66};
        System.out.println("Original array:");
        printArray(arr);

        radix_sort(arr, false);

        System.out.println("Sorted array:");
        printArray(arr);
    }
}
