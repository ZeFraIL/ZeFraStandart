package zeev.fraiman.zefrastandart;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class MyDiaryFragment extends Fragment {

    private BaseMenu baseMenu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_my_diary, container, false);

        baseMenu = new BaseMenu(requireContext());

        CustomToolbar customToolbar = view.findViewById(R.id.custom_toolbar);
        customToolbar.setTitle("My diary");
        customToolbar.getRotatingSpiral().setOnClickListener(v -> baseMenu.showPopupMenu(v));

        return view;
    }



}