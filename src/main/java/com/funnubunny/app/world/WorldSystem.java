package com.funnubunny.app.world;

import com.funnubunny.app.quest.QuestManager;
import com.funnubunny.app.quest.QuestState;

public class WorldSystem {

    private final QuestManager questManager;
    private final IslandScene islandScene;
    private final Lighthouse lighthouse;

    public WorldSystem(QuestManager questManager, IslandScene islandScene, Lighthouse lighthouse) {

        this.questManager = questManager;
        this.islandScene = islandScene;
        this.lighthouse = lighthouse;
    }

    public void update() {

        QuestState state = questManager.getState();

        boolean lighthouseOn = state == QuestState.REACHED_LIGHTHOUSE || state == QuestState.FINAL_CHOICE;

        islandScene.setLighthouseOn(lighthouseOn);

        lighthouse.setActive(lighthouseOn);
    }
}