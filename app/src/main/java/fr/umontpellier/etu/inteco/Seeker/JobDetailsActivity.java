package fr.umontpellier.etu.inteco.Seeker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.WriteResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fr.umontpellier.etu.inteco.R;

public class JobDetailsActivity extends AppCompatActivity {

    private static final String TAG = "debug JobDetails";
    private String idOffer;

    String idDocumentUser;
    DocumentReference myDocRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);





        // Find views by IDs
        TextView companyNameTV = findViewById(R.id.companyName);
        TextView jobTitleTV = findViewById(R.id.jobTitle);
        TextView jobTypeTV = findViewById(R.id.job_type);
        TextView countryTV = findViewById(R.id.country);
        TextView postDateTV = findViewById(R.id.postDate);
        TextView salaryTV = findViewById(R.id.salary);
        TextView descriptionTV = findViewById(R.id.description);
        TextView requirementsTV = findViewById(R.id.requirements);
        TextView locationTV = findViewById(R.id.location);
        TextView positionTV = findViewById(R.id.position);
        TextView qualificationTV = findViewById(R.id.qualification);
        TextView experienceTV = findViewById(R.id.experience);
        TextView contractTypeTV = findViewById(R.id.contract_type);
        //Buttons
        Button saveButton = findViewById(R.id.button_save);
        Button applyButton = findViewById(R.id.button_apply);

        // Get data from Intent
        Intent intent = getIntent();
        idOffer = intent.getStringExtra("id");
        String jobTitle = intent.getStringExtra("jobTitle");
        String companyName = intent.getStringExtra("companyName");
        String place = intent.getStringExtra("place");
        String postDate = intent.getStringExtra("postDate");
        String salary = intent.getStringExtra("salary");
        //TODO Récupèrer le reste des infos
        String description = "Wear a strawberry fruit costume under the sun for publicity...";
        String requirements = "• You like fruits";
        String location = "Overlook Avenue, Belleville, NJ, USA";
        String position = "Senior Wearer";
        String qualification = "Phd";
        String experience = "None required";
        String contractType = "Permanent contract";
        String jobType = "Full-Time";
        String country = "Greenland";

        // Set texts
        companyNameTV.setText(companyName);
        jobTitleTV.setText(jobTitle);
        jobTypeTV.setText(jobType);
        countryTV.setText(country);
        postDateTV.setText(postDate);
        salaryTV.setText(salary);
        descriptionTV.setText(description);
        requirementsTV.setText(requirements);
        locationTV.setText(location);
        positionTV.setText(position);
        qualificationTV.setText(qualification);
        experienceTV.setText(experience);
        contractTypeTV.setText(contractType);

        applyButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                           //TODO  apply button
               }
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"Todo : saved.contains(this.post) ? Tell user : add this post to saved & tell him anyway",Toast.LENGTH_SHORT).show();

                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();

                String email = currentUser.getEmail();

                Log.d(TAG, "onClick: email utilisateur courrant"+email);
                Log.d(TAG, "onClick: id offre courante = "+idOffer);
                Log.d(TAG, "onClick: nom offre courante = "+intent.getStringExtra("jobTitle"));




                MutableLiveData<QueryDocumentSnapshot> waiter = new MutableLiveData<>();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("users")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        String mail = document.get("email", String.class);
                                        if (email.equals(mail)){
                                            Log.d(TAG, "onComplete: "+document.getData());
                                            waiter.postValue(document);
                                            idDocumentUser = document.getId();
                                            break;
                                        }
                                    }
                                } else {
                                    Log.w(TAG, "Error getting documents.", task.getException());
                                }
                            }
                        });

                MutableLiveData<ArrayList<QueryDocumentSnapshot>> waiterOffer = new MutableLiveData<>();
                waiter.observe(JobDetailsActivity.this, new Observer<QueryDocumentSnapshot>() {
                    @Override
                    public void onChanged(QueryDocumentSnapshot document) {
                        Log.d(TAG, "onChanged: user matched");
//                        Log.d(TAG, "onChanged: "+document);
                        // Récupération des ids des documents
                        ArrayList<String> savedListId = new ArrayList<>();

                        ((ArrayList<DocumentReference>) document.get("saved")).stream().forEach(e -> {
                            savedListId.add(e.getId());
                        });
                        
                        if (savedListId.contains(idOffer)) {
                            Log.d(TAG, "onChanged: current offer already saved");
                            Toast.makeText(getApplicationContext(),"Current offer already saved",Toast.LENGTH_SHORT).show();

                        } else {
                            Log.d(TAG, "onChanged: current offer not already saved");
                            DocumentReference userRef = db.collection("users").document(idDocumentUser);

                            db.collection("offers")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot document : task.getResult()) {
                                                    String id = document.getId();
                                                    if (id.equals(idOffer)){
                                                        myDocRef = document.getReference();
                                                        break;
                                                    }
                                                }

                                                userRef.update("saved", FieldValue.arrayUnion(myDocRef))
                                                        .addOnCompleteListener(task1 ->
                                                                Toast.makeText(JobDetailsActivity.this,"Success",Toast.LENGTH_SHORT).show()
                                                        );

                                                Log.d(TAG, "onComplete: myDocRef="+myDocRef);
                                            } else {
                                                Log.w(TAG, "Error getting documents.", task.getException());
                                            }
                                        }
                                    });
                        }
                    }
                });
            }
        });
    }
}