package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Scanner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import main.Scheduler;

public class AppTest {
    private Scheduler scheduler;
    private final int STOP_TIME = 1000;
    private final int MOVE_FLOOR_TIME = 3000;
    private final int BUFFER_TIME = 60;
    
    @BeforeEach                                         
    public void setUp() {
        scheduler = new Scheduler(10, new Scanner(System.in));
        new Thread(scheduler).start();
    }

    /*
        Test stop at floor 2
        Simulated input: 2
    */
    @Test
    public void testOneStop() {
        scheduler.getButtons().requestStopFloor(2);
        synchronized (this) {
            try {
                this.wait(MOVE_FLOOR_TIME * 1 + BUFFER_TIME);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        assertEquals(scheduler.getElevator().getCurrentFloor(), 2);
        assertTrue(scheduler.getElevator().getIsStopped());
    }

    /*
        Test stop at floors 1 and 3
        Simulated input: 3, 1
    */
    @Test
    public void testMultipleStops() {
        scheduler.getButtons().requestStopFloor(1);
        scheduler.getButtons().requestStopFloor(3);
        assertEquals(scheduler.getElevator().getCurrentFloor(), 1);
        assertTrue(scheduler.getElevator().getIsStopped());
        synchronized (this) {
            try {
                this.wait(MOVE_FLOOR_TIME * 2 + BUFFER_TIME);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        assertEquals(scheduler.getElevator().getCurrentFloor(), 3);
        assertTrue(scheduler.getElevator().getIsStopped());
    }
    
    /*
        Test stop at floors 3, 4, and 2
        Elevator should pass over 2 since it's in the wrong direction
        Simulated input: 2D, 4, 3U
    */
    @Test
    public void testPassDownRequest() {
        scheduler.getButtons().requestDownFloor(2);
        scheduler.getButtons().requestStopFloor(4);
        scheduler.getButtons().requestUpFloor(3);
        synchronized (this) {
            try {
                this.wait(MOVE_FLOOR_TIME * 2 + BUFFER_TIME);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            assertEquals(scheduler.getElevator().getCurrentFloor(), 3);
            assertTrue(scheduler.getElevator().getIsStopped());
            try {
                this.wait(STOP_TIME * 1 + MOVE_FLOOR_TIME * 1 + BUFFER_TIME);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            assertEquals(scheduler.getElevator().getCurrentFloor(), 4);
            assertTrue(scheduler.getElevator().getIsStopped());
            try {
                this.wait(STOP_TIME * 1 + MOVE_FLOOR_TIME * 2 + BUFFER_TIME);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            assertEquals(scheduler.getElevator().getCurrentFloor(), 2);
            assertTrue(scheduler.getElevator().getIsStopped());
        }
    }
    
    /*
        Test stop at floors 4, 2, and 3
        Elevator should pass over 2, then 3, because they're in the wrong direction
        Simulated input: 4, 2D, [wait], 3U
    */
    @Test
    public void testPassUpRequest() {
        scheduler.getButtons().requestDownFloor(2);
        scheduler.getButtons().requestStopFloor(4);
        synchronized (this) {
            try {
                this.wait(MOVE_FLOOR_TIME * 3 + BUFFER_TIME);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            assertEquals(scheduler.getElevator().getCurrentFloor(), 4);
            assertTrue(scheduler.getElevator().getIsStopped());
            scheduler.getButtons().requestUpFloor(3);
            try {
                this.wait(STOP_TIME * 1 + MOVE_FLOOR_TIME * 2 + BUFFER_TIME);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            assertEquals(scheduler.getElevator().getCurrentFloor(), 2);
            assertTrue(scheduler.getElevator().getIsStopped());
            try {
                this.wait(STOP_TIME * 1 + MOVE_FLOOR_TIME * 1 + BUFFER_TIME);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            assertEquals(scheduler.getElevator().getCurrentFloor(), 3);
            assertTrue(scheduler.getElevator().getIsStopped());
        }
    }
    
    /*
        Test stop at floors 3 and 2
        Elevator should pass over 2 because it's requested less than a stop away
        Simulated input: 3, [wait], 2
    */
    @Test
    public void testRejectedStop() {
        scheduler.getButtons().requestStopFloor(3);
        synchronized (this) {
            try {
                this.wait(MOVE_FLOOR_TIME * 1 + BUFFER_TIME);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            scheduler.getButtons().requestStopFloor(2);
            try {
                this.wait(BUFFER_TIME);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            assertFalse(scheduler.getElevator().getIsStopped());
            try {
                this.wait(MOVE_FLOOR_TIME * 1);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            assertEquals(scheduler.getElevator().getCurrentFloor(), 3);
            assertTrue(scheduler.getElevator().getIsStopped());
            try {
                this.wait(STOP_TIME * 1 + MOVE_FLOOR_TIME * 1 + BUFFER_TIME);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            assertEquals(scheduler.getElevator().getCurrentFloor(), 2);
            assertTrue(scheduler.getElevator().getIsStopped());
        }
    }
    
    /*
        TODO: (would do given more time)
        Test quit request
    */
}
