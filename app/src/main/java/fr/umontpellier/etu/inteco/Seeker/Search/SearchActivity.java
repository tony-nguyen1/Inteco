package fr.umontpellier.etu.inteco.Seeker.Search;

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
import android.view.View;
import android.widget.Button;
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
import fr.umontpellier.etu.inteco.Seeker.JobDetailsActivity;
import fr.umontpellier.etu.inteco.Seeker.MyItemRecyclerViewAdapter;
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

        MyItemRecyclerViewAdapter.AdapterItemClickListener myClickListernerReusable = new MyItemRecyclerViewAdapter.AdapterItemClickListener() {
            @Override
            public void onItemClickListener(Offer item, int position) {
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
        };

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


                MyItemRecyclerViewAdapter customAdaptator = new MyItemRecyclerViewAdapter(myList, myClickListernerReusable);

                RecyclerView recyclerView = findViewById(R.id.recyclerView);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(customAdaptator);

            }
        });

        EditText editTextJobTitle = findViewById(R.id.jobTitleEditText);
        EditText editTextLocation = findViewById(R.id.locationEditText);

        Button btnSearch = findViewById(R.id.search_button);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SearchActivity.this, "Searching", Toast.LENGTH_SHORT).show();
                String[] tab1 = editTextJobTitle.getText().toString().replace("\n","").toLowerCase().split(" ");
                String[] tab2 = editTextLocation.getText().toString().replace("\n","").toLowerCase().split(" ");
                List<String> keywords1 = Arrays.asList(tab1);
                List<String> keywords2 = Arrays.asList(tab2);

                MutableLiveData<ArrayList<QueryDocumentSnapshot>> answer = new MutableLiveData<>();
                searchForJob(keywords1, keywords2, answer);

                answer.observe(SearchActivity.this, new Observer<ArrayList<QueryDocumentSnapshot>>() {
                    @Override
                    public void onChanged(ArrayList<QueryDocumentSnapshot> queryDocumentSnapshots) {
                        Log.d(TAG, "onChanged: It is changing");
                        ArrayList<Offer> myList = new ArrayList<>();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
                            Offer p = new Offer(
                                    document.getId(),
                                    document.get("jobTitle", String.class),
                                    document.get("companyName", String.class),
                                    document.get("place", String.class),
                                    "theDate",//document.get("postDate", String.class),
                                    "1000"//document.get("salary", String.class)
                            );
//                                Log.d(TAG, "onChanged: "+p);
                            myList.add(p);
//                                myList = new ArrayList<>();
//                                MyItemRecyclerViewAdapter customAdaptator = new MyItemRecyclerViewAdapter(myList, new MyItemRecyclerViewAdapter.AdapterItemClickListener() {
//                                    @Override
//                                    public void onItemClickListener(Offer item, int position) {
//
//                                    }
//                                });
//                                RecyclerView recyclerView = findViewById(R.id.recyclerView);
//                                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//                                recyclerView.setLayoutManager(mLayoutManager);
//                                recyclerView.setAdapter(customAdaptator);
                        }
                        Log.d(TAG, "onChanged: list created, ready to change recycler view");
                        Log.d(TAG, "onChanged: "+myList);
                        MyItemRecyclerViewAdapter customAdaptator = new MyItemRecyclerViewAdapter(myList, myClickListernerReusable);
                        RecyclerView recyclerView = findViewById(R.id.recyclerView);
                        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setAdapter(customAdaptator);

                    }
                });
            }
        });
    }

    public void searchForJob(List<String> keywordsTitle, List<String> keywordsLocation, MutableLiveData<ArrayList<QueryDocumentSnapshot>> response) {
        Log.d(TAG, "searchForJob: keywordsTitle="+keywordsTitle+"    keywordsLocation="+keywordsLocation);
        db.collection("offers").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                ArrayList<QueryDocumentSnapshot> matchingDocumentsArrayList = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        boolean thisDocumentIsSelected = false;
                        String jobTitle = document.get("jobTitle", String.class);
                        String location = document.getString("place");

                        String[] tab = jobTitle.toLowerCase().split(" ");
                        List l = Arrays.asList(tab);
                        for (String keyword : keywordsTitle) {
                            if (l.contains(keyword)) {
//                                Log.d(TAG, "onComplete: keyword="+keyword);
                                thisDocumentIsSelected = true;
                            }
                        }

                        tab = location.toLowerCase().split(" ");
                        l = Arrays.asList(tab);
                        Log.d(TAG, "onComplete: searching in"+l);
                        Log.d(TAG, "onComplete: keywordsLocation="+keywordsLocation);
                        for (String keyword : keywordsLocation) {
                            if (l.contains(keyword)) {
//                                Log.d(TAG, "onComplete: keyword="+keyword);
                                thisDocumentIsSelected = true;
                            }
                        }

                        if (thisDocumentIsSelected) {
                            matchingDocumentsArrayList.add(document);
                            Log.d(TAG, "onComplete: match");
                        }
                    }
                    Log.d(TAG, "onComplete: matched :"+matchingDocumentsArrayList);
                    response.postValue(matchingDocumentsArrayList);
                } else {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
            }
        });
    }
}
