package fr.umontpellier.etu.inteco;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

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
        return true;
    }
    private void handleSignUp(String email, String password) {
        if (email == null || email.isEmpty() || verifyEmailExists(email)) {
            showRedundantEmailAlert();
        }
        else{
            Intent intent = new Intent(SignUpSeeker.this, SignUpEnterprise.class);
            intent.putExtra("email",email);
            intent.putExtra("password",password);
            startActivity(intent);
        }
    }







    private void showRedundantEmailAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpSeeker.this);
        builder.setTitle("SigningUp failed");
        builder.setMessage("The email you entered is already registerd. Please try again.");
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