package com.funnubunny.app.command;

import com.funnubunny.app.command.commands.GameAnswer;
import com.funnubunny.app.command.commands.GameCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandBus {

    private final Map<Class<?>, CommandHandler<?, ?>> commandHandlers = new HashMap<>();

    public <C extends GameCommand, A extends GameAnswer> void register(Class<C> type, CommandHandler<C, A> commandHandler) {
        commandHandlers.put(type, commandHandler);
    }

    @SuppressWarnings("unchecked")
    public <C extends GameCommand, A extends GameAnswer> A dispatch(C command) {
        CommandHandler<C, A> handler = (CommandHandler<C, A>) commandHandlers.get(command.getClass());

        if (handler == null) {
            throw new RuntimeException("No handler registered");
        }

        return handler.handle(command);
    }

    public interface CommandHandler<C extends GameCommand, A extends GameAnswer> {
        A handle(C command);
    }
}
