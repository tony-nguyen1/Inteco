package fr.umontpellier.etu.inteco.Enterprise.fragements.candidateForAJob;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.umontpellier.etu.inteco.Enterprise.fragements.candidateForAJob.placeholder.PlaceholderContent.PlaceholderItem;
import fr.umontpellier.etu.inteco.Seeker.placeholder.Offer;
import fr.umontpellier.etu.inteco.databinding.FragmentCandidateCardBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyCandidateRecyclerViewAdapter extends RecyclerView.Adapter<MyCandidateRecyclerViewAdapter.ViewHolder> {

    private final List<PlaceholderItem> mValues;
    private final AdapterItemClickListener itemClickListener;

    public interface AdapterItemClickListener {
        void onItemClickListener(PlaceholderItem item, int position);
    }

    public MyCandidateRecyclerViewAdapter(List<PlaceholderItem> items, AdapterItemClickListener adapterItemClickListener) {
        mValues = items;
        this.itemClickListener = adapterItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentCandidateCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).name);
        holder.mAppliedDateView.setText(mValues.get(position).timestamp);
        holder.mStatusView.setText(mValues.get(position).status);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mNameView;
        public final TextView mAppliedDateView;
        public final TextView mStatusView;
        public PlaceholderItem mItem;

        public ViewHolder(FragmentCandidateCardBinding binding) {
            super(binding.getRoot());
            mNameView = binding.name;
            mAppliedDateView = binding.appliedDate;
            mStatusView = binding.status;
        }

    }
}