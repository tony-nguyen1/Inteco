package fr.umontpellier.etu.inteco.Seeker.fragements;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

import fr.umontpellier.etu.inteco.R;
import fr.umontpellier.etu.inteco.helper.Helper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsSeeker#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsSeeker extends Fragment {

    private static final String ARG_EMAIL = "email";
    private static final String ARG_FIRST_NAME = "firstName";
    private static final String ARG_LAST_NAME = "lastName";
    private static final String TAG = "debug SettingsSeeker";

    private EditText mEmailEditText;
    private EditText mFirstNameEditText;
    private EditText mLastNameEditText;
    private EditText mDobEditText;
    private EditText mPhoneNumberEditText;
    private EditText mLocationEditText;
    private EditText mCityEditText;
    private EditText mNationalityEditText;
    private RadioGroup mGenderGroup;
    private RadioButton mGenderMale;
    private RadioButton mGenderFemale;
    private RadioButton mGenderOther;
    private Button mSaveButton;

    private String mEmail;
    private String mFirstName;
    private String mLastName;

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public SettingsSeeker() {
        // Required empty public constructor
    }

    public static SettingsSeeker newInstance(String email, String firstName, String lastName) {
        SettingsSeeker fragment = new SettingsSeeker();
        Bundle args = new Bundle();
        args.putString(ARG_EMAIL, email);
        args.putString(ARG_FIRST_NAME, firstName);
        args.putString(ARG_LAST_NAME, lastName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mEmail = getArguments().getString(ARG_EMAIL);
            mFirstName = getArguments().getString(ARG_FIRST_NAME);
            mLastName = getArguments().getString(ARG_LAST_NAME);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings_seeker, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize views
        mEmailEditText = view.findViewById(R.id.email);
        mFirstNameEditText = view.findViewById(R.id.firstname);
        mLastNameEditText = view.findViewById(R.id.lastname);
        mDobEditText = view.findViewById(R.id.dob);
        mPhoneNumberEditText = view.findViewById(R.id.phoneNumber);
        mLocationEditText = view.findViewById(R.id.location);
        mCityEditText = view.findViewById(R.id.city);
        mNationalityEditText = view.findViewById(R.id.nationality);
        mGenderGroup = view.findViewById(R.id.genderGroup);
        mGenderMale = view.findViewById(R.id.genderMale);
        mGenderFemale = view.findViewById(R.id.genderFemale);
        mGenderOther = view.findViewById(R.id.genderOther);
        mSaveButton = view.findViewById(R.id.saveButton);

        // Set initial values
        mEmailEditText.setText(mEmail);
        mFirstNameEditText.setText(mFirstName);
        mLastNameEditText.setText(mLastName);

        //TODO set the rest of the inputs to their values in the database
        /**
         * TODO :
         * db request
         */

        MutableLiveData<QueryDocumentSnapshot> listen = new MutableLiveData<>();
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            boolean foundEmail = false;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                String s = document.get("email", String.class);
                                if (mEmail.equals(s)){
                                    listen.postValue(document); // post a value
                                    foundEmail = true;
                                }
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

        listen.observe(getViewLifecycleOwner(), new Observer<QueryDocumentSnapshot>() {
            @Override
            public void onChanged(QueryDocumentSnapshot queryDocumentSnapshot) {
                mDobEditText.setText(queryDocumentSnapshot.get("birthday",String.class));
                mPhoneNumberEditText.setText(queryDocumentSnapshot.get("phoneNumber",String.class));
                mLocationEditText.setText(queryDocumentSnapshot.get("location",String.class));
                mCityEditText.setText(queryDocumentSnapshot.get("city",String.class));
                mNationalityEditText.setText(queryDocumentSnapshot.get("nationality",String.class));
                String gender = queryDocumentSnapshot.get("gender",String.class);
                switch (gender) {
                    case "Male":
                        mGenderMale.setChecked(true);
                        break;
                    case "Female":
                        mGenderFemale.setChecked(true);
                        break;
                    case "Other":
                        mGenderOther.setChecked(true);
                        break;
                }
                mGenderGroup = view.findViewById(R.id.genderGroup);

                TextView userNameTextView, userLocationTextView;
                userNameTextView = view.findViewById(R.id.userName);
                userLocationTextView = view.findViewById(R.id.userLocation);

                userNameTextView.setText(mFirstName + " " + mLastName);
                userLocationTextView.setText(queryDocumentSnapshot.get("location",String.class));


//                mDobEditText = view.findViewById(R.id.dob);
//                mPhoneNumberEditText = view.findViewById(R.id.phoneNumber);
//                mLocationEditText = view.findViewById(R.id.location);
//                mCityEditText = view.findViewById(R.id.city);
//                mNationalityEditText = view.findViewById(R.id.nationality);
//                mGenderGroup = view.findViewById(R.id.genderGroup);
//                mGenderMale = view.findViewById(R.id.genderMale);
//                mGenderFemale = view.findViewById(R.id.genderFemale);
//                mGenderOther = view.findViewById(R.id.genderOther);
//                mSaveButton = view.findViewById(R.id.saveButton);
            }
        });




        /*
        mDobEditText.setText();
        mPhoneNumberEditText.setText();
        mLocationEditText.setText();
        mCityEditText.setText();
        mNationalityEditText.setText();
        setGender("male");
        */

        // Add listener to Save button
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveAndLogInputs();
            }
        });
    }

    private void retrieveAndLogInputs() {
        Log.d(TAG, "retrieveAndLogInputs: ");
        
        String email = mEmailEditText.getText().toString();
        String firstName = mFirstNameEditText.getText().toString();
        String lastName = mLastNameEditText.getText().toString();
        String dob = mDobEditText.getText().toString();
        String phoneNumber = mPhoneNumberEditText.getText().toString();
        String location = mLocationEditText.getText().toString();
        String city = mCityEditText.getText().toString();
        String nationality = mNationalityEditText.getText().toString();
        String gender;
        int selectedGenderId = mGenderGroup.getCheckedRadioButtonId();
        if (selectedGenderId == R.id.genderMale) {
            gender = "Male";
        } else if (selectedGenderId == R.id.genderFemale) {
            gender = "Female";
        } else {
            gender = "Other";
        }

        Log.v(TAG, "Email: " + email);
        Log.v(TAG, "First Name: " + firstName);
        Log.v(TAG, "Last Name: " + lastName);
        Log.v(TAG, "Date of Birth: " + dob);
        Log.v(TAG, "Phone Number: " + phoneNumber);
        Log.v(TAG, "Location: " + location);
        Log.v(TAG, "City: " + city);
        Log.v(TAG, "Nationality: " + nationality);
        Log.v(TAG, "Gender: " + gender);

        //TODO db request here to save

        FirebaseUser currentUser = mAuth.getCurrentUser();
        MutableLiveData<QueryDocumentSnapshot> listen = new MutableLiveData<>();
        Helper.getJobSeekerDocumentReference(currentUser, listen);
        listen.observe(getViewLifecycleOwner(), new Observer<QueryDocumentSnapshot>() {
            @Override
            public void onChanged(QueryDocumentSnapshot aUser) {
                Map<String,Object> userData = aUser.getData();
//                userData.replace("adress", );//FIXME
                userData.replace("birthday", dob);
                userData.replace("city", city);
                userData.replace("firstname", firstName);
                userData.replace("gender", gender);
                userData.replace("lastname", lastName);
                userData.replace("nationality", nationality);
                userData.replace("phoneNumber", phoneNumber);



                Log.d(TAG, "onChanged: userData="+userData);
                aUser.getReference().update(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(SettingsSeeker.this.getContext(), "Success update", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void setGender(String gender) {
        if (gender.equalsIgnoreCase("male")) {
            mGenderMale.setChecked(true);
        } else if (gender.equalsIgnoreCase("female")) {
            mGenderFemale.setChecked(true);
        } else {
            mGenderOther.setChecked(true);
        }
    }
}
