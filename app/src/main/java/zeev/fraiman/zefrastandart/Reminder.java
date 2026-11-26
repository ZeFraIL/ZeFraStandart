package zeev.fraiman.zefrastandart;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.app.NotificationCompat;

import java.util.Calendar;

public class Reminder extends AppCompatActivity {

    private static final int REQUEST_NOTIFICATION_PERMISSION = 101;

    Context context;
    private EditText etInterval;
    private TimePicker timePicker;
    private RadioGroup rgNotificationType;
    private RadioButton rbSingle, rbRepeating;
    private EditText etRepeatPeriod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        context = this;

        // Ensure notification channels exist
        NotificationChannels.createNotificationChannels(this);

        // Check POST_NOTIFICATIONS permission on Android 13+ (API 33+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        REQUEST_NOTIFICATION_PERMISSION
                );
            }
        }

        // Initialization of interface elements
        etInterval = findViewById(R.id.etInterval);
        timePicker = findViewById(R.id.timePicker);
        rgNotificationType = findViewById(R.id.rgNotificationType);
        rbSingle = findViewById(R.id.rbSingle);
        rbRepeating = findViewById(R.id.rbRepeating);
        etRepeatPeriod = findViewById(R.id.etRepeatPeriod);

        rgNotificationType.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbRepeating) {
                etRepeatPeriod.setVisibility(View.VISIBLE);
            } else {
                etRepeatPeriod.setVisibility(View.GONE);
            }
        });

        Button btnSetNotification = findViewById(R.id.btnSetNotification);
        btnSetNotification.setOnClickListener(v -> setNotification());
    }

    private void setNotification() {
        int intervalInSeconds = 0;
        boolean isRepeating = rbRepeating.isChecked();
        int repeatPeriodInSeconds = 0;

        // Get interval time (if set)
        try {
            String intervalStr = etInterval.getText().toString().trim();
            if (!intervalStr.isEmpty()) {
                intervalInSeconds = Integer.parseInt(intervalStr);
            }
        } catch (NumberFormatException e) {
            intervalInSeconds = 0;
        }

        // Get the repeat period (if set)
        if (isRepeating) {
            try {
                String repeatStr = etRepeatPeriod.getText().toString().trim();
                if (repeatStr.isEmpty()) {
                    Toast.makeText(this, "Please enter a valid repeat period.", Toast.LENGTH_SHORT).show();
                    return;
                }
                repeatPeriodInSeconds = Integer.parseInt(repeatStr);
                if (repeatPeriodInSeconds <= 0) {
                    Toast.makeText(this, "Repeat period must be greater than 0.", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter a valid repeat period.", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Setting time from TimePicker
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
        calendar.set(Calendar.MINUTE, timePicker.getMinute());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // If an interval is set, add it to the current time
        if (intervalInSeconds > 0) {
            calendar.add(Calendar.SECOND, intervalInSeconds);
        } else if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            // If selected time today has already passed, schedule for tomorrow
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        // AlarmManager setup
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null) {
            Toast.makeText(this, "Alarm service unavailable", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, NotificationReceiver.class);
        intent.putExtra("title", "Reminder");
        intent.putExtra("text", "This is your notification!");
        intent.putExtra("priority", NotificationCompat.PRIORITY_HIGH);

        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flags |= PendingIntent.FLAG_IMMUTABLE;
        }

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                intent,
                flags
        );

        if (isRepeating) {
            // Repeating notification
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    repeatPeriodInSeconds * 1000L,
                    pendingIntent
            );
            Toast.makeText(this, "Recurring notification set", Toast.LENGTH_SHORT).show();
        } else {
            // One-time notification
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExact(
                            AlarmManager.RTC_WAKEUP,
                            calendar.getTimeInMillis(),
                            pendingIntent
                    );
                } else {
                    alarmManager.setAndAllowWhileIdle(
                            AlarmManager.RTC_WAKEUP,
                            calendar.getTimeInMillis(),
                            pendingIntent
                    );
                }
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(),
                        pendingIntent
                );
            } else {
                alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(),
                        pendingIntent
                );
            }
            Toast.makeText(this, "One-time notification set", Toast.LENGTH_SHORT).show();
        }
    }
}
