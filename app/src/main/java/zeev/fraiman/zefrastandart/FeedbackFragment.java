package zeev.fraiman.zefrastandart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaRecorder;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
                    }
                }
            }
    );

    private final BroadcastReceiver internetReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateInternetAvailability(context);
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
        bRecordAudio = view.findViewById(R.id.bRecordAudio);

        bRecordAudio.setOnClickListener(v -> {
            if (mediaRecorder == null) {
                startAudioRecording();
                bRecordAudio.setText(getString(R.string.stop_record));
            } else {
                stopAudioRecording();
                bRecordAudio.setText(getString(R.string.start_record));
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
            } else {
                Toast.makeText(requireContext(), R.string.no_internet, Toast.LENGTH_SHORT).show();
            }
        });

        bSaveFeedback.setOnClickListener(v -> saveTextToFile(etFeedback.getText().toString()));

        updateUIState();
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

    private void updateInternetAvailability(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            Network network = cm.getActiveNetwork();
            NetworkCapabilities capabilities = cm.getNetworkCapabilities(network);
            isInternetAvailable = capabilities != null &&
                    capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        } else {
            isInternetAvailable = false;
        }
    }

    private void startSpeechToText() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        speechLauncher.launch(intent);
    }

    private void saveTextToFile(String text) {
        File file = new File(requireContext().getFilesDir(), "feedback.txt");
        try (FileOutputStream fos = new FileOutputStream(file, true)) {
            fos.write((text + "\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), R.string.save_error, Toast.LENGTH_SHORT).show();
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
            Toast.makeText(requireContext(), R.string.start_recording, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), R.string.record_error, Toast.LENGTH_SHORT).show();
        }
    }

    private void stopAudioRecording() {
        if (mediaRecorder != null) {
            try {
                mediaRecorder.stop();
            } catch (RuntimeException e) {
                e.printStackTrace();
                Toast.makeText(requireContext(), R.string.stop_record_error, Toast.LENGTH_SHORT).show();
            } finally {
                mediaRecorder.release();
                mediaRecorder = null;
                Toast.makeText(requireContext(), R.string.finish_recording, Toast.LENGTH_SHORT).show();
            }
        }
    }
}