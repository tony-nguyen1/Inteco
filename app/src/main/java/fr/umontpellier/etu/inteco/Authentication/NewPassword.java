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

public class NewPassword extends AppCompatActivity {

    private TextInputEditText passwordEditText1;
    private TextInputEditText passwordEditText2;
    private Button btnResetPassword;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        passwordEditText1 = findViewById(R.id.passwordEditText1);
        passwordEditText2 = findViewById(R.id.passwordEditText2);
        btnResetPassword = findViewById(R.id.btnResetPassword);
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass1 = passwordEditText1.getText().toString();
                String pass2 = passwordEditText2.getText().toString();

                if(pass1.equals(pass2)){
                    savePassword(email, pass1);
                    Intent intent = new Intent(NewPassword.this, LoginFirstActivity.class);
                    startActivity(intent);
                }
                else{
                    showIncorrectPasswordsAlert();
                }
            }
        });

    }
    private void savePassword(String email,String password){
        //TODO complete
    }


    private void showIncorrectPasswordsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(NewPassword.this);
        builder.setTitle("Reseting Failed");
        builder.setMessage("The Passwords you entered are not the same. Please try again.");
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