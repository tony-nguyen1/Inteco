package fr.umontpellier.etu.inteco.Seeker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fr.umontpellier.etu.inteco.R;
import fr.umontpellier.etu.inteco.Seeker.placeholder.Offer;
import fr.umontpellier.etu.inteco.Seeker.placeholder.PlaceholderContent;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "debug SearchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        if (savedInstanceState == null) {
            Bundle bundle1 = new Bundle();
            bundle1.putString("theText", "Hello");

            Bundle bundle2 = new Bundle();
            bundle2.putString("theText", "World");

            Bundle bundle3 = new Bundle();
            bundle3.putString("theText", "Goodbye");

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, OfferCardFragment.class, bundle1)
                    .add(R.id.fragment_container_view, OfferCardFragment.class, bundle2)
                    .add(R.id.fragment_container_view, OfferCardFragment.class, bundle3)
                    .commit();
        }


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        MutableLiveData<Map<String,QuerySnapshot>> mutableContent = new MutableLiveData<>();


        db.collection("offers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
//                            }
                            HashMap<String,QuerySnapshot> hashMap = new HashMap<>();
                            hashMap.put("result",task.getResult());
                            mutableContent.postValue(hashMap);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        mutableContent.observe(SearchActivity.this, new Observer<Map<String, QuerySnapshot>>() {
            @Override
            public void onChanged(Map<String, QuerySnapshot> stringObjectMap) {
                ArrayList<Offer> myList = new ArrayList<>();
                for (QueryDocumentSnapshot document : stringObjectMap.get("result")) {
                    Log.d(TAG, document.getId() + " => " + document.getData());
                    Offer p = new Offer(
                            document.getId(),
                            document.get("jobTitle", String.class),
                            document.get("companyName", String.class),
                            document.get("place", String.class),
                            "theDate",//document.get("postDate", String.class),
                            "1000"//document.get("salary", String.class)
                    );
                    Log.d(TAG, "onChanged: "+p);
                    myList.add(p);
                }


                MyItemRecyclerViewAdapter customAdaptator = new MyItemRecyclerViewAdapter(myList, new MyItemRecyclerViewAdapter.AdapterItemClickListener() {
                    @Override
                    public void onItemClickListener(Offer item, int position) {
                        // TODO : lancer une nouvelle activité : description post
                        Log.d(TAG, "onItemClickListener: appuyé sur n°"+position+": "+item.toString());
                    }
                });

                RecyclerView recyclerView = findViewById(R.id.recyclerView);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(customAdaptator);


//                for (PlaceholderContent.PlaceholderItem item : PlaceholderContent.ITEMS) {
//                    Log.d(TAG, "onCreate: "+item);
//                }
            }
        });


    }
}