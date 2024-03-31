package info.hccis.grading.broadcast.receiver;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;
import android.provider.Settings;

import info.hccis.grading.net.ApiWatcher;

public class AirplaneModeReceiver extends android.content.BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Handle the broadcast event
        Log.d("JJ Receiver", "AirplaneModeReceiver - onReceive - Broadcast received");
        if (isAirplaneModeOn(context.getApplicationContext())) {
            // Airplane mode is ON
            Log.d("BJM Receiver", "Airplane mode is ON");
            ApiWatcher.setConnectedToNetwork(false); //todo Fix connected to network issue.
            showToast(context, "Grading3 - Airplane mode is ON");

        } else {
            // Airplane mode is OFF
            ApiWatcher.setConnectedToNetwork(true);
            //Log.d("BJM Receiver", "Airplane mode is OFF"); //todo Fix connected to network issue
            showToast(context, "Grading3 - Airplane mode is OFF");
        }
    }


    private boolean isAirplaneModeOn(Context context) {
//        return Settings.System.getInt(
//                context.getContentResolver(),
//                Settings.Global.AIRPLANE_MODE_ON, 0) != 0; //In the context of boolean values, 0 often signifies false, while 1 signifies true.

        //https://stackoverflow.com/questions/4319212/how-can-one-detect-airplane-mode-on-android
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.System.getInt(context.getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON, 0) != 0;
        } else {
            return Settings.Global.getInt(context.getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
        }


    }

    private void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
