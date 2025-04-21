import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner( System.in );

    public static void main(String[] args) {
        

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "HH:mm:ss" );
        LocalTime alarmTime = null;
        String filePath = "alarm.wav";
        System.out.print( "Enter an alarm time (HH:MM:SS): " );

        do {

            try {

                String inputTime = scanner.nextLine();
                
                alarmTime = LocalTime.parse( inputTime, formatter );
                System.out.println( "Alarm set for " + alarmTime );
                
            }
            catch ( DateTimeParseException e ) {
                System.out.print( "Invalid Time, Time should be in format (HH:MM:SS): " );
            }
        }
        while ( alarmTime == null);

        AlarmClock alarmClock = new AlarmClock( alarmTime, filePath );
        Thread alarmThread = new Thread( alarmClock );

        alarmThread.start();

        
    }

}
