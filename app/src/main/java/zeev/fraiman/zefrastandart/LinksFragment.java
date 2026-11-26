package zeev.fraiman.zefrastandart;

import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class LinksFragment extends Fragment {

    ListView lvLinks;
    ArrayList<Link> all_links;
    ArrayList<String> links_names;
    ArrayAdapter<String> adapter;
    HelperDB helperDB;
    SQLiteDatabase db;
    private InternetConnectionReceiver internetConnectionReceiver;

    @Override
    public void onStart() {
        super.onStart();
        if (internetConnectionReceiver != null) {
            IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            requireContext().registerReceiver(internetConnectionReceiver, filter);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (internetConnectionReceiver != null) {
            requireContext().unregisterReceiver(internetConnectionReceiver);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_links, container, false);
        lvLinks = view.findViewById(R.id.lvLinks);

        buildLinks();

        internetConnectionReceiver = new InternetConnectionReceiver(lvLinks);

        lvLinks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Link link = all_links.get(position);
                if (link.getLinkType().equals("video")) {
                    CustomAlertDialog customAlertDialog = new CustomAlertDialog(getContext());
                    customAlertDialog.showDialog(link.getLinkID());
                } else {
                    CustomAlertDialog customAlertDialog = new CustomAlertDialog(getContext());
                    customAlertDialog.showWeb(link.getLinkID());
                }
            }
        });
        return view;
    }

    private void buildLinks() {
        all_links = new ArrayList<>();
        links_names = new ArrayList<>();
        helperDB = new HelperDB(getContext());
        db = helperDB.getReadableDatabase();
        Cursor cursor = db.query(helperDB.LINKS_TABLE,
                null, null, null,
                null, null, null);
        if (cursor == null) {
            db.close();
            return;
        }
        if (cursor.getCount() == 0) {
            cursor.close();
            db.close();
            My_Toast.showToast(getContext(), "Database of Links is empty", 11, Color.RED);
            return;
        }
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int nameIndex = cursor.getColumnIndex(helperDB.LINK_NAME);
            int descIndex = cursor.getColumnIndex(helperDB.LINK_DESCRIPTION);
            int idIndex = cursor.getColumnIndex(helperDB.LINK_ID);
            int typeIndex = cursor.getColumnIndex(helperDB.LINK_TYPE);

            String stName = nameIndex != -1 ? cursor.getString(nameIndex) : "";
            String stDiscr = descIndex != -1 ? cursor.getString(descIndex) : "";
            String stID = idIndex != -1 ? cursor.getString(idIndex) : "";
            String stType = typeIndex != -1 ? cursor.getString(typeIndex) : "";

            Link link = new Link(stName, stDiscr, stType, stID);
            all_links.add(link);
            links_names.add(link.getLinkName());
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_list_item_1,
                links_names);
        lvLinks.setAdapter(adapter);
    }
}
