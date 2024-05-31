package fr.umontpellier.etu.inteco.Enterprise.fragements.myPosts;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.umontpellier.etu.inteco.Enterprise.fragements.candidateForAJob.InfoPostFragment;
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

    public static MyPostsEnterprise newInstance(String companyName, String email) {
        MyPostsEnterprise fragment = new MyPostsEnterprise();
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

        assert getActivity() != null;
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_inside, PostedListFragment.newInstance(1))
                .commit();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_posts_enterprise, container, false);
    }
}