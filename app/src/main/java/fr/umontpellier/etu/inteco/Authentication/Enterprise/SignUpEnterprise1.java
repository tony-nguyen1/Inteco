package fr.umontpellier.etu.inteco.Authentication.Enterprise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import fr.umontpellier.etu.inteco.R;

public class SignUpEnterprise1 extends AppCompatActivity {
    private static final String TAG = "debug signUp";
    private String email;
    private String password;
    private String companyName ;


    private TextInputEditText phoneNumberEditText;
    private TextInputEditText cityEditText;
    private TextInputEditText addressEditText;
    private Button btnNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_enterprise1);

        // Retrieve data from the intent
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        companyName = intent.getStringExtra("companyName");



        // Initialize TextInputEditTexts
        phoneNumberEditText = findViewById(R.id.PhoneNumberEditText);
        cityEditText = findViewById(R.id.CityEditText);
        addressEditText = findViewById(R.id.AddressEditText);

        // Initialize the button and set onClickListener
        Button nextButton = findViewById(R.id.btnNext);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SignUpEnterprise1.this, SignUpEnterprise2.class);

                // Retrieve values from TextInputEditTexts
                String phoneNumber = phoneNumberEditText.getText().toString();
                String city = cityEditText.getText().toString();
                String address = addressEditText.getText().toString();

                Log.d(TAG, "onClick: name="+companyName);

                // Add them to the next activity
                intent.putExtra("email",email);
                intent.putExtra("password",password);
                intent.putExtra("companyName",companyName);
                intent.putExtra("phoneNumber",phoneNumber);
                intent.putExtra("city",city);
                intent.putExtra("address",address);
                startActivity(intent);
            }
        });
    }
}