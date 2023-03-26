package main;
import java.util.Scanner;

public class App {
    
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int numFloors = 101;

        System.out.println("Welcome to the elevator simulator!");
        while (numFloors < 2 || numFloors > 100) {
            System.out.println("How many floors are in the building? Please choose a number between 2 and 100");
            String input = scanner.nextLine();
            try {
                numFloors = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Input is not a number. Please try again.");
            }
        }

        Scheduler scheduler = new Scheduler(numFloors, scanner);
        new Thread(scheduler).start();
    }
}
