package info.hccis.grading.notification;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

/**
 * Class used to setup app for providing notifications to the user.
 * @since 20240229
 * @author JJ
 */

public class NotificationApplication extends Application {
        public static final String MEMBER_CHANNEL_ID = "GradingChannel";
        private static Context mContext;

        public static Context getContext() {
            return mContext;
        }

        public static void setContext(Context mContext) {
            NotificationApplication.mContext = mContext;
        }
        @Override
        public void  onCreate() {
            super.onCreate();
            createNotificationChannels();

        }
        private void createNotificationChannels(){
            if (Build.VERSION.SDK_INT < 26) {
                return;
            }
            NotificationChannel channel1 = new NotificationChannel(
                    MEMBER_CHANNEL_ID,
                    "Grading Chanel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("Grading Notifications");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
}