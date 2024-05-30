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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

import fr.umontpellier.etu.inteco.Authentication.Seeker.SignUpSeeker;
import fr.umontpellier.etu.inteco.Seeker.HomePageSeeker;
import fr.umontpellier.etu.inteco.R;
import fr.umontpellier.etu.inteco.helper.Helper;

public class LoginFirstActivity extends AppCompatActivity {
    private static final String TAG = "debug login";
    private TextInputEditText emailEditText;
    private TextInputEditText passwordEditText;
    private Button btnLogin;
    private Button btnAnonymous;
    private TextView forgotPassword;
    private TextView signUp;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: LoginFirstActivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_first);

        // Initialize views
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        btnLogin = findViewById(R.id.btnLogin);
        btnAnonymous = findViewById(R.id.btnAnonymous);
        forgotPassword = findViewById(R.id.forgotPassword);
        signUp = findViewById(R.id.tvSignUp);

        // Set up button click listeners
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                Log.d(TAG, "onClick: "+email+password);
                handleSignIn(email,password);
            }
        });

        btnAnonymous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginFirstActivity.this, HomePageSeeker.class);
                startActivity(intent);
            }
        });


        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginFirstActivity.this, ResetPassword.class);
                startActivity(intent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginFirstActivity.this, SignUpSeeker.class);
                startActivity(intent);
            }
        });

        // ...
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
//            reload();
//            finish();
//            startActivity(getIntent());

        }

    }

    private void handleSignIn(String email, String password) {
        Log.d(TAG, "handleSignIn: "+email+", "+password);

        MutableLiveData<Map<String,Object>> listenInfo = new MutableLiveData<>();

        if (email == null || email.isEmpty() || password==null|| password.isEmpty()|| !verifyLoggingInInfo(email,password,listenInfo)) {
            showRedundantEmailAlert();
        }
        else{
            //TODO Add the intenent to the first activity after signin
            /*Intent intent = new Intent(LoginFirstActivity.this, HomePageSeeker.class);
              intent.putExtra("email",email);
              startActivity(intent);*/
        }

        listenInfo.observe(this, new Observer<Map<String,Object>>()  {
            @Override
            public void onChanged(Map<String, Object> theUserInfo) {
                Log.d(TAG, "onChanged: "+theUserInfo.toString());
                Intent intent = new Intent(LoginFirstActivity.this, HomePageSeeker.class);
                intent.putExtra("email", theUserInfo.get("email").toString());
                intent.putExtra("firstname", theUserInfo.get("firstname").toString());
                intent.putExtra("lastname", theUserInfo.get("lastname").toString());
              startActivity(intent);
            }
        });
    }

    private boolean verifyLoggingInInfo(String email, String password, MutableLiveData<Map<String,Object>> responseInfo){
        Log.d(TAG, "verifyLoggingInInfo: ");
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        MutableLiveData<FirebaseUser> listenAuth = new MutableLiveData<>();
        //TODO complete ( true if info are correct and false if not)

        listenAuth.observe(LoginFirstActivity.this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                MutableLiveData<Helper.userType> listen = new MutableLiveData<>();
                Helper.checkTypeUser(firebaseUser.getEmail(),listen);
                listen.observe(LoginFirstActivity.this, new Observer<Helper.userType>() {
                    @Override
                    public void onChanged(Helper.userType userType) {
                        Log.d(TAG, "onChanged: userType="+userType);
                        String type = null;
                        switch (userType) {
                            case ENTREPRISE:
                                type = "company";
                                break;
                            case JOB_SEEKER:
                                type = "users";
                                break;
                        }

                        Helper.getUser(firebaseUser, type, responseInfo);
                    }
                });
            }
        });


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                            listenAuth.postValue(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginFirstActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                            response.postValue(null);
                        }
                    }
                });
        Log.d(TAG, "verifyLoggingInInfo: done");


        return true;
    }
    private void showRedundantEmailAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginFirstActivity.this);
        builder.setTitle("LoggingIn failed");
        builder.setMessage("The email or the password you entered is incorrect. Please try again.");
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




