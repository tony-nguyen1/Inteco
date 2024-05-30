package fr.umontpellier.etu.inteco.Authentication.Enterprise;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import fr.umontpellier.etu.inteco.Authentication.LoginFirstActivity;
import fr.umontpellier.etu.inteco.R;
import fr.umontpellier.etu.inteco.helper.Helper;

//TODO problem with company name
public class SignUpEnterprise extends AppCompatActivity {

    private static final String TAG = "debug signUp";
    private TextInputEditText emailEditText;
    private TextInputEditText companyEditText;

    private TextInputEditText passwordEditText;
    private Button btnSignUp;
    private TextView tvSignIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_enterprise);

        // Initialize views
        companyEditText = findViewById(R.id.companyEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvSignIn = findViewById(R.id.tvSignIn);


        // Set up the button click listener
        btnSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                // Get the input text values
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String company = companyEditText.getText().toString();
                Log.d(TAG, "onClick: SignUpEntreprise name="+company);

                handleSignUp(email, password, company);
            }
        });


        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpEnterprise.this, LoginFirstActivity.class);
                startActivity(intent);
            }
        });
    }

        private void verifyEmailExists(String email, MutableLiveData<Boolean> listen) {
            Helper.verifyEmailExists(email, "company", listen);

            //TODO complete ( true if it exists and false if not)
//            return false;
        }

        private void handleSignUp(String email, String password,String companyName) {
            Log.d(TAG, "handleSignUp: name="+companyName);
            if (email == null || email.isEmpty() || password == null || password.isEmpty() || companyName == null || companyName.isEmpty() ) {
                showRedundantEmailAlert(false);
            }

            else {
                MutableLiveData<Boolean> listen = new MutableLiveData<>();
                verifyEmailExists(email, listen);
                listen.observe(SignUpEnterprise.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if (aBoolean) {
                            showRedundantEmailAlert(true);
                        } else {
                            Intent intent = new Intent(SignUpEnterprise.this, SignUpEnterprise1.class);
                            intent.putExtra("email", email);
                            intent.putExtra("password", password);
                            intent.putExtra("companyName", companyName);
                            startActivity(intent);
                        }
                    }
                });
            }
        }


        private void showRedundantEmailAlert(boolean option) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SignUpEnterprise.this);
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