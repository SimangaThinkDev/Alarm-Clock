import java.time.LocalTime;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.File;
import java.io.IOException;

/*
 * I think we know what runnable is now, but this is where our other logic lies.
 */
public class AlarmClock implements Runnable {

    private final LocalTime alarmTime;
    private final String filePath;

    public AlarmClock(LocalTime alarmTime, String filePath) {
        this.alarmTime = alarmTime;
        this.filePath = filePath;
    }

    @Override
    public void run() {

        // Get the current time for comparison with user chosen time...
        LocalTime now = LocalTime.now();
        
        while ( now.isBefore( alarmTime ) ) {

            try {
                Thread.sleep(1000);

                // Using a carriage return to avoid taking up more space to print more time
                // When we could achieve that in one line...
                System.out.printf( "\r%02d:%02d:%02d",
                                     now.getHour(), 
                                     now.getMinute(), 
                                     now.getSecond() 
                                );
            } catch (InterruptedException e) {
                System.out.println( "Thread was interrupted" );
            }
            // renew the localTime object that was created
            now = LocalTime.now();

        }

        System.out.println( "\nAlarm Noises" );
        // Toolkit.getDefaultToolkit().beep(); // This does not work on Mint

        // Use the playsound method to play alarm
        playSound( filePath );

    }

    /*
     * This method is responsible for Playing our Alarm
     * @param : filePath, String, should be the path to where your desired alarm sound is
     * NB -> should be a wav file. though the logic for that is handled by the run method
     */
    private void playSound( String filePath ) {

        // Create a husk file out of the string path provided
        File audioFile   = new File( filePath );
        // AudioInputStream is in a scanner because it (Like a scanner) needs to be closed
        try ( AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            // What this does for us is it uses our husk file to get the actual audio out of the object 
              ) {
            // Now to use the Clip class so we can effectively play/manipulate the audio file.
            Clip clip = AudioSystem.getClip();
            clip.open( audioStream );
            clip.start(); // Plays the clip

            System.out.println("Press enter to stop the alarm!");
            Main.scanner.nextLine(); // So if I just say this, The program will wait for an [enter] carriage in the terminal

        } catch (UnsupportedAudioFileException e) {
            System.out.println( "File not supported" );
        }
        catch ( LineUnavailableException e ) {
            System.out.println( "Audio is unavailable" );
        } 
        catch ( IOException e ) {
            System.out.println( "There was an error" );
        }
        // As a result of getting used in multiple places, We close the scanner where the
        // program logic ends
        Main.scanner.close();
    }

}
