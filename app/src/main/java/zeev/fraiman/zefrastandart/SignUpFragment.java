package zeev.fraiman.zefrastandart;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SignUpFragment extends Fragment {

    Button bSignup;
    EditText etNameSU, etPassSU, etPhoneSU, etEmailSU;
    String stName="", stPass="",stPhone="",stEmail="";
    HelperDB helperDB;
    SQLiteDatabase db;
    User user=null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        helperDB=new HelperDB(getContext());
        etNameSU=view.findViewById(R.id.etNameSU);
        etPassSU=view.findViewById(R.id.etPassSU);
        etPhoneSU=view.findViewById(R.id.etPhoneSU);
        etEmailSU=view.findViewById(R.id.etEmailSU);
        Button signupButton = view.findViewById(R.id.bSignup);

        signupButton.setOnClickListener(v -> {
            stName = etNameSU.getText().toString().trim();
            if (stName.isEmpty()) {
                My_Toast.showToast(requireContext(), "Name field is EMPTY!",
                        11, Color.RED);
                return;
            }
            stPass = etPassSU.getText().toString().trim();
            if (stPass.isEmpty()) {
                My_Toast.showToast(requireContext(), "Password field is EMPTY!",
                        11, Color.RED);
                return;
            }
            stEmail = etEmailSU.getText().toString().trim();
            if (stEmail.isEmpty()) {
                My_Toast.showToast(requireContext(), "E-mail field is EMPTY!",
                        11, Color.RED);
                return;
            }
            stPhone = etPhoneSU.getText().toString().trim();
            if (stPhone.isEmpty()) {
                My_Toast.showToast(requireContext(), "Phone's number field is EMPTY!",
                        11, Color.RED);
                return;
            }
            if (!isUserFound(stName, stPass)) {
                user = new User(stName, stPass, stPhone, stEmail);
                saveUser(user);
                Intent go = new Intent(getActivity(), Junction.class);
                go.putExtra("user", user);
                startActivity(go);
            }
            else {
                My_Toast.showToast(requireContext(), "Enter other name or password, please",
                        11, Color.RED);
            }
        });
        return view;
    }

    private void saveUser(User user) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            SQLiteDatabase db = helperDB.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(helperDB.USER_NAME, stName);
            contentValues.put(helperDB.USER_PASSWORD, stPass);
            contentValues.put(helperDB.USER_EMAIL, stEmail);
            contentValues.put(helperDB.USER_PHONE, stPhone);
            db.insert(helperDB.TABLE_USER, null, contentValues);
            db.close();

            if (getActivity() instanceof Activity) {
                getActivity().runOnUiThread(() -> My_Toast.showToast(requireContext(), "Write data in DB successful",
                        12, Color.GREEN));
            }
        });
    }

    private boolean isUserFound(String stName, String stPassword) {
        boolean flag = false;
        db = helperDB.getReadableDatabase();
        Cursor cursor = db.query(helperDB.TABLE_USER,
                null, null, null,
                null, null, null);
        if (cursor == null) {
            db.close();
            return false;
        }
        if (cursor.getCount() == 0) {
            cursor.close();
            db.close();
            return false;
        }
        cursor.moveToFirst();
        while (!flag && !cursor.isAfterLast()) {
            int nameIndex = cursor.getColumnIndex(helperDB.USER_NAME);
            int passIndex = cursor.getColumnIndex(helperDB.USER_PASSWORD);
            String pointerName = nameIndex != -1 ? cursor.getString(nameIndex) : "";
            String pointerPassword = passIndex != -1 ? cursor.getString(passIndex) : "";
            if (pointerName.equals(stName)) {
                flag = true;
            }
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return flag;
    }
}
