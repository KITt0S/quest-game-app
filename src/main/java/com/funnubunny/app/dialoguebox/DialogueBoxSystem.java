package com.funnubunny.app.dialoguebox;

import com.funnubunny.app.command.*;
import com.funnubunny.app.command.commands.*;
import com.funnubunny.app.command.commands.isactivedialoguebox.IsActiveDialogueBoxAnswer;
import com.funnubunny.app.command.commands.isactivedialoguebox.IsActiveDialogueBoxCommand;
import com.funnubunny.app.command.commands.nextdialogue.NextDialogueCommand;
import com.funnubunny.app.command.commands.showdialogue.ShowDialogueCommand;

public class DialogueBoxSystem {
    private final DialogueBox dialogueBox;

    public DialogueBoxSystem(DialogueBox dialogueBox, CommandBus commandBus) {
        this.dialogueBox = dialogueBox;
        commandBus.register(IsActiveDialogueBoxCommand.class, this::isActiveDialogueBox);
        commandBus.register(ShowDialogueCommand.class, this::showDialogue);
        commandBus.register(NextDialogueCommand.class, this::next);
    }

    private IsActiveDialogueBoxAnswer isActiveDialogueBox(IsActiveDialogueBoxCommand command) {
        return new IsActiveDialogueBoxAnswer(dialogueBox.isActive());
    }

    private VoidAnswer showDialogue(ShowDialogueCommand gameCommand) {
        dialogueBox.show(gameCommand.getDialogue());
        return new VoidAnswer();
    }

    private VoidAnswer next(NextDialogueCommand command) {
        if (dialogueBox.isActive()) {
            dialogueBox.next();
        }

        return new VoidAnswer();
    }
}
