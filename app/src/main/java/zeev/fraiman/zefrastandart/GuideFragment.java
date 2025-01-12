package zeev.fraiman.zefrastandart;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GuideFragment extends Fragment {

    private LinearLayout LL_guide;
    private final Button[] buttons = new Button[12];
    private final TextView[] textViews = new TextView[12];
    private final int[] textFiles={R.raw.part1};
    int indexTV=-1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_guide, container, false);

        LL_guide = view.findViewById(R.id.LL_guide);

        for (int i = 0; i < 12; i++) {
            int widthInPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    300, getResources().getDisplayMetrics());

            Button button = new Button(getContext());
            button.setText("Part #" + (i + 1));
            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                    widthInPx,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            buttonParams.bottomMargin = 30;
            button.setLayoutParams(buttonParams);

            TextView textView = new TextView(getContext());
            textView.setText("Part #" + (i + 1));
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            textView.setVisibility(View.GONE);

            final int index = i;
            button.setOnClickListener(v -> {
                if (textViews[index].getVisibility() == View.GONE) {
                    indexTV=index;
                    new ReadFileTask().execute();
                    textViews[index].setVisibility(View.VISIBLE);
                } else {
                    textViews[index].setVisibility(View.GONE);
                }
            });

            LL_guide.addView(button);
            LL_guide.addView(textView);

            buttons[i] = button;
            textViews[i] = textView;
        }
        return view;
    }

    private class ReadFileTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(requireContext(), "Please wait, reading text from file",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                InputStream inputStream = getResources().openRawResource(textFiles[indexTV]);
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            textViews[indexTV].setText(result);
            Toast.makeText(requireContext(), "Reading completed", Toast.LENGTH_SHORT).show();
        }
    }
}
