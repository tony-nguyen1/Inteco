package fr.umontpellier.etu.inteco.Enterprise.fragements.myPosts;

import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.umontpellier.etu.inteco.Enterprise.fragements.candidateForAJob.MyCandidateRecyclerViewAdapter;
import fr.umontpellier.etu.inteco.Enterprise.fragements.myPosts.placeholder.PlaceholderContent.PlaceholderItem;
import fr.umontpellier.etu.inteco.Seeker.placeholder.Offer;
import fr.umontpellier.etu.inteco.databinding.FragmentPostedCardBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyPostRecyclerViewAdapter extends RecyclerView.Adapter<MyPostRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "debug MyPostRecyclerViewAdaptater";
    private final List<PlaceholderItem> mValues;
    private final AdapterItemClickListener itemClickListener;
    public interface AdapterItemClickListener {
        void onItemClickListener(PlaceholderItem item, int position);
    }

    public MyPostRecyclerViewAdapter(List<PlaceholderItem> items, AdapterItemClickListener adapterItemClickListener) {
        mValues = items;itemClickListener=adapterItemClickListener;
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

        holder.itemView.setOnClickListener( v -> {
            Log.d(TAG, "onBindViewHolder: click");
            itemClickListener.onItemClickListener(mValues.get(position), position);
        });
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