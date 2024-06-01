package fr.umontpellier.etu.inteco.Seeker.Search;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;

import fr.umontpellier.etu.inteco.Seeker.placeholder.Offer;

import java.util.List;
import java.util.Locale;

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

        fr.umontpellier.etu.inteco.databinding.FragmentItemBinding v = fr.umontpellier.etu.inteco.databinding.FragmentItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);



        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // on met les valeurs dans les vues
        holder.mItem = mValues.get(position);
        Offer item = mValues.get(position);
        //holder.mIdView.setText(mValues.get(position).id.getId());
        holder.mContentView.setText(item.jobTitle);
        //holder.mDetailsView.setText("test");
        holder.companyInfoView.setText(/*item.details*/ "name Company" +" . "+mValues.get(position).city+", "+mValues.get(position).country);
        holder.mPostDateView.setText(new PrettyTime(new Locale("en")).format(mValues.get(position).realDate.toDate()));
        holder.mSalaryView.setText(Long.toString(item.salary)+"€/Mo");
        // click functionality





        holder.itemView.setOnClickListener (v -> {
            itemClickListener.onItemClickListener(item, position);
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //public final TextView mIdView;
        public final TextView mContentView;
//        public final TextView mDetailsView;
        public final TextView companyInfoView;
//        public final TextView mPlaceView;
        public final TextView mPostDateView;
        public final TextView mSalaryView;
        public Offer mItem;

        public ViewHolder(fr.umontpellier.etu.inteco.databinding.FragmentItemBinding binding) {
            // on associe aux vues, leur emplacement grâce à l'id (binding.id)
            super(binding.getRoot());
            //mIdView = binding.itemNumber;
            companyInfoView= binding.companyInfo;
            mContentView = binding.content;
//            mDetailsView = binding.details;
//            mPlaceView = binding.place;
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