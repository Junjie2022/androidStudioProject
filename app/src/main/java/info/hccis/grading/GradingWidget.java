package info.hccis.grading;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

import info.hccis.grading.R;
import info.hccis.grading.util.CisUtility;

/**
 * Implementation of App Widget functionality.
 * This app will be designed to show the highest score to the user.
 * @since 20240321
 * @author BJM
 */
public class GradingWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        //******************************************************************************************
        // Using the default shared preferences.  Using the application context - may want to access the
        // shared prefs from other activities.
        //******************************************************************************************

        Log.d("JJ widget","updating app widget");
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        String highGrade = sharedPref.getString(context.getString(R.string.preference_high_grade), "0");

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.grading_widget);
        views.setTextViewText(R.id.appwidget_text, highGrade);
        Log.d("JJ widget","High grade"+highGrade);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        Log.d("JJ widget onUpdate","Triggered "+ CisUtility.getTodayString("yyyyMMdd hh:mm:ss"));
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}