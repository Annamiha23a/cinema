package model;

import java.io.Serializable;

public class Ticket implements Serializable {
    private int id;
    private String registrationDate;
    private String date;
    private String time;
    private String place;
    private String comment;
    private int movie_id;
    private int client_id;

    public Ticket(){
        setId(-1);
        setRegistrationDate("");
        setDate("");
        setTime("");
        setPlace("");
        setComment("");
        setMovie_id(-1);
        setClient_id(-1);
    }
    public Ticket(int id, String registrationDate, String date, String time, String place, String comment, int movie_id, int client_id){
        setId(id);
        setRegistrationDate(registrationDate);
        setDate(date);
        setTime(time);
        setPlace(place);
        setComment(comment);
        setMovie_id(movie_id);
        setClient_id(client_id);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }
}
