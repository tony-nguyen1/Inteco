package fr.umontpellier.etu.inteco.Enterprise.fragements;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;



import fr.umontpellier.etu.inteco.R;
import fr.umontpellier.etu.inteco.Seeker.placeholder.Offer;

public class AddPostEnterprise extends Fragment {
    private static final String ARG_COMPANY_NAME = "companyName";
    private static final String ARG_EMAIL = "email";

    private String mCompanyName;
    private String mEmail;

    private EditText titleInput, descriptionInput, requirementsInput, salaryInput,
            startingTimeInput, durationInput, experienceInput, qualificationInput, locationInput;
    private Spinner jobTypeSpinner, contractTypeSpinner, categorySpinner;
    private Button confirmButton;

    public AddPostEnterprise() {
        // Required empty public constructor
    }

    public static AddPostEnterprise newInstance(String companyName, String email) {
        AddPostEnterprise fragment = new AddPostEnterprise();
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_post_enterprise, container, false);

        // Bind the XML views
        titleInput = view.findViewById(R.id.title);
        descriptionInput = view.findViewById(R.id.description);
        requirementsInput = view.findViewById(R.id.requirements);
        salaryInput = view.findViewById(R.id.salary);
        startingTimeInput = view.findViewById(R.id.startingTime);
        durationInput = view.findViewById(R.id.duration);
        experienceInput = view.findViewById(R.id.experience);
        qualificationInput = view.findViewById(R.id.qualification);
        locationInput = view.findViewById(R.id.location);
        jobTypeSpinner = view.findViewById(R.id.jobTypeSpinner);
        contractTypeSpinner = view.findViewById(R.id.contractTypeSpinner);
        categorySpinner = view.findViewById(R.id.categorySpinner);
        confirmButton = view.findViewById(R.id.confirmButton);

        // Set up the spinners
        setupSpinners();

        // Set click listener for the button
        confirmButton.setOnClickListener(v -> savePostToFirebase());

        return view;
    }

    private void setupSpinners() {
        // Job Type Spinner
        ArrayAdapter<CharSequence> jobTypeAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.job_types, android.R.layout.simple_spinner_item);
        jobTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jobTypeSpinner.setAdapter(jobTypeAdapter);

        // Contract Type Spinner
        ArrayAdapter<CharSequence> contractTypeAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.contract_types, android.R.layout.simple_spinner_item);
        contractTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        contractTypeSpinner.setAdapter(contractTypeAdapter);

        // Category Spinner
        ArrayAdapter<CharSequence> categoryAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.categories, android.R.layout.simple_spinner_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryAdapter);
    }

    private void savePostToFirebase() {
        // Get input values
        String title = titleInput.getText().toString().trim();
        String description = descriptionInput.getText().toString().trim();
        String requirements = requirementsInput.getText().toString().trim();
        String salary = salaryInput.getText().toString().trim();
        String startingTime = startingTimeInput.getText().toString().trim();
        String jobType = jobTypeSpinner.getSelectedItem().toString();
        String contractType = contractTypeSpinner.getSelectedItem().toString();
        String duration = durationInput.getText().toString().trim();
        String experience = experienceInput.getText().toString().trim();
        String qualification = qualificationInput.getText().toString().trim();
        String location = locationInput.getText().toString().trim();
        String category = categorySpinner.getSelectedItem().toString();

        //TODO Generate a unique ID for the offer
        String offerId= "d";
        /*
        String offerId = FirebaseDatabase.getInstance().getReference("posts").push().getKey();
        */
        String postDate = null; // Current date as post date
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            postDate = java.time.LocalDate.now().toString();
        }

        // Create an Offer object

        Offer offer = new Offer(offerId, title, mEmail, mCompanyName, location, postDate, salary,
                description, requirements, jobType, contractType, duration, experience,
                qualification, location, category,startingTime);

        //TODO Save to Firebase


    }
}
