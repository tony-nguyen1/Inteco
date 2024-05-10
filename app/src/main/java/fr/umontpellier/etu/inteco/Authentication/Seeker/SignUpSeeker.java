package fr.umontpellier.etu.inteco.Authentication.Seeker;

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
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import fr.umontpellier.etu.inteco.Authentication.Enterprise.SignUpEnterprise;
import fr.umontpellier.etu.inteco.Authentication.LoginFirstActivity;
import fr.umontpellier.etu.inteco.R;

public class SignUpSeeker extends AppCompatActivity {

    private static final String TAG = "debug signUp";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextInputEditText emailEditText;
    private TextInputEditText passwordEditText;
    private Button btnSignUp;
    private TextView tvSignIn;
    private TextView tvEnterpriseSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("debug", "SignUpSeeker");
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

                Log.d(TAG, "onClick: ("+email+","+password+")");
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

    private boolean verifyEmailExists(String email, MutableLiveData<Boolean> response){
        //TODO complete ( true if it exists and false if not)
        Log.d(TAG, "verifyEmailExists: "+email);
        //response.setValue(Boolean.FALSE); //Initilize with a value


        // read
        Task<QuerySnapshot> q = db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            boolean foundEmail = false;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                                String s = document.get("email", String.class);
                                //Log.d(TAG, "onComplete: "+s);
                                //Log.d(TAG, "onComplete: "+email);

                                assert s != null;
                                if (email.equals(s)){
                                    response.postValue(Boolean.TRUE); // post a value
                                    foundEmail = true;
                                }
                            }
                            //Log.d(TAG, "onComplete: foundEmail="+foundEmail);
                            if (!foundEmail) {
                                //Log.d(TAG, "onComplete: inside if"+foundEmail);
                                response.postValue(Boolean.FALSE); }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        /*response.observe(this,new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean changedValue) {
                //Do something with the changed value
                Log.d(TAG, "onChanged: changedValue="+changedValue);
                Log.d(TAG, changedValue ? email + " already exists" : email + " not in database");
            }
        });*/

        return false;
    }

    private void handleSignUp(String email, String password) {
        Log.d(TAG, "handleSignUp");
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            showRedundantEmailAlert(false);
        } else {
            MutableLiveData<Boolean> listen = new MutableLiveData<>();
            verifyEmailExists(email, listen);

            listen.observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {
                    Log.d(TAG, "onChanged: " + (aBoolean ? email + " already exists" : email + " not in database"));
                    if (aBoolean) { // true
                        showRedundantEmailAlert(true);
                    } else { // false
                        Intent intent = new Intent(SignUpSeeker.this, SignUpSeeker1.class);
                        intent.putExtra("email",email);
                        intent.putExtra("password",password);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    private void showRedundantEmailAlert(boolean option) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpSeeker.this);
        builder.setTitle("SigningUp failed");
        if(option){
            builder.setMessage("The email you entered is already registerd. Please try again.");
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