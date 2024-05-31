package fr.umontpellier.etu.inteco.Authentication;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

import fr.umontpellier.etu.inteco.Authentication.Seeker.SignUpSeeker;
import fr.umontpellier.etu.inteco.Authentication.Seeker.SignUpSeeker1;
import fr.umontpellier.etu.inteco.R;
import fr.umontpellier.etu.inteco.helper.Helper;

public class ResetPassword extends AppCompatActivity {

    private static final String TAG = "debug ResetPassword";
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
                else {
                    MutableLiveData<Boolean> listen = new MutableLiveData<>();
                    Helper.verifyEmailExists(email, "allUsers",listen);

                    listen.observe(ResetPassword.this, new Observer<Boolean>() {
                        @Override
                        public void onChanged(Boolean aBoolean) {
                            if (aBoolean) { // true
                                Helper.resetPassword(email, new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "Email sent.");
                                            Toast.makeText(ResetPassword.this, "Email sent", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else { // false
                                showIncorrectEmailAlert(true);
                            }
                        }
                    });
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