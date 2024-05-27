package fr.umontpellier.etu.inteco.Seeker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

        Button saveButton = findViewById(R.id.button_save);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Todo : saved.contains(this.post) ? Tell user : add this post to saved & tell him anyway",Toast.LENGTH_SHORT).show();
            }
        });
    }
}