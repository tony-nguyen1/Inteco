package fr.umontpellier.etu.inteco.Enterprise.fragements.jobHaver;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

import fr.umontpellier.etu.inteco.R;
import fr.umontpellier.etu.inteco.Enterprise.fragements.jobHaver.placeholder.PlaceholderContent;

/**
 * A fragment representing a list of Items.
 */
public class JobHaverFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String TAG = "debug JobHaverFragment";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private QueryDocumentSnapshot myDocRef;
    final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private MutableLiveData<ArrayList<Map<String, Object>>> listen = new MutableLiveData<ArrayList<Map<String, Object>>>();
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public JobHaverFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static JobHaverFragment newInstance(QueryDocumentSnapshot docRef) {
        JobHaverFragment fragment = new JobHaverFragment();
        fragment.myDocRef = docRef;
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, 1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_haver_list, container, false);

        this.grosseFonction();
//        listen.observe(getViewLifecycleOwner(), new Observer<ArrayList<Map<String, Object>>>() {
//            @Override
//            public void onChanged(ArrayList<QueryDocumentSnapshot> queryDocumentSnapshots) {
//                for (QueryDocumentSnapshot snap :
//                        queryDocumentSnapshots) {
//                    Log.d(TAG, "onChanged: "+snap.getData());
//                }
//
//            }
//        });


        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new JobHaverRecyclerViewAdapter(PlaceholderContent.ITEMS));
        }
        return view;
    }

    private void grosseFonction() {
        /**
         * TODO
         * get aller chercher toutes les offers docRef posted dans company DONE
         * get all offers doc ref that match inside offers DONE
         * inside each offer.apply get accepted user
         * then get user info from users
         */

        Log.d(TAG, "grosseFonction: "+this.myDocRef.getData());

        this.auxGetAllOffersOfThisCompany((ArrayList<DocumentReference>) this.myDocRef.getData().get("posted"));
    }

    private void auxGetAllOffersOfThisCompany(ArrayList<DocumentReference> jobPostedDocRefList) {
        Log.d(TAG, "auxGetAllOffersOfThisCompany: "+jobPostedDocRefList);
        Log.d(TAG, "auxGetAllOffersOfThisCompany: "+jobPostedDocRefList.get(0).getId());

        ArrayList<QueryDocumentSnapshot> postedOffersDocRefList = new ArrayList<>();
        db.collection("offers").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        if (jobPostedDocRefList.contains(document.getReference())) {
//                            auxUpdateUsers("accepted", document);
                            postedOffersDocRefList.add(document);
                        }
                    }
                    JobHaverFragment.this.auxGetAllAcceptedCandidateOfTheseOffers(postedOffersDocRefList);
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });
    }
    private void auxGetAllAcceptedCandidateOfTheseOffers(ArrayList<QueryDocumentSnapshot> postedOffersDocRefList) {
        Log.d(TAG, "auxGetAllAcceptedCandidateOfTheseOffers: "+postedOffersDocRefList);
        Log.d(TAG, "auxGetAllAcceptedCandidateOfTheseOffers: "+postedOffersDocRefList.get(0).getId());

        ArrayList<DocumentReference> acceptedCandidatesDocRefList = new ArrayList<>();
        db.collection("offers").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        document.getData().get("apply");
                        for (Map<String, Object> uneMap :
                                (ArrayList<Map<String,Object>>) document.getData().get("apply")) {
                            if (uneMap.get("status").equals("accepted")) {
                                acceptedCandidatesDocRefList.add((DocumentReference) uneMap.get("ref"));
                            }
                        }

//                        if (postedOffersDocRefList.contains(document.getReference())) {
////                            auxUpdateUsers("accepted", document);
//                            acceptedCandidatesDocRefList.add(document.getReference());
//                        }
                    }
                    Log.d(TAG, "auxGetAllAcceptedCandidateOfTheseOffers: out="+acceptedCandidatesDocRefList.get(0).getId());
//                    JobHaverFragment.this.auxGetAllAcceptedCandidateOfTheseOffers(postedOffersDocRefList);
                    auxGetInfoUsers(acceptedCandidatesDocRefList, postedOffersDocRefList);
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });
    }
    private void auxGetInfoUsers(ArrayList<DocumentReference> acceptedCandidatesDocRefList, ArrayList<QueryDocumentSnapshot> postedOffersDocRefList) {
        ArrayList<Map<String,Object>> listDataUserAcceptedWithJobData = new ArrayList<>();
        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> userMap = document.getData();


                        for (QueryDocumentSnapshot offerSnap :
                                postedOffersDocRefList) {
                            for (Map<String,Object> applyMap : (ArrayList<Map<String,Object>>) document.getData().get("apply")) {
                                if (applyMap.get("ref").equals(offerSnap.getReference())) {
                                    userMap.put("jobData", offerSnap.getData());
                                    listDataUserAcceptedWithJobData.add(userMap);
                                }
                            }
                        }
                        Log.d(TAG, "onComplete: userMap="+userMap);



                        if (acceptedCandidatesDocRefList.contains(document.getReference())) {
//                            listDataUserAcceptedWithJobData.add(document);
                        }
                    }
//                    JobHaverFragment.this.auxGetAllAcceptedCandidateOfTheseOffers(postedOffersDocRefList);
                    //post here
                    listen.postValue(listDataUserAcceptedWithJobData);
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });
    }
}