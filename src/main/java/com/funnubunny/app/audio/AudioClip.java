package com.funnubunny.app.audio;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class AudioClip {

    private final Clip clip;

    public AudioClip(String path) {

        try {

            InputStream inputStream = getClass().getResourceAsStream(path);

            if (inputStream == null) {

                throw new RuntimeException("Audio file not found: " + path);
            }

            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);

            clip = AudioSystem.getClip();

            clip.open(audioInputStream);

        } catch (
                UnsupportedAudioFileException | IOException | LineUnavailableException e) {

            throw new RuntimeException("Failed to load audio: " + path, e);
        }
    }

    public Clip getClip() {

        return clip;
    }
}