package fr.umontpellier.etu.inteco.Enterprise.fragements;

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
import fr.umontpellier.etu.inteco.Seeker.fragements.HomeSeeker;
import fr.umontpellier.etu.inteco.Seeker.fragements.MyApplications;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeEnterprise#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeEnterprise extends Fragment {

    private static final String ARG_COMPANY_NAME = "companyName";
    private static final String ARG_EMAIL = "email";
    private static final String TAG = "debug HomeEntreprise";

    private TextView nameTV, inWaitingTV, acceptedTV, refusedTV, applicationsDetailsTV;
    private String mCompanyName;
    private String mEmail;



    public HomeEnterprise() {
        // Required empty public constructor
    }

    public static HomeEnterprise newInstance(String companyName, String email) {
        Log.d(TAG, "newInstance: companyName="+companyName);
        HomeEnterprise fragment = new HomeEnterprise();
        Bundle args = new Bundle();
        args.putString(ARG_COMPANY_NAME, companyName);
        args.putString(ARG_EMAIL, email);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCompanyName = getArguments().getString(ARG_COMPANY_NAME);
            mEmail = getArguments().getString(ARG_EMAIL);
            Log.d(TAG, "onCreate: mCompanyName="+mCompanyName);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_enterprise, container, false);
        nameTV = rootView.findViewById(R.id.nameTextView);
        inWaitingTV = rootView.findViewById(R.id.inWaiting);
        acceptedTV = rootView.findViewById(R.id.accepted);
        refusedTV = rootView.findViewById(R.id.refused);
        applicationsDetailsTV = rootView.findViewById(R.id.applicationsDetails);
        TextView nameTextView = rootView.findViewById(R.id.nameTextView);

        // Setting the name
        nameTextView.setText(mCompanyName);

        // TODO Ici pour modifier les cases
        /*
        inWaitingTV.setText("5"+ "In waiting");
        acceptedTV.setText("4" + "Accepted");
        refusedTV.setText("3" + "Refused");

        applicationsDetailsTV.setText("");
         */



        return rootView;
    }
}




