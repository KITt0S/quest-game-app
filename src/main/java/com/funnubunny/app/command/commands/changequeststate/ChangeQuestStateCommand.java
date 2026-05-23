package com.funnubunny.app.command.commands.changequeststate;

import com.funnubunny.app.command.commands.GameCommand;
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
