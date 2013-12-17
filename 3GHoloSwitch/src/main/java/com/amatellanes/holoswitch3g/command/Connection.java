package com.amatellanes.holoswitch3g.command;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;
import com.amatellanes.holoswitch3g.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * The Receiver class.
 */
public class Connection {

    private Method setMobileDataEnabledMethod;
    private Object iConnectivityManager;
    private Context context;

    public Connection(Context context) {
        try {
            this.context = context;
            final ConnectivityManager conman = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            final Class conmanClass = Class.forName(conman.getClass().getName());
            final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
            iConnectivityManagerField.setAccessible(true);
            this.iConnectivityManager = iConnectivityManagerField.get(conman);
            final Class iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
            this.setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
            setMobileDataEnabledMethod.setAccessible(true);
        } catch (Exception e) {
            Toast.makeText(context, context.getString(R.string.error_connection), Toast.LENGTH_LONG).show();
        }
    }

    public boolean isOn() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTING ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTING;
    }

    public void switchOn() {
        try {
            setMobileDataEnabledMethod.invoke(iConnectivityManager, true);
        } catch (Exception e) {
            Toast.makeText(context, context.getString(R.string.error_connection), Toast.LENGTH_LONG).show();
        }
    }

    public void switchOff() {
        try {
            setMobileDataEnabledMethod.invoke(iConnectivityManager, false);
        } catch (Exception e) {
            Toast.makeText(context, context.getString(R.string.error_connection), Toast.LENGTH_LONG).show();
        }
    }

}
