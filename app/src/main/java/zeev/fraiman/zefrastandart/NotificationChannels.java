package zeev.fraiman.zefrastandart;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

public class NotificationChannels {

    public static final String CHANNEL_LOW = "channel_low";
    public static final String CHANNEL_HIGH = "channel_high";

    public static void createNotificationChannels(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = context.getSystemService(NotificationManager.class);

            NotificationChannel lowChannel = new NotificationChannel(
                    CHANNEL_LOW, "Обычный приоритет", NotificationManager.IMPORTANCE_LOW);

            NotificationChannel highChannel = new NotificationChannel(
                    CHANNEL_HIGH, "Высокий приоритет", NotificationManager.IMPORTANCE_HIGH);

            manager.createNotificationChannel(lowChannel);
            manager.createNotificationChannel(highChannel);
        }
    }
}
