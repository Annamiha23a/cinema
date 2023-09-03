package model;
import java.io.Serializable;
public class Movie implements Serializable {
    private int id;
    private String name;
    private String genre;
    private String country;
    private String year;
    private String duration;
    private String ageLimit;
    private String producer;
    private String[] schedule=new String[14];


    public Movie(){
        this.id=1;
        this.name="";
        this.genre="";
        this.country="";
        this.year="";
        this.duration="";
        this.ageLimit="";
        this.producer="";

        for(int i=0; i<14;i++){
            this.schedule[i]="";
        }
    }

    public Movie(int movie_id, String name, String genre, String country, String year, String duration, String ageLimit, String producer,String[] schedule){
        setId(movie_id);
        setName(name);
        setGenre(genre);
        setCountry(country);
        setYear(year);
        setDuration(duration);
        setAgeLimit(ageLimit);
        setProducer(producer);
        setSchedule(schedule);

    }
    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(String ageLimit) {
        this.ageLimit = ageLimit;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String[] getSchedule() {
        return schedule;
    }

    public void setSchedule(String[] schedule) {
        this.schedule = schedule;
    }


}