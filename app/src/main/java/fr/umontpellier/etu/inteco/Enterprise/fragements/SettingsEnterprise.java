package fr.umontpellier.etu.inteco.Enterprise.fragements;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import fr.umontpellier.etu.inteco.R;

public class SttingsEnterprise extends Fragment {

    private static final String ARG_COMPANY_NAME = "companyName";
    private static final String ARG_EMAIL = "email";

    private EditText mCompanyNameEditText;
    private EditText mEmailEditText;
    private EditText mPhoneNumberEditText;
    private EditText mCityEditText;
    private EditText mAddressEditText;
    private EditText mWebsiteEditText;
    private EditText mFacebookEditText;
    private EditText mLinkedinEditText;
    private EditText mInstagramEditText;
    private Button mSaveButton;

    private String mCompanyName;
    private String mEmail;
    private String mPhoneNumber;
    private String mCity;
    private String mAddress;
    private String mWebsite;
    private String mFacebook;
    private String mLinkedin;
    private String mInstagram;

    public SttingsEnterprise() {
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings_enterprise, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        mCompanyNameEditText = view.findViewById(R.id.companyName);
        mEmailEditText = view.findViewById(R.id.email);
        mPhoneNumberEditText = view.findViewById(R.id.phoneNumber);
        mCityEditText = view.findViewById(R.id.city);
        mAddressEditText = view.findViewById(R.id.address);
        mWebsiteEditText = view.findViewById(R.id.website);
        mFacebookEditText = view.findViewById(R.id.facebook);
        mLinkedinEditText = view.findViewById(R.id.linkedin);
        mInstagramEditText = view.findViewById(R.id.instagram);
        mSaveButton = view.findViewById(R.id.saveButton);

        // Set initial values
        mCompanyNameEditText.setText(mCompanyName);
        mEmailEditText.setText(mEmail);
        mPhoneNumberEditText.setText(mPhoneNumber);
        mCityEditText.setText(mCity);
        mAddressEditText.setText(mAddress);
        mWebsiteEditText.setText(mWebsite);
        mFacebookEditText.setText(mFacebook);
        mLinkedinEditText.setText(mLinkedin);
        mInstagramEditText.setText(mInstagram);

        // Add listener to Save button
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveAndLogInputs();
            }
        });
    }

    private void retrieveAndLogInputs() {
        String companyName = mCompanyNameEditText.getText().toString();
        String email = mEmailEditText.getText().toString();
        String phoneNumber = mPhoneNumberEditText.getText().toString();
        String city = mCityEditText.getText().toString();
        String address = mAddressEditText.getText().toString();
        String website = mWebsiteEditText.getText().toString();
        String facebook = mFacebookEditText.getText().toString();
        String linkedin = mLinkedinEditText.getText().toString();
        String instagram = mInstagramEditText.getText().toString();

        Log.v("CompanySettings", "Company Name: " + companyName);
        Log.v("CompanySettings", "Email: " + email);
        Log.v("CompanySettings", "Phone Number: " + phoneNumber);
        Log.v("CompanySettings", "City: " + city);
        Log.v("CompanySettings", "Address: " + address);
        Log.v("CompanySettings", "Website: " + website);
        Log.v("CompanySettings", "Facebook: " + facebook);
        Log.v("CompanySettings", "LinkedIn: " + linkedin);
        Log.v("CompanySettings", "Instagram: " + instagram);
    }
}