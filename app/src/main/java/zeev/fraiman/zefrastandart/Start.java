package zeev.fraiman.zefrastandart;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class Start extends AppCompatActivity {

    Context context;
    ImageView ivLogo;
    Button bStart;
    CountDownTimer cdt;
    public static TextToSpeech totalTTS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        context=this;

        HelperDB helperDB=new HelperDB(context);
        SQLiteDatabase db=helperDB.getWritableDatabase();
        db.close();

        totalTTS = new TextToSpeech(context,
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status == TextToSpeech.SUCCESS) {
                            totalTTS.setLanguage(Locale.US);

                        }
                    }
                });


        ivLogo=findViewById(R.id.ivLogo);
        bStart=findViewById(R.id.bStart);

        ivLogo.animate().rotation(360f).setDuration(4000).start();

        bStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdt.cancel();
                Intent go=new Intent(Start.this, Welcome.class);
                startActivity(go);
            }
        });

        cdt=new CountDownTimer(4000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Intent go=new Intent(Start.this, Welcome.class);
                startActivity(go);
            }
        }.start();
    }
}