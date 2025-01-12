package zeev.fraiman.zefrastandart;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class CustomAlertDialog {

    //ТАК ДЕЛАТЬ ВЫЗОВ!!!
    /*
    // Инициализация CustomAlertDialog и показ видео
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(this);
        customAlertDialog.showDialog("dQw4w9WgXcQ"); // Укажите ID видео
     */

    private Context context;

    public CustomAlertDialog(Context context) {
        this.context = context;
    }

    public void showDialog(String videoId) {
        // Создаем билдера для диалога
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        // Загружаем layout для диалога
        View dialogView = inflater.inflate(R.layout.dialog_layout, null);

        builder.setView(dialogView);

        // Инициализация YouTubePlayerView
        YouTubePlayerView youTubePlayerView = dialogView.findViewById(R.id.youtube_player_view);

        // Подключаем жизненный цикл
        if (context instanceof AppCompatActivity) {
            ((AppCompatActivity) context).getLifecycle().addObserver(youTubePlayerView);
        }

        // Добавляем слушатель для YouTubePlayer
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                // Загружаем видео по ID
                youTubePlayer.loadVideo(videoId, 0); // Начинаем с 0 секунды
            }
        });

        // Настроим кнопку для закрытия диалога
        builder.setPositiveButton("Close", (dialog, which) -> dialog.dismiss());

        // Показываем диалог
        builder.show();
    }

    public void showWeb(String siteAddress)  {
        WebView webView = new WebView(context);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(siteAddress);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(webView, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                800 // Высота WebView в пикселях (можно изменить по необходимости)
        ));

        // Создаем AlertDialog
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(layout)
                .setCancelable(true)
                .create();

        // Создаем кнопку "Закрыть" и добавляем в Layout
        Button closeButton = new Button(context);
        closeButton.setText("Close");
        closeButton.setOnClickListener(v -> dialog.dismiss()); // Здесь dialog уже определен
        layout.addView(closeButton, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        // Показываем диалог
        dialog.show();
    }

}
