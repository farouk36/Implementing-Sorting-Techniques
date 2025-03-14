package Implementing_Sorting_Techniques;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SortArray {
    private List<Integer> list;

    private void readFile(String filePath) {
        this.list = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(filePath);
            Scanner in = new Scanner(fileReader);
            while (in.hasNext()) {
                String line = in.nextLine();
                String[] arr = line.split(",");
                for (String s : arr) {
                    list.add(Integer.parseInt(s.replaceAll(" ", "")));
                }
            }
            in.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public SortArray(String filePath) {
        readFile(filePath);
    }

    public List<List<Integer>> SimpleSort(boolean finalArray) {   // O(n^2) bubble sort
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> arr = new ArrayList<>(this.list);
        if (!finalArray) {
            result.add(new ArrayList<>(list));
        }
        boolean flag=false;

        for (int i = 0; i < arr.size()-1; i++) {
            flag=false;
            for (int j = 0; j < arr.size()-i-1; j++) {
                if (arr.get(j) > arr.get(j+1)) {
                    int temp = arr.get(j);
                    arr.set(j, arr.get(j+1));
                    arr.set(j+1, temp);
                    flag=true;

                    if (!finalArray) result.add(new ArrayList<>(arr));
                }
            }
            if(!flag) {
                if (finalArray) result.add(new ArrayList<>(arr));
                return result;
            }
            result.add(null);
        }
        if (finalArray) result.add(new ArrayList<>(arr));

        return result;
    }

    public List<List<Integer>> EfficientSort(boolean finalArray) {    // O(nlog(n)) merge sort
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> copiedList = new ArrayList<>(this.list);
        copiedList = mergeSort(copiedList, result, finalArray);
        if (result.size() > 1) result.removeLast();
        result.add(new ArrayList<>(copiedList));
        return result;
    }// merge sort n*log n

    private List<Integer> mergeSort(List<Integer> arr, List<List<Integer>> result, boolean finalArray){
        if (arr.size() <= 1){
            return arr;
        }
        int mid = arr.size() / 2;
        List<Integer> arrLeft = mergeSort(arr.subList(0, mid), result, finalArray);
        List<Integer> arrRight = mergeSort(arr.subList(mid, arr.size()), result, finalArray);
        return merge(arrLeft, arrRight, result, finalArray);

    }//log n
    private List<Integer> merge(List<Integer> arrLeft, List<Integer> arrRight, List<List<Integer>> result, boolean finalArray){
        List<Integer> arr = new ArrayList<>();
        if (finalArray){
            result.add(new ArrayList<>(arrLeft));
            result.add(new ArrayList<>(arrRight));
        }

        int i = 0, j = 0;
        while (i < arrLeft.size() && j < arrRight.size()) {
            if (arrLeft.get(i) < arrRight.get(j)) {
                arr.add(arrLeft.get(i));
                i++;
            } else {
                arr.add(arrRight.get(j));
                j++;
            }
        }
        while (i < arrLeft.size()) {
            arr.add(arrLeft.get(i));
            i++;
        }
        while (j < arrRight.size()) {
            arr.add(arrRight.get(j));
            j++;
        }
        if (finalArray){
            result.add(new ArrayList<>(arr));
            result.add(null);
        }
        return arr;
    }

    // Helper function for radix sort
    private void countSort(List<Integer> arr, int exp, int start, int end, boolean pos) {
        int[] count = new int[10];
        int t;
        for (int i = start; i < end; i++) {
            t = pos ? arr.get(i) : -1 * arr.get(i);
            count[(t / exp) % 10]++;
        }
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }
        int[] temp = new int[end - start];
        for (int i = end - 1; i >= start; i--) {
            int a = pos ? arr.get(i) : -1 * arr.get(i);
            temp[count[(a / exp) % 10] - 1] = pos ? a : -1 * a;
            count[(a / exp) % 10]--;
        }
        for (int i = start, j = 0; i < end; i++, j++) {
            arr.set(i, temp[j]);
        }
    }

    private void RadixSort(List<Integer> arr, List<List<Integer>> result, boolean finalArray, int start, int end, boolean pos) {
        int max = -1;
        for (int i = start; i < end; i++) {
            max = pos ? Math.max(max, arr.get(i)) : Math.max(max, -1 * arr.get(i));
        }
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countSort(arr, exp, start, end, pos);
            if (!finalArray) {
                result.add(new ArrayList<>(arr));
            }
        }
    }

    public List<List<Integer>> NonComparisonSort(boolean finalArray) {   // O(n) radix sort
        List<List<Integer>> result = new ArrayList<>();
        int n = getSize();
        // The first step (initial array)
        if (!finalArray) {
            result.add(new ArrayList<>(list));
            result.add(null);
        }
        // The second step (divide positive and negative)
        List<Integer> neg = new ArrayList<>(), pos = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int element = list.get(i);
            if (element < 0) neg.add(element);
            else pos.add(element);
        }
        List<Integer> finalList = new ArrayList<>();
        finalList.addAll(neg);
        finalList.addAll(pos);
        if (!finalArray) {
            result.add(new ArrayList<>(finalList));
            result.add(null);
        }

        // The third step (sorted the positive part)
        int start = neg.size(), end = finalList.size();
        RadixSort(finalList, result, finalArray, start, end, true);
        if (!finalArray) result.add(null);

        // The fourth step (sorted the negative part)
        start = 0;
        end = neg.size();
        RadixSort(finalList, result, finalArray, start, end, false);

        // reverse negative part
        for(int i = 0; i < end/2; i++){
            int t = finalList.get(i);
            finalList.set(i, finalList.get(end-1-i));
            finalList.set(end-1-i, t);
        }
        if (!finalArray) {
            result.add(null);
            result.add(new ArrayList<>(finalList));
            result.add(null);
        }

        // The final step (merge them)
        result.add(new ArrayList<>(finalList));

        return result;
    }

    public int getSize() {
        return list.size();
    }

//    public static void main(String[] args) {
//        SortArray sa = new SortArray("/media/braamostafa/Stuff/learning/engineering/year 2/semester 2/DSA/labs/lab 1/code/Implementing-Sorting-Techniques/input.txt");
//        List<List<Integer>> result= sa.EfficientSort(false);
//        for(List<Integer> l : result){
//            if (l != null) {
//                System.out.println(l);
//            }else
//                System.out.println("null here");
//        }
//     }
}
