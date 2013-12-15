package com.amatellanes.holoswitch3g.command;

import android.content.Context;
import android.widget.Toast;

/**
 * The Receiver class.
 */
public class Connection {

    public Connection() {
    }

    public void switchOn(Context context) {
        Toast.makeText(context, "ON", Toast.LENGTH_LONG).show();
    }

    public void switchOff(Context context) {
        Toast.makeText(context, "OFF", Toast.LENGTH_LONG).show();
    }

}
