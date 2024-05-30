package fr.umontpellier.etu.inteco.Seeker.fragements;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import fr.umontpellier.etu.inteco.R;
import fr.umontpellier.etu.inteco.Seeker.Search.SearchActivity;


public class HomeSeeker extends Fragment {

    private static final String TAG = "debug HomeSeeker";
    private Button btnSearch;
    private static final String email = "AYS";
    private static final String firstName = "le prénom";
    private static final String LastName = "le nom de famille";
    private String mEmail;
    private String mFirstName;
    private String mLastName;
    private LinearLayout applicationsDetailsLayout;
    private LinearLayout myCandidatesLayout;

    private TextView nameTV, inWaitingTV, acceptedTV, refusedTV, applicationsDetailsTV;

    public HomeSeeker() {
        // Required empty public constructor
    }

    public static HomeSeeker newInstance(String param1, String param2, String param3) {
        Log.d(TAG, "newInstance: "+param1+" "+param2+" "+param3);
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
        if (getArguments() != null) {
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
        nameTV = rootView.findViewById(R.id.nameTextView);
        inWaitingTV = rootView.findViewById(R.id.inWaiting);
        acceptedTV = rootView.findViewById(R.id.accepted);
        refusedTV = rootView.findViewById(R.id.refused);
        applicationsDetailsTV = rootView.findViewById(R.id.applicationsDetails);
        applicationsDetailsLayout = rootView.findViewById(R.id.applicationsDetailsLayout);
        myCandidatesLayout = rootView.findViewById(R.id.my_candidates_layout);

        // Set the name
        if(Objects.equals(mFirstName, "null")){
            nameTV.setText("Anonymous");
        }
        nameTV.setText(mFirstName +" "+ mLastName);
        // Search button
        btnSearch = rootView.findViewById(R.id.button_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra("email", mEmail);
                startActivity(intent);
            }
        });

        // Click listener on the layouts
        applicationsDetailsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyApplications.class);
                intent.putExtra("email", mEmail);
                startActivity(intent);
            }
        });
        myCandidatesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyApplications.class);
                intent.putExtra("email", mEmail);
                startActivity(intent);
            }
        });

        // TODO Ici pour modifier les cases
        /*
        inWaitingTV.setText("5"+ "In waiting");
        acceptedTV.setText("4" + "Accepted");
        refusedTV.setText("3" + "Refused");

        applicationsDetailsTV.setText("");
         */

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Log.d(TAG, "onCreateView: user is signed in");
        } else {
            Log.d(TAG, "onCreateView: user is anonymous");
        }

        return rootView;
    }
}

/*
        btnTest = rootView.findViewById(R.id.btnTest);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TestAddDocumentActivity.class);
                startActivity(intent);
            }
        });
 */