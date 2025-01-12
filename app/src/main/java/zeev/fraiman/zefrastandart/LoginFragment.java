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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LoginFragment extends Fragment {

    EditText etNameL, etPassL;
    String stName="", stPass="";
    Button bLogin;
    HelperDB helperDB;
    SQLiteDatabase db;
    User user=null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        etNameL= view.findViewById(R.id.etNameL);
        etPassL=view.findViewById(R.id.etPassL);
        bLogin = view.findViewById(R.id.bLogin);
        bLogin.setOnClickListener(v -> {
            stName=etNameL.getText().toString();
            stPass=etPassL.getText().toString();
            helperDB=new HelperDB(getActivity());
            db=helperDB.getReadableDatabase();
            if (stName.equals("")) {
                My_Toast.showToast(requireContext(), "E-mail field is EMPTY!",
                        11, Color.RED);
                BaseMenuClass.speak("E-mail field is EMPTY!");
                return;
            }
            if (stPass.equals("")) {
                My_Toast.showToast(requireContext(), "Password field is EMPTY!",
                        11, Color.RED);
                BaseMenuClass.speak("Password field is EMPTY!");
                return;
            }
            if (isUserFound(stName,stPass))  {
                Intent go = new Intent(getActivity(), Junction.class);
                go.putExtra("user",user);
                startActivity(go);
            }
            else {
                My_Toast.showToast(requireContext(), "User not found",
                        11, Color.RED);
                BaseMenuClass.speak("User not found");

            }
        });
        return view;
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
