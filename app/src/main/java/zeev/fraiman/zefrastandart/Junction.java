package zeev.fraiman.zefrastandart;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Junction extends AppCompatActivity {

    Context context;
    private final long totalTime=10000;
    private final long interval=100;
    private final long radius=90;
    TextView tv1;
    ImageView ivPUMJ;
    BaseMenuClass baseMenuClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_junction);

        context=this;

        tv1=findViewById(R.id.tv1);
        goShadow();

        ivPUMJ=findViewById(R.id.ivPUMJ);
        baseMenuClass=new BaseMenuClass(context);
        ivPUMJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseMenuClass.showPopupMenu(v, R.menu.main_menu);
            }
        });
    }

    private void goShadow()  {
        new CountDownTimer(totalTime, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                float elapsedTime=(totalTime-millisUntilFinished)/(float)totalTime;
                float angle=(float) (2*Math.PI*elapsedTime);
                float dx=(float) (radius*Math.cos(angle));
                float dy=(float) (radius*Math.sin(angle));
                tv1.setShadowLayer(1,dx,dy,0xFFF44336);
            }

            @Override
            public void onFinish() {
                tv1.setShadowLayer(0,0,0,0xFFF44336);
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        baseMenuClass.releaseTextToSpeech();
    }
}