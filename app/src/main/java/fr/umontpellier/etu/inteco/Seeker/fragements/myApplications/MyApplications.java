package fr.umontpellier.etu.inteco.Seeker.fragements.myApplications;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import fr.umontpellier.etu.inteco.Enterprise.fragements.myPosts.PostedListFragment;
import fr.umontpellier.etu.inteco.R;

public class MyApplications extends AppCompatActivity {
    private String fullName;
    private TextView nameTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_applications);
        fullName = getIntent().getStringExtra("fullName");


        nameTextView = findViewById(R.id.nameTextView);


        nameTextView.setText(fullName);


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_inside, MyAppliedListFragment.newInstance(1))
                .commit();
    }
}