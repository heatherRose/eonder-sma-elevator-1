package main;
import java.util.Scanner;
import java.util.TreeSet;

public class Buttons implements Runnable {
    private Logger logger;
    private Scanner scanner;
    private int numFloors;
    private TreeSet<Integer> stopRequested;
    private TreeSet<Integer> upRequested;
    private TreeSet<Integer> downRequested;
    private int newRequest;
    private boolean quit;

    public Buttons(int numFloors, Scanner scanner, Logger logger) {
        this.logger = logger;
        this.scanner = scanner;
        this.numFloors = numFloors;
        this.stopRequested = new TreeSet<Integer>();
        this.upRequested = new TreeSet<Integer>();
        this.downRequested = new TreeSet<Integer>();
        this.newRequest = -1;
        this.quit = false;
    }

    public void run() {
        while (!quit) {
            String input = scanner.nextLine();
            if (!input.isEmpty()) {
                // Quit with "Q" input
                if (input.equals("Q")) {
                    System.out.println("Quit request submitted. Quitting after all floors visited");
                    quit = true;
                    break;
                }

                // Parse input in format #, #U, #D
                String request = input;
                String lastChar = input.substring(input.length() - 1);
                if (lastChar.equals("U") || lastChar.equals("D")) {
                    input = input.substring(0, input.length() - 1);
                }

                int requestedFloor = 0;
                try {
                    requestedFloor = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid floor request. Please try again");
                    continue;
                }

                // If requested floor in range, add to relevant set
                if (requestedFloor > 0 && requestedFloor <= this.numFloors) {
                    if (lastChar.equals("U")) {
                        // up direction
                        requestUpFloor(requestedFloor);
                    } else if (lastChar.equals("D")) {
                        // down direction
                        requestDownFloor(requestedFloor);
                    } else {
                        // request inside elevator
                        requestStopFloor(requestedFloor);
                    }
                    newRequest = requestedFloor;
                    logger.logRequest(request);
                } else {
                    System.out.println("Invalid floor request. Please try again");
                }
            }
        }
        scanner.close();
    }

    public boolean requestsExist() {
        return !stopRequested.isEmpty() || !downRequested.isEmpty() || !upRequested.isEmpty();
    }

    public void removeFloorRequest(int floor, int direction) {
        if (direction == 1) {
            stopRequested.remove(floor);
            upRequested.remove(floor);
        } else {
            stopRequested.remove(floor);
            downRequested.remove(floor);
        }
    }

    public void requestStopFloor(int floor) {
        if (!stopRequested.contains(floor)) {
            stopRequested.add(floor);
        }
    }

    public void removeStopRequest(int floor) {
        stopRequested.remove(floor);
    }
    
    public void requestDownFloor(int floor) {
        if (!downRequested.contains(floor)) {
            downRequested.add(floor);
        }
    }

    public void removeDownRequest(int floor) {
        downRequested.remove(floor);
    }
    
    public void requestUpFloor(int floor) {
        if (!upRequested.contains(floor)) {
            upRequested.add(floor);
        }
    }

    public void removeUpRequest(int floor) {
        upRequested.remove(floor);
    }

    public int getNumFloors() {
        return this.numFloors;
    }

    public void setNumFloors(int numFloors) {
        this.numFloors = numFloors;
    }

    public TreeSet<Integer> getStopRequested() {
        return this.stopRequested;
    }

    public void setStopRequested(TreeSet<Integer> stopRequested) {
        this.stopRequested = stopRequested;
    }

    public TreeSet<Integer> getUpRequested() {
        return this.upRequested;
    }

    public void setUpRequested(TreeSet<Integer> upRequested) {
        this.upRequested = upRequested;
    }

    public TreeSet<Integer> getDownRequested() {
        return this.downRequested;
    }

    public void setDownRequested(TreeSet<Integer> downRequested) {
        this.downRequested = downRequested;
    }

    public int getNewRequest() {
        return this.newRequest;
    }

    public void setNewRequest(int newRequest) {
        this.newRequest = newRequest;
    }

    public boolean isNewRequest() {
        return this.newRequest != -1;
    }

    public boolean getQuit() {
        return this.quit;
    }

    public void setQuit(boolean quit) {
        this.quit = quit;
    }
}
