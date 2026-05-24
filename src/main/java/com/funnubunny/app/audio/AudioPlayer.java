package com.funnubunny.app.audio;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class AudioPlayer {

    public void play(AudioClip audioClip) {

        Clip clip = audioClip.getClip();

        clip.stop();

        clip.setFramePosition(0);

        clip.start();
    }

    public void loop(AudioClip audioClip) {

        Clip clip = audioClip.getClip();

        clip.loop(Clip.LOOP_CONTINUOUSLY);

        clip.start();
    }

    public void stop(AudioClip audioClip) {

        audioClip.getClip().stop();
    }

    public void setVolume(AudioClip audioClip, float volume) {

        Clip clip = audioClip.getClip();

        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        float min = gainControl.getMinimum();
        float max = gainControl.getValue();

        float gain = min + (max - min) * volume;
        gainControl.setValue(gain);
    }
}