package fr.umontpellier.etu.inteco.Seeker.placeholder;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import org.ocpsoft.prettytime.PrettyTime;

import java.io.Serializable;
import java.util.Locale;

public class Offer implements Serializable {
    public String id;
    public String content; // jobTitle
    public String details; // companyName
    public String place;
    public String postDate;
    public String salary;

    public String mail;
    public String description;
    public String requirements;
    public String jobType;
    public String contractType;
    public String startingTime;
    public String duration;
    public String experience;
    public String qualification;
    public String location;
    public String category;

    private DocumentReference documentReference;





    public String jobTitle;
    public int numberApplicants;
    public String state;
    public String dateDetails;

    public Offer(String id, String content, String mail,String details, String place, String postDate, String salary,
                 String description, String requirements, String jobType, String contractType,
                 String duration, String experience, String qualification, String location, String category, String startingTime) {
        this.id = id;
        this.content = content;
        this.details = details;
        this.place = place;
        this.postDate = postDate;
        this.salary = salary;
        this.description = description;
        this.requirements = requirements;
        this.jobType = jobType;
        this.contractType = contractType;
        this.duration = duration;
        this.experience = experience;
        this.qualification = qualification;
        this.location = location;
        this.category = category;
        this.startingTime=startingTime;
        this.mail=mail;
    }
    public Offer(String id, String content, String details, String place, String postDate, String salary) {
        this.id = id;
        this.content = content;
        this.details = details;
        this.place = place;
        this.postDate = postDate;
        this.salary = salary;
    }

    public Offer(String jobTitle, int n, String state, Timestamp stamp) {
        this.jobTitle = jobTitle;
        this.numberApplicants = n;
        this.state = state;
        this.dateDetails = new PrettyTime(new Locale("en")).format(stamp.toDate());
    }

    public Offer setDocumentReference(DocumentReference documentReference) {
        this.documentReference = documentReference;
        return this;
    }

    public DocumentReference getDocumentReference() {
        return documentReference;
    }

    public Offer() {
        this("","","","","","");
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", details='" + details + '\'' +
                ", mail='" + mail +  '\'' +
                ", place='" + place + '\'' +
                ", postDate='" + postDate + '\'' +
                ", salary='" + salary + '\'' +
                ", description='" + description + '\'' +
                ", requirements='" + requirements + '\'' +
                ", jobType='" + jobType + '\'' +
                ", contractType='" + contractType + '\'' +
                ", duration='" + duration + '\'' +
                ", experience='" + experience + '\'' +
                ", qualification='" + qualification + '\'' +
                ", location='" + location + '\'' +
                ", category='" + category + '\'' +
                ", startingTime='" + startingTime+'\''+
                '}';
    }
}

