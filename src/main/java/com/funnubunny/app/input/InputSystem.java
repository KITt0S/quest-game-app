package com.funnubunny.app.input;

import com.funnubunny.app.command.*;
import com.funnubunny.app.command.commands.destroylighthouse.DestroyLighthouseCommand;
import com.funnubunny.app.command.commands.interaction.InteractionCommand;
import com.funnubunny.app.command.commands.nextdialogue.NextDialogueCommand;
import com.funnubunny.app.command.commands.relightlighthouse.RelightLighthouseCommand;
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
    }

    public void pressedW() {}

    public void pressedA() {}

    public void pressedS() {}

    public void pressedD() {}

    public void pressedE() {
        if (Input.isKeyPressed(KeyEvent.VK_E)) {
            commandBus.dispatch(new InteractionCommand());
        }
    }

    public void pressedR() {
        if (Input.isKeyPressed(KeyEvent.VK_R)) {
            commandBus.dispatch(new RelightLighthouseCommand());
        }
    }

    public void pressedF() {
        if (Input.isKeyPressed(KeyEvent.VK_R)) {
            commandBus.dispatch(new DestroyLighthouseCommand());
        }
    }

    public void pressedSpace() {
        if (Input.isKeyPressed(KeyEvent.VK_SPACE)) {
            commandBus.dispatch(new NextDialogueCommand());
        }
    }
}
