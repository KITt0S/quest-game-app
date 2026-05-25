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

        clip.stop();

        clip.setFramePosition(0);

        clip.loop(Clip.LOOP_CONTINUOUSLY);

        clip.start();
    }

    public void stop(AudioClip audioClip) {

        Clip clip = audioClip.getClip();

        clip.stop();

        clip.setFramePosition(0);
    }

    public void setVolume(AudioClip audioClip, float volume) {

        Clip clip = audioClip.getClip();

        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        volume = Math.max(0.0f, Math.min(1.0f, volume));

        float min = gainControl.getMinimum();

        float gain;

        if (volume == 0f) {

            gain = min;

        } else {

            gain = (float) (20f * Math.log10(volume));
        }

        gainControl.setValue(gain);
    }
}