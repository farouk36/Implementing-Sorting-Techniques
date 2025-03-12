import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

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

    List<List<Integer>> bubbleSort(boolean finalArray) {

        List<List<Integer>> result = new ArrayList<>();
        if (!finalArray) {
            result.add(new ArrayList<>(list));
        }

        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i) > list.get(j)) {
                    int temp = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, temp);

                    if (!finalArray) {
                        result.add(new ArrayList<>(list));
                    }
                }
            }
        }
        if (finalArray) {
            result.add(new ArrayList<>(list));
        }
        return result;
    }

    List<List<Integer>> EfficentSort(boolean finalArray) {
        List<List<Integer>> result = new ArrayList<>();

        return result;
    }

    List<List<Integer>> NonComparisonSort(boolean finalArray) {
        List<List<Integer>> result = new ArrayList<>();

        return result;
    }





    // public static void main(String[] args) {
    // SortArray sa=new SortArray("");
    // List<List<Integer>> result=sa.simpleSort(false);
    // for(List<Integer> l:result){
    // for(int i:l){
    // System.out.print(i+" ");
    // }
    // System.out.println();
    // }
    //
    // }
}
