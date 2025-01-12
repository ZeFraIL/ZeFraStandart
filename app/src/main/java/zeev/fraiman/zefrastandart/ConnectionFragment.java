package zeev.fraiman.zefrastandart;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ConnectionFragment extends Fragment {

    private Button bConnCall, bConnSMS, bConnMail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_connection, container, false);


        bConnCall = view.findViewById(R.id.bConnCall);
        bConnSMS = view.findViewById(R.id.bConnSMS);
        bConnMail = view.findViewById(R.id.bConnMail);

        bConnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCallDialog();
            }
        });

        bConnSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSMSDialog();
            }
        });

        bConnMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEmailDialog();
            }
        });

        return view;
    }

    private void showCallDialog() {

    }


    private void showSMSDialog() {

    }

    private void showEmailDialog() {

    }
}