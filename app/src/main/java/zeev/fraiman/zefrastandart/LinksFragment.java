package zeev.fraiman.zefrastandart;

import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewTreeViewModelKt;

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
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_links, container, false);
        lvLinks=view.findViewById(R.id.lvLinks);

        buildLinks();

        internetConnectionReceiver = new InternetConnectionReceiver(lvLinks);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

        lvLinks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Link link=all_links.get(position);
                //Toast.makeText(getContext(), ""+link.getLinkType(), Toast.LENGTH_SHORT).show();
                if (link.getLinkType().equals("video"))  {
                    CustomAlertDialog customAlertDialog = new CustomAlertDialog(getContext());
                    customAlertDialog.showDialog(link.getLinkID());
                }
                else  {
                    CustomAlertDialog customAlertDialog = new CustomAlertDialog(getContext());
                    customAlertDialog.showWeb(link.getLinkID());
                }
            }
        });
        return view;
    }

    private void buildLinks() {
        all_links=new ArrayList<>();
        links_names=new ArrayList<>();
        helperDB=new HelperDB(getContext());
        db=helperDB.getReadableDatabase();
        Cursor cursor=db.query(helperDB.LINKS_TABLE,
                null,null,null,
                null,null,null);
        if (cursor.getCount()==0) {
            db.close();
            My_Toast.showToast(getContext(),"Database of Links is empty",11, Color.RED);
            return;
        }
        cursor.moveToFirst();
        while (!cursor.isAfterLast())  {
            String stName=cursor.getString((int)cursor.getColumnIndex(helperDB.LINK_NAME));
            String stDiscr=cursor.getString((int)cursor.getColumnIndex(helperDB.LINK_DESCRIPTION));
            String stID=cursor.getString((int)cursor.getColumnIndex(helperDB.LINK_ID));
            String stType=cursor.getString((int)cursor.getColumnIndex(helperDB.LINK_TYPE));
            Link link=new Link(stName,stDiscr,stType,stID);
            all_links.add(link);
            links_names.add(link.getLinkName());
            cursor.moveToNext();
        }
        db.close();
        adapter=new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1,
                links_names);
        lvLinks.setAdapter(adapter);
    }
}