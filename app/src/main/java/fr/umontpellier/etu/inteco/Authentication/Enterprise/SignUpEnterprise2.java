package fr.umontpellier.etu.inteco.Authentication.Enterprise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.rpc.Help;

import java.util.HashMap;
import java.util.Map;

import fr.umontpellier.etu.inteco.Authentication.Seeker.SignUpSeeker3;
import fr.umontpellier.etu.inteco.Enterprise.HomePageEnterprise;
import fr.umontpellier.etu.inteco.R;
import fr.umontpellier.etu.inteco.helper.Helper;

public class SignUpEnterprise2 extends AppCompatActivity {
    private static final String TAG = "debug SignUp";
    private TextInputEditText webSiteEditText;
    private TextInputEditText linkFbEditText;
    private TextInputEditText linkLiEditText;
    private TextInputEditText linkInstaEditText;
    private Button btnNext;
    private String email;
    private String password;
    private String companyName;
    private String phoneNumber;
    private String city;
    private String address;



    private FirebaseAuth mAuth = FirebaseAuth.getInstance();;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_enterprise2);

        // Retreive the data from the precedent activity
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        companyName = intent.getStringExtra("companyName");
        phoneNumber = intent.getStringExtra("phoneNumber");
        city = intent.getStringExtra("city");
        address = intent.getStringExtra("address");

        Log.d(TAG, "onCreate: name="+companyName);


        // Initialize the TextInputEditTexts and Button
        webSiteEditText = findViewById(R.id.WebSiteEditText);
        linkFbEditText = findViewById(R.id.LinkFbEditText);
        linkLiEditText = findViewById(R.id.AddressEditText);
        linkInstaEditText = findViewById(R.id.LinkInstaEditText);
        btnNext = findViewById(R.id.btnNext);

        // Setup the OnClickListener for the button
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve values from TextInputEditTexts
                String website = webSiteEditText.getText().toString();
                String facebookLink = linkFbEditText.getText().toString();
                String linkedinLink = linkLiEditText.getText().toString();
                String instagramLink = linkInstaEditText.getText().toString();

                // Handle the data as needed
                handleNextButtonClick(website, facebookLink, linkedinLink, instagramLink, email, password, companyName, phoneNumber, city, address);

//                Intent intent2 = new Intent(SignUpEnterprise2.this, HomePageEnterprise.class);
//                intent2.putExtra("email", email);
//                Log.i(TAG,website+facebookLink+linkedinLink+instagramLink+email+password+companyName+phoneNumber+city+address);
//                startActivity(intent2);
            }
        });
    }

    private void handleNextButtonClick(String website, String facebook, String linkedin, String instagram, String email, String password, String companyName, String phoneNumber, String city, String address) {
        //TODO complete signUp the company here
        Log.d(TAG, "handleNextButtonClick: enregistrement d'un nouveau compte entreprise");

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.d(TAG, "onSuccess: createUserWithEmailAndPassword");

                        FirebaseUser user = mAuth.getCurrentUser();
                        Log.d(TAG, "onSuccess: new company is"+user.toString());

                        // TODO add user to allUsers & company collection
                        Map<String, Object> aCompany = new HashMap<>();
                        aCompany.put("adress", address);
                        aCompany.put("city", city);
                        aCompany.put("country","N/A");
                        aCompany.put("email", email);
                        aCompany.put("facebook",facebook);
                        aCompany.put("instagram",instagram);
                        aCompany.put("linked_in",linkedin);
                        aCompany.put("name",companyName);
                        aCompany.put("phone",phoneNumber);
                        aCompany.put("website",website);

                        MutableLiveData<Boolean> listen = new MutableLiveData<>();
                        listen.observe(SignUpEnterprise2.this, new Observer<Boolean>() {
                            @Override
                            public void onChanged(Boolean aBoolean) {
                                if (aBoolean.booleanValue()) {
                                    Toast.makeText(SignUpEnterprise2.this, "Success", Toast.LENGTH_SHORT).show();


                                    Intent intent2 = new Intent(SignUpEnterprise2.this, HomePageEnterprise.class);
                                    intent2.putExtra("email", email);
//                                    Log.i(TAG,website+facebookLink+linkedinLink+instagramLink+email+password+companyName+phoneNumber+city+address);
                                    startActivity(intent2);
                                }
                            }
                        });
                        Helper.addACompanyToCollections(aCompany,listen);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUpEnterprise2.this, "error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}