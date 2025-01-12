package zeev.fraiman.zefrastandart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Locale;

public class FeedbackFragment extends Fragment {

    private EditText etFeedback;
    private Button bSaveFeedback, bSayWrite, bRecordAudio;
    private CheckBox chbSayWrite;
    private boolean isInternetAvailable = false;
    private MediaRecorder mediaRecorder;
    private File audioFile;

    private final ActivityResultLauncher<Intent> speechLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == requireActivity().RESULT_OK && result.getData() != null) {
                    ArrayList<String> speechResult = result.getData()
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (speechResult != null && !speechResult.isEmpty()) {
                        String spokenText = speechResult.get(0);
                        etFeedback.setText(spokenText);
                        saveTextToFile(spokenText);
                        saveAudioToInternalStorage(result.getData());
                    }
                }
            }
    );

    private final BroadcastReceiver internetReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            isInternetAvailable = cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
            updateUIState();
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        etFeedback = view.findViewById(R.id.etFeedback);
        bSaveFeedback = view.findViewById(R.id.bSaveFeedback);
        chbSayWrite = view.findViewById(R.id.chbSayWrite);
        bSayWrite = view.findViewById(R.id.bSayWrite);
        Button bRecordAudio = view.findViewById(R.id.bRecordAudio);

        bRecordAudio.setOnClickListener(v -> {
            if (mediaRecorder == null) {
                startAudioRecording();
                bRecordAudio.setText("STOP record");
            } else {
                stopAudioRecording();
                bRecordAudio.setText("START record");
            }
        });

        etFeedback.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bSaveFeedback.setEnabled(s.length() > 0);
            }
        });

        bSayWrite.setOnClickListener(v -> {
            if (isInternetAvailable && chbSayWrite.isChecked()) {
                startSpeechToText();
            }
        });

        bSaveFeedback.setOnClickListener(v -> saveTextToFile(etFeedback.getText().toString()));

        updateUIState(); // Устанавливаем начальное состояние кнопок
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().registerReceiver(internetReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onPause() {
        super.onPause();
        requireActivity().unregisterReceiver(internetReceiver);
    }

    private void updateUIState() {
        bSayWrite.setEnabled(isInternetAvailable);
    }

    private void startSpeechToText() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        speechLauncher.launch(intent);
    }

    private void saveTextToFile(String text) {
        try {
            File file = new File(requireContext().getFilesDir(), "feedback.txt");
            FileOutputStream fos = new FileOutputStream(file, true);
            fos.write((text + "\n").getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveAudioToInternalStorage(Intent data) {
        try {
            File file = new File(requireContext().getFilesDir(), "feedback_audio.txt");
            FileOutputStream fos = new FileOutputStream(file, true);
            fos.write(("Audio Recorded\n").getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startAudioRecording() {
        try {
            audioFile = new File(requireContext().getFilesDir(), "recorded_audio.3gp");

            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setOutputFile(audioFile.getAbsolutePath());
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            mediaRecorder.prepare();
            mediaRecorder.start();
            System.out.println("Start recording audio");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in recording audio: " + e.getMessage());
        }
    }

    private void stopAudioRecording() {
        try {
            if (mediaRecorder != null) {
                mediaRecorder.stop();
                mediaRecorder.release();
                mediaRecorder = null;
                System.out.println("Finish recording. File: " + audioFile.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error in recording audio: " + e.getMessage());
        }
    }
}
