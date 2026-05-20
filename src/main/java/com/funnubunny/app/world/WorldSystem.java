package com.funnubunny.app.world;

import com.funnubunny.app.command.CommandBus;
import com.funnubunny.app.command.GetQuestStateAnswer;
import com.funnubunny.app.command.GetQuestStateCommand;
import com.funnubunny.app.quest.QuestState;

public class WorldSystem {
    private final CommandBus commandBus;
    private final IslandScene islandScene;
    private final Lighthouse lighthouse;

    public WorldSystem(CommandBus commandBus, IslandScene islandScene, Lighthouse lighthouse) {
        this.commandBus = commandBus;
        this.islandScene = islandScene;
        this.lighthouse = lighthouse;
    }

    public void update() {
        QuestState state = ((GetQuestStateAnswer) commandBus.dispatch(new GetQuestStateCommand())).getQuestState();

        boolean lighthouseOn = state == QuestState.REACHED_LIGHTHOUSE || state == QuestState.FINAL_CHOICE;

        islandScene.setLighthouseOn(lighthouseOn);

        lighthouse.setActive(lighthouseOn);
    }
}