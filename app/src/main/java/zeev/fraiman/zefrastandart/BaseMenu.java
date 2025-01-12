package zeev.fraiman.zefrastandart;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.BroadcastReceiver;
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
    private ChargerConnectionReceiver chargerConnectionReceiver=new ChargerConnectionReceiver();

    public BaseMenu(Context context) {
        this.context = context;
    }

    // Метод для отображения PopupMenu
    public void showPopupMenu(View anchorView) {
        PopupMenu popupMenu = new PopupMenu(context, anchorView);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.main_menu, popupMenu.getMenu());

        // Обработчик выбора пунктов меню
        popupMenu.setOnMenuItemClickListener(this::handleMenuItemClick);

        popupMenu.show();
    }

    // Метод для обработки кликов по пунктам меню
    public boolean handleMenuItemClick(MenuItem item) {
        int id = item.getItemId();

        // Проверка состояний пунктов
        if (id == R.id.action_tts_check) {
            item.setChecked(!item.isChecked());
            if (item.isChecked()) {
                enableBatteryCheck();
                Toast.makeText(context, "Проверка заряда включена", Toast.LENGTH_SHORT).show();
            } else {
                disableBatteryCheck();
                Toast.makeText(context, "Проверка заряда выключена", Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        if (id == R.id.action_tts_check) {
            if (item.isChecked()) {
                // Если пункт меню был уже включен, то отключаем проверку
                context.unregisterReceiver(chargerConnectionReceiver);
                Toast.makeText(context, "Проверка зарядного устройства отключена", Toast.LENGTH_SHORT).show();
            } else {
                // Если пункт меню был выключен, включаем проверку
                IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                context.registerReceiver(chargerConnectionReceiver, filter);
                Toast.makeText(context, "Проверка зарядного устройства включена", Toast.LENGTH_SHORT).show();
            }

            // Переключаем состояние пункта меню
            item.setChecked(!item.isChecked());
            return true;
        }


        if (id == R.id.action_reminder) {
            Toast.makeText(context, "Reminder", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, Reminder.class);
            context.startActivity(intent);
            return true;
        }

        // Навигация
        if (id == R.id.action_go_back) {
            if (context instanceof android.app.Activity) {
                android.app.Activity activity = (android.app.Activity) context;

                // Используем SupportFragmentManager вместо устаревшего FragmentManager
                if (activity instanceof androidx.fragment.app.FragmentActivity) {
                    androidx.fragment.app.FragmentActivity fragmentActivity = (androidx.fragment.app.FragmentActivity) activity;
                    if (fragmentActivity.getSupportFragmentManager().getBackStackEntryCount() > 0) {
                        fragmentActivity.getSupportFragmentManager().popBackStack(); // Возврат к предыдущему фрагменту
                    } else {
                        activity.finish(); // Возврат к предыдущей активности
                    }
                } else {
                    activity.finish(); // Если это не FragmentActivity, просто завершаем активность
                }
            }
            Toast.makeText(context, "Возврат на предыдущий экран", Toast.LENGTH_SHORT).show();
            return true;
        }


        if (id == R.id.action_exit) {
            Toast.makeText(context, "Выход из активности", Toast.LENGTH_SHORT).show();
            if (context instanceof android.app.Activity) {
                ((android.app.Activity) context).finish();
            }
            return true;
        }

        if (id == R.id.action_restart) {
            Toast.makeText(context, "Перезапуск приложения", Toast.LENGTH_SHORT).show();
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
