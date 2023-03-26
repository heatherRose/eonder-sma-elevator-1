package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Scanner;

import org.junit.Test;

import main.Scheduler;

public class AppTest {
    Scheduler scheduler;

    @Test
    public void demoTestMethod() {
        assertTrue(true);
    }

    
    @Test
    public void testElevator() {
        scheduler = new Scheduler(10, new Scanner(System.in));
        new Thread(scheduler).start();
        scheduler.getButtons().requestUpFloor(3);
        synchronized (this) {
            try {
                this.wait(12000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        assertEquals(scheduler.getElevator().getCurrentFloor(), 3);
    }
}
