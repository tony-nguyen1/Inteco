package fr.umontpellier.etu.inteco.Seeker.placeholder;

import android.util.Log;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import org.ocpsoft.prettytime.PrettyTime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

public class Offer implements Serializable {
    private static final String TAG = "debug Offer";


    public int numberApplicants;
    public DocumentReference id;


    // clean from here
    public String adress;
    public ArrayList<DocumentReference> apply;
    public String city;
    public String contractType;
    public String country;
    public String description;
    public String duration;
    public String experienceWanted;
    public String jobTitle;
    public String jobType;
    public String postTitle;
    public String qualificationWanted;
    public Timestamp realDate;
    public DocumentReference refCompany;
    public long salary;
    public Timestamp startTime;
    public String state;

    public static Offer newInstance(DocumentReference id, Map<String,Object> arg) {
        Log.d(TAG, "newInstance: arg="+arg);
        Offer o = new Offer();

        o.id = id;

        o.adress = (String) arg.get("adress");
        o.apply = (ArrayList<DocumentReference>) arg.get("apply");
        o.city = (String) arg.get("city");
        o.contractType = (String) arg.get("contract_type");
        o.country = (String) arg.get("country");
        o.description = (String) arg.get("description");
        o.duration = (String) arg.get("duration");
        o.experienceWanted = (String) arg.get("experience_wanted");
        o.jobTitle = (String) arg.get("job_title");
        o.jobType = (String) arg.get("job_type");
        o.postTitle = (String) arg.get("post_title");
        o.qualificationWanted = (String) arg.get("qualification_wanted");
        o.realDate = (Timestamp) arg.get("realDate");
        o.refCompany = (DocumentReference) arg.get("refCompany");
        o.salary = ((Long) arg.get("salary")).longValue();
        o.startTime = (Timestamp) arg.get("startTime");
        o.state = (String) arg.get("state");

        return o;
    }

    public Offer() {

    }

    @Override
    public String toString() {
        return "Offer{" +
                "numberApplicants=" + numberApplicants +
                ", id=" + id +
                ", adress='" + adress + '\'' +
                ", apply=" + apply +
                ", city='" + city + '\'' +
                ", contractType='" + contractType + '\'' +
                ", country='" + country + '\'' +
                ", description='" + description + '\'' +
                ", duration='" + duration + '\'' +
                ", experienceWanted='" + experienceWanted + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", jobType='" + jobType + '\'' +
                ", postTitle='" + postTitle + '\'' +
                ", qualificationWanted='" + qualificationWanted + '\'' +
                ", realDate=" + realDate +
                ", refCompany=" + refCompany +
                ", salary=" + salary +
                ", startTime=" + startTime +
                ", state='" + state + '\'' +
                '}';
    }

    public String getPrettyTime() {
        return new PrettyTime(new Locale("en")).format(realDate.toDate());
    }
}

//