package game;

import sun.audio.*;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Erfan on 25/06/2016.
 */
public class MusicPlayer {

    AudioPlayer MGP = AudioPlayer.player;
    AudioStream BGM;
    AudioData MD;
    ContinuousAudioDataStream loop = null;
    AudioDataStream lp = null;

    public MusicPlayer()
    {

    }

    public void music(String url) {

        try {
            BGM = new AudioStream(new FileInputStream(url));
            MD = BGM.getData();
            lp = new AudioDataStream(MD);

            MGP.start(lp);
        } catch (IOException error) {
        }

    }

    public void ContinuesMusic(String url)
    {
        try {
            BGM = new AudioStream(new FileInputStream(url));
            MD = BGM.getData();
            loop = new ContinuousAudioDataStream(MD);

            MGP.start(loop);
        } catch (IOException error) {
        }

    }


}
