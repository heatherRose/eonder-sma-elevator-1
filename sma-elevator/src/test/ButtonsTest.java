package test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Scanner;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import main.Buttons;
import main.Logger;

public class ButtonsTest {
    Buttons buttons;

    @BeforeEach                                         
    public void setUp() {
        buttons = new Buttons(10, new Scanner(System.in), new Logger());
    }

    @Test
    public void demoTestMethod() {
        assertTrue(true);
    }

    @Test
    public void testStopRequest() {

    }

    @Test
    public void testUpRequest() {
        
    }
    
}
