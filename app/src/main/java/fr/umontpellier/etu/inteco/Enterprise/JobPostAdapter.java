package fr.umontpellier.etu.inteco.Enterprise;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.umontpellier.etu.inteco.R;
import fr.umontpellier.etu.inteco.Seeker.placeholder.Offer;

public class JobPostAdapter extends RecyclerView.Adapter<JobPostAdapter.JobPostViewHolder> {

    private List<Offer> jobPostList;

    public OfferAdapter(List<Offer> jobPostList) {
        this.jobPostList = jobPostList;
    }

    @Override
    public JobPostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job_post, parent, false);
        return new JobPostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JobPostViewHolder holder, int position) {
        Offer offer = jobPostList.get(position);
        holder.jobTitle.setText(offer.getTitle());
        holder.applicants.setText(offer.getApplicants() + " applicants");
        holder.status.setText(offer.getStatus());
        holder.status.setTextColor(offer.getStatus().equals("Open") ? Color.GREEN : Color.RED);
    }

    @Override
    public int getItemCount() {
        return jobPostList.size();
    }

    public static class JobPostViewHolder extends RecyclerView.ViewHolder {
        TextView jobTitle, applicants, status;

        public JobPostViewHolder(View itemView) {
            super(itemView);
            jobTitle = itemView.findViewById(R.id.job_title);
            applicants = itemView.findViewById(R.id.applicants);
            status = itemView.findViewById(R.id.status);
        }
    }
}
