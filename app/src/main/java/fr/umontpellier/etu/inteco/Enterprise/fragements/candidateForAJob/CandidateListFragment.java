package fr.umontpellier.etu.inteco.Enterprise.fragements.candidateForAJob;

import android.content.Context;
import android.content.Intent;
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
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Map;

import fr.umontpellier.etu.inteco.Authentication.Enterprise.SignUpEnterprise1;
import fr.umontpellier.etu.inteco.Authentication.Enterprise.SignUpEnterprise2;
import fr.umontpellier.etu.inteco.R;
import fr.umontpellier.etu.inteco.Enterprise.fragements.candidateForAJob.placeholder.PlaceholderContent;
import fr.umontpellier.etu.inteco.helper.Helper;

/**
 * A fragment representing a list of Items.
 */
public class CandidateListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String TAG = "debug CandidateListFragment";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private DocumentReference docRefOffer;

    public void setDocRefOffer(DocumentReference docRefOffer) {
        this.docRefOffer = docRefOffer;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CandidateListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CandidateListFragment newInstance(int columnCount, DocumentReference docRefOffer) {
        CandidateListFragment fragment = new CandidateListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        fragment.setDocRefOffer(docRefOffer);
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
        View view = inflater.inflate(R.layout.fragment_candidate_list_, container, false);


        assert getActivity() != null;
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_info_post, InfoPostFragment.newInstance(docRefOffer))
                .commit();


        // TODO chercher la liste de tous les ceux qui ont postulé + nb applicants + state +
        final ArrayList<PlaceholderContent.PlaceholderItem> myList = new ArrayList<>();
        Log.d(TAG, "onCreateView: "+this.docRefOffer.getId()+" docRef="+this.docRefOffer);
        MutableLiveData<QueryDocumentSnapshot> listen = new MutableLiveData<>();
        MutableLiveData<ArrayList<QueryDocumentSnapshot>> listenForUserData = new MutableLiveData<>();
        MutableLiveData<ArrayList<PlaceholderContent.PlaceholderItem>> listenForUserItem = new MutableLiveData<>();

        Helper.getAnOffer(this.docRefOffer, listen);
        listen.observe(getViewLifecycleOwner(), new Observer<QueryDocumentSnapshot>() {
            @Override
            public void onChanged(QueryDocumentSnapshot queryDocumentSnapshot) {
                Log.d(TAG, "onChanged: snap of currunt offer : "+queryDocumentSnapshot.getData().toString());

                Log.d(TAG, "onChanged: looking for applicants inside apply field");
                ArrayList<Map<String,Object>> a = new ArrayList<>((ArrayList<Map<String,Object>>) queryDocumentSnapshot.getData().get("apply"));
                Log.d(TAG, "onChanged: a="+a);
                Helper.getUsers(a, listenForUserData);
            }
        });
        listenForUserData.observe(getViewLifecycleOwner(), new Observer<ArrayList<QueryDocumentSnapshot>>() {
            @Override
            public void onChanged(ArrayList<QueryDocumentSnapshot> usersCandidates) {
                Log.d(TAG, "onChanged: I have my users, i can continue");
                Log.d(TAG, "onChanged: usersCandidates="+usersCandidates);

                Log.d(TAG, "onChanged: "+usersCandidates.size()+" candidates for this post");
                // Convertion en object
                usersCandidates.forEach(snap -> {
                    Map<String, Object> data = snap.getData();
                    Log.d(TAG, "onChanged: userData="+data);
                    PlaceholderContent.PlaceholderItem item;



                    ArrayList<Map<String,Object>> foo = (ArrayList<Map<String,java.lang.Object>>) snap.get("apply");
//                    Log.d(TAG, "onChanged: foo="+foo);
                    assert foo != null;
                    for (Map<String,Object> aUserData : foo) {
                        Log.d(TAG, "inside foreach: applyCurrentUserData="+aUserData);
                        Log.d(TAG, "inside foreach: snap="+snap.getData());
                        if (docRefOffer.equals(aUserData.get("ref"))) {
                            item = new PlaceholderContent.PlaceholderItem(
                                    snap.getReference(),
                                    snap.getString("firstname")+" "+snap.getString("lastname"),
                                    (Timestamp) aUserData.get("date"),
                                    (String) aUserData.get("status")
                            );
                            myList.add(item);
                        }
                    }
                });

                //post
                Log.d(TAG, "onChanged: myList="+myList.toString());
                Log.d(TAG, "onChanged: I have "+myList.size()+" elements in myList");
                listenForUserItem.postValue(myList);
            }
        });
        listenForUserItem.observe(getViewLifecycleOwner(), new Observer<ArrayList<PlaceholderContent.PlaceholderItem>>() {
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
                    recyclerView.setAdapter(new MyCandidateRecyclerViewAdapter(placeholderItems, new MyCandidateRecyclerViewAdapter.AdapterItemClickListener() {
                        @Override
                        public void onItemClickListener(PlaceholderContent.PlaceholderItem item, int position) {
                            Intent intent = new Intent(CandidateListFragment.this.getActivity(), CandidateProfilActivity.class);

                            intent.putExtra("idString",item.docRef.getId());
                            intent.putExtra("idStringJob",docRefOffer.getId());

                            startActivity(intent);
                        }
                    }));
                }
            }
        });
        return view;
    }
}