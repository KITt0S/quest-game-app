package com.funnubunny.app.event;

import com.funnubunny.app.quest.QuestState;

public class QuestStateChangedEvent implements GameEvent {

    private final QuestState questState;

    public QuestStateChangedEvent(QuestState questState) {
        this.questState = questState;
    }

    public QuestState getQuestState() {
        return questState;
    }
}
