package fr.umontpellier.etu.inteco.Enterprise.fragements.candidateForAJob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    private Button acceptButton, rejectButton;

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

        acceptButton = findViewById(R.id.accept_button);
        rejectButton = findViewById(R.id.reject_button);

        this.getUser();

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * TODO :
                 * Hyptothèse : cette utilisateur a postulé à cette offre, cette offre a enregistré cette personne
                 *
                 * dans collection offers, mettre à jour l'état de cette personne à accepted et des autres à rejected
                 * dans offers, state <- closed
                 *
                 * dans la collection users mettre à jour l'état de cette offre à accepted
                 *
                 * envoyer des notifications ?
                 */

                // DONE
                CandidateProfilActivity.this.updateOffers();

                // DONE
                CandidateProfilActivity.this.updateUsers();
            }
        });

        // TODO
        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void updateUsers() {
        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.getReference().getId().equals(idCandidate)) {
                            auxUpdateUsers("accepted", document);
                        }
                        else {
                            auxUpdateUsers("rejected", document);
                        }
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });
    }

    private void auxUpdateUsers(String newStatus, QueryDocumentSnapshot document) {
        // we have the current user
        DocumentReference currentUser = document.getReference();
        Map<String,Object> data = document.getData();

//                            data.replace("state","closed");
//                            assert data.get("state").equals("closed");

        for (Map<String,Object> aMap: (ArrayList<Map<String, Object>>) data.get("apply")) {
            if (((DocumentReference) aMap.get("ref")).getId().equals(idJob)) {
                aMap.replace("status",newStatus);
            }
        }

        currentUser.update(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "updateUsers succeful");
//                                        Log.d(TAG, "updateUsers: onComplete: collection users updated with data="+data);
                }else {
                    Log.d(TAG, "onComplete: not successful");
                }
            }
        });

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
//                Log.d(TAG, "onChanged: doc="+queryDocumentSnapshot.getData());
//                Log.d(TAG, "onChanged: doc="+queryDocumentSnapshot.getString("firstname"));
//                Log.d(TAG, "onChanged: doc="+queryDocumentSnapshot.getString("lastname"));
//                Log.d(TAG, "onChanged: doc="+queryDocumentSnapshot.getString("phoneNumber"));
//                Log.d(TAG, "onChanged: doc="+queryDocumentSnapshot.getString("nationality"));
//                Log.d(TAG, "onChanged: doc="+queryDocumentSnapshot.getString("birthday"));

                for (Map<String,Object> unMap:
                        (ArrayList<Map<String,Object>>) queryDocumentSnapshot.getData().get("apply")) {
                    if (((DocumentReference)unMap.get("ref")).getId().equals(idJob)) {
//                        Log.d(TAG, "onChanged: "+unMap.get("status"));
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

    /**
     * Update offers collection. Close the offer, rejecte all job seeker except for this.idCandidate who is accepted.
     */
    private void updateOffers() {
//        Log.d(TAG, "updateOffers: ");
        db.collection("offers").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
//                    Log.d(TAG, "onComplete: ");
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (document.getReference().getId().equals(idJob)) {
                            // we have the current offer
                            DocumentReference currentOffer = document.getReference();
                            Map<String,Object> data = document.getData();
//                            Log.d(TAG, "onComplete: before data="+data);


                            data.replace("state","closed");
                            assert data.get("state").equals("closed");

                            for (Map<String,Object> aMap: (ArrayList<Map<String, Object>>) data.get("apply")) {
                                if (((DocumentReference) aMap.get("ref")).getId().equals(idCandidate)) {
                                    aMap.replace("status","accepted");
                                } else {
                                    aMap.replace("status","rejected");
                                }
                            }


//                            Log.d(TAG, "onComplete: after data="+data);
                            currentOffer.update(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "updateOffers successful");
//                                        Log.d(TAG, "updateOffers: onComplete: collection offers updated with data="+data);
                                    }else {
                                        Log.d(TAG, "onComplete: not successful");
                                    }
                                }
                            });
                            break;
                        }
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });
    }
}