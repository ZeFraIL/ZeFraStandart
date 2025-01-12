package zeev.fraiman.zefrastandart;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Получение данных из Intent
        String title = intent.getStringExtra("title");
        String text = intent.getStringExtra("text");
        int priority = intent.getIntExtra("priority", NotificationCompat.PRIORITY_DEFAULT);

        // Создание пользовательского макета уведомления
        RemoteViews notificationLayout = new RemoteViews(context.getPackageName(), R.layout.notification_layout);
        notificationLayout.setTextViewText(R.id.notification_title, title != null ? title : "Напоминание");
        notificationLayout.setTextViewText(R.id.notification_text, text != null ? text : "Это ваше уведомление.");
        notificationLayout.setImageViewResource(R.id.notification_image, R.drawable.ic_launcher_foreground);

        // Настройка действий для кнопок
        Intent closeIntent = new Intent(context, CloseNotificationService.class);
        PendingIntent closePendingIntent = PendingIntent.getService(context, 0, closeIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent openAppIntent = new Intent(context, Reminder.class);
        openAppIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent openAppPendingIntent = PendingIntent.getActivity(context, 0, openAppIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        notificationLayout.setOnClickPendingIntent(R.id.btn_close, closePendingIntent);
        notificationLayout.setOnClickPendingIntent(R.id.btn_open_app, openAppPendingIntent);

        // Создание уведомления
        Notification notification = new NotificationCompat.Builder(context, NotificationChannels.CHANNEL_HIGH)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setCustomContentView(notificationLayout)
                .setPriority(priority)
                .setAutoCancel(true)
                .build();

        // Показ уведомления
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }
}
