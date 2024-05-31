package fr.umontpellier.etu.inteco.Seeker.fragements.myApplications;

import android.content.Context;
import android.os.Bundle;

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

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import fr.umontpellier.etu.inteco.Enterprise.fragements.myPosts.MyPostRecyclerViewAdapter;
import fr.umontpellier.etu.inteco.R;
import fr.umontpellier.etu.inteco.Seeker.fragements.myApplications.placeholder.PlaceholderContent;
import fr.umontpellier.etu.inteco.Seeker.placeholder.Offer;
import fr.umontpellier.etu.inteco.helper.Helper;

/**
 * A fragment representing a list of Items.
 */
public class MyAppliedListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String TAG = "debug MyAppliedListFragment";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    final private ArrayList<Map<String,Object>> foo = new ArrayList<>();


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MyAppliedListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MyAppliedListFragment newInstance(int columnCount) {
        MyAppliedListFragment fragment = new MyAppliedListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
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
        View view = inflater.inflate(R.layout.fragment_applied_list, container, false);



        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null) {
            throw new RuntimeException("currentUser should be set but isn't");
        }
        MutableLiveData<ArrayList<Offer>> listen = new MutableLiveData<>();
        this.getOffers(currentUser, listen);

        listen.observe(getViewLifecycleOwner(), new Observer<ArrayList<Offer>>() {
            @Override
            public void onChanged(ArrayList<Offer> placeholderItems) {

                // Set the adapter
                if (view instanceof RecyclerView) {
                    Context context = view.getContext();
                    RecyclerView recyclerView = (RecyclerView) view;
                    if (mColumnCount <= 1) {
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    } else {
                        recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                    }
                    recyclerView.setAdapter(new MyApplicationsRecyclerViewAdapter(placeholderItems));
                }

            }
        });
        return view;
    }

    private void getOffers(FirebaseUser user, MutableLiveData<ArrayList<Offer>> answer) {
        MutableLiveData<QueryDocumentSnapshot> listenCompany = new MutableLiveData<>();
        MutableLiveData<ArrayList<Map<String, Object>>> listenDocRefOffers = new MutableLiveData<>();
        MutableLiveData<ArrayList<Map<String,Object>>> listenDocRefOffersDetails = new MutableLiveData<>();

        Helper.getJobSeekerDocumentReference(user, listenCompany);
        listenCompany.observe(getViewLifecycleOwner(), new Observer<QueryDocumentSnapshot>() {
            @Override
            public void onChanged(QueryDocumentSnapshot queryDocumentSnapshot) {
                Log.d(TAG, "onChanged: retrieved the reference of the jobSeeker succesfully");

//                foo.addAll(((ArrayList<DocumentReference>) queryDocumentSnapshot.get("apply")));

                foo.addAll(((ArrayList<Map<String, Object>>) queryDocumentSnapshot.get("apply")));
                listenDocRefOffers.postValue(foo);
            }
        });

        listenDocRefOffers.observe(getViewLifecycleOwner(), new Observer<ArrayList<Map<String, Object>>>() {
            @Override
            public void onChanged(ArrayList<Map<String, Object>> documentReferences) {
                Log.d(TAG, "onChanged: received this :"+documentReferences);

                Helper.getOffersOfCompanyWithDate(documentReferences, listenDocRefOffersDetails);
            }
        });

        listenDocRefOffersDetails.observe(getViewLifecycleOwner(), new Observer<ArrayList<Map<String, Object>>>() {
            @Override
            public void onChanged(ArrayList<Map<String, Object>> list) {
                Log.d(TAG, "onChanged: i have the map with the key values");
                Log.d(TAG, "onChanged: list="+list.toString());

                ArrayList<Offer> listItem = new ArrayList<>();

                list.stream().forEach(map -> {
                    listItem.add(new Offer(
                            (String)map.get("post_title"),
                            map.get("city")+", "+map.get("country"),
                            (String)map.get("status"),
                            (Timestamp) Objects.requireNonNull(map.get("realDate")))
                    );
                });

                Log.d(TAG, "onChanged: listItem="+listItem);
                answer.postValue(listItem);
            }
        });
    }
}