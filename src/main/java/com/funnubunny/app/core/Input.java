package com.funnubunny.app.core;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;

import java.util.HashSet;
import java.util.Set;

public class Input implements KeyListener {

    private static final Set<Short> pressedKeys = new HashSet<>();

    @Override
    public void keyPressed(KeyEvent event) {
        if (event.isAutoRepeat()) {
            return;
        }

        pressedKeys.add(event.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent event) {
        if (event.isAutoRepeat()) {
            return;
        }

        pressedKeys.remove(event.getKeyCode());
    }

    public static boolean isKeyPressed(short keyCode) {
        return pressedKeys.contains(keyCode);
    }
}
