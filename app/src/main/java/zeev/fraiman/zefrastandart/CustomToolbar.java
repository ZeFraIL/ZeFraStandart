package zeev.fraiman.zefrastandart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.view.animation.RotateAnimation;
import android.view.animation.LinearInterpolator;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

public class CustomToolbar extends LinearLayout {
    private ImageView leftImageView;
    private TextView titleTextView;
    private AppCompatImageView rotatingSpiral;

    public CustomToolbar(Context context) {
        super(context);
        init(context);
    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(HORIZONTAL);

        leftImageView = new ImageView(context);
        leftImageView.setImageResource(R.drawable.smartwolf);
        leftImageView.setLayoutParams(new LayoutParams(150, 150));

        titleTextView = new TextView(context);
        titleTextView.setText("ZeFra Toolbar");
        titleTextView.setTypeface(Typeface.create("PT Serif", Typeface.BOLD));
        titleTextView.setTextSize(24f);
        titleTextView.setTextColor(Color.parseColor("#25E4D2"));
        titleTextView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        titleTextView.setPadding(100,0,100,0);
        titleTextView.setGravity(Gravity.CENTER);

        rotatingSpiral = new AppCompatImageView(context);
        rotatingSpiral.setImageResource(R.drawable.spiral);
        rotatingSpiral.setLayoutParams(new LayoutParams(150, 150));

        RotateAnimation rotate = new RotateAnimation(0, 360,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(2000);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setRepeatCount(RotateAnimation.INFINITE);
        rotatingSpiral.startAnimation(rotate);

        rotatingSpiral.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });

        addView(leftImageView);
        addView(titleTextView);
        addView(rotatingSpiral);
    }

    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(getContext(), view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.main_menu, popupMenu.getMenu());
        popupMenu.show();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void setTitle(String myTitle) {
        titleTextView.setText(myTitle);
    }

    public AppCompatImageView getRotatingSpiral() {
        return rotatingSpiral;
    }
}
