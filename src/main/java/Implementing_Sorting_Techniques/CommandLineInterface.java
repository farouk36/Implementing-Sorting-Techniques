package Implementing_Sorting_Techniques;

import java.util.List;
import java.util.Scanner;

public class CommandLineInterface {
    //colors
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";


    private SortArray sortArray;
    private Scanner in;
    CommandLineInterface() {
        in=new Scanner(System.in);
    }


    void run() {
        boolean program = true;
        
        while (program) {
            System.out.print(BLUE + "\nEnter the path of the file: " + RESET);
            String filePath = in.next();
            
            try {
                sortArray = new SortArray(filePath);
                
                boolean currentFile = true;
                while (currentFile) {
                    int sortTechnique = menu();
                    
                    System.out.print(BLUE + "Do you want to see the intermediate steps? (y/n): " + RESET);
                    String choice = in.next();
                    while (!choice.equalsIgnoreCase("y") && !choice.equalsIgnoreCase("n")) {
                        System.out.println(RED + "Invalid choice.try again."+  RESET);
                        choice = in.next();
                    }
                    boolean finalArray = choice.equalsIgnoreCase("n");
                    
                    List<List<Integer>> result = null;
                    long startTime = System.nanoTime();

                    result = switch (sortTechnique) {
                        case 1 -> sortArray.SimpleSort(finalArray);
                        case 2 -> sortArray.EfficientSort(finalArray);
                        case 3 -> sortArray.NonComparisonSort(finalArray);
                        default -> null;
                    };
                    
                    long endTime = System.nanoTime();
                    double duration = (endTime - startTime) / 1_000_000.0;
                    
                    displayResult(result, finalArray, duration, sortTechnique);
                    
                    System.out.println(BLUE + "\nWhat next?" + RESET);
                    System.out.println(YELLOW + "1. Use another sorting technique on the same file" + RESET);
                    System.out.println(YELLOW + "2. Use a different file" + RESET);
                    System.out.println(RED + "3. Exit the program" + RESET);
                    
                    int nextAction = getValidIntInput(1, 3);
                    
                    if (nextAction == 2) {
                        currentFile = false;
                    } else if (nextAction == 3) {
                       currentFile = false;
                       program = false;
                    }
                }
                
            } catch (Exception e) {
                System.out.println(RED + "Error: " + e.getMessage() + RESET);
                
                System.out.println(YELLOW + "\n1. Try a different file" + RESET);
                System.out.println(RED + "2. Exit the program" + RESET);
                
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
            System.out.println(RED + "Invalid choice.try again." + RESET);
            choice = in.nextInt();
        }
        return choice;
    }
    private int menu(){
        System.out.println(BLUE + "\nChoose the sorting technique: " + RESET);
        System.out.println(YELLOW + "1. Bubble Sort" + RESET);
        System.out.println(YELLOW + "2. Merge Sort" + RESET);
        System.out.println(YELLOW + "3. Radix Sort" + RESET);
        System.out.println(RED + "4. Exit" + RESET);

        int choice=getValidIntInput(0, 4);
        if(choice==4) System.exit(0);
        return choice;
    }
    private void displayResult(List<List<Integer>> result, boolean finalArray, double duration,int sortTechnique){
        if(finalArray){
            System.out.println("\n" + YELLOW + "Sorted Array: " + RESET);
            System.out.println(GREEN + result.getLast() + RESET);
        }
        else{
            switch (sortTechnique) {

                case 1: {
                    displayStepsForBubbleSort(result);
                    break;
                }
                case 2: {
                   displayStepsForMergeSort(result);
                   break;
                }
                case 3:         
                    displayStepsForRadixSort(result);
                    break;
            }
        }
        System.out.println(CYAN + "Time taken: " + duration + "ms" + RESET);
    }

    private void displayStepsForRadixSort(List<List<Integer>> result) {
        if (result.getFirst().size() <= 1){
            System.out.println(YELLOW + "intial array: " + RESET + GREEN + result.getFirst() + RESET);
            System.out.println(YELLOW + "\nFinal result: " + RESET + GREEN + result.getLast() + RESET + "\n");
            return;
        }
        System.out.println(PURPLE + "Intermediate steps: " + RESET);
        System.out.println();
        System.out.println(YELLOW + "intial array: " + RESET + GREEN + result.getFirst() + RESET);
        int state = 1;
        for (int index = 2; index < result.size() && result.getFirst().size() > 1; index++) {
            if (result.get(index) == null)
                continue;

            if (state == 1) {
                System.out.println(YELLOW + "\nsplit Negative numbers and Positive numbers : " + RESET + GREEN + result.get(index) + RESET);
            } else if (state == 2) {
                System.out.println(YELLOW + "\nsort Positive numbers : " + RESET);
                while (index < result.size() && result.get(index) != null) {
                    System.out.println(GREEN + result.get(index++) + RESET);
                }
            } else if (state == 3) {
                System.out.println(YELLOW + "\nsort Negative numbers as a Positive numbers: " + RESET);
                while (index < result.size() && result.get(index) != null) {
                    System.out.println(GREEN + result.get(index++) + RESET);
                }
            } else if (state == 4) {
                System.out.println(YELLOW + "\nreverse and return negative numbers (after sorted it as Positive numbers) : " + RESET + GREEN + result.get(index) + RESET);
            }
            state++;
        }
        System.out.println(YELLOW + "\nFinal result: " + RESET + GREEN + result.getLast() + RESET + "\n");
    }

    private void displayStepsForMergeSort(List<List<Integer>> result) {
        if (sortArray.getList().size() > 1)
            result.removeLast();

        System.out.println(PURPLE + "Intermediate steps: " + RESET);
        System.out.println();
        System.out.println(YELLOW + "intial array: " + RESET + GREEN + sortArray.getList() + RESET + "\n");
        System.out.println(YELLOW + "---- SPLIT/MERGE ----" + RESET);
        for (List<Integer> step : result) {
            if (step == null) {
                System.out.println(YELLOW + "---- SPLIT/MERGE ----" + RESET);
            } else {
                System.out.println(GREEN + step + RESET);
            }
        }
        System.out.println();
    }

    private void displayStepsForBubbleSort(List<List<Integer>> result) {
        System.out.println(PURPLE + "\nIntermediate steps: " + RESET);
        int i = 1;
        boolean ready = true;
        for (List<Integer> list : result) {
            if (ready) System.out.println(YELLOW + "\nStep #" + i + RESET);
            if (list == null) {
                System.out.println();
                i++;
                ready = true;
                continue;
            } else {
                System.out.println(GREEN + list + RESET);
                ready = false;
            }
        }
    }



    public static void main(String[] args) {
        CommandLineInterface cli = new CommandLineInterface();
        cli.run();
    }
    
}
