package fr.umontpellier.etu.inteco.Seeker.fragements.Saved;

import fr.umontpellier.etu.inteco.Seeker.fragements.Saved.SaveJobRecyclerViewAdapter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.LogDescriptor;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import fr.umontpellier.etu.inteco.R;
import fr.umontpellier.etu.inteco.Seeker.JobDetailsActivity;
import fr.umontpellier.etu.inteco.Seeker.fragements.Saved.MyItemRecyclerViewAdapter;
import fr.umontpellier.etu.inteco.Seeker.placeholder.Offer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SavedSeeker#newInstance} factory method to
 * create an instance of this fragment.
 *
 * Layout : fragment_saved_seeker fragment_offer_saved_list fragment_offer_saved_card
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

    private final MutableLiveData<ArrayList<String>> listen = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<QueryDocumentSnapshot>> listen2 = new MutableLiveData<>();

    String idDocumentUser;
    DocumentReference myDocRef;


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
                        ArrayList<String> aux = new ArrayList<>();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String s = document.get("email",String.class);
                                if (currentUser.getEmail().equals(s)) {
                                    Log.d(TAG, "onComplete: "+document.get("saved").getClass().getName());
                                    ((ArrayList<DocumentReference>) document.get("saved")).stream().forEach(e -> aux.add(e.getId()));

                                }
//                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            listen.postValue(aux);
//                            Log.d(TAG, "onComplete: "+aux);
//                            for (String s :
//                                    aux) {
//                                Log.d(TAG, "onComplete: "+s);
//                            }

                            /**
                             * TODO :
                             * - db request for offer info DONE
                             * - post into mutable live data DONE
                             * - create array of object containing the data DONE
                             * - use SaveJobRecyclerViewAdapter
                             * - it should work
                             */

                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        listen.observe(SavedSeeker.this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> documentReferences) {
                db.collection("offers")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                ArrayList<QueryDocumentSnapshot> aux = new ArrayList<>();
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "onComplete: requesting offers");
//                                                boolean found = false;
//                                                QueryDocumentSnapshot infoOffer = null;
                                    for (QueryDocumentSnapshot document : task.getResult()) {
//                                        Log.d(TAG, "onComplete: "+document.getId()+"=>"+document.getData());

                                        if (documentReferences.contains(document.getId())) {
//                                            Log.d(TAG, "onComplete: match"+document.getId()+"=>"+document.getData().toString());
                                            aux.add(document);
                                        }
                                    }
                                    listen2.postValue(aux);
                                } else {
                                    Log.w(TAG, "Error getting documents.", task.getException());
                                }
                            }
                        });

            }
        });

        listen2.observe(SavedSeeker.this, new Observer<ArrayList<QueryDocumentSnapshot>>() {
            @Override
            public void onChanged(ArrayList<QueryDocumentSnapshot> queryDocumentSnapshots) {
                ArrayList<Offer> myList = new ArrayList<>();
                for (QueryDocumentSnapshot document :
                        queryDocumentSnapshots) {

                    Timestamp t = document.get("postDate", Timestamp.class);
                    Date d = t.toDate();
//                    Log.d(TAG, "onChanged: Timestamp="+t+" Date="+d);
                    String theDateGoodFormat = ""+d.getDate()+"/"+d.getMonth()+"/"+d.getYear();
                    Log.d(TAG, "onChanged: good date ?="+theDateGoodFormat);

                    myList.add(new Offer(document.getId(),
                            document.get("jobTitle", String.class),
                            document.get("companyName", String.class),
                            document.get("place", String.class),
                            theDateGoodFormat,//(new Date(document.get("postDate", String.class))).toString(),
                            document.get("salary", String.class)
                    ));
                }

                Log.d(TAG, "onChanged: the offers saved by this user:");
                Log.d(TAG, "onChanged: "+myList);

                // TODO : utiliser recycler view ici
                SaveJobRecyclerViewAdapter customAdaptator = new SaveJobRecyclerViewAdapter(myList, new MyItemRecyclerViewAdapter.AdapterItemClickListener() {
                    @Override
                    public void onItemClickListener(Offer item, int position) {
                        Toast.makeText(SavedSeeker.this.getContext(), "J'ai appuyé sur APPLY (id:"+item.id+")", Toast.LENGTH_SHORT).show();
                    }
                }, new MyItemRecyclerViewAdapter.AdapterItemClickListener() {
                    @Override
                    public void onItemClickListener(Offer item, int position) {
//                        Toast.makeText(SavedSeeker.this.getContext(), "J'ai appuyé sur DELETE (id:"+item.id+")", Toast.LENGTH_SHORT).show();


                        Log.d(TAG, "onItemClickListener: looking up users");
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
                                                if (mEmail.equals(mail)){
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

                        waiter.observe(SavedSeeker.this, new Observer<QueryDocumentSnapshot>() {
                            @Override
                            public void onChanged(QueryDocumentSnapshot document) {
//                                Log.d(TAG, "onItemClickListener: looking up users");
//                        Log.d(TAG, "onChanged: "+document);
                                // Récupération des ids des documents
                                ArrayList<String> savedListId = new ArrayList<>();
                                ArrayList<DocumentReference> savedListRef = new ArrayList<>();

                                ((ArrayList<DocumentReference>) document.get("saved")).stream().forEach(e -> {
                                    savedListId.add(e.getId());
//                            savedListRef.add(e);
//                            if (idOffer.equals(e.getId())) {
//                                myDocRef = e;
//                            }

                                });
                                Log.d(TAG, "onChanged: I have the saved jobs offers of " + mEmail + "=>"+savedListId);
//                        Log.d(TAG, "onChanged: savedList="+savedListId);

                                String idOffer = item.id;
                                if (!savedListId.contains(idOffer)) {
//                                    Log.d(TAG, "onChanged: current offer already saved");
                                    Toast.makeText(SavedSeeker.this.getContext(),"The offer "+idOffer+" isn't saved",Toast.LENGTH_SHORT).show();
                                } else {
//                                    Log.d(TAG, "onChanged: current offer not already saved");
//                                    Toast.makeText(SavedSeeker.this.getContext(),"The offer "+idOffer+" is saved, deleting now",Toast.LENGTH_SHORT).show();
                                    DocumentReference userRef = db.collection("users").document(idDocumentUser);
//
                                    db.collection("offers")
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d(TAG, "onComplete: searching for the DocumentReference");
                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                            String id = document.getId();
                                                            if (id.equals(idOffer)){
                                                                myDocRef = document.getReference();
                                                                break;
                                                            }
                                                        }
//
                                                        userRef.update("saved", FieldValue.arrayRemove(myDocRef))
                                                                .addOnCompleteListener(task1 ->
                                                                        Toast.makeText(SavedSeeker.this.getContext(),"Deleted "+myDocRef.getId(),Toast.LENGTH_SHORT).show()
                                                                );
//
//                                                        Log.d(TAG, "onComplete: myDocRef="+myDocRef);
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

                RecyclerView recyclerView = SavedSeeker.this.getView().findViewById(R.id.recyclerView);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(SavedSeeker.this.getContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(customAdaptator);

                Log.d(TAG, "onChanged: ça devrait faire un truc");
            }
        });
    }

    /*@Override
            public void onChanged(ArrayList<Map<String, Object>> maps) {
                ArrayList<Offer> myList = new ArrayList<>();
                myList.add(new Offer(maps.get()));
            }
        });*/

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