package com.epam.jwd.command;

public interface Command {
    CommandResponse execute(CommandRequest request) throws InterruptedException;

    static Command of(String name) {
        return CommandRegistry.of(name);
    }
}
