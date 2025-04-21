import java.time.LocalTime;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

public class AlarmClock implements Runnable {

    private final LocalTime alarmTime;
    private final String filePath;

    public AlarmClock(LocalTime alarmTime, String filePath) {
        this.alarmTime = alarmTime;
        this.filePath = filePath;
    }

    @Override
    public void run() {

        // Where our logic runs
        LocalTime now = LocalTime.now();
        
        while ( now.isBefore( alarmTime ) ) {

            try {
                Thread.sleep(1000);

                System.out.printf( "\r%02d:%02d:%02d",
                                     now.getHour(), 
                                     now.getMinute(), 
                                     now.getSecond() 
                                );
            } catch (InterruptedException e) {
                System.out.println( "Thread was interrupted" );
            }
            now = LocalTime.now();

        }

        System.out.println( "\nAlarm Noises" );
        // Toolkit.getDefaultToolkit().beep(); // This does not work on Mint

        playSound( filePath );

    }

    private void playSound( String filePath ) {

        File audioFile   = new File( filePath );
        try ( AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile); ) {
            Clip clip = AudioSystem.getClip();
            clip.open( audioStream );
            clip.start();

            System.out.println("Press enter to stop the alarm!");
            Main.scanner.nextLine();

        } catch (UnsupportedAudioFileException e) {
            System.out.println( "File not supported" );
        }
        catch ( LineUnavailableException e ) {
            System.out.println( "Audio is unavailable" );
        } 
        catch ( IOException e ) {
            System.out.println( "There was an error" );
        }
        Main.scanner.close();
    }

}
