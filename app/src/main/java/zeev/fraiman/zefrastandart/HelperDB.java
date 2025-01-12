package zeev.fraiman.zefrastandart;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class HelperDB extends SQLiteOpenHelper {

    private static final String FILE_DB_NAME = "info.db";

    //User Table
    public static final String TABLE_USER = "Users";
    public static final String USER_NAME = "User_Name";
    public static final String USER_PASSWORD = "User_Password";
    public static final String USER_EMAIL = "User_Email";
    public static final String USER_PHONE = "User_Phone";
    // Links table
    public static final String LINKS_TABLE="Links";
    public static final String LINK_NAME="lName";
    public static final String LINK_DESCRIPTION="lDescription";
    public static final String LINK_ID="lID";
    public static final String LINK_TYPE="lType";


    public HelperDB(@Nullable Context context) {
        super(context, FILE_DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String st1="CREATE TABLE IF NOT EXISTS "+TABLE_USER;
        st1+=" ( "+USER_NAME+" TEXT, "+USER_PASSWORD+" TEXT, ";
        st1+=USER_EMAIL+" TEXT, "+USER_PHONE+" TEXT);";
        db.execSQL(st1);

        String st2="CREATE TABLE IF NOT EXISTS "+LINKS_TABLE;
        st2+=" ( "+LINK_NAME+" TEXT, "+LINK_DESCRIPTION+" TEXT, ";
        st2+=LINK_ID+" TEXT, "+LINK_TYPE+" TEXT);";
        db.execSQL(st2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
