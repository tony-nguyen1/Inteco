package fr.umontpellier.etu.inteco.Enterprise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Map;

import fr.umontpellier.etu.inteco.Enterprise.fragements.jobHaver.AcceptedCandidatesEnterprise;
import fr.umontpellier.etu.inteco.Enterprise.fragements.addPost.AddPostEnterprise;
import fr.umontpellier.etu.inteco.Enterprise.fragements.HomeEnterprise;
import fr.umontpellier.etu.inteco.Enterprise.fragements.myPosts.MyPostsEnterprise;
import fr.umontpellier.etu.inteco.Enterprise.fragements.SettingsEnterprise;
import fr.umontpellier.etu.inteco.R;
import fr.umontpellier.etu.inteco.helper.Helper;

public class HomePageEnterprise extends AppCompatActivity {

    private static final String TAG = "debug HomePageEntreprise";
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String email, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_enterprise);




        MutableLiveData<Boolean> listenWhenDbRequestDone = new MutableLiveData<>();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            this.email = currentUser.getEmail();
            MutableLiveData<Map<String, Object>> listen = new MutableLiveData<>();
            Helper.getUser(currentUser, "company",null, listen);
            listen.observe(HomePageEnterprise.this, new Observer<Map<String, Object>>() {
                @Override
                public void onChanged(Map<String, Object> stringObjectMap) {
                    Log.d(TAG, "onChanged: "+stringObjectMap.toString());
                    HomePageEnterprise.this.name = (String) stringObjectMap.get("name");

                    Log.d(TAG, "onChanged: ("+email+", "+HomePageEnterprise.this.name+")");
                    listenWhenDbRequestDone.postValue(Boolean.TRUE);
                }
            });
        }

        listenWhenDbRequestDone.observe(this, new Observer<Boolean>() {
            @Override
            // db request done, it has the name of the company and email adresse
            public void onChanged(Boolean aBoolean) {
                /*********** Navigation Bar **********/
                BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
                bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment fragment = null;
                        int itemId = item.getItemId();
                        if (itemId == R.id.nav_settings) {
                            fragment = SettingsEnterprise.newInstance(name, email);
                        } else if (itemId == R.id.nav_home) {
                            fragment = HomeEnterprise.newInstance(name, email);
                        } else if (itemId == R.id.nav_add_post) {
                            fragment = AddPostEnterprise.newInstance(name, email);
                        } else if (itemId == R.id.nav_posts) {
                            fragment = MyPostsEnterprise.newInstance(name, email);
                        } else if (itemId == R.id.nav_accepted) {
                            fragment = AcceptedCandidatesEnterprise.newInstance(name, email);
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
                            .replace(R.id.fragment_container, HomeEnterprise.newInstance(name, email))
                            .commit();
                    bottomNavigationView.setSelectedItemId(R.id.nav_home); // Default screen
                }

            }
        });
    }
}
