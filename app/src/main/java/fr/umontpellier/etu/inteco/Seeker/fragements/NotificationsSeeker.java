package fr.umontpellier.etu.inteco.Seeker.fragements;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.umontpellier.etu.inteco.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationsSeeker#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationsSeeker extends Fragment {

    private static final String email = "AYS";
    private static final String firstName = "";
    private static final String LastName = "";
    private String mEmail;
    private String mFirstName;
    private String mLastName;

    public NotificationsSeeker() {
        // Required empty public constructor
    }


    public static NotificationsSeeker newInstance(String param1, String param2, String param3) {
        NotificationsSeeker fragment = new NotificationsSeeker();
        Bundle args = new Bundle();
        args.putString(email, param1);
        args.putString(firstName, param2);
        args.putString(LastName, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mEmail = getArguments().getString(email);
            mFirstName = getArguments().getString(firstName);
            mLastName = getArguments().getString(LastName);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v("My email", email);
        return inflater.inflate(R.layout.fragment_notifications_seeker, container, false);
    }
}