package com.amatellanes.holoswitch3g.command;

import android.content.Context;

/**
 * The Command for switching OFF a connection.
 */
public class SwitchOffCommand implements Command {

    private Context context;
    private Connection connection;

    public SwitchOffCommand(Context context, Connection connection) {
        this.connection = connection;
        this.context = context;
    }

    public void execute() {
        connection.switchOff(context);
    }

}