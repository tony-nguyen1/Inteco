package fr.umontpellier.etu.inteco.Seeker.fragements.myApplications;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

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

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);


        holder.mJobTitle.setText("title");
        holder.companyInfo.setText("Haïti");
        holder.mDate.setText("date");
        holder.mStatus.setText("status");


        holder.mJobTitle.setText(mValues.get(position).jobTitle);
        holder.companyInfo.setText(mValues.get(position).place);
        holder.mDate.setText("Applied "+mValues.get(position).dateDetails);
        holder.mStatus.setText(mValues.get(position).state);
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