package com.funnubunny.app.quest;

public class QuestManager {
    private QuestState state = QuestState.ARRIVED;

    public QuestState getState() {
        return state;
    }

    public void setState(QuestState state) {
        this.state = state;
        System.out.println("[QUEST UPDATED] " + state);
    }

    public void advance() {
        switch (state) {
            case ARRIVED -> setState(QuestState.TALKED_TO_KEEPER);
            case TALKED_TO_KEEPER -> setState(QuestState.FOUND_FUSE);
            case FOUND_FUSE -> setState(QuestState.RESTORED_POWER);
            case RESTORED_POWER -> setState(QuestState.LIGHTHOUSE_ON);
            case LIGHTHOUSE_ON -> setState(QuestState.COMPLETED);
            default -> {}
        }
    }
}
