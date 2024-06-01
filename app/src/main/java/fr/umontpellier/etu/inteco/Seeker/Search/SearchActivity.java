package fr.umontpellier.etu.inteco.Seeker.Search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import fr.umontpellier.etu.inteco.R;
import fr.umontpellier.etu.inteco.Seeker.JobDetailsActivity;
import fr.umontpellier.etu.inteco.Seeker.placeholder.Offer;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "debug SearchActivity";
    private String email;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FusedLocationProviderClient locationClient;
    private SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent intent = getIntent();
        this.email = intent.getStringExtra("email");
        Log.d(TAG, "onCreate: this.email="+email);

        /** Location **/
        locationClient = LocationServices.getFusedLocationProviderClient(this);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        checkLocationPermission();


        MyItemRecyclerViewAdapter.AdapterItemClickListener myClickListernerReusable = new MyItemRecyclerViewAdapter.AdapterItemClickListener() {
            @Override
            public void onItemClickListener(Offer item, int position) {
                Log.d(TAG, "onItemClickListener: appuyé sur n°"+position+": "+item.toString());
                Intent intent = new Intent(SearchActivity.this, JobDetailsActivity.class);
                intent.putExtra("test","test");
                intent.putExtra("id",item.id.getId());
                intent.putExtra("jobTitle",item.jobTitle);
                intent.putExtra("companyName","FIXME");//FIXME
                intent.putExtra("place",item.city + " · " + item.country);
                intent.putExtra("postDate",item.realDate.toString());
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
//                    Offer p = new Offer(
//                            document.getId(),
//                            document.get("jobTitle", String.class),
//                            document.get("companyName", String.class),
//                            document.get("place", String.class),
//                            "theDate",//document.get("postDate", String.class),
//                            "1000"//document.get("salary", String.class)
//                    );
                    Offer p = Offer.newInstance(document.getReference(), document.getData());
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
//                            Offer p = new Offer(
//                                    document.getId(),
//                                    document.get("jobTitle", String.class),
//                                    document.get("companyName", String.class),
//                                    document.get("place", String.class),
//                                    "theDate",//document.get("postDate", String.class),
//                                    "1000"//document.get("salary", String.class)
//                            );
                            Offer p = Offer.newInstance(document.getReference(), document.getData());
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
        Log.d(TAG, "searchForJob: keywordsTitle=" + keywordsTitle + " keywordsLocation=" + keywordsLocation);
        db.collection("offers").get().addOnCompleteListener(task -> {
            ArrayList<QueryDocumentSnapshot> matchingDocumentsArrayList = new ArrayList<>();
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    boolean thisDocumentIsSelected = false;
                    String jobTitle = document.getString("jobTitle");
                    String location = document.getString("place");


                    if (jobTitle != null) {
                        Log.d("Document??", jobTitle);
                        String[] tab = jobTitle.toLowerCase().split(" ");
                        List<String> l = Arrays.asList(tab);
                        Log.d("Keywords BD",jobTitle);
                        Log.d("Keywords PARAM", keywordsTitle.toString());
                        for (String keyword : keywordsTitle) {
                            if (l.contains(keyword)) {
                                thisDocumentIsSelected = true;
                                Log.d(TAG, "onComplete: keyword="+keyword);
                                break;
                            }
                        }
                    }

                    if (location != null) {
                        Log.d("Document??", location);
                        String[] tab = location.toLowerCase().split(" ");
                        List<String> l = Arrays.asList(tab);
                        Log.d(TAG, "onComplete: searching in" + l);
                        Log.d(TAG, "onComplete: keywordsLocation=" + keywordsLocation);
                        for (String keyword : keywordsLocation) {
                            if (l.contains(keyword)) {
                                thisDocumentIsSelected = true;
                                Log.d(TAG, "onComplete: keyword="+keyword);
                                break;
                            }
                        }
                    }

                    if (thisDocumentIsSelected) {
                        matchingDocumentsArrayList.add(document);
                        Log.d(TAG, "onComplete: match");
                    }
                }
                Log.d(TAG, "onComplete: matched :" + matchingDocumentsArrayList);
                response.postValue(matchingDocumentsArrayList);
            } else {
                Log.w(TAG, "Error getting documents.", task.getException());
            }
        });
    }
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            storeLastLocation();
            performDefaultSearch();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            storeLastLocation();
            performDefaultSearch();
        }
    }

    private void storeLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Task<Location> task = locationClient.getLastLocation();
            task.addOnSuccessListener(location -> {
                if (location != null) {
                    // Store the location in SharedPreferences
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putFloat("LastKnownLatitude", (float) location.getLatitude());
                    editor.putFloat("LastKnownLongitude", (float) location.getLongitude());
                    editor.apply();
                }
            });
        }
    }
    private void performDefaultSearch() {
        float lastKnownLatitude = prefs.getFloat("LastKnownLatitude", 0);
        float lastKnownLongitude = prefs.getFloat("LastKnownLongitude", 0);

        if (lastKnownLatitude != 0 && lastKnownLongitude != 0) {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            try {
                List<Address> addresses = geocoder.getFromLocation(lastKnownLatitude, lastKnownLongitude, 1);
                if (!addresses.isEmpty()) {
                    Address address = addresses.get(0);
                    String city = address.getLocality();
                    String country = address.getCountryName();
                    String location = city +" "+ country;

                    Log.d(TAG, "performDefaultSearch: Default location = " + city);

                    EditText editTextLocation = findViewById(R.id.locationEditText);
                    editTextLocation.setText(location);

                    //performSearch(findViewById(R.id.jobTitleEditText), editTextLocation, myClickListernerReusable);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
