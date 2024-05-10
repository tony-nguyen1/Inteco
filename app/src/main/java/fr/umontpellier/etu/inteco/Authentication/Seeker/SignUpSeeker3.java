package fr.umontpellier.etu.inteco.Authentication.Seeker;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import fr.umontpellier.etu.inteco.Seeker.HomePageSeeker;
import fr.umontpellier.etu.inteco.R;

public class SignUpSeeker3 extends AppCompatActivity {
    private static final String TAG = "debug signUp";
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
    private FirebaseAuth mAuth;

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
            //TODO check if inputs are empty or not ???
            createAccount(email,password,firstName,lastName,birthday,nationality,phoneNumber,city,address,sex);
            Intent intent2 = new Intent(SignUpSeeker3.this, HomePageSeeker.class);
            intent2.putExtra("email", email);
            intent2.putExtra("firstname", firstName);
            intent2.putExtra("lastname", lastName);
            startActivity(intent2);
        });


        // ...
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    private void createAccount(String email, String password, String firstName, String lastName,
                               String birthday, String nationality, String phoneNumber,
                               String city, String address, String sex){
        //TODO add CV to the parameters
        //TODO create the account for the user
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("email", email);
        user.put("password", password);
        user.put("firstname", firstName);
        user.put("lastname", lastName);
        user.put("birthday",birthday);
        user.put("nationality",nationality);
        user.put("phoneNumber",phoneNumber);
        user.put("city",city);
        user.put("address",address);
        user.put("sex",sex);

        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                            Log.d(TAG, "onComplete: "+user.toString());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpSeeker3.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }
                    }
                });
    }
    private void performFileSearch() {
        //TODO implement filesearch + permissions
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}