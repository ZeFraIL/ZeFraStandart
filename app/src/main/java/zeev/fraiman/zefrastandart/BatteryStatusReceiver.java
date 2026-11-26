package zeev.fraiman.zefrastandart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BatteryStatusReceiver extends BroadcastReceiver {

    private int lastShownPercentage = -1;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int batteryPct = (int) ((level / (float) scale) * 100);

            if ((batteryPct <= 15 && lastShownPercentage == -1) || (lastShownPercentage > batteryPct && batteryPct < lastShownPercentage)) {
                lastShownPercentage = batteryPct;

                // Create custom Toast
                Toast customToast = new Toast(context);

                // Create root Layout
                LinearLayout toastLayout = new LinearLayout(context);
                toastLayout.setOrientation(LinearLayout.HORIZONTAL);
                toastLayout.setPadding(20, 10, 20, 10);
                toastLayout.setGravity(Gravity.CENTER_VERTICAL);
                toastLayout.setBackgroundColor(0xFFFF0000); // Red background

                // Add icon
                ImageView icon = new ImageView(context);
                icon.setImageResource(R.drawable.low_level_battery); // Replace with your icon
                LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(100, 100); // Icon dimensions
                iconParams.setMargins(0, 0, 20, 0); // Margins
                icon.setLayoutParams(iconParams);

                // Add text
                TextView textView = new TextView(context);
                textView.setText("Battery level too low: " + batteryPct + "%");
                textView.setTextSize(18f);
                textView.setTextColor(0xFFFFFFFF); // White text

                // Add elements to Layout
                toastLayout.addView(icon);
                toastLayout.addView(textView);

                // Configure Toast
                customToast.setView(toastLayout);
                customToast.setDuration(Toast.LENGTH_LONG);
                customToast.setGravity(Gravity.CENTER, 0, 0);
                customToast.show();
            }
        }
    }
}
