package info.hccis.grading.bo;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.List;

import info.hccis.grading.R;
import info.hccis.grading.GradingWidget;
import info.hccis.grading.entity.GradingAssessmentTechnical;

public class GradingWidgetUtil {

    public static void setWidgetText(Context context){
        //******************************************************************************************
        //BJM Widget.
        //Updating the text on the widget.There are several steps required for this to work.  I'm
        //storing the highest score in SharedPreferences to allow easy access to it when it's needed.
        //
        //https://stackoverflow.com/questions/3455123/programmatically-update-widget-from-activity-service-receiver
        //******************************************************************************************

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String highScore = sharedPref.getString(context.getString(R.string.preference_high_grade), "0");

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.grading_widget);
        ComponentName squashWidget = new ComponentName(context, GradingWidget.class);
        remoteViews.setTextViewText(R.id.appwidget_text, highScore);
        appWidgetManager.updateAppWidget(squashWidget, remoteViews);


    }

    public static void setHighGrade(Context context, List<GradingAssessmentTechnical> newList){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        Double highGrade = 0.0;
        for(GradingAssessmentTechnical current: newList){
           Double currentHighGrade = current.getNumericGrade();
            Log.d("JJ setHighGrade","Checking: "+currentHighGrade);
            if(currentHighGrade > highGrade){
                highGrade = currentHighGrade;
            }
        }

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.preference_high_grade), ""+highGrade);
        editor.commit();

    }
}
