package com.funnubunny.app.quest;

public class QuestManager {
    private QuestState state = QuestState.ARRIVED_ON_ISLAND;

    public QuestState getState() {
        return state;
    }

    public void setState(QuestState state) {
        this.state = state;
        System.out.println("[QUEST UPDATED] " + state);
    }
}
