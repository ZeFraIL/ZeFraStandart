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
import android.widget.Toast;

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
            stName = etNameSU.getText().toString();
            if (stName.equals("")) {
                My_Toast.showToast(requireContext(), "Name field is EMPTY!",
                        11, Color.RED);
                return;
            }
            stPass = etPassSU.getText().toString();
            if (stPass.equals("")) {
                My_Toast.showToast(requireContext(), "Password field is EMPTY!",
                        11, Color.RED);
                return;
            }
            stEmail = etEmailSU.getText().toString();
            if (stEmail.equals("")) {
                My_Toast.showToast(requireContext(), "E-mail field is EMPTY!",
                        11, Color.RED);
                return;
            }
            stPhone = etPhoneSU.getText().toString();
            if (stPhone.equals("")) {
                My_Toast.showToast(requireContext(), "Phone's number field is EMPTY!",
                        11, Color.RED);
                return;
            }
            if (!isUserFound(stName, stPass)) {
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
        executor.execute(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase db = helperDB.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(helperDB.USER_NAME, stName);
                contentValues.put(helperDB.USER_PASSWORD, stPass);
                contentValues.put(helperDB.USER_EMAIL, stEmail);
                contentValues.put(helperDB.USER_PHONE, stPhone);
                db.insert(helperDB.TABLE_USER, null, contentValues);
                db.close();

                if (getActivity() instanceof Activity) {
                    (getActivity()).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            My_Toast.showToast(requireContext(),"Write data in DB successful",
                                    12, Color.GREEN);
                        }
                    });
                }
            }
        });
    }

    private boolean isUserFound(String stName, String stPassword) {
        boolean flag=false;
        db=helperDB.getReadableDatabase();
        Cursor cursor=db.query(helperDB.TABLE_USER,
                null,null,null,
                null,null,null);
        if (cursor.getCount()==0) {
            db.close();
            return flag;
        }
        cursor.moveToFirst();
        while (flag==false && !cursor.isAfterLast())  {
            String pointerName=cursor.getString((int)cursor.getColumnIndex(helperDB.USER_NAME));
            String pointerPassword=cursor.getString((int)cursor.getColumnIndex(helperDB.USER_PASSWORD));
            if (pointerName.equals(stName) || pointerPassword.equals(stPassword))  {
                String pointerPhone=cursor.getString((int)cursor.getColumnIndex(helperDB.USER_PHONE));
                String pointerMail=cursor.getString((int)cursor.getColumnIndex(helperDB.USER_EMAIL));
                user=new User(pointerName,pointerPassword,pointerPhone,pointerMail);
                Toast.makeText(getActivity(), ""+user.toString(), Toast.LENGTH_SHORT).show();
                flag=true;
            }
            cursor.moveToNext();
        }
        db.close();
        return flag;
    }
}