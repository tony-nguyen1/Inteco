package fr.umontpellier.etu.inteco.Enterprise.fragements.candidateForAJob;

import android.content.Context;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Locale;

import fr.umontpellier.etu.inteco.R;
import fr.umontpellier.etu.inteco.helper.Helper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfoPostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoPostFragment extends Fragment {
    private static final String TAG = "debug InfoPost";
    private DocumentReference docRefOffer;


    public InfoPostFragment() {
        // Required empty public constructor
    }

    public static InfoPostFragment newInstance(DocumentReference docRefOffer) {
        InfoPostFragment fragment = new InfoPostFragment(); fragment.docRefOffer = docRefOffer;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info_post, container, false);

        Log.d(TAG, "onCreateView: debut "+ this.docRefOffer.getId());
        MutableLiveData<QueryDocumentSnapshot> listen = new MutableLiveData<>();
        Helper.getAnOffer(this.docRefOffer, listen);
        listen.observe(getViewLifecycleOwner(), new Observer<QueryDocumentSnapshot>() {
            @Override
            public void onChanged(QueryDocumentSnapshot queryDocumentSnapshot) {
                Log.d(TAG, "onChanged: ");

                Log.d(TAG, "onChanged: snap="+queryDocumentSnapshot.getData().toString());
                // set text

                assert getActivity() != null;
                TextView jobTitleTextView = getActivity().findViewById(R.id.jobTitle);
                TextView postDateTextView = getActivity().findViewById(R.id.postDate);
                TextView currentStateTextView = getActivity().findViewById(R.id.currentState);
                TextView nbApplicantsTextView = getActivity().findViewById(R.id.nbApplicants);

                jobTitleTextView.setText(queryDocumentSnapshot.getString("post_title"));
                postDateTextView.setText(new PrettyTime(new Locale("en")).format(queryDocumentSnapshot.get("realDate", Timestamp.class).toDate()));
                currentStateTextView.setText(queryDocumentSnapshot.getString("state"));
                Context context = currentStateTextView.getContext();
                if ("open".equalsIgnoreCase(queryDocumentSnapshot.getString("state"))) {
                    currentStateTextView.setTextColor(ContextCompat.getColor(context, R.color.status_open));
                } else if ("closed".equalsIgnoreCase(queryDocumentSnapshot.getString("state"))) {
                    currentStateTextView.setTextColor(ContextCompat.getColor(context, R.color.status_false));
                }
            }
        });



        Log.d(TAG, "onCreateView: fin");
        return view;
    }
}