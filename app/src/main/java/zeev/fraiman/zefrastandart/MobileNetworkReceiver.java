package zeev.fraiman.zefrastandart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.widget.Button;
import android.widget.Toast;

public class MobileNetworkReceiver extends BroadcastReceiver {

    private Button bSMSMe, bCallme;

    public MobileNetworkReceiver() {}
    public MobileNetworkReceiver(Button bSMSMe, Button bCallme) {
        this.bSMSMe=bSMSMe;
        this.bCallme=bCallme;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
            boolean isMobileNetworkAvailable = capabilities != null && capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR);

            bSMSMe.setEnabled(isMobileNetworkAvailable);
            bCallme.setEnabled(isMobileNetworkAvailable);

            if (!isMobileNetworkAvailable) {
                Toast.makeText(context, "Нет подключения к мобильной сети!", Toast.LENGTH_SHORT).show();
                MediaPlayer mp=MediaPlayer.create(context.getApplicationContext(), R.raw.alarm);
                mp.start();
            }
        }
    }
}
