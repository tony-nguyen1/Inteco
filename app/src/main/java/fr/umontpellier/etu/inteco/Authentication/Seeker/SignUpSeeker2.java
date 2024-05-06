package fr.umontpellier.etu.inteco.Authentication.Seeker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import fr.umontpellier.etu.inteco.R;

public class SignUpSeeker2 extends AppCompatActivity {
    private String email;
    private String password;
    private String firstName ;
    private String lastName ;
    private String birthday ;
    private String sex;

    private TextInputEditText phoneNumberEditText;
    private TextInputEditText cityEditText;
    private TextInputEditText addressEditText;
    private TextInputEditText nationalityEditText;
    private Button btnNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_seeker2);

        // Retrieve data from the intent
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        firstName = intent.getStringExtra("firstName");
        lastName = intent.getStringExtra("lastName");
        birthday = intent.getStringExtra("birthday");
        sex= intent.getStringExtra("sex");


        // Initialize TextInputEditTexts
        phoneNumberEditText = findViewById(R.id.PhoneNumberEditText);
        cityEditText = findViewById(R.id.CityEditText);
        addressEditText = findViewById(R.id.AddressEditText);
        nationalityEditText = findViewById(R.id.NationalityEditText);

        // Initialize the button and set onClickListener
        Button nextButton = findViewById(R.id.btnNext);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SignUpSeeker2.this, SignUpSeeker3.class);

                // Retrieve values from TextInputEditTexts
                String phoneNumber = phoneNumberEditText.getText().toString();
                String city = cityEditText.getText().toString();
                String address = addressEditText.getText().toString();
                String nationality = nationalityEditText.getText().toString();

                // Add them to the next activity
                intent.putExtra("email",email);
                intent.putExtra("password",password);
                intent.putExtra("firstName",firstName);
                intent.putExtra("lastName",lastName);
                intent.putExtra("birthday",birthday);
                intent.putExtra("sex",sex);
                intent.putExtra("phoneNumber",phoneNumber);
                intent.putExtra("city",city);
                intent.putExtra("address",address);
                intent.putExtra("nationality",nationality);

                startActivity(intent);



            }
        });
    }


}
