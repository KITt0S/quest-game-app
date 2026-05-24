package com.funnubunny.app.render;

import com.funnubunny.app.event.events.QuestStateChangedEvent;

public class QuestStateChangedEventTextHandler implements EventTextHandler<QuestStateChangedEvent> {

    @Override
    public String getText(QuestStateChangedEvent event) {
        return event.getQuestState().name();
    }
}
