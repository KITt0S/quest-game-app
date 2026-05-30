package com.funnubunny.app.dialoguebox;

import java.util.*;

public class DialogueBox {
    private final Queue<String> lines;
    private String line;

    public DialogueBox() {
        this.lines = new LinkedList<>();
    }

    public void setLines(List<String> lines) {
        reset();
        this.lines.addAll(lines);
        next();
    }

    public void next() {
        line = lines.poll();
    }

    public String getCurrentLine() {
        return line == null ? "" : line;
    }

    public void reset() {
        lines.clear();
        line = null;
    }

    public boolean isActive() {
        return line != null;
    }
}
