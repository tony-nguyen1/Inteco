package fr.umontpellier.etu.inteco.Enterprise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import fr.umontpellier.etu.inteco.Enterprise.fragements.AcceptedCandidatesEnterprise;
import fr.umontpellier.etu.inteco.Enterprise.fragements.AddPostEnterprise;
import fr.umontpellier.etu.inteco.Enterprise.fragements.HomeEnterprise;
import fr.umontpellier.etu.inteco.Enterprise.fragements.MyPostsEnterprise;
import fr.umontpellier.etu.inteco.Enterprise.fragements.SettingsEnterprise;
import fr.umontpellier.etu.inteco.R;

public class HomePageEnterprise extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_enterprise);








        /*********** Navigation Bar **********/
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                int itemId = item.getItemId();
                if (itemId == R.id.nav_settings) {
                    fragment = new SettingsEnterprise();
                } else if (itemId == R.id.nav_home) {
                    fragment = new HomeEnterprise();
                } else if (itemId == R.id.nav_add_post) {
                    fragment = new AddPostEnterprise();
                } else if (itemId == R.id.nav_posts) {
                    fragment = new MyPostsEnterprise();
                } else if (itemId == R.id.nav_accepted) {
                    fragment = new AcceptedCandidatesEnterprise();
                }
                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .commit();
                }
                return true;
            }
        });

        // Set default selection
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeEnterprise())
                    .commit();
            bottomNavigationView.setSelectedItemId(R.id.nav_home); // Default screen
        }
    }
}
