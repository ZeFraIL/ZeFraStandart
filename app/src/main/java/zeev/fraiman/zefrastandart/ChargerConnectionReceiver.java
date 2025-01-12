package zeev.fraiman.zefrastandart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Handler;
import android.widget.Toast;

public class ChargerConnectionReceiver extends BroadcastReceiver {

    private boolean warningDisplayed = false; // Флаг для предотвращения повторного предупреждения при 15%
    private Handler handler; // Для выполнения задач с задержкой

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || !Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())) {
            return;
        }

        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

        // Рассчитываем процент заряда
        int batteryPct = (int) ((level / (float) scale) * 100);

        // Проверяем подключение к зарядному устройству
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;

        if (isCharging) {
            // Если зарядное устройство подключено, отменяем любые предупреждения
            warningDisplayed = false;
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
            }
            return;
        }

        // Действия в зависимости от уровня заряда
        if (batteryPct == 15 && !warningDisplayed) {
            // Разовое сообщение при 15%
            Toast.makeText(context, "Заряд батареи низкий! Подключите зарядное устройство.", Toast.LENGTH_LONG).show();
            warningDisplayed = true;
        } else if (batteryPct == 10) {
            // Сообщение через каждую минуту при 10%
            if (handler == null) {
                handler = new Handler();
            }
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, "Критический заряд батареи! Срочно подключите зарядное устройство.", Toast.LENGTH_LONG).show();
                    handler.postDelayed(this, 60000); // Повтор через минуту
                }
            }, 0);
        } else if (batteryPct == 5) {
            // Закрытие приложения при 5%
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
            }
            Toast.makeText(context, "Заряд батареи критически низкий! Приложение будет закрыто.", Toast.LENGTH_LONG).show();

            // Принудительное завершение приложения
            System.exit(0);
        }
    }
}
