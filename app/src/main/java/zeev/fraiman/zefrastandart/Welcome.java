package zeev.fraiman.zefrastandart;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Welcome extends AppCompatActivity {

    Context context;
    ImageView ivPUM;
    BaseMenuClass baseMenuClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        context=this;

        ivPUM=findViewById(R.id.ivPUM);
        baseMenuClass=new BaseMenuClass(context);
        ivPUM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseMenuClass.showPopupMenu(v, R.menu.main_menu);
            }
        });

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);

        bottomNavigation.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId()==R.id.nav_login)
                selectedFragment = new LoginFragment();
            if (item.getItemId()==R.id.nav_signup)
                selectedFragment = new SignUpFragment();
            if (item.getItemId()==R.id.nav_guest)
                selectedFragment = new GuestFragment();


            if (selectedFragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .addToBackStack(null)
                        .commit();
            }
            return true;
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new LoginFragment())
                    .commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        baseMenuClass.releaseTextToSpeech();
    }
}