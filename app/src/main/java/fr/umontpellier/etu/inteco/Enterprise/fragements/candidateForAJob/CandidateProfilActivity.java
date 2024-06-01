package fr.umontpellier.etu.inteco.Enterprise.fragements.candidateForAJob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

import fr.umontpellier.etu.inteco.R;

public class CandidateProfilActivity extends AppCompatActivity {

    private static final String TAG = "debug CandidateProfilActivity";

    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String idCandidate;
    private String idJob;
    private final MutableLiveData<QueryDocumentSnapshot> listen = new MutableLiveData<>();

    private TextView nomView, phoneNumberView, emailView, nationalityView, birthdayView, dateView, statusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_profil);

        Intent intent = getIntent();
        this.idCandidate = intent.getStringExtra("idString");
        this.idJob = intent.getStringExtra("idStringJob");

        Log.d(TAG, "onCreate: idCandidate="+idCandidate+" idJob="+idJob);

        nomView = findViewById(R.id.nom);
        phoneNumberView = findViewById(R.id.tel);
        emailView = findViewById(R.id.email);
        nationalityView = findViewById(R.id.birthday);
        birthdayView = findViewById(R.id.nationality);
        dateView = findViewById(R.id.dateAppliedTo);
        statusView = findViewById(R.id.status);

        this.getUser();
    }

    private void getUser() {
        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.getReference().getId().equals(idCandidate)) {
                            listen.postValue(document);
                            break;
                        }
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });

        listen.observe(CandidateProfilActivity.this, new Observer<QueryDocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onChanged(QueryDocumentSnapshot queryDocumentSnapshot) {
                String firstName, lastName, phoneNumber, email, nationality, birthday, status = null;
                Timestamp t = null;

                firstName = queryDocumentSnapshot.getString("firstname");
                lastName = queryDocumentSnapshot.getString("lastname");
                phoneNumber = queryDocumentSnapshot.getString("phoneNumber");
                email = queryDocumentSnapshot.getString("email");
                nationality = queryDocumentSnapshot.getString("nationality");
                birthday = queryDocumentSnapshot.getString("birthday");
                Log.d(TAG, "onChanged: doc="+queryDocumentSnapshot.getData());
                Log.d(TAG, "onChanged: doc="+queryDocumentSnapshot.getString("firstname"));
                Log.d(TAG, "onChanged: doc="+queryDocumentSnapshot.getString("lastname"));
                Log.d(TAG, "onChanged: doc="+queryDocumentSnapshot.getString("phoneNumber"));
                Log.d(TAG, "onChanged: doc="+queryDocumentSnapshot.getString("nationality"));
                Log.d(TAG, "onChanged: doc="+queryDocumentSnapshot.getString("birthday"));

                for (Map<String,Object> unMap:
                        (ArrayList<Map<String,Object>>) queryDocumentSnapshot.getData().get("apply")) {
                    if (((DocumentReference)unMap.get("ref")).getId().equals(idJob)) {
                        Log.d(TAG, "onChanged: "+unMap.get("status"));
                        status = (String) unMap.get("status");
                        t = (Timestamp) unMap.get("date");
                        break;
                    }
                }

                assert t != null;

                nomView.setText(firstName+" "+lastName);
                phoneNumberView.setText(phoneNumber);
                emailView.setText(email);
                nationalityView.setText(nationality);
                birthdayView.setText(birthday);
                dateView.setText("Applied "+new PrettyTime(new Locale("en")).format(t.toDate()));
                statusView.setText(status);
            }
        });
    }
}