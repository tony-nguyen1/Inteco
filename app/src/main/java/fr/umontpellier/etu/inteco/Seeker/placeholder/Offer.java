package fr.umontpellier.etu.inteco.Seeker.placeholder;

public class Offer {
    public final String id;
    public final String content;
    public final String details;
    public final String place;
    public final String postDate;
    public final String salary;
    public String description;
    public String requirements;
    public String jobType;
    public String contractType;
    public String duration;
    public String experience;
    public String qualification;
    public String location;


    /**
     *
     * @param id
     * @param content == jobTitle
     * @param details == companyName
     */
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
        return "PlaceholderItem{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", details='" + details + '\'' +
                ", place='" + place + '\'' +
                ", postDate='" + postDate + '\'' +
                ", salary='" + salary + '\'' +
                '}';
    }
}