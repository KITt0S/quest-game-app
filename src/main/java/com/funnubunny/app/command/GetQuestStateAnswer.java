package com.funnubunny.app.command;

import com.funnubunny.app.quest.QuestState;

public class GetQuestStateAnswer implements GameAnswer {
    private final QuestState questState;

    public GetQuestStateAnswer(QuestState questState) {
        this.questState = questState;
    }

    public QuestState getQuestState() {
        return questState;
    }
}
