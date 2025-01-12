package zeev.fraiman.zefrastandart;

import static zeev.fraiman.zefrastandart.My_Toast.showToast;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.speech.tts.TextToSpeech;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.view.MenuItem;
import android.view.View;

import java.util.Locale;

public class BaseMenuClass {

    private static Context context = null;
    private static TextToSpeech textToSpeech;
    private static boolean isTextToSpeechEnabled = false;
    private boolean isChargerCheckEnabled = false;


    public BaseMenuClass(Context context) {
        this.context = context;
        initTextToSpeech();
    }

    public void showPopupMenu(View anchorView, int menuResId) {
        PopupMenu popupMenu = new PopupMenu(context, anchorView);
        popupMenu.getMenuInflater().inflate(menuResId, popupMenu.getMenu());

        MenuItem ttsCheckItem = popupMenu.getMenu().findItem(R.id.action_tts_check);
        ttsCheckItem.setChecked(isChargerCheckEnabled);

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                handleMenuItemClick(item);
                return true;
            }
        });

        popupMenu.show();
    }

    private void handleMenuItemClick(MenuItem item) {
        boolean isChecked = item.isCheckable() && !item.isChecked();
        if (item.isCheckable()) {
            item.setChecked(isChecked);
        }

        if (item.getItemId() == R.id.action_battery_check) {
            showToast("Battery check: " + (isChecked ? "Enabled" : "Disabled"));
        }

        if (item.getItemId() == R.id.action_tts_check) {
            isChargerCheckEnabled = isChecked;
            toggleTextToSpeech(isChecked);
            showToast("Sound commentary: " + (isChecked ? "Enabled" : "Disabled"));
        }

        if (item.getItemId() == R.id.action_reminder) {
            showToast("Reminder clicked");
            Intent go=new Intent(context, Reminder.class);
            context.startActivity(go);
        }

        if (item.getItemId() == R.id.action_go_back) {
            if (context instanceof Activity) {
                ((Activity) context).finish();
            }
        }
        if (item.getItemId() == R.id.action_exit) {
            showToast("Exiting app...");
            if (context instanceof Activity) {
                ((Activity) context).finishAffinity();
            }
        }
        if (item.getItemId() == R.id.action_restart) {
            showToast("Restarting app...");
            if (context instanceof Activity) {
                ((Activity) context).recreate();
            }
        }
    }

    private void initTextToSpeech() {
        textToSpeech = new TextToSpeech(context, status -> {
            if (status == TextToSpeech.SUCCESS) {
                textToSpeech.setLanguage(Locale.US);
                showToast("TextToSpeech initialized");
            } else {
                showToast("Failed to initialize TextToSpeech");
            }
        });
    }

    private void toggleTextToSpeech(boolean enable) {
        isTextToSpeechEnabled = enable;
        if (enable) {
            showToast("TextToSpeech enabled");
            speak("TextToSpeech is now enabled.");
        } else {
            showToast("TextToSpeech disabled");
        }
    }

    public static void speak(String text) {
        if (isTextToSpeechEnabled && textToSpeech != null) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            showToast("TextToSpeech is disabled");
        }
    }

    public void releaseTextToSpeech() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

    private static void showToast(String st) {
        My_Toast.showToast(context,st,12, Color.GREEN);
    }
}
