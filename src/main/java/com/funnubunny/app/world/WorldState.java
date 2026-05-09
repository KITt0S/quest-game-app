package com.funnubunny.app.world;

import com.funnubunny.app.quest.QuestState;

public class WorldState {
    private boolean lighthouseOn = false;

    public boolean isLighthouseOn() {
        return lighthouseOn;
    }

    public void updateFromQuest(QuestState state) {
        if (state == QuestState.LIGHTHOUSE_ON || state == QuestState.COMPLETED) {
            lighthouseOn = true;
            System.out.println("[WORLD] Lighthouse activated. Light returns.");
        }
    }
}
