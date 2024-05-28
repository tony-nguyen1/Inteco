package fr.umontpellier.etu.inteco.Seeker.fragements;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import fr.umontpellier.etu.inteco.R;
import fr.umontpellier.etu.inteco.Seeker.HomePageSeeker;
import fr.umontpellier.etu.inteco.Seeker.SearchActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeSeeker#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeSeeker extends Fragment {


    private static final String TAG = "debug HomeSeeker";
    private Button btnTest, btnSearch;


    private static final String email = "AYS";
    private static final String firstName = "";
    private static final String LastName = "";
    private String mEmail;
    private String mFirstName;
    private String mLastName;

    public HomeSeeker() {
        // Required empty public constructor
    }


    public static HomeSeeker newInstance(String param1, String param2, String param3) {
        Log.d(TAG, "newInstance: "+param1);
        HomeSeeker fragment = new HomeSeeker();
        Bundle args = new Bundle();
        args.putString(email, param1);
        args.putString(firstName, param2);
        args.putString(LastName, param3);
        fragment.setArguments(args);
        Log.d(TAG, "newInstance: "+args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Log.d(TAG, "onCreate: ");
        if (getArguments() != null) {
//            Log.d(TAG, "onCreate: getArguments()="+getArguments().toString());
            mEmail = getArguments().getString(email);
            mFirstName = getArguments().getString(firstName);
            mLastName = getArguments().getString(LastName);
            Log.d(TAG, "onCreate: mEmail="+mEmail + " mFirstName=" + mFirstName + " mLastName=" + mLastName);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_seeker, container, false);
//        Log.v("My email", mEmail);

        btnSearch = rootView.findViewById(R.id.btnDevGoToSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("email",mEmail);
//                Log.d(TAG, "onClick: ");
                startActivity(intent);
            }
        });


        return rootView;
    }
}