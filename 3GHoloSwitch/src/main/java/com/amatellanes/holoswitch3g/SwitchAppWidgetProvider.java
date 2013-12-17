package com.amatellanes.holoswitch3g;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.Toast;
import com.amatellanes.holoswitch3g.command.*;


public class SwitchAppWidgetProvider extends AppWidgetProvider {

    public static String ACTION_SWITCH_UPDATE = "com.amatellanes.holosqitch3g.ACTION_SWITCH_UPDATE";
    public static String CURRENT_STATE = "com.amatellanes.holosqitch3g.CURRENT_STATE";


    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i = 0; i < N; i++) {

            int appWidgetId = appWidgetIds[i];

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget);

            Connection connection = new Connection(context);
            boolean state = connection.isOn();  // Enabled or disabled 3G connection

            int redId = (state) ? R.drawable.ic_enable_3g : R.drawable.ic_disable_3g;
            views.setImageViewResource(R.id.widget_button, redId);

            // Create an Intent to update 3G connection state
            Intent intent = new Intent(ACTION_SWITCH_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intent.putExtra(CURRENT_STATE, state);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, appWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.widget_button, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        super.onReceive(context, intent);

        if (intent.getAction().equals(ACTION_SWITCH_UPDATE)) {

            Connection connection = new Connection(context);
            Switch aSwitch = new Switch();

            boolean state = intent.getBooleanExtra(CURRENT_STATE, false); // Current 3G connection state
            Command command = (state) ? new SwitchOffCommand(connection) : new SwitchOnCommand(connection);
            aSwitch.execute(command);

            updateWidget(context, intent);
        }

    }

    public void updateWidget(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName thisAppWidget = new ComponentName(context.getPackageName(), SwitchAppWidgetProvider.class.getName());
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);

            onUpdate(context, appWidgetManager, appWidgetIds);
        }
    }

}