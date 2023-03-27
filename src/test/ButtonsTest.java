package test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Scanner;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import main.Buttons;
import main.Logger;

public class ButtonsTest {
    Buttons buttons;

    @BeforeEach                                         
    public void setUp() {
        buttons = new Buttons(10, new Scanner(System.in), new Logger());
    }

    // In a more robust test suite, would test that input correctly adds to request lists
    @Test
    public void testStopRequest() {

    }

    @Test
    public void testUpRequest() {
        
    }
    
}
