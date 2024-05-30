package fr.umontpellier.etu.inteco.Seeker.fragements.Saved;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.umontpellier.etu.inteco.Seeker.placeholder.Offer;
import fr.umontpellier.etu.inteco.databinding.FragmentItemBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Offer}.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<Offer> mValues;
    AdapterItemClickListener itemClickListener;

    public MyItemRecyclerViewAdapter(List<Offer> items, AdapterItemClickListener itemClickListener) {
        mValues = items;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        FragmentItemBinding v = FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);



        return new ViewHolder(v);
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

        // click functionality
        Offer item = mValues.get(position);
        holder.itemView.setOnClickListener (v -> {
            itemClickListener.onItemClickListener(item, position);
        });
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
        public Offer mItem;

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

    public interface AdapterItemClickListener {
        void onItemClickListener(Offer item, int position);
    }
}