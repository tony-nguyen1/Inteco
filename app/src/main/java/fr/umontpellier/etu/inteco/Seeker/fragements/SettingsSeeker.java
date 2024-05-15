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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import fr.umontpellier.etu.inteco.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsSeeker#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsSeeker extends Fragment {

    private static final String ARG_EMAIL = "email";
    private static final String ARG_FIRST_NAME = "firstName";
    private static final String ARG_LAST_NAME = "lastName";

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

        Log.v("SettingsSeeker", "Email: " + email);
        Log.v("SettingsSeeker", "First Name: " + firstName);
        Log.v("SettingsSeeker", "Last Name: " + lastName);
        Log.v("SettingsSeeker", "Date of Birth: " + dob);
        Log.v("SettingsSeeker", "Phone Number: " + phoneNumber);
        Log.v("SettingsSeeker", "Location: " + location);
        Log.v("SettingsSeeker", "City: " + city);
        Log.v("SettingsSeeker", "Nationality: " + nationality);
        Log.v("SettingsSeeker", "Gender: " + gender);
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
