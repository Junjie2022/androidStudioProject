package info.hccis.squash.broadcast.receiver;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BroadcastSender {

    public static void sendCustomBroadcast(Context context) {
        Intent intent = new Intent("info.hccis.squash.DATA_CHANGED");
        Log.d("BJM BroadcastSender","broadcast - about to send.");
        context.sendBroadcast(intent);
    }

}
