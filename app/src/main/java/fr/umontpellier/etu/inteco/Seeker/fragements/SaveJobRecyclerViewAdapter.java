package fr.umontpellier.etu.inteco.Seeker.fragements;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.umontpellier.etu.inteco.Seeker.fragements.placeholder.PlaceholderContent.PlaceholderItem;
import fr.umontpellier.etu.inteco.Seeker.placeholder.Offer;
import fr.umontpellier.etu.inteco.databinding.FragmentOfferSavedCardBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class SaveJobRecyclerViewAdapter extends RecyclerView.Adapter<SaveJobRecyclerViewAdapter.ViewHolder> {

    private final List<Offer> mValues;

    public SaveJobRecyclerViewAdapter(List<Offer> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentOfferSavedCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);
        holder.mDetailsView.setText(mValues.get(position).details);
        holder.mPlaceView.setText(mValues.get(position).place);
        holder.mSalaryView.setText(mValues.get(position).salary);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mDetailsView;
        public final TextView mPlaceView;
        public final TextView mSalaryView;
        public Offer mItem;

        public ViewHolder(FragmentOfferSavedCardBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;
            mDetailsView = binding.details;
            mPlaceView = binding.place;
            mSalaryView = binding.salary;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}