package fr.umontpellier.etu.inteco.Authentication.Enterprise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import fr.umontpellier.etu.inteco.Enterprise.HomePageEnterprise;
import fr.umontpellier.etu.inteco.R;

public class SignUpEnterprise2 extends AppCompatActivity {
    private TextInputEditText webSiteEditText;
    private TextInputEditText linkFbEditText;
    private TextInputEditText linkLiEditText;
    private TextInputEditText linkInstaEditText;
    private Button btnNext;
    private String email;
    private String password;
    private String companyName;
    private String phoneNumber;
    private String city;
    private String address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_enterprise2);

        // Retreive the data from the precedent activity
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        companyName = intent.getStringExtra("CompanyName");
        phoneNumber = intent.getStringExtra("phoneNumber");
        city = intent.getStringExtra("city");
        address = intent.getStringExtra("address");


        // Initialize the TextInputEditTexts and Button
        webSiteEditText = findViewById(R.id.WebSiteEditText);
        linkFbEditText = findViewById(R.id.LinkFbEditText);
        linkLiEditText = findViewById(R.id.AddressEditText);
        linkInstaEditText = findViewById(R.id.LinkInstaEditText);
        btnNext = findViewById(R.id.btnNext);

        // Setup the OnClickListener for the button
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve values from TextInputEditTexts
                String website = webSiteEditText.getText().toString();
                String facebookLink = linkFbEditText.getText().toString();
                String linkedinLink = linkLiEditText.getText().toString();
                String instagramLink = linkInstaEditText.getText().toString();

                // Handle the data as needed
                handleNextButtonClick(website, facebookLink, linkedinLink, instagramLink, email, password, companyName, phoneNumber, city, address);

                Intent intent2 = new Intent(SignUpEnterprise2.this, HomePageEnterprise.class);
                intent2.putExtra("email", email);
                Log.i("Testing information",website+facebookLink+linkedinLink+instagramLink+email+password+companyName+phoneNumber+city+address);
                startActivity(intent2);
            }
        });
    }

    private void handleNextButtonClick(String website, String facebook, String linkedin, String instagram, String email, String password, String companyName, String phoneNumber, String city, String address) {
        //TODO complete signUp the company here
    }

}