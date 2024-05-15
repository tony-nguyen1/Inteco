package fr.umontpellier.etu.inteco.Enterprise.fragements;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.umontpellier.etu.inteco.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyPostsEnterprise#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyPostsEnterprise extends Fragment {

    private static final String ARG_COMPANY_NAME = "companyName";
    private static final String ARG_EMAIL = "email";
    private String mCompanyName;
    private String mEmail;
    public MyPostsEnterprise() {
        // Required empty public constructor
    }

    public static SttingsEnterprise newInstance(String companyName, String email) {
        SttingsEnterprise fragment = new SttingsEnterprise();
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
            //TODO Get the value from the back end
            /*
            mPhoneNumber = ;
            mCity =
            mAddress =
            mWebsite =
            mFacebook =
            mLinkedin =
            mInstagram =

             */
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_posts_enterprise, container, false);
    }
}