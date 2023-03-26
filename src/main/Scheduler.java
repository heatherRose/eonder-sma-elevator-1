package main;
import java.util.Optional;
import java.util.Scanner;

public class Scheduler implements Runnable {
    private Elevator elevator;
    private Buttons buttons;
    private Logger logger;

    public Scheduler(int numFloors, Scanner scanner) {
        this.logger = new Logger();
        this.elevator = new Elevator(logger);
        this.buttons = new Buttons(numFloors, scanner, logger);
    }
    
    public void run() {
        new Thread(elevator).start();
        new Thread(buttons).start();
        
        while (!buttons.getQuit()) {
            // Update nextFloor when there's a new request, or when elevator stops
            if (buttons.isNewRequest()) {
                // Only schedule new request if > 1 floor away
                if (buttons.getNewRequest() != elevator.getCurrentFloor() + elevator.getDirection()) {
                    schedule();
                }
                buttons.setNewRequest(-1);
            }
            if (elevator.getIsStopped()) {
                // Remove floor request only if elevator is going in correct direction
                buttons.removeFloorRequest(elevator.getCurrentFloor(), elevator.getDirection());
                if (buttons.requestsExist()) {
                    schedule();
                }
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                continue;
            }
        }
        while (buttons.requestsExist()) {
            if (elevator.getIsStopped()) {
                schedule();
            }
        }
        elevator.setStop();
        logger.writeLogToFile();
    }

    private void schedule() {
        int currentFloor = elevator.getCurrentFloor();
        if (elevator.getDirection() == 1) {
            // Check if there's a higher floor request (to stop or go up)
            int nextStop = Optional.ofNullable(buttons.getStopRequested().higher(currentFloor)).orElse(0);
            int nextUp = Optional.ofNullable(buttons.getUpRequested().higher(currentFloor)).orElse(0);
            // If there isn't a higher floor request
            if (nextStop == 0 && nextUp == 0) {
                // Check if there's a higher floor request to go down
                int topRequested = 0;
                if (!buttons.getDownRequested().isEmpty()) {
                    topRequested = Optional.ofNullable(buttons.getDownRequested().last()).orElse(0);
                }
                // If not, change directions
                if (topRequested == 0) {
                    elevator.setDirection(-1);
                } else {
                    elevator.setNextFloor(topRequested);
                }
            } else if (nextStop == 0) {
                // If there's only a request to go up, do that
                elevator.setNextFloor(nextUp);
            } else if (nextUp == 0) {
                // If there's only a request to stop, do that
                elevator.setNextFloor(nextStop);
            } else {
                // Choose the lower of two requests
                elevator.setNextFloor(Math.min(nextStop, nextUp));
            }
        } else {
            // Check if there's a lower floor request (to stop or go down)
            int nextStop = Optional.ofNullable(buttons.getStopRequested().lower(currentFloor)).orElse(0);
            int nextDown = Optional.ofNullable(buttons.getDownRequested().lower(currentFloor)).orElse(0);
            // If there isn't a lower floor request
            if (nextStop == 0 && nextDown == 0) {
                // Check if there's a lower floor request to go up
                int bottomRequested = 0;
                if (!buttons.getUpRequested().isEmpty()) {
                    bottomRequested = Optional.ofNullable(buttons.getUpRequested().first()).orElse(0);
                }
                // If not, change directions
                if (bottomRequested == 0) {
                    elevator.setDirection(1);
                } else {
                    elevator.setNextFloor(bottomRequested);
                }
            } else if (nextStop == 0) {
                // If there's only a request to go down, do that
                elevator.setNextFloor(nextDown);
            } else if (nextDown == 0) {
                // If there's only a request to stop, do that
                elevator.setNextFloor(nextStop);
            } else {
                // go to highest of nextStop and nextDown
                elevator.setNextFloor(Math.max(nextStop, nextDown));
            }
        }
    }


    public Elevator getElevator() {
        return this.elevator;
    }

    public Buttons getButtons() {
        return this.buttons;
    }

}
