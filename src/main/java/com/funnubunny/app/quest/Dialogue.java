package com.funnubunny.app.quest;

import java.util.List;

public class Dialogue {
    private final List<String> lines;

    private int index = 0;

    public Dialogue(List<String> lines) {
        this.lines = lines;
    }

    public String getCurrentLine() {
        if (lines.isEmpty()) {
            return "";
        }

        return lines.get(index);
    }

    public void next() {
        if (index < lines.size() - 1) {
            index++;
        }
    }

    public boolean isFinished() {
        return index == lines.size() - 1;
    }

    public void reset() {
        index = 0;
    }
}
