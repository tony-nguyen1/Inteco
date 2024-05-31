package fr.umontpellier.etu.inteco.Enterprise.fragements.myPosts;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.umontpellier.etu.inteco.Enterprise.fragements.myPosts.placeholder.PlaceholderContent.PlaceholderItem;
import fr.umontpellier.etu.inteco.R;
import fr.umontpellier.etu.inteco.databinding.FragmentPostedCardBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyPostRecyclerViewAdapter extends RecyclerView.Adapter<MyPostRecyclerViewAdapter.ViewHolder> {

    private final List<PlaceholderItem> mValues;

    public MyPostRecyclerViewAdapter(List<PlaceholderItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentPostedCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
//        holder.mIdView.setText(mValues.get(position).id);
//        holder.mContentView.setText(mValues.get(position).content);

        holder.mJobTitle.setText("title");
        holder.mNbApplicants.setText("16 applicants");
        holder.mDate.setText("date");
        holder.mStatus.setText("status");
        holder.mJobTitle.setText(mValues.get(position).jobTitle);
        holder.mNbApplicants.setText(String.valueOf(mValues.get(position).numberApplicants)+" applicants");
        holder.mDate.setText(mValues.get(position).dateDetails);
        holder.mStatus.setText(mValues.get(position).state);
        // Set the text color based on the state value
        Context context = holder.mStatus.getContext();
        if ("open".equalsIgnoreCase(mValues.get(position).state)) {
            holder.mStatus.setTextColor(ContextCompat.getColor(context, R.color.status_open));
        } else if ("false".equalsIgnoreCase(mValues.get(position).state)) {
            holder.mStatus.setTextColor(ContextCompat.getColor(context, R.color.status_false));
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mJobTitle;
        public final TextView mNbApplicants;
        public final TextView mDate;
        public final TextView mStatus;
        public PlaceholderItem mItem;

        public ViewHolder(FragmentPostedCardBinding binding) {
            super(binding.getRoot());
            mJobTitle = binding.jobTitle ;
            mNbApplicants = binding.nbApplicants ;
            mDate = binding.date ;
            mStatus = binding.status ;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mItem + "'";
        }
    }
}