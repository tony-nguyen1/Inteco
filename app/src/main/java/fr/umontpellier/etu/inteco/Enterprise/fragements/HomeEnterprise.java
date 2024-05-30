package fr.umontpellier.etu.inteco.Enterprise.fragements;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.umontpellier.etu.inteco.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeEnterprise#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeEnterprise extends Fragment {

    private static final String ARG_COMPANY_NAME = "companyName";
    private static final String ARG_EMAIL = "email";
    private static final String TAG = "debug HomeEntreprise";


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
        // Inflate the layout for this fragment

        TextView nameTextView = rootView.findViewById(R.id.nameTextView);
        Log.d(TAG, "onCreateView: just before showing name="+mCompanyName);
        nameTextView.setText(mCompanyName);
        return rootView;
    }
}