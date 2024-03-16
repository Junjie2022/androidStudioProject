package info.hccis.grading.broadcast.receiver;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BroadcastSender {

    public static void sendCustomBroadcast(Context context) {
        Intent intent = new Intent("info.hccis.grading.DATA_CHANGED");
        Log.d("JJ BroadcastSender","broadcast - about to send.");
        context.sendBroadcast(intent);
    }

}
