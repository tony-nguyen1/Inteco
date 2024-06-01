package fr.umontpellier.etu.inteco.Enterprise.fragements.jobHaver;

import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

        holder.mButtonMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", holder.mMailView.getText().toString(), null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Job Application");
                v.getContext().startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });

        holder.mButtonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + holder.mPhoneNumberView.getText().toString()));

                if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) v.getContext(), new String[]{Manifest.permission.CALL_PHONE}, 1);
                    return;
                }
                v.getContext().startActivity(callIntent);
            }
        });
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
        public final Button mButtonMail;
        public final Button mButtonCall;
        public PlaceholderItem mItem;

        public ViewHolder(FragmentJobHaverCardBinding binding) {
            super(binding.getRoot());
            mNameView = binding.name;
            mPostTitleView = binding.postTitle;
            mStatusView = binding.status;
            mPhoneNumberView = binding.phoneNumber;
            mMailView = binding.mail;
            mButtonMail = binding.buttonMail;
            mButtonCall = binding.buttonCall;
        }

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