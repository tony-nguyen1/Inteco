package fr.umontpellier.etu.inteco.Seeker.fragements;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import fr.umontpellier.etu.inteco.R;

public class MyApplications extends AppCompatActivity {
    private String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_applications);

        email = getIntent().getStringExtra("email");


    }
}