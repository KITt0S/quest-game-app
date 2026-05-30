package com.funnubunny.app.command.commands.moveplayer;

import com.funnubunny.app.command.commands.GameCommand;

public class MovePlayerCommand implements GameCommand {
    private final Direction direction;

    public MovePlayerCommand(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public enum Direction {
        LEFT,
        RIGHT,
        TOP,
        BOTTOM
    }
}
