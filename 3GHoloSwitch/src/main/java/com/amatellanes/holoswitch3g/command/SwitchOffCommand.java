package com.amatellanes.holoswitch3g.command;

import android.content.Context;

/**
 * The Command for switching OFF a connection.
 */
public class SwitchOffCommand implements Command {

    private Connection connection;

    public SwitchOffCommand(Connection connection) {
        this.connection = connection;
    }

    public void execute() {
        connection.switchOff();
    }

}