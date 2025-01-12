package zeev.fraiman.zefrastandart;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class My_Toast {

    public static void showToast(Context context, String message, int picIndex, int color) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(R.layout.my_toast, null);

        ImageView imageView = layout.findViewById(R.id.ivIn);
        TextView textView = layout.findViewById(R.id.tvIn);

        if (picIndex==11)
            imageView.setImageResource(R.drawable.problem);
        if (picIndex==12) {
            imageView.setImageResource(R.drawable.success);
            textView.setBackgroundColor(color);
        }
        textView.setText(message);

        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setView(layout);
        toast.show();
    }

}
