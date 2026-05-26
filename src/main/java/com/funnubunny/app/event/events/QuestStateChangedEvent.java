package com.funnubunny.app.event.events;

import com.funnubunny.app.state.QuestState;

public class QuestStateChangedEvent implements StateChangedEvent {

    private final QuestState questState;

    public QuestStateChangedEvent(QuestState questState) {
        this.questState = questState;
    }

    public QuestState getQuestState() {
        return questState;
    }
}
