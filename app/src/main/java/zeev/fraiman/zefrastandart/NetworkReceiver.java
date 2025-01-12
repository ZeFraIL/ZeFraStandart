package zeev.fraiman.zefrastandart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Button;

public class NetworkReceiver extends BroadcastReceiver {
    private Button emailButton;

    public NetworkReceiver() {}

    public NetworkReceiver(Button emailButton) {
        this.emailButton = emailButton;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        emailButton.setEnabled(isConnected); // Блокируем/разблокируем кнопку
    }
}
