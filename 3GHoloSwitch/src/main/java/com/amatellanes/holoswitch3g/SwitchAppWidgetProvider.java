package com.amatellanes.holoswitch3g;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import com.amatellanes.holoswitch3g.command.Connection;
import com.amatellanes.holoswitch3g.command.SwitchOffCommand;
import com.amatellanes.holoswitch3g.command.SwitchOnCommand;
import com.amatellanes.holoswitch3g.command.Switch;


public class SwitchAppWidgetProvider extends AppWidgetProvider {

    public static String ACTION_SWITCH_UPDATE = "com.amatellanes.holosqitch3g.ACTION_SWITCH_UPDATE";
    public static String EXTRA = "extra";


    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i = 0; i < N; i++) {

            int appWidgetId = appWidgetIds[i];

            // Create an Intent to launch ExampleActivity
            Intent intent = new Intent(ACTION_SWITCH_UPDATE);
            intent.putExtra(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget);
            views.setOnClickPendingIntent(R.id.widget_button, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION_SWITCH_UPDATE)) {

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            boolean state = preferences.getBoolean(EXTRA, false);
            if (!state) {
                Switch aSwitch = new Switch();
                aSwitch.execute(new SwitchOnCommand(context, new Connection()));
            } else {
                Switch aSwitch = new Switch();
                aSwitch.execute(new SwitchOffCommand(context, new Connection()));
            }

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(EXTRA, !state);
            editor.commit();

        }

        super.onReceive(context, intent);

    }

}