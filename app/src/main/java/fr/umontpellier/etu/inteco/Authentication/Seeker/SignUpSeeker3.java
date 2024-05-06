package fr.umontpellier.etu.inteco.Authentication.Seeker;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import fr.umontpellier.etu.inteco.HomePageSeeker;
import fr.umontpellier.etu.inteco.R;

public class SignUpSeeker3 extends AppCompatActivity {
    private String email;
    private String password;
    private String firstName ;
    private String lastName ;
    private String birthday ;
    private String nationality;
    private String phoneNumber;
    private String city;
    private String address;
    private String sex;
    private Button btnUploadCV ;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_seeker3);

        // Retrieve data from the intent
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        firstName = intent.getStringExtra("firstName");
        lastName = intent.getStringExtra("lastName");
        birthday = intent.getStringExtra("birthday");
        nationality= intent.getStringExtra("nationality");
        phoneNumber= intent.getStringExtra("phoneNumber");
        city= intent.getStringExtra("city");
        address= intent.getStringExtra("address");
        sex= intent.getStringExtra("sex");

        btnUploadCV = findViewById(R.id.btnCV);
        btnNext = findViewById(R.id.btnNext);

        btnUploadCV.setOnClickListener(v -> {
            performFileSearch();
        });

        btnNext.setOnClickListener(v -> {
            Log.i("Test Inputs",email+password+firstName+lastName+birthday+nationality+phoneNumber+city+address+sex);
            Intent intent2 = new Intent(SignUpSeeker3.this, HomePageSeeker.class);
            intent2.putExtra("email", email);
            startActivity(intent2);
        });

    }

    private void createAccount(String email, String password, String firstName, String lastName,
                               String birthday, String nationality, String phoneNumber,
                               String city, String address, String sex){
        //TODO add CV to the parameters
        //TODO create the account for the user

    }
    private void performFileSearch() {
        //TODO implement filesearch + permissions
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}