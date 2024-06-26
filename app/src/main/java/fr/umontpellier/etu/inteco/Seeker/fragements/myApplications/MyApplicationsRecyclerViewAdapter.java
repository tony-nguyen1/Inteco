package fr.umontpellier.etu.inteco.Seeker.fragements.myApplications;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.umontpellier.etu.inteco.R;
import fr.umontpellier.etu.inteco.Seeker.placeholder.Offer;
import fr.umontpellier.etu.inteco.databinding.FragmentAppliedCardBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Offer}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyApplicationsRecyclerViewAdapter extends RecyclerView.Adapter<MyApplicationsRecyclerViewAdapter.ViewHolder> {

    private final List<Offer> mValues;

    public MyApplicationsRecyclerViewAdapter(List<Offer> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentAppliedCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);



        holder.mJobTitle.setText(mValues.get(position).jobTitle);
        //TODO Add the name of the company
        holder.companyInfo.setText(mValues.get(position).city+", "+mValues.get(position).country);
        holder.mDate.setText("Posted "+mValues.get(position).getPrettyTime());
        holder.mStatus.setText(mValues.get(position).state);
        // Set the text color based on the state value
        Context context = holder.mStatus.getContext();
        if ("open".equalsIgnoreCase(mValues.get(position).state)) {
            holder.mStatus.setTextColor(ContextCompat.getColor(context, R.color.status_open));
        } else if ("closed".equalsIgnoreCase(mValues.get(position).state)) {
            holder.mStatus.setTextColor(ContextCompat.getColor(context, R.color.status_false));
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        public final TextView mIdView;
//        public final TextView mContentView;

        public final TextView mJobTitle;
        public final TextView companyInfo;
        public final TextView mDate;
        public final TextView mStatus;
        public Offer mItem;

        public ViewHolder(FragmentAppliedCardBinding binding) {
            super(binding.getRoot());

            mJobTitle = binding.jobTitle ;
            companyInfo = binding.companyInfo ;
            mDate = binding.postDateApplied ;
            mStatus = binding.applicationState ;
        }

//        @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }
    }
}