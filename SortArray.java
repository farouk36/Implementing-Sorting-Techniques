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

    List<List<Integer>> EfficentSort(boolean finalArray) {
        List<List<Integer>> result = new ArrayList<>();

        return result;
    }

    List<List<Integer>> NonComparisonSort(boolean finalArray) {
        List<List<Integer>> result = new ArrayList<>();

        return result;
    }





//     public static void main(String[] args) {
//     SortArray sa=new SortArray("");
//     List<List<Integer>> result=sa.bubbleSort(false);
//     for(List<Integer> l:result){
//     for(int i:l){
//     System.out.print(i+"   ");
//     }
//     System.out.println();
//     }
// }
}
