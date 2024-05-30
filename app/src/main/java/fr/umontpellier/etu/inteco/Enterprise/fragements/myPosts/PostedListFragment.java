package fr.umontpellier.etu.inteco.Enterprise.fragements.myPosts;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Map;

import fr.umontpellier.etu.inteco.R;
import fr.umontpellier.etu.inteco.Enterprise.fragements.myPosts.placeholder.PlaceholderContent;
import fr.umontpellier.etu.inteco.Seeker.placeholder.Offer;
import fr.umontpellier.etu.inteco.helper.Helper;

/**
 * A fragment representing a list of Items.
 */
public class PostedListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_CUSTOM_LIST = "ma-list";
    private static final String TAG = "debug PostedListFragment";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private ArrayList<PlaceholderContent.PlaceholderItem> customList;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    final private ArrayList<DocumentReference> foo = new ArrayList<>();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PostedListFragment() {
        this.customList = new ArrayList<>();
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PostedListFragment newInstance(int columnCount/*, ArrayList<PlaceholderContent.PlaceholderItem> cstmList*/) {
        PostedListFragment fragment = new PostedListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
//        Bundle bundle = new Bundle();
//        bundle.putParcelableArrayList("list",cstmList);
//        args.putBundle(ARG_CUSTOM_LIST, );
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
        View view = inflater.inflate(R.layout.fragment_posted_list, container, false);



        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null) {
            throw new RuntimeException("currentUser should be set but isn't");
        }
        MutableLiveData<ArrayList<PlaceholderContent.PlaceholderItem>> listen = new MutableLiveData<>();
        this.getOffers(currentUser, listen);
        listen.observe(getViewLifecycleOwner(), new Observer<ArrayList<PlaceholderContent.PlaceholderItem>>() {
            @Override
            public void onChanged(ArrayList<PlaceholderContent.PlaceholderItem> placeholderItems) {

                // Set the adapter
                if (view instanceof RecyclerView) {
                    Context context = view.getContext();
                    RecyclerView recyclerView = (RecyclerView) view;
                    if (mColumnCount <= 1) {
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    } else {
                        recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                    }
                    recyclerView.setAdapter(new MyPostRecyclerViewAdapter(placeholderItems));
                }

            }
        });

        return view;
    }

    private void getOffers(FirebaseUser user, MutableLiveData<ArrayList<PlaceholderContent.PlaceholderItem>> answer) {
        MutableLiveData<QueryDocumentSnapshot> listenCompany = new MutableLiveData<>();
        MutableLiveData<ArrayList<DocumentReference>> listenDocRefOffers = new MutableLiveData<>();
        MutableLiveData<ArrayList<Map<String,Object>>> listenDocRefOffersDetails = new MutableLiveData<>();

        Helper.getCompanyDocumentReference(user, listenCompany);
        listenCompany.observe(getViewLifecycleOwner(), new Observer<QueryDocumentSnapshot>() {
            @Override
            public void onChanged(QueryDocumentSnapshot queryDocumentSnapshot) {
                Log.d(TAG, "onChanged: retrieved the reference of the company succesfully");

                foo.addAll(((ArrayList<DocumentReference>) queryDocumentSnapshot.get("posted")));
                listenDocRefOffers.postValue(foo);
            }
        });

        listenDocRefOffers.observe(getViewLifecycleOwner(), new Observer<ArrayList<DocumentReference>>() {
            @Override
            public void onChanged(ArrayList<DocumentReference> documentReferences) {
                Log.d(TAG, "onChanged: received this :"+documentReferences);

                Helper.getOffersOfCompany(documentReferences, listenDocRefOffersDetails);
            }
        });

        listenDocRefOffersDetails.observe(getViewLifecycleOwner(), new Observer<ArrayList<Map<String, Object>>>() {
            @Override
            public void onChanged(ArrayList<Map<String, Object>> list) {
                Log.d(TAG, "onChanged: i have the map with the key values");
                Log.d(TAG, "onChanged: list="+list.toString());

                ArrayList<PlaceholderContent.PlaceholderItem> listItem = new ArrayList<>();

                list.stream().forEach(map -> {
                    listItem.add(new PlaceholderContent.PlaceholderItem(
                            (String)map.get("post_title"),
                            -1,
                            (String)map.get("state"),
                            (String)map.get("date"))
                    );
                });

                Log.d(TAG, "onChanged: listItem="+listItem);
                answer.postValue(listItem);
            }
        });
    }
}