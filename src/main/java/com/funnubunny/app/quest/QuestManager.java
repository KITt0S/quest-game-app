package com.funnubunny.app.quest;

import com.funnubunny.app.event.EventBus;
import com.funnubunny.app.event.GameEvent;

public class QuestManager implements EventBus.EventListener {

    private QuestState state = QuestState.NOT_STARTED;

    @Override
    public void onEvent(GameEvent event) {

        switch (state) {

            case NOT_STARTED:
                handleNotStarted(event);
                break;

            case TALKED_TO_KEEPER:
                handleTalkedToKeeper(event);
                break;

            case FOUND_FIRST_NOTE:
                handleFoundFirstNote(event);
                break;

            case FOUND_ALL_NOTES:
                handleFoundAllNotes(event);
                break;

            case REACHED_LIGHTHOUSE:
                handleReachedLighthouse(event);
                break;
        }
    }

    private void handleNotStarted(GameEvent event) {
        if (event == GameEvent.TALKED_TO_KEEPER) {
            setState(QuestState.TALKED_TO_KEEPER);
        }
    }

    private void handleTalkedToKeeper(GameEvent event) {
        if (event == GameEvent.NOTE_COLLECTED) {
            setState(QuestState.FOUND_FIRST_NOTE);
        }
    }

    private void handleFoundFirstNote(GameEvent event) {
        if (event == GameEvent.ALL_NOTES_FOUND) {
            setState(QuestState.FOUND_ALL_NOTES);
        }
    }

    private void handleFoundAllNotes(GameEvent event) {
        if (event == GameEvent.REACHED_LIGHTHOUSE) {
            setState(QuestState.REACHED_LIGHTHOUSE);
        }
    }

    private void handleReachedLighthouse(GameEvent event) {
        if (event == GameEvent.CHOSE_RELIGHT ||
                event == GameEvent.CHOSE_DESTROY) {

            setState(QuestState.FINAL_CHOICE);
        }
    }

    private void setState(QuestState newState) {

        if (state == newState) {
            return;
        }

        System.out.println("Quest changed: " + state + " -> " + newState);

        state = newState;
    }

    public QuestState getState() {
        return state;
    }
}
