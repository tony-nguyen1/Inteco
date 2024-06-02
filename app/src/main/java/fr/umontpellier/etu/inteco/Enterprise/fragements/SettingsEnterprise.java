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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Map;

import fr.umontpellier.etu.inteco.R;
import fr.umontpellier.etu.inteco.helper.Helper;

public class SettingsEnterprise extends Fragment {

    private static final String ARG_COMPANY_NAME = "companyName";
    private static final String ARG_EMAIL = "email";
    private static final String TAG = "debug Settings";

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

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public SettingsEnterprise() {
        // Required empty public constructor
    }

    public static SettingsEnterprise newInstance(String companyName, String email) {
        SettingsEnterprise fragment = new SettingsEnterprise();
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


        FirebaseUser currentUser = mAuth.getCurrentUser();
        MutableLiveData<QueryDocumentSnapshot> listen = new MutableLiveData<>();
        Helper.getCompanyDocumentReference(currentUser, listen);
        listen.observe(getViewLifecycleOwner(), new Observer<QueryDocumentSnapshot>() {
            @Override
            public void onChanged(QueryDocumentSnapshot aCompany) {
                mCompanyNameEditText.setText(aCompany.getString("name"));
                mEmailEditText.setText(aCompany.getString("email"));
                mPhoneNumberEditText.setText(aCompany.getString("phone"));
                mCityEditText.setText(aCompany.getString("city"));
                mAddressEditText.setText(aCompany.getString("adress"));
                mWebsiteEditText.setText(aCompany.getString("website"));
                mFacebookEditText.setText(aCompany.getString("facebook"));
                mInstagramEditText.setText(aCompany.getString("instagram"));
                mLinkedinEditText.setText(aCompany.getString("linked_in"));
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

        FirebaseUser currentUser = mAuth.getCurrentUser();
        MutableLiveData<QueryDocumentSnapshot> listen = new MutableLiveData<>();
        Helper.getCompanyDocumentReference(currentUser, listen);

        listen.observe(getViewLifecycleOwner(), new Observer<QueryDocumentSnapshot>() {
            @Override
            public void onChanged(QueryDocumentSnapshot aCompany) {
                Map<String,Object> updatedDataCompany = aCompany.getData();

                updatedDataCompany.replace("name",companyName);
                updatedDataCompany.replace("email",email);
                updatedDataCompany.replace("phone",phoneNumber);
                updatedDataCompany.replace("city",city);
                updatedDataCompany.replace("adress",address);
                updatedDataCompany.replace("website",website);
                updatedDataCompany.replace("facebook",facebook);
                updatedDataCompany.replace("instagram",instagram);
                updatedDataCompany.replace("linked_in",linkedin);

                Log.d(TAG, "onChanged: "+updatedDataCompany);

                aCompany.getReference().update(updatedDataCompany).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getContext(), "Update successful", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}