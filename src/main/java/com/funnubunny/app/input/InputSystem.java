package com.funnubunny.app.input;

import com.funnubunny.app.command.*;
import com.funnubunny.app.command.commands.changeenginestate.ChangeEngineStateCommand;
import com.funnubunny.app.command.commands.interaction.InteractionCommand;
import com.funnubunny.app.command.commands.interaction.ChoiceInteractionCommand;
import com.funnubunny.app.command.commands.moveplayer.MovePlayerCommand;
import com.funnubunny.app.command.commands.nextdialogue.NextDialogueCommand;
import com.funnubunny.app.core.Input;
import com.jogamp.newt.event.KeyEvent;

public class InputSystem {

    private final CommandBus commandBus;

    public InputSystem(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    public void update() {
        pressedW();
        pressedA();
        pressedS();
        pressedD();
        pressedE();
        pressedR();
        pressedF();
        pressedSpace();
        pressedEnter();
    }

    public void pressedW() {
        if (Input.isKeyPressed(KeyEvent.VK_W)) {
            commandBus.dispatch(new MovePlayerCommand(MovePlayerCommand.Direction.TOP));
        }
    }

    public void pressedA() {
        if (Input.isKeyPressed(KeyEvent.VK_A)) {
            commandBus.dispatch(new MovePlayerCommand(MovePlayerCommand.Direction.LEFT));
        }
    }

    public void pressedS() {
        if (Input.isKeyPressed(KeyEvent.VK_S)) {
            commandBus.dispatch(new MovePlayerCommand(MovePlayerCommand.Direction.BOTTOM));
        }
    }

    public void pressedD() {
        if (Input.isKeyPressed(KeyEvent.VK_D)) {
            commandBus.dispatch(new MovePlayerCommand(MovePlayerCommand.Direction.RIGHT));
        }}

    public void pressedE() {
        if (Input.isKeyPressed(KeyEvent.VK_E)) {
            commandBus.dispatch(new InteractionCommand());
            Input.releaseKey(KeyEvent.VK_E);
        }
    }

    public void pressedR() {
        if (Input.isKeyPressed(KeyEvent.VK_R)) {
            commandBus.dispatch(new ChoiceInteractionCommand(ChoiceInteractionCommand.Choice.FIRST));
        }
    }

    public void pressedF() {
        if (Input.isKeyPressed(KeyEvent.VK_F)) {
            commandBus.dispatch(new ChoiceInteractionCommand(ChoiceInteractionCommand.Choice.SECOND));
        }
    }

    public void pressedSpace() {
        if (Input.isKeyPressed(KeyEvent.VK_SPACE)) {
            commandBus.dispatch(new NextDialogueCommand());
            Input.releaseKey(KeyEvent.VK_SPACE);
        }
    }

    public void pressedEnter() {
        if (Input.isKeyPressed(KeyEvent.VK_ENTER)) {
            commandBus.dispatch(new ChangeEngineStateCommand());
        }
    }
}
