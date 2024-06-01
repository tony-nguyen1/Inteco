package fr.umontpellier.etu.inteco.Enterprise.fragements.jobHaver;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.umontpellier.etu.inteco.Enterprise.fragements.jobHaver.placeholder.PlaceholderContent.PlaceholderItem;
import fr.umontpellier.etu.inteco.databinding.FragmentJobHaverCardBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class JobHaverRecyclerViewAdapter extends RecyclerView.Adapter<JobHaverRecyclerViewAdapter.ViewHolder> {

    private final List<PlaceholderItem> mValues;

    public JobHaverRecyclerViewAdapter(List<PlaceholderItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentJobHaverCardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);


        holder.mNameView.setText(mValues.get(position).name);
        holder.mPostTitleView.setText(mValues.get(position).jobTitle);
        holder.mStatusView.setText(mValues.get(position).status);
        holder.mPhoneNumberView.setText(mValues.get(position).phoneNumer);
        holder.mMailView.setText(mValues.get(position).email);

//        holder.mIdView.setText(mValues.get(position).id);
//        holder.mContentView.setText(mValues.get(position).content);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mNameView;
        public final TextView mPostTitleView;
        public final TextView mStatusView;
        public final TextView mPhoneNumberView;
        public final TextView mMailView;
        public PlaceholderItem mItem;

        public ViewHolder(FragmentJobHaverCardBinding binding) {
            super(binding.getRoot());
            mNameView = binding.name;
            mPostTitleView = binding.postTitle;
            mStatusView = binding.status;
            mPhoneNumberView = binding.phoneNumber;
            mMailView = binding.mail;
        }

//        @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }

        @Override
        public String toString() {
            return "ViewHolder{" +
                    "mNameView=" + mNameView +
                    ", mPostTitleView=" + mPostTitleView +
                    ", mStatusView=" + mStatusView +
                    ", mPhoneNumberView=" + mPhoneNumberView +
                    ", mMailView=" + mMailView +
                    ", mItem=" + mItem +
                    '}';
        }
    }
}