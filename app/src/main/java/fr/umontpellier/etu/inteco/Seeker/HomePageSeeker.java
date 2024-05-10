package fr.umontpellier.etu.inteco.Seeker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.autofill.AutofillId;
import android.widget.LinearLayout;
import android.widget.TextView;

import fr.umontpellier.etu.inteco.R;

public class HomePageSeeker extends AppCompatActivity {

    private static final String TAG = "debug login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page_seeker);

        Intent intent = getIntent();

        Log.d(TAG, "onCreate: "+intent.toString());

        String email, firstname, lastname;
        email = intent.getStringExtra("email");
        firstname = intent.getStringExtra("firstname");
        lastname = intent.getStringExtra("lastname");

        Log.d(TAG, "onCreate: "+email);
        Log.d(TAG, "onCreate: "+firstname);
        Log.d(TAG, "onCreate: "+lastname);

        TextView tv;

        tv = findViewById(R.id.emailTextView);
        tv.setText(email);

        tv = findViewById(R.id.firstnameTextView);
        tv.setText(firstname);

        tv = findViewById(R.id.lastnameTextView);
        tv.setText(lastname);

        /*TextView valueTV = new TextView(this);
        valueTV.setText(email);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                valueTV.setAutofillId(AutofillId.create(valueTV,0));
            }
        }
        valueTV.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT));

        TextView firstnameTV = new TextView(this);
        valueTV.setText(email);
        valueTV.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT));

        TextView lastnameTV = new TextView(this);
        valueTV.setText(lastname);
        valueTV.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT));




        ConstraintLayout constraintLayout = (ConstraintLayout)findViewById(R.id.homeJobSeeker);
        constraintLayout.addView(valueTV);
        constraintLayout.addView(firstnameTV);
        constraintLayout.addView(lastnameTV);*/
    }
}