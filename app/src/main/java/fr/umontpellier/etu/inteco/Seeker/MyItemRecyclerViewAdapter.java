package fr.umontpellier.etu.inteco.Seeker;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.umontpellier.etu.inteco.Seeker.placeholder.PlaceholderContent.PlaceholderItem;
import fr.umontpellier.etu.inteco.databinding.FragmentItemBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<PlaceholderItem> mValues;

    public MyItemRecyclerViewAdapter(List<PlaceholderItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // on met les valeurs dans les vues
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);
        holder.mDetailsView.setText(mValues.get(position).details);
        holder.mPlaceView.setText(mValues.get(position).place);
        holder.mPostDateView.setText(mValues.get(position).postDate);
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
        public final TextView mPostDateView;
        public final TextView mSalaryView;
        public PlaceholderItem mItem;

        public ViewHolder(FragmentItemBinding binding) {
            // on associe aux vues, leur emplacement grâce à l'id (binding.id)
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;
            mDetailsView = binding.details;
            mPlaceView = binding.place;
            mPostDateView = binding.postDate;
            mSalaryView = binding.salary;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}