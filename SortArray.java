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

    SortArray(String filePath) {
        readFile(filePath);
    }

    List<List<Integer>> SimpleSort(boolean finalArray) {   // O(n^2) bubble sort

        List<List<Integer>> result = new ArrayList<>();
        List<Integer> list = new ArrayList<>(this.list);
        if (!finalArray) {
            result.add(new ArrayList<>(list));
        }
        boolean flag=false;

        for (int i = 0; i < list.size()-1; i++) {
            flag=false;
            for (int j = 0; j < list.size()-i-1; j++) {
                if (list.get(j) > list.get(j+1)) {
                    int temp = list.get(j);
                    list.set(j, list.get(j+1));
                    list.set(j+1, temp);
                    flag=true;

                    if (!finalArray) {
                        result.add(new ArrayList<>(list));
                    }
                }
            }
            if(!flag)return result;
        }
        if (finalArray) {
            result.add(new ArrayList<>(list));
        }
        return result;
    }

    List<List<Integer>> EfficientSort(boolean finalArray) {    // O(nlog(n)) merge/quick sort
        List<List<Integer>> result = new ArrayList<>();

        return result;
    }

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

    private void RadixSort (List<Integer> arr, List<List<Integer>> result, boolean finalArray){
        int max = -1;
        for(int i = 0; i<arr.size(); i++){
            max = Math.max(max, arr.get(i));
        }
        if(finalArray) result.add(new ArrayList<>(arr));
        for(int exp = 1; max/exp > 0; exp *= 10){
            countSort(arr, exp);
            if(finalArray) result.add(new ArrayList<>(arr));
        }
    }

    List<List<Integer>> NonComparisonSort(boolean finalArray) {   // O(n) radix sort
        List<List<Integer>> result = new ArrayList<>();
        int n = getSize();
        if(finalArray){
            result.add(new ArrayList<>(list));
            result.add(null);
        }
        // divide it into negative and positive
        List<Integer> neg = new ArrayList<>(), pos = new ArrayList<>();
        for(int i = 0; i<n; i++){
            int element = list.get(i);
            if(element < 0)  neg.add(-1*element);
            else pos.add(element);
        }

        // perform the sort for the positive part
        RadixSort(pos, result, finalArray);

        if(finalArray){
            result.add(null);
        }

        // perform the sort for the negative part
        RadixSort(neg, result, finalArray);

        // merge them
        List<Integer> finalList = new ArrayList<>();
        for(int i = neg.size()-1; i>=0; i--){
            finalList.add(-1 * neg.get(i));
        }
        if(finalArray) {
            result.add(null);
            result.add(new ArrayList<>(finalList));
        }
        finalList.addAll(pos);
        result.add(new ArrayList<>(finalList));
        return result;
    }

    public int getSize() {
        return list.size();
    }

     public static void main(String[] args) {
     SortArray sa=new SortArray("/media/braamostafa/Stuff/learning/engineering/year 2/semester 2/DSA/labs/lab 1/code/Implementing-Sorting-Techniques/input.txt");
     List<List<Integer>> result=sa.NonComparisonSort(true);
     for(List<Integer> l:result){
         if(l == null) continue;
         for(int i:l){
             System.out.print(i+"   ");
         }
         System.out.println();
     }
 }
}
