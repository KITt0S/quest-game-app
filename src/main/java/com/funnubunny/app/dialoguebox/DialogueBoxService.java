package com.funnubunny.app.dialoguebox;

public class DialogueBoxService {
    private final DialogueBox dialogueBox;

    public DialogueBoxService(DialogueBox dialogueBox) {
        this.dialogueBox = dialogueBox;
    }

    public DialogueBox getDialogueBox() {
        return dialogueBox;
    }
}
