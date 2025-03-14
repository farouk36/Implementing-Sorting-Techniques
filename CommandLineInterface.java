
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandLineInterface {
    private SortArray sortArray;
    private Scanner in;
    CommandLineInterface() {
        in=new Scanner(System.in);
    }


    void run() {
        boolean program = true;
        
        while (program) {
            System.out.print("\nEnter the path of the file: ");
            String filePath = in.next();
            
            try {
                sortArray = new SortArray(filePath);
                
                boolean currentFile = true;
                while (currentFile) {
                    int sortTechnique = menu();
                    
                    System.out.print("Do you want to see the intermediate steps? (y/n): ");
                    String choice = in.next();
                    boolean finalArray = choice.equalsIgnoreCase("n");
                    
                    List<List<Integer>> result = null;
                    long startTime = System.nanoTime();
                    
                    switch (sortTechnique) {
                        case 1:
                            result = sortArray.SimpleSort(finalArray);
                            break;
                        case 2:
                            result = sortArray.EfficientSort(finalArray);
                            break;
                        case 3:
                            result = sortArray.NonComparisonSort(finalArray);
                            break;
                    }
                    
                    long endTime = System.nanoTime();
                    double duration = (endTime - startTime) / 1_000_000.0;
                    
                    displayResult(result, finalArray, duration, sortTechnique);
                    
                    System.out.println("\nWhat next?");
                    System.out.println("1. Use another sorting technique on the same file");
                    System.out.println("2. Use a different file");
                    System.out.println("3. Exit the program");
                    
                    int nextAction = getValidIntInput(1, 3);
                    
                    if (nextAction == 2) {
                        currentFile = false;
                    } else if (nextAction == 3) {
                       currentFile = false;
                       program = false;
                    }
                }
                
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                
                System.out.println("\n1. Try a different file");
                System.out.println("2. Exit the program");
                
                int choice = getValidIntInput(1, 2);          
                if (choice == 2) {
                    program = false;
                }
            }
        }
    }
    private int getValidIntInput(int from,int to){
        int choice = in.nextInt();
        while(choice<from || choice>to){
            System.out.println("Invalid choice.try again.");
            choice = in.nextInt();
        }
        return choice;
    }
    private int menu(){
        System.out.println("\nChoose the sorting technique: ");
        System.out.println("1. Bubble Sort");
        System.out.println("2. Merge Sort");
        System.out.println("3. Radix Sort");
        System.out.println("4. Exit");

        int choice=getValidIntInput(0, 4);
        if(choice==4) System.exit(0);
        return choice;
    }
    private void displayResult(List<List<Integer>> result, boolean finalArray, double duration,int sortTechnique){
        if(finalArray){
            System.out.println("Sorted Array: ");
            System.out.println(result.get(result.size()-1));
        }
        else{
            switch (sortTechnique) {

                case 1:
                    System.out.println("Intermediate steps: ");
                    int i=1;boolean ready=true;
                    for(List<Integer> list: result){
                        if(ready)System.out.println("\nAt i = "+i);
                        if(list==null){
                            System.out.println();
                            i++;
                            ready=true;
                            continue;
                        }else {
                            System.out.println(list);
                            ready=false;
                        }
                        
                        
                    }
                    break;
          
                case 2:    
                    break;
                case 3:         
                    break;        
            }
        }
        System.out.println("Time taken: " + duration + "ms");
    }


    public static void main(String[] args) {
        CommandLineInterface cli = new CommandLineInterface();
        cli.run();

    }
    
}
