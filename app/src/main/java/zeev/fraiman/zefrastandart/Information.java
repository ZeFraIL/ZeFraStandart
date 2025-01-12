package zeev.fraiman.zefrastandart;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import com.google.android.material.navigation.NavigationView;

public class Information extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    Context context;
    private ChargerConnectionReceiver chargerConnectionReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        context=this;
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        chargerConnectionReceiver = new ChargerConnectionReceiver();

        try {
            context.unregisterReceiver(chargerConnectionReceiver);
        } catch (IllegalArgumentException e) {

        }

        drawerLayout.post(() -> {
            float density = getResources().getDisplayMetrics().density;
            int peekSize = (int) (1 * density + 0.5f);
            drawerLayout.openDrawer(navigationView);
            drawerLayout.setDrawerElevation(peekSize);
        });

        navigationView.setNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            if (item.getItemId()==R.id.nav_about_me)
                selectedFragment = new AboutMeFragment();
            if (item.getItemId()==R.id.nav_guide)
                selectedFragment = new GuideFragment();
            if (item.getItemId()==R.id.nav_feedback)
                selectedFragment = new FeedbackFragment();
            if (item.getItemId()==R.id.nav_my_diary)
                selectedFragment = new MyDiaryFragment();
            if (item.getItemId()==R.id.nav_links)
                selectedFragment = new LinksFragment();

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
                drawerLayout.closeDrawer(GravityCompat.START, false);
            }
            return true;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(chargerConnectionReceiver);
        } catch (IllegalArgumentException e) {

        }
    }

}
