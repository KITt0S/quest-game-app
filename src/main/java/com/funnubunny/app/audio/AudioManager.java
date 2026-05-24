package com.funnubunny.app.audio;

import java.util.HashMap;
import java.util.Map;

public class AudioManager {

    private final AudioPlayer player = new AudioPlayer();

    private final Map<String, AudioClip> clips = new HashMap<>();

    public void load(String name, String path) {

        clips.put(name, new AudioClip(path));
    }

    public void play(String name) {

        AudioClip clip = clips.get(name);

        if (clip != null) {

            player.play(clip);
        }
    }

    public void loop(String name) {

        AudioClip clip = clips.get(name);

        if (clip != null) {

            player.loop(clip);
        }
    }

    public void stop(String name) {

        AudioClip clip = clips.get(name);

        if (clip != null) {

            player.stop(clip);
        }
    }

    public void setVolume(String name, float volume) {

        AudioClip clip = clips.get(name);

        if (clip != null) {

            player.setVolume(clip, volume);
        }
    }
}