# SMA Technologies Build an Elevator Coding Challenge!

## Operation

Run the following command to start the elevator in the console:
```bash
java -jar .\sma-elevator.jar
```

The asynchronous request buttons can be entered by the application user via the console, by entering "5U" (request from 5th floor wanting to go Up) or "8D" (request from 8th floor wanting to go Down) or "2" (request from inside elevator wanting to stop at 2nd floor). When the user enters "Q" on the console, the application ends after visiting all floors entered before "Q".

The following will be logged to elevator_log.txt:
* Timestamp and asynchronous floor request, every time one occurs.
* Timestamp and floor, every time elevator passes a floor.
* Timestamp and floor, every time elevator stops at a floor.