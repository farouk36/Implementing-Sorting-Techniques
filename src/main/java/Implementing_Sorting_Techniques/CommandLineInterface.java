package Implementing_Sorting_Techniques;

import java.util.List;
import java.util.Scanner;

public class CommandLineInterface {
    // ANSI color constants
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";

    private SortArray sortArray;
    private final Scanner scanner;

    public CommandLineInterface() {
        scanner = new Scanner(System.in);
    }

    public void run() {
        boolean continueProgram = true;

        while (continueProgram) {
            continueProgram = processFile();
        }
    }

    private boolean processFile() {
        System.out.print(BLUE + "\nEnter the path of the file: " + RESET);
        String filePath = scanner.next();

        try {
            sortArray = new SortArray(filePath);
            return handleSorting();
        } catch (Exception e) {
            System.out.println(RED + "Error: " + RESET + YELLOW + e.getMessage() + RESET);
            return handleError();
        }
    }

    private boolean handleSorting() {
        boolean continueWithCurrentFile = true;

        while (continueWithCurrentFile) {
            int sortTechnique = displaySortingMenu();
            boolean showFinalArrayOnly = promptForIntermediateSteps();

            List<List<Integer>> result = executeSortingAlgorithm(sortTechnique, showFinalArrayOnly);

            int nextAction = promptForNextAction();

            if (nextAction == 2) {
                continueWithCurrentFile = false;
            } else if (nextAction == 3) {
                return false; // Exit program
            }
        }

        return true; // Continue program with a different file
    }

    private int displaySortingMenu() {
        System.out.println(BLUE + "\nChoose the sorting technique: " + RESET);
        System.out.println(YELLOW + "1. Bubble Sort" + RESET);
        System.out.println(YELLOW + "2. Merge Sort" + RESET);
        System.out.println(YELLOW + "3. Radix Sort" + RESET);
        System.out.println(RED + "4. Exit" + RESET);

        int choice = getValidIntInput(1, 4);
        if (choice == 4) {
            System.exit(0);
        }
        return choice;
    }

    private boolean promptForIntermediateSteps() {
        System.out.print(BLUE + "Do you want to see the intermediate steps? (y/n): " + RESET);
        String choice = scanner.next();

        while (!choice.equalsIgnoreCase("y") && !choice.equalsIgnoreCase("n")) {
            System.out.println(RED + "Invalid choice. Try again." + RESET);
            choice = scanner.next();
        }

        return choice.equalsIgnoreCase("n");
    }

    private List<List<Integer>> executeSortingAlgorithm(int technique, boolean finalArrayOnly) {
        long startTime = System.nanoTime();

        List<List<Integer>> result = switch (technique) {
            case 1 -> sortArray.SimpleSort(finalArrayOnly);
            case 2 -> sortArray.EfficientSort(finalArrayOnly);
            case 3 -> sortArray.NonComparisonSort(finalArrayOnly);
            default -> null;
        };

        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1_000_000.0;

        displayResult(result, finalArrayOnly, duration, technique);

        return result;
    }

    private int promptForNextAction() {
        System.out.println(BLUE + "\nWhat next?" + RESET);
        System.out.println(YELLOW + "1. Use another sorting technique on the same file" + RESET);
        System.out.println(YELLOW + "2. Use a different file" + RESET);
        System.out.println(RED + "3. Exit the program" + RESET);

        return getValidIntInput(1, 3);
    }

    private boolean handleError() {
        System.out.println(YELLOW + "\n1. Try a different file" + RESET);
        System.out.println(RED + "2. Exit the program" + RESET);

        int choice = getValidIntInput(1, 2);
        return choice != 2;
    }

    private int getValidIntInput(int min, int max) {
        int choice = scanner.nextInt();

        while (choice < min || choice > max) {
            System.out.println(RED + "Invalid choice. Try again." + RESET);
            choice = scanner.nextInt();
        }

        return choice;
    }

    private void displayResult(List<List<Integer>> result, boolean finalArrayOnly,
                               double duration, int sortTechnique) {
        if (finalArrayOnly) {
            System.out.println("\n" + YELLOW + "Sorted Array: " + RESET);
            System.out.println(GREEN + result.getLast() + RESET);
        } else {
            switch (sortTechnique) {
                case 1 -> displayStepsForBubbleSort(result);
                case 2 -> displayStepsForMergeSort(result);
                case 3 -> displayStepsForRadixSort(result);
            }
        }

        System.out.println(CYAN + "Time taken: " + duration + "ms" + RESET);
    }

    private void displayStepsForRadixSort(List<List<Integer>> result) {
//        if (result.getFirst().size() <= 1) {
//            System.out.println(YELLOW + "Initial array: " + RESET + GREEN + result.getFirst() + RESET);
//            System.out.println(YELLOW + "\nFinal result: " + RESET + GREEN + result.getLast() + RESET + "\n");
//            return;
//        }

        System.out.println(PURPLE + "Intermediate steps: " + RESET);
        System.out.println();
        System.out.println(YELLOW + "Initial array: " + RESET + GREEN + result.getFirst() + RESET);

        int state = 1;
        for (int index = 2; index < result.size(); index++) {
            if (result.get(index) == null) {
                continue;
            }

            switch (state) {
                case 1 -> {
                    System.out.println(YELLOW + "\nSplit Negative numbers and Positive numbers: " + RESET +
                            GREEN + result.get(index) + RESET);
                }
                case 2 -> {
                    System.out.println(YELLOW + "\nSort Positive numbers: " + RESET);
                    while (index < result.size() && result.get(index) != null) {
                        System.out.println(GREEN + result.get(index++) + RESET);
                    }
                }
                case 3 -> {
                    System.out.println(YELLOW + "\nSort Negative numbers as Positive numbers: " + RESET);
                    while (index < result.size() && result.get(index) != null) {
                        System.out.println(GREEN + result.get(index++) + RESET);
                    }
                }
                case 4 -> {
                    System.out.println(YELLOW + "\nReverse and return negative numbers (after sorted as Positive numbers): " +
                            RESET + GREEN + result.get(index) + RESET);
                }
            }
            state++;
        }

        System.out.println(YELLOW + "\nFinal result: " + RESET + GREEN + result.getLast() + RESET + "\n");
    }

    private void displayStepsForMergeSort(List<List<Integer>> result) {
        if (sortArray.getList().size() > 1) {
            result.removeLast();
        }

        System.out.println(PURPLE + "Intermediate steps: " + RESET);
        System.out.println();
        System.out.println(YELLOW + "Initial array: " + RESET + GREEN + sortArray.getList() + RESET + "\n");
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

        int stepNumber = 1;
        boolean readyForNewStep = true;

        for (List<Integer> list : result) {
            if (readyForNewStep) {
                System.out.println(YELLOW + "\nStep #" + stepNumber + RESET);
            }

            if (list == null) {
                System.out.println();
                stepNumber++;
                readyForNewStep = true;
                continue;
            } else {
                System.out.println(GREEN + list + RESET);
                readyForNewStep = false;
            }
        }
    }

    public static void main(String[] args) {
        CommandLineInterface cli = new CommandLineInterface();
        cli.run();
    }
}
