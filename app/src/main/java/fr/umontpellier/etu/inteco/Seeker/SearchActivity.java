package fr.umontpellier.etu.inteco.Seeker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.umontpellier.etu.inteco.Authentication.Enterprise.SignUpEnterprise;
import fr.umontpellier.etu.inteco.Authentication.Seeker.SignUpSeeker;
import fr.umontpellier.etu.inteco.R;
import fr.umontpellier.etu.inteco.Seeker.placeholder.Offer;
import fr.umontpellier.etu.inteco.Seeker.placeholder.PlaceholderContent;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "debug SearchActivity";
    private String email;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = getIntent();
        this.email = intent.getStringExtra("email");
        Log.d(TAG, "onCreate: this.email="+email);
//        if (savedInstanceState == null) {
//            Bundle bundle1 = new Bundle();
//            bundle1.putString("theText", "Hello");
//
//            Bundle bundle2 = new Bundle();
//            bundle2.putString("theText", "World");
//
//            Bundle bundle3 = new Bundle();
//            bundle3.putString("theText", "Goodbye");
//
//            getSupportFragmentManager().beginTransaction()
//                    .setReorderingAllowed(true)
//                    .add(R.id.fragment_container_view, OfferCardFragment.class, bundle1)
//                    .add(R.id.fragment_container_view, OfferCardFragment.class, bundle2)
//                    .add(R.id.fragment_container_view, OfferCardFragment.class, bundle3)
//                    .commit();
//        }


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
                        Intent intent = new Intent(SearchActivity.this, JobDetailsActivity.class);
                        intent.putExtra("test","test");
                        intent.putExtra("id",item.id);
                        intent.putExtra("jobTitle",item.content);
                        intent.putExtra("companyName",item.details);
                        intent.putExtra("place",item.place);
                        intent.putExtra("postDate",item.postDate);
                        intent.putExtra("salary",item.salary);

                        startActivity(intent);
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

        EditText editTextJobTitle = findViewById(R.id.jobTitleEditText);
        editTextJobTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().contains("\n")) {
                    Toast.makeText(SearchActivity.this, "TODO: launch search", Toast.LENGTH_SHORT).show();

                    String[] tab = s.toString().replace("\n","").toLowerCase().split(" ");
                    List keywords = Arrays.asList(tab);

                    searchForJob(keywords, null);


                    //TODO modifier cette liste
                    ArrayList<Offer> myList = new ArrayList<Offer>();
                    MyItemRecyclerViewAdapter customAdaptator = new MyItemRecyclerViewAdapter(myList, new MyItemRecyclerViewAdapter.AdapterItemClickListener() {
                        @Override
                        public void onItemClickListener(Offer item, int position) {

                        }
                    });
                    RecyclerView recyclerView = findViewById(R.id.recyclerView);
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setAdapter(customAdaptator);

                }
            }
        });


    }

    public void searchForJob(List<String> keywords, MutableLiveData<QueryDocumentSnapshot> response) {
        Log.d(TAG, "searchForJob: keywords="+keywords);
        db.collection("offers").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
//                        if (Collections.)
                        String jobTitle = document.get("jobTitle", String.class);

                        String[] tab = jobTitle.toLowerCase().split(" ");
                        List l = Arrays.asList(tab);
                        Log.d(TAG, "onComplete: "+l);

                        for (String keyword : keywords) {
                            if (l.contains(keyword)) {
                                Log.d(TAG, "onComplete: keyword="+keyword);
                            }
                        }

//                        if (Collections.disjoint(l,keywords)) {
//                            Log.d(TAG, "onComplete: disjoint");
//                        } else {
//                            Log.d(TAG, "onComplete: overlap");
//                        }
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });
    }
}