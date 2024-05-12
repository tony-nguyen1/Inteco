package fr.umontpellier.etu.inteco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import fr.umontpellier.etu.inteco.Authentication.LoginFirstActivity;

public class shareLocation extends AppCompatActivity {
    private FusedLocationProviderClient locationClient;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_location);


        /** Location **/
        locationClient = LocationServices.getFusedLocationProviderClient(this);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Button shareLocation = findViewById(R.id.myButton2);
        shareLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLocationPermission();
            }
        });





        FloatingActionButton fab = findViewById(R.id.my_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(shareLocation.this, LoginFirstActivity.class);
                startActivity(intent);
            }
        });
    }
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            storeLastLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            storeLastLocation();
        }
    }

    private void storeLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Task<Location> task = locationClient.getLastLocation();
            task.addOnSuccessListener(location -> {
                if (location != null) {
                    // Store the location in SharedPreferences
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putFloat("LastKnownLatitude", (float) location.getLatitude());
                    editor.putFloat("LastKnownLongitude", (float) location.getLongitude());
                    editor.apply();
                }
            });
        }
    }
}