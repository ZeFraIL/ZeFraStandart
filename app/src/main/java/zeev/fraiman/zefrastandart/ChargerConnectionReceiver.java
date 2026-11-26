package zeev.fraiman.zefrastandart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Handler;
import android.widget.Toast;

public class ChargerConnectionReceiver extends BroadcastReceiver {

    private boolean warningDisplayed = false; // Flag to prevent repeated warning at 15%
    private Handler handler; // For executing tasks with delay

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || !Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
            return;
        }

        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

        // Calculate battery percentage
        int batteryPct = (int) ((level / (float) scale) * 100);

        // Check charger connection
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;

        if (isCharging) {
            // If charger is connected, cancel any warnings
            warningDisplayed = false;
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
            }
            return;
        }

        // Actions depending on battery level
        if (batteryPct == 15 && !warningDisplayed) {
            // One-time message at 15%
            Toast.makeText(context, "Battery is low! Please connect charger.", Toast.LENGTH_LONG).show();
            warningDisplayed = true;
        } else if (batteryPct == 10) {
            // Message every minute at 10%
            if (handler == null) {
                handler = new Handler();
            }
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "Critical battery level! Urgently connect charger.", Toast.LENGTH_LONG).show();
                    handler.postDelayed(this, 60000); // Repeat after a minute
                }
            }, 0);
        } else if (batteryPct == 5) {
            // Close app at 5%
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
            }
            Toast.makeText(context, "Battery level critically low! Application will close.", Toast.LENGTH_LONG).show();

            // Force exit application
            System.exit(0);
        }
    }
}
