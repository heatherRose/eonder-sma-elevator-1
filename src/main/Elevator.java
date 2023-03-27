package main;

public class Elevator implements Runnable {
    private Logger logger;
    private int direction = 1;
    private int currentFloor = 1;
    private int nextFloor = 1;
    private boolean isStopped = true;
    private int weight = 0;
    private int weightLimit = 1000;
    private boolean stop = false;
    
    private final int STOP_TIME = 1000;
    private final int MOVE_FLOOR_TIME = 3000;
    private final int BUFFER_TIME = 10;

    public Elevator(Logger logger) {
        this.logger = logger;
    }

    public void run() {
        while (!stop) {
            if (nextFloor != currentFloor) {
                //move to next floor
                int floorDifference = nextFloor - currentFloor;
                direction = floorDifference > 0 ? 1 : -1;
                moveOneFloor();
                if (nextFloor != currentFloor) {
                    passFloor();
                } else {
                    stopAtFloor();
                }
            }
            try {
                Thread.sleep(BUFFER_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void stopAtFloor() {
        logger.logStop(currentFloor);
        isStopped = true;
        try {
            Thread.sleep(STOP_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void moveOneFloor() {
        isStopped = false;
        try {
            Thread.sleep(MOVE_FLOOR_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        currentFloor+=direction;
    }

    private void passFloor() {
        logger.logPass(currentFloor);
    }

    public int getDirection() {
        return this.direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getCurrentFloor() {
        return this.currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public int getNextFloor() {
        return this.nextFloor;
    }

    public void setNextFloor(int nextFloor) {
        this.nextFloor = nextFloor;
    }

    public boolean getIsStopped() {
        return this.isStopped;
    }

    public void setIsStopped(boolean isStopped) {
        this.isStopped = isStopped;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeightLimit() {
        return this.weightLimit;
    }

    public void setWeightLimit(int weightLimit) {
        this.weightLimit = weightLimit;
    }

    public void setStop() {
        stop = true;
    }

}
