package zeev.fraiman.zefrastandart;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class AboutMeFragment extends Fragment {

    Button bCallme, bSMSMe, bMailMe;
    private NetworkReceiver networkReceiver;
    private MobileNetworkReceiver mobileNetworkReceiver;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_me, container, false);

        bCallme = view.findViewById(R.id.bCallMe);
        bSMSMe = view.findViewById(R.id.bSMSMe);
        bMailMe = view.findViewById(R.id.bMailMe);

        networkReceiver = new NetworkReceiver(bMailMe);
        mobileNetworkReceiver = new MobileNetworkReceiver(bSMSMe, bCallme);

        requireActivity().registerReceiver(networkReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        requireActivity().registerReceiver(mobileNetworkReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));

        bCallme.setOnClickListener(v -> {
            String phoneNumber = "123456789";
            Uri uriPhoneNumber = Uri.parse("tel:" + phoneNumber);
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(uriPhoneNumber);
            startActivity(callIntent);
        });

        bSMSMe.setOnClickListener(v -> {
            String phoneNum = "123456789";
            String smsText = "Text SMS";
            try {
                SmsManager smgr = SmsManager.getDefault();
                smgr.sendTextMessage(phoneNum, null, smsText, null, null);
                Toast.makeText(getActivity(), "SMS sent successfully", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Error sending SMS", Toast.LENGTH_SHORT).show();
            }
        });

        bMailMe.setOnClickListener(v -> {
            String to = "example@mail.com";
            String subject = "Subject";
            String message = "Message";
            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
            email.putExtra(Intent.EXTRA_SUBJECT, subject);
            email.putExtra(Intent.EXTRA_TEXT, message);
            email.setType("message/rfc822");
            startActivity(Intent.createChooser(email, "Choose an app:"));
        });

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        requireActivity().unregisterReceiver(networkReceiver);
        requireActivity().unregisterReceiver(mobileNetworkReceiver);
    }
}
