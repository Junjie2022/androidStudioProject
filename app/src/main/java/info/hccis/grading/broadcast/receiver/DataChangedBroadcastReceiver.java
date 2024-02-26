package info.hccis.squash.broadcast.receiver;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class DataChangedBroadcastReceiver extends android.content.BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Handle the broadcast event
        Log.d("TestBroadcastReceiver", "Broadcast received (data changed)");
        String message = "Skills assessment data changed.";
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
