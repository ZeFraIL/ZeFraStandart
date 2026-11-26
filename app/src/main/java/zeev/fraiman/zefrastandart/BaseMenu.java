package zeev.fraiman.zefrastandart;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

public class BaseMenu {

    private final Context context;
    private BatteryStatusReceiver batteryReceiver;
    private ChargerConnectionReceiver chargerConnectionReceiver = new ChargerConnectionReceiver();

    public BaseMenu(Context context) {
        this.context = context;
    }

    // Method to display PopupMenu
    public void showPopupMenu(View anchorView) {
        PopupMenu popupMenu = new PopupMenu(context, anchorView);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.main_menu, popupMenu.getMenu());

        // Menu item selection handler
        popupMenu.setOnMenuItemClickListener(this::handleMenuItemClick);

        popupMenu.show();
    }

    // Method to handle menu item clicks
    public boolean handleMenuItemClick(MenuItem item) {
        int id = item.getItemId();

        // Check item states
        if (id == R.id.action_battery_check) {
            item.setChecked(!item.isChecked());
            if (item.isChecked()) {
                enableBatteryCheck();
                Toast.makeText(context, "Battery check enabled", Toast.LENGTH_SHORT).show();
            } else {
                disableBatteryCheck();
                Toast.makeText(context, "Battery check disabled", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        if (id == R.id.action_tts_check) {
            if (item.isChecked()) {
                // If menu item was already checked, disable charger check
                context.unregisterReceiver(chargerConnectionReceiver);
                Toast.makeText(context, "Charger check disabled", Toast.LENGTH_SHORT).show();
            } else {
                // If menu item was unchecked, enable charger check
                IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                context.registerReceiver(chargerConnectionReceiver, filter);
                Toast.makeText(context, "Charger check enabled", Toast.LENGTH_SHORT).show();
            }

            // Toggle menu item state
            item.setChecked(!item.isChecked());
            return true;
        }


        if (id == R.id.action_reminder) {
            Toast.makeText(context, "Reminder", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, Reminder.class);
            context.startActivity(intent);
            return true;
        }

        // Navigation
        if (id == R.id.action_go_back) {
            if (context instanceof android.app.Activity) {
                android.app.Activity activity = (android.app.Activity) context;

                // Use SupportFragmentManager instead of deprecated FragmentManager
                if (activity instanceof androidx.fragment.app.FragmentActivity) {
                    androidx.fragment.app.FragmentActivity fragmentActivity = (androidx.fragment.app.FragmentActivity) activity;
                    if (fragmentActivity.getSupportFragmentManager().getBackStackEntryCount() > 0) {
                        fragmentActivity.getSupportFragmentManager().popBackStack(); // Return to previous fragment
                    } else {
                        activity.finish(); // Return to previous activity
                    }
                } else {
                    activity.finish(); // If not FragmentActivity, simply finish activity
                }
            }
            Toast.makeText(context, "Returned to previous screen", Toast.LENGTH_SHORT).show();
            return true;
        }


        if (id == R.id.action_exit) {
            Toast.makeText(context, "Exiting activity", Toast.LENGTH_SHORT).show();
            if (context instanceof android.app.Activity) {
                ((android.app.Activity) context).finish();
            }
            return true;
        }

        if (id == R.id.action_restart) {
            Toast.makeText(context, "Restarting application", Toast.LENGTH_SHORT).show();
            Intent intent = context.getPackageManager()
                    .getLaunchIntentForPackage(context.getPackageName());
            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
            return true;
        }

        return false;
    }

    private void enableBatteryCheck() {
        if (batteryReceiver == null) {
            batteryReceiver = new BatteryStatusReceiver();
            IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            context.registerReceiver(batteryReceiver, filter);
        }
    }

    private void disableBatteryCheck() {
        if (batteryReceiver != null) {
            context.unregisterReceiver(batteryReceiver);
            batteryReceiver = null;
        }
    }
}
