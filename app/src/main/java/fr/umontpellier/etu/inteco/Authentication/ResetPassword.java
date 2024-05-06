package fr.umontpellier.etu.inteco.Authentication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import fr.umontpellier.etu.inteco.R;

public class ResetPassword extends AppCompatActivity {

    private TextInputEditText emailEditText;
    private Button btnResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);


        emailEditText = findViewById(R.id.emailEditText);
        btnResetPassword = findViewById(R.id.btnResetPassword);

        // Set up button click listeners
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();

                //TODO complete verification method if the mail exists in our data
                if(email == null || email.isEmpty() ){
                    showIncorrectEmailAlert(false);
                }
                else if(verifyEmailExists(email)){
                    showIncorrectEmailAlert(true);
                }
                else{
                    Intent intent = new Intent(ResetPassword.this, NewPassword.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }


            }
        });
    }



    private boolean verifyEmailExists(String email){
        //TODO complete ( true if it exists and false if not)
        return false;
    }




    private void showIncorrectEmailAlert(boolean option) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ResetPassword.this);
        builder.setTitle("Reseting Failed");
        if(option) {
            builder.setMessage("The email you entered is incorrect. Please try again.");
        }else{
            builder.setMessage("Please enter your mail.");
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