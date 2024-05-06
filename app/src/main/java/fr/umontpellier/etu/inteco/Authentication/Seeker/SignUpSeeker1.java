package fr.umontpellier.etu.inteco.Authentication.Seeker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import fr.umontpellier.etu.inteco.R;

public class SignUpSeeker1 extends AppCompatActivity {
    String email;
    String password;
    private TextInputEditText tvSignIn;
    private TextInputEditText tvEnterpriseSignUp;
    private TextInputEditText firstNameEditText ;
    private TextInputEditText lastNameEditText;
    private TextInputEditText birthdayEditText;
    private TextInputEditText EditText;

    AutoCompleteTextView sexAutoCompleteTextView;
    private Button btnNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_seeker1);

        // Intent retrieve extra strings
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");

        // Retrieve values from interface
        firstNameEditText= findViewById(R.id.FirstNameEditText);
        lastNameEditText= findViewById(R.id.LastNameEditText);
        birthdayEditText= findViewById(R.id.birthdayEditText);
        sexAutoCompleteTextView = findViewById(R.id.SexAutoCompleteTextView);

        String[] sexOptions = getResources().getStringArray(R.array.sex_options);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, sexOptions);
        sexAutoCompleteTextView.setAdapter(adapter);

        // Optional: Make the dropdown icon non-clickable if it's purely decorative
        sexAutoCompleteTextView.setKeyListener(null);

        // Find the button by its ID
        btnNext = findViewById(R.id.btnNext);

        // Set the OnClickListener
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the new activity
                Intent intent = new Intent(SignUpSeeker1.this, SignUpSeeker2.class);
                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String birthday = birthdayEditText.getText().toString();
                String selectedSex = sexAutoCompleteTextView.getText().toString();

                if (firstName.isEmpty()|| lastName.isEmpty()){
                    showRedundantEmailAlert();
                }
                else {
                    intent.putExtra("email", email);
                    intent.putExtra("password", password);
                    intent.putExtra("firstName", firstName);
                    intent.putExtra("lastName", lastName);
                    intent.putExtra("birthday", birthday);
                    intent.putExtra("sex", selectedSex);
                    startActivity(intent);
                }
            }
        });
    }
    private void showRedundantEmailAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpSeeker1.this);
        builder.setTitle("SigningUp failed");
        builder.setMessage("Please enter first and last name.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // User clicked OK button
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}