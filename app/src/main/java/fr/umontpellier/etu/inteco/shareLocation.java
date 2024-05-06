package fr.umontpellier.etu.inteco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import fr.umontpellier.etu.inteco.Authentication.LoginFirstActivity;

public class shareLocation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_location);

        FloatingActionButton fab = findViewById(R.id.my_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the ShareLocation Activity
                Intent intent = new Intent(shareLocation.this, LoginFirstActivity.class);
                startActivity(intent);
            }
        });
    }
}