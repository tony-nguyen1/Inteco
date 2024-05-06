package fr.umontpellier.etu.inteco.Authentication.Seeker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import fr.umontpellier.etu.inteco.Authentication.Enterprise.SignUpEnterprise;
import fr.umontpellier.etu.inteco.Authentication.LoginFirstActivity;
import fr.umontpellier.etu.inteco.R;

public class SignUpSeeker extends AppCompatActivity {

    private TextInputEditText emailEditText;
    private TextInputEditText passwordEditText;
    private Button btnSignUp;
    private TextView tvSignIn;
    private TextView tvEnterpriseSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_seeker);

        // Initialize views
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvSignIn = findViewById(R.id.tvSignIn);
        tvEnterpriseSignUp = findViewById(R.id.tvEnterpriseSignUp);

        // Set up the button click listener
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the input text values
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Handle the sign-up logic here
                handleSignUp(email, password);
            }
        });


        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpSeeker.this, LoginFirstActivity.class);
                startActivity(intent);
            }
        });

        tvEnterpriseSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpSeeker.this, SignUpEnterprise.class);
                startActivity(intent);
            }
        });
    }

    private boolean verifyEmailExists(String email){
        //TODO complete ( true if it exists and false if not)
        return false;
    }
    private void handleSignUp(String email, String password) {
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            showRedundantEmailAlert(false);
        } else if (verifyEmailExists(email)) {
            showRedundantEmailAlert(true);
        } else{
            Intent intent = new Intent(SignUpSeeker.this, SignUpSeeker1.class);
            intent.putExtra("email",email);
            intent.putExtra("password",password);
            startActivity(intent);
        }
    }









    private void showRedundantEmailAlert(boolean option) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpSeeker.this);
        builder.setTitle("SigningUp failed");
        if(option){
            builder.setMessage("The email you entered is already registerd or your . Please try again.");
        }
        else{
            builder.setMessage("The informations you entered are not complete. Please try again.");
        }
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