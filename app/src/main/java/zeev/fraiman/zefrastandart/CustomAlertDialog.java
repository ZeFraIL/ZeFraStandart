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

    // HOW TO CALL THIS!!!
    /*
    // CustomAlertDialog initialization and video playback
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(this);
        customAlertDialog.showDialog("dQw4w9WgXcQ"); // Specify video ID
     */

    private Context context;

    public CustomAlertDialog(Context context) {
        this.context = context;
    }

    public void showDialog(String videoId) {
        // Create dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        // Load dialog layout
        View dialogView = inflater.inflate(R.layout.dialog_layout, null);

        builder.setView(dialogView);

        // YouTubePlayerView initialization
        YouTubePlayerView youTubePlayerView = dialogView.findViewById(R.id.youtube_player_view);

        // Connect lifecycle
        if (context instanceof AppCompatActivity) {
            ((AppCompatActivity) context).getLifecycle().addObserver(youTubePlayerView);
        }

        // Add listener for YouTubePlayer
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                // Load video by ID
                youTubePlayer.loadVideo(videoId, 0); // Start from 0 seconds
            }
        });

        // Configure button to close dialog
        builder.setPositiveButton("Close", (dialog, which) -> dialog.dismiss());

        // Show dialog
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
                800 // WebView height in pixels (can be changed if needed)
        ));

        // Create AlertDialog
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(layout)
                .setCancelable(true)
                .create();

        // Create "Close" button and add to Layout
        Button closeButton = new Button(context);
        closeButton.setText("Close");
        closeButton.setOnClickListener(v -> dialog.dismiss()); // Here dialog is already defined
        layout.addView(closeButton, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        // Show dialog
        dialog.show();
    }

}
