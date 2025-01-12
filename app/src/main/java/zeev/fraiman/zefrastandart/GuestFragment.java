package zeev.fraiman.zefrastandart;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class GuestFragment extends Fragment {

    private LinearLayout linearLayout;
    Button bGoToInformation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guest, container, false);

        bGoToInformation=view.findViewById(R.id.bGoToInformation);
        bGoToInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go=new Intent(getActivity(), Information.class);
                startActivity(go);
            }
        });
        linearLayout = view.findViewById(R.id.LL);

        return view;
    }
}
