package com.funnubunny.app.render;

import com.funnubunny.app.event.GameEvent;
import com.funnubunny.app.event.QuestStateChangedEvent;

public class QuestStateChangedEventTextHandler implements EventTextHandler<QuestStateChangedEvent> {

    @Override
    public String getText(QuestStateChangedEvent event) {
        return event.getQuestState().name();
    }
}
