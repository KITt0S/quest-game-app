package com.funnubunny.app.command;

import com.funnubunny.app.quest.QuestState;

public class ChangeQuestStateCommand implements GameCommand {
    private final QuestState questState;

    public ChangeQuestStateCommand(QuestState questState) {
        this.questState = questState;
    }

    public QuestState getQuestState() {
        return questState;
    }
}
