import java.time.LocalTime;

public class AlarmClock implements Runnable {

    private final LocalTime alarmTime;

    public AlarmClock(LocalTime alarmTime) {
        this.alarmTime = alarmTime;
    }

    @Override
    public void run() {

        // Where our logic runs
        LocalTime now = LocalTime.now();
        
        while ( now.isBefore( alarmTime ) ) {

            try {
                Thread.sleep(1000);

                int hours   = now.getHour();
                int minutes = now.getMinute();
                int seconds = now.getSecond();


                System.out.printf( "%d:%d:%d\n", hours, minutes, seconds );
            } catch (InterruptedException e) {
                System.out.println( "Thread was interrupted" );
            }
            now = LocalTime.now();

        }

    }

}
