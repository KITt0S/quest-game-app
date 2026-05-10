package com.funnubunny.app.quest;

import java.util.ArrayList;
import java.util.List;

public class NoteManager {

    private final List<Clue> clues;

    public NoteManager() {
        clues = new ArrayList<>();
    }

    public void addClue(Clue clue) {
        clues.add(clue);
    }

    public int discoveredCount() {
        int count = 0;

        for (Clue clue : clues) {
            if (clue.isDiscovered()) {
                count++;
            }
        }

        return count;
    }

    public boolean hasEnoughClues() {
        return discoveredCount() >= 3;
    }
}
