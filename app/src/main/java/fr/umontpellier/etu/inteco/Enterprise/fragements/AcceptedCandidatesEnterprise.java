package fr.umontpellier.etu.inteco.Enterprise.fragements;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.umontpellier.etu.inteco.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AcceptedCandidatesEnterprise#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AcceptedCandidatesEnterprise extends Fragment {
    private static final String ARG_COMPANY_NAME = "companyName";
    private static final String ARG_EMAIL = "email";

    private String mCompanyName;
    private String mEmail;

    public AcceptedCandidatesEnterprise() {
        // Required empty public constructor
    }

    public static AcceptedCandidatesEnterprise newInstance(String companyName, String email) {
        AcceptedCandidatesEnterprise fragment = new AcceptedCandidatesEnterprise();
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

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_accepted_candidates_enterprise, container, false);
    }
}