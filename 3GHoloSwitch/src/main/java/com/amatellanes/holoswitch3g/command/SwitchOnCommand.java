package com.amatellanes.holoswitch3g.command;

import android.content.Context;

/**
 * The Command for switching ON a connection.
 */
public class SwitchOnCommand implements Command {

    private Connection connection;

    public SwitchOnCommand(Connection connection) {
        this.connection = connection;
    }

    public void execute() {
        connection.switchOn();
    }
}
