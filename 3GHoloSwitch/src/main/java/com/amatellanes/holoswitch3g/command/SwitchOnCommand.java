package com.amatellanes.holoswitch3g.command;

import android.content.Context;

/**
 * The Command for switching ON a connection.
 */
public class SwitchOnCommand implements Command {

    private Context context;
    private Connection connection;

    public SwitchOnCommand(Context context, Connection connection) {
        this.connection = connection;
        this.context = context;
    }

    public void execute() {
        connection.switchOn(context);
    }
}
