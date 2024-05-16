package fr.umontpellier.etu.inteco.Seeker.fragements;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import fr.umontpellier.etu.inteco.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SavedSeeker#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SavedSeeker extends Fragment {
    private static final String email = "AYS";
    private static final String firstName = "";
    private static final String LastName = "";
    private static final String TAG = "debug SavedSeeker";
    private String mEmail;
    private String mFirstName;
    private String mLastName;

    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    private final MutableLiveData<ArrayList<QueryDocumentSnapshot>> listen = new MutableLiveData<>();

    public SavedSeeker() {
        // Required empty public constructor
    }


    public static SavedSeeker newInstance(String param1, String param2, String param3) {
        SavedSeeker fragment = new SavedSeeker();
        Bundle args = new Bundle();
        args.putString(email, param1);
        args.putString(firstName, param2);
        args.putString(LastName, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mEmail = getArguments().getString(email);
            mFirstName = getArguments().getString(firstName);
            mLastName = getArguments().getString(LastName);
        }

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            Log.d(TAG, "onCreate: connected as "+currentUser.getEmail());
        }


        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ArrayList<DocumentReference> idOfferList = null;
                        if (task.isSuccessful()) {
                            boolean found = false;
                            QueryDocumentSnapshot infoUser = null;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String s = document.get("email",String.class);
                                if (currentUser.getEmail().equals(s)) {
                                    found = true;
//                                    infoUser = document;
                                    Log.d(TAG, "onComplete: "+document.get("saved").getClass().getName());
                                    idOfferList = (ArrayList<DocumentReference>) document.get("saved");
                                }
//                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
//                            listen.postValue(infoUser);
                            Log.d(TAG, "onComplete: "+idOfferList);
                            for (DocumentReference s :
                                    idOfferList) {
                                Log.d(TAG, "onComplete: "+s);
                            }

//                            db.collection("offers")
//                                    .get()
//                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                            if (task.isSuccessful()) {
//                                                boolean found = false;
//                                                QueryDocumentSnapshot infoOffer = null;
//                                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                                    String s = document.getId();
//                                                    if (currentUser.getEmail().equals(s)) {
//                                                        found = true;
//                                                        infoOffer = document;
//                                                    }
////                                Log.d(TAG, document.getId() + " => " + document.getData());
//                                                }
////                                                listen.postValue(infoUser);
//                                            } else {
//                                                Log.w(TAG, "Error getting documents.", task.getException());
//                                            }
//                                        }
//                                    });
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            Log.d(TAG, "onCreate: connected as "+currentUser.getEmail());
        }

//        listen.observe(getViewLifecycleOwner(), new Observer<QueryDocumentSnapshot>() {
//            @Override
//            public void onChanged(QueryDocumentSnapshot queryDocumentSnapshot) {
//                Log.d(TAG, "onChanged: It changed !");
//                Log.d(TAG, "onChanged: "+queryDocumentSnapshot.getData());
//            }
//        });
        // Inflate the layout for this fragment
        Log.v("My email", email);
        return inflater.inflate(R.layout.fragment_saved_seeker, container, false);
    }
}