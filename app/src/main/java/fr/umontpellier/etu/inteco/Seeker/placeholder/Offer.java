package fr.umontpellier.etu.inteco.Seeker.placeholder;

public class Offer {
    public final String id;
    public final String content; // jobTitle
    public final String details; // companyName
    public final String place;
    public final String postDate;
    public final String salary;

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

