package fr.umontpellier.etu.inteco.Seeker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import fr.umontpellier.etu.inteco.R;

public class JobDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);


        TextView jobTitleTV = findViewById(R.id.jobTitle);
        TextView companyNameTV = findViewById(R.id.companyName);
        TextView placeTV = findViewById(R.id.place);
        TextView postDateTV = findViewById(R.id.postDate);
        TextView salaryTV = findViewById(R.id.salary);

        Intent intent = getIntent();
        jobTitleTV.setText(intent.getStringExtra("jobTitle"));
        companyNameTV.setText(intent.getStringExtra("companyName"));
        placeTV.setText(intent.getStringExtra("place"));
        postDateTV.setText(intent.getStringExtra("postDate"));
        salaryTV.setText(intent.getStringExtra("salary"));
    }
}