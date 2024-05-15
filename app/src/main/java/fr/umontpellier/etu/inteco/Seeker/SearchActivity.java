package fr.umontpellier.etu.inteco.Seeker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import fr.umontpellier.etu.inteco.R;
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

//        ArrayList<PlaceholderContent.PlaceholderItem> data = new ArrayList<>();
//        data.add()
        MyItemRecyclerViewAdapter customAdaptateur = new MyItemRecyclerViewAdapter(PlaceholderContent.ITEMS);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(customAdaptateur);


        for (PlaceholderContent.PlaceholderItem item : PlaceholderContent.ITEMS) {
            Log.d(TAG, "onCreate: "+item);
        }
    }
}