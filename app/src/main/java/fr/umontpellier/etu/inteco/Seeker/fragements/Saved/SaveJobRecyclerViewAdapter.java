package fr.umontpellier.etu.inteco.Seeker.fragements.Saved;

import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;

import fr.umontpellier.etu.inteco.R;
import fr.umontpellier.etu.inteco.Seeker.fragements.Saved.MyItemRecyclerViewAdapter;
import fr.umontpellier.etu.inteco.Seeker.fragements.placeholder.PlaceholderContent.PlaceholderItem;
import fr.umontpellier.etu.inteco.Seeker.placeholder.Offer;
import fr.umontpellier.etu.inteco.databinding.FragmentOfferSavedCardBinding;

import java.text.BreakIterator;
import java.util.List;
import java.util.Locale;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class SaveJobRecyclerViewAdapter extends RecyclerView.Adapter<SaveJobRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "debug SaveJobRecycler";
    private final List<Offer> mValues;

    MyItemRecyclerViewAdapter.AdapterItemClickListener itemClickListener;
    MyItemRecyclerViewAdapter.AdapterItemClickListener itemClickListener2;

    public SaveJobRecyclerViewAdapter(List<Offer> items, MyItemRecyclerViewAdapter.AdapterItemClickListener itemClickListener, MyItemRecyclerViewAdapter.AdapterItemClickListener itemClickListener2) {
        mValues = items;
        this.itemClickListener = itemClickListener;
        this.itemClickListener2 = itemClickListener2;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_offer_saved_card, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Offer item = mValues.get(position);
        Log.d(TAG, "onBindViewHolder: "+item);
        holder.jobTitleView.setText(item.jobTitle);
        holder.companyInfoView.setText(item.city + " · " + item.country);
        holder.postDateView.setText("Posted : "+item.getPrettyTime());
        holder.salaryView.setText(item.salary+"€/Month");
        holder.jobTypeView.setText(item.jobType);
        holder.contractTypeView.setText(item.contractType);


        holder.buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onItemClickListener(item, holder.getBindingAdapterPosition());
            }
        });
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener2.onItemClickListener(item, holder.getBindingAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView jobTitleView;
        public final TextView companyInfoView;
        public final TextView postDateView;
        public final TextView salaryView;
        public final Button buttonApply;
        public final Button buttonDelete;
        public final TextView contractTypeView;
        public final TextView jobTypeView;

        public ViewHolder(View view) {
            super(view);
            jobTitleView = view.findViewById(R.id.job_title);
            companyInfoView = view.findViewById(R.id.company_info);
            postDateView = view.findViewById(R.id.post_date_start);
            salaryView = view.findViewById(R.id.salary);
            jobTypeView = view.findViewById(R.id.job_type);
            contractTypeView = view.findViewById(R.id.contract_type);
            buttonApply = view.findViewById(R.id.button_apply);
            buttonDelete = view.findViewById(R.id.button_decline);


        }

    }
}