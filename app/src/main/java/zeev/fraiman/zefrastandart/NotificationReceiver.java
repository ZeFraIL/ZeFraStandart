package zeev.fraiman.zefrastandart;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Ensure channels exist before showing notification
        NotificationChannels.createNotificationChannels(context);

        // Get data from Intent
        String title = intent.getStringExtra("title");
        String text = intent.getStringExtra("text");
        int priority = intent.getIntExtra("priority", NotificationCompat.PRIORITY_DEFAULT);

        // Create custom notification layout
        RemoteViews notificationLayout = new RemoteViews(context.getPackageName(), R.layout.notification_layout);
        notificationLayout.setTextViewText(R.id.notification_title, title != null ? title : "Reminder");
        notificationLayout.setTextViewText(R.id.notification_text, text != null ? text : "This is your notification.");
        notificationLayout.setImageViewResource(R.id.notification_image, R.drawable.ic_launcher_foreground);

        // Configure actions for buttons
        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flags |= PendingIntent.FLAG_IMMUTABLE;
        }

        Intent closeIntent = new Intent(context, CloseNotificationService.class);
        PendingIntent closePendingIntent = PendingIntent.getService(context, 0, closeIntent, flags);

        Intent openAppIntent = new Intent(context, Reminder.class);
        openAppIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent openAppPendingIntent = PendingIntent.getActivity(context, 0, openAppIntent, flags);

        notificationLayout.setOnClickPendingIntent(R.id.btn_close, closePendingIntent);
        notificationLayout.setOnClickPendingIntent(R.id.btn_open_app, openAppPendingIntent);

        // Create notification
        Notification notification = new NotificationCompat.Builder(context, NotificationChannels.CHANNEL_HIGH)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setCustomContentView(notificationLayout)
                .setPriority(priority)
                .setAutoCancel(true)
                .build();

        // Show notification
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(1, notification);
        }
    }
}
