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
        }
        if (finalArray) result.add(new ArrayList<>(arr));

        return result;
    }

    public List<List<Integer>> EfficientSort(boolean finalArray) {    // O(nlog(n)) merge sort
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> copiedList = new ArrayList<>(this.list);
        copiedList = mergeSort(copiedList, result, finalArray);
        if (result.size() > 1) result.removeLast();
        if (!finalArray) result.add(new ArrayList<>(copiedList));
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

    // helper function for radix sort
    private void countSort (List<Integer> arr, int exp){
        int[] count = new int[10];
        int n = arr.size();
        for(int a : arr){
            count[(a/exp)%10]++;
        }
        for(int i = 1; i<10; i++){
            count[i] += count[i-1];
        }
        int[] temp = new int[n];
        for(int i = n-1; i>=0; i--){
            int a = arr.get(i);
            temp[count[(a/exp)%10]-1] = a;
            count[(a/exp)%10]--;
        }
        for(int i = 0; i<n; i++){
            arr.set(i, temp[i]);
        }
    }

    private void write (List<List<Integer>> result, List<Integer> arr, List<Integer> other, Boolean p){
        // add the step to the result
        List<Integer> temp;
        if(p){
            temp = new ArrayList<>(arr);
            for (Integer integer : other) {
                temp.add(-1 * integer);
            }
        }else {
            temp = new ArrayList<>(other);
            for (Integer integer : arr) {
                temp.add(-1 * integer);
            }
        }
        result.add(new ArrayList<>(temp));
    }

    private void RadixSort (List<Integer> arr, List<List<Integer>> result, boolean finalArray, List<Integer> other, Boolean p){
        int max = -1;
        for(int i = 0; i<arr.size(); i++){
            max = Math.max(max, arr.get(i));
        }
        for(int exp = 1; max/exp > 0; exp *= 10){
            countSort(arr, exp);
            if(!finalArray) {
                write(result, arr, other, p);
            }
        }
    }

    public List<List<Integer>> NonComparisonSort(boolean finalArray) {   // O(n) radix sort
        List<List<Integer>> result = new ArrayList<>();
        int n = getSize();
        // the first step (initial array)
        if(!finalArray){
            result.add(new ArrayList<>(list));
            result.add(null);
        }
        // the second step (divide positive and negative)
        List<Integer> neg = new ArrayList<>(), pos = new ArrayList<>();
        for(int i = 0; i<n; i++){
            int element = list.get(i);
            if(element < 0)  neg.add(-1*element);
            else pos.add(element);
        }
        if(!finalArray){
            write(result, pos, neg, true);
            result.add(null);
        }

        // the third step (sorted the positive part)
        RadixSort(pos, result, finalArray, neg, true);
        if(!finalArray) result.add(null);

        // the fourth step (sorted the negative part)
        RadixSort(neg, result, finalArray, pos, false);
        if(!finalArray) result.add(null);

        // the final step (merge them)
        List<Integer> finalList = new ArrayList<>();
        for(int i = neg.size()-1; i>=0; i--){
            finalList.add(-1 * neg.get(i));
        }
        finalList.addAll(pos);
        result.add(new ArrayList<>(finalList));

        return result;
    }

    public int getSize() {
        return list.size();
    }

//     public static void main(String[] args) {
//        SortArray sa = new SortArray("/media/braamostafa/Stuff/learning/engineering/year 2/semester 2/DSA/labs/lab 1/code/Implementing-Sorting-Techniques/input.txt");
//        List<List<Integer>> result= sa.SimpleSort(true);
//        for(List<Integer> l : result){
//            if (l != null) {
//                System.out.println(l);
//            }else
//                System.out.println("null here");
//        }
//     }
}
