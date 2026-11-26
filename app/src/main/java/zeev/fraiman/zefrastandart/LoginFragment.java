package zeev.fraiman.zefrastandart;

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

public class LoginFragment extends Fragment {

    Button bLogin;
    EditText etName, etPass;
    String stName="", stPass="";
    HelperDB helperDB;
    SQLiteDatabase db;
    User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        helperDB=new HelperDB(getContext());
        etName=view.findViewById(R.id.etNameL);
        etPass=view.findViewById(R.id.etPassL);
        bLogin=view.findViewById(R.id.bLogin);

        bLogin.setOnClickListener(v -> {
            stName = etName.getText().toString().trim();
            if (stName.isEmpty()) {
                My_Toast.showToast(requireContext(), "Name field is EMPTY!",
                        11, Color.RED);
                BaseMenuClass.speak("Name field is EMPTY!");
                return;
            }
            stPass = etPass.getText().toString().trim();
            if (stPass.isEmpty()) {
                My_Toast.showToast(requireContext(), "Password field is EMPTY!",
                        11, Color.RED);
                BaseMenuClass.speak("Password field is EMPTY!");
                return;
            }

            if (isUserFound(stName, stPass)) {
                My_Toast.showToast(requireContext(), "User found!",
                        11, Color.GREEN);
                BaseMenuClass.speak("User found");
                Intent go = new Intent(getActivity(), Junction.class);
                go.putExtra("user", user);
                startActivity(go);
            } else {
                My_Toast.showToast(requireContext(), "User not found!",
                        11, Color.RED);
                BaseMenuClass.speak("User not found");
            }
        });
        return view;
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
            int phoneIndex = cursor.getColumnIndex(helperDB.USER_PHONE);
            int emailIndex = cursor.getColumnIndex(helperDB.USER_EMAIL);

            String pointerName = nameIndex != -1 ? cursor.getString(nameIndex) : "";
            String pointerPassword = passIndex != -1 ? cursor.getString(passIndex) : "";
            if (pointerName.equals(stName) && pointerPassword.equals(stPassword)) {
                String pointerPhone = phoneIndex != -1 ? cursor.getString(phoneIndex) : "";
                String pointerMail = emailIndex != -1 ? cursor.getString(emailIndex) : "";
                user = new User(pointerName, pointerPassword, pointerPhone, pointerMail);
                flag = true;
            }
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return flag;
    }

}
