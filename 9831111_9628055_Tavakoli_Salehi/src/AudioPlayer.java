import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * this audio player class represents a player for .wav files and play them for sound effects.
 *
 * @author narges salehi & sepehr tavakoli
 * @version 1.1
 * @since July 21 2020
 */
public class AudioPlayer {

    //audio file path.
    private String audioFilePath;

    //check playing completed.
    private boolean playCompleted;

    //repeat number.
    private int loop;

    //audio clip.
    private Clip audioClip;

    /**
     * play the audio file here.
     *
     * @param audioFilePath path of the audio file.
     * @param loop          number of repeats.
     */
    public AudioPlayer(String audioFilePath, int loop) {
        this.audioFilePath = audioFilePath;
        this.loop = loop;
        playCompleted = false;
        File audioFile = new File(audioFilePath);

        //start playing.
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            audioClip = AudioSystem.getClip();
            audioClip.open(audioStream);
            audioClip.start();
            audioClip.loop(loop);
        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            ex.printStackTrace();
        }
    }

    public void stop() {
        audioClip.stop();
    }
}