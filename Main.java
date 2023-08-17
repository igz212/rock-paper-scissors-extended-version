package rockpaperscissors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println( System.getProperty ("user.dir"));
        String ratingFilePath = ".\\rating.txt";
        String shapeOrCommand;
        String userName = getUserNameAndGreet();
        boolean loopCondition = true;
        int[] userRate = getUserRateFromFile(ratingFilePath, userName);
//        int[] userRate = {100};
        String[] optionsList = initializeOptions();
        System.out.println("Okay, let's start");
        Scanner scannerSys = new Scanner(System.in);
        while (loopCondition) {
            shapeOrCommand = scannerSys.next();
            loopCondition = checkInputString(shapeOrCommand, userRate, optionsList);
        }


    }

    private static String[] initializeOptions() {
        Scanner scannerSys = new Scanner(System.in);
        String temp = scannerSys.nextLine();
        if (!temp.isEmpty()) {
            return temp.split(",");
        } else {
            return new String[]{"rock", "paper", "scissors"};
        }

    }

    private static int[] getUserRateFromFile(String ratingFilePath, String userName) {
        int[] userRate = {0};
        File file = new File(ratingFilePath);
        String[] record;
        try(Scanner scannerFile = new Scanner(file)) {
            while(scannerFile.hasNextLine()) {
                record = scannerFile.nextLine().split(" ");
                if (record[0].equals(userName)) {
                    userRate[0] = Integer.parseInt(record[1]);
                    return userRate;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return userRate;
    }

    private static String getUserNameAndGreet() {
        System.out.print("Enter your name: ");
        Scanner scanner = new Scanner(System.in);
        String userName = scanner.next();
        System.out.printf("Hello, %s\n", userName);
        return userName;
    }



    private static boolean checkInputString(String shapeOrCommand, int[] userRate, String[] optionsList) {
        switch (shapeOrCommand) {
            case "!exit" -> {
                System.out.println("Bye!");
                return false;
            }
            case "!rating" -> {
                System.out.printf("Your rating is: %d", userRate[0]);
                return true;
            }
            default -> {
                if (Arrays.asList(optionsList).contains(shapeOrCommand)) {
                    System.out.println(takeDecisionByComputer(shapeOrCommand, userRate, optionsList));
                } else {
                    System.out.println("Invalid input");
                }
                return true;
            }
        }
    }

    private static String takeDecisionByComputer(String shape, int[] userRate, String[] optionsList) {
        Random random = new Random();
        int decisionIndex = random.nextInt(optionsList.length);
        int length = optionsList.length;
        int halfLength = (length - 1) / 2;
        int shapeIndex = findShapeIndex(optionsList, shape);

        if (shapeIndex == decisionIndex) {
            userRate[0] += 50;
            return String.format("There is a draw (%s)", optionsList[decisionIndex]);
        } else if (decisionIndex == halfLength) {
            if (shapeIndex < decisionIndex) {
                return String.format("Sorry, but the computer chose %s", optionsList[decisionIndex]);
            } else {
                userRate[0] += 100;
                return String.format("Well done. The computer chose %s and failed", optionsList[decisionIndex]);
            }
        } else if (decisionIndex < halfLength) {
            if (decisionIndex > shapeIndex || shapeIndex > decisionIndex + halfLength) {
                return String.format("Sorry, but the computer chose %s", optionsList[decisionIndex]);
            } else {
                userRate[0] += 100;
                return String.format("Well done. The computer chose %s and failed", optionsList[decisionIndex]);
            }
        } else {
            if (shapeIndex > decisionIndex || decisionIndex > shapeIndex + halfLength) {
                userRate[0] += 100;
                return String.format("Well done. The computer chose %s and failed", optionsList[decisionIndex]);
            } else {
                return String.format("Sorry, but the computer chose %s", optionsList[decisionIndex]);
            }
        }
    }

    private static int findShapeIndex(String[] optionsList, String shape) {
        for (int i = 0; i < optionsList.length; i++) {
            if (optionsList[i].equals(shape)) {
                return i;
            }
        }
        return -1;
    }
}
