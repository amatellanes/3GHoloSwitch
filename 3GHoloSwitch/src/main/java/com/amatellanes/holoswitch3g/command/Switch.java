package com.amatellanes.holoswitch3g.command;

/**
 * The Invoker class.
 */
public class Switch {


    public Switch() {
    }

    public void execute(Command command) {
        command.execute();
    }
}