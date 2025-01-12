package zeev.fraiman.zefrastandart;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.util.Calendar;

public class Reminder extends AppCompatActivity {

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

        // Initialization of interface elements
        context=this;
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
            intervalInSeconds = Integer.parseInt(etInterval.getText().toString());
        } catch (NumberFormatException e) {
            // Оставить значение по умолчанию 0
        }

        // Get the repeat period (if set)
        if (isRepeating) {
            try {
                repeatPeriodInSeconds = Integer.parseInt(etRepeatPeriod.getText().toString());
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

        // If an interval is set, add it to the current time
        if (intervalInSeconds > 0) {
            calendar.add(Calendar.SECOND, intervalInSeconds);
        }

        // AlarmManager setup
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, NotificationReceiver.class);
        intent.putExtra("title", "Reminder");
        intent.putExtra("text", "This is your notification!");
        intent.putExtra("priority", NotificationCompat.PRIORITY_HIGH);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
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
            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    pendingIntent
            );
            Toast.makeText(this, "One-time notification set", Toast.LENGTH_SHORT).show();
        }
    }
}
