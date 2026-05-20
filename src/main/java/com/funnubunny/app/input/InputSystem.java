package com.funnubunny.app.input;

import com.funnubunny.app.command.CommandBus;
import com.funnubunny.app.command.DestroyLighthouseCommand;
import com.funnubunny.app.command.InteractionCommand;
import com.funnubunny.app.command.RelightLighthouseCommand;
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
}
