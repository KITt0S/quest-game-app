package com.funnubunny.app.ui;

import com.funnubunny.app.quest.QuestState;
import com.jogamp.opengl.GL3;

public class HUD extends UI {
    private QuestState state;
    private boolean canInteract;

    public void setQuestState(QuestState state) {
        this.state = state;
    }

    public void setCanInteract(boolean canInteract) {
        this.canInteract = canInteract;
    }

    @Override
    public void render(GL3 gl) {
//        if (canInteract) {
//            System.out.println("[HUD] Press E to interact");
//        }
//
//        if (state != null) {
//            System.out.println("[QUEST] " + state);
//        }
    }
}
