package model;

import java.io.Serializable;

public class Person implements Serializable{
    private int id;
    private String surname;
    private String name;
    private String lastname;
    private String phone;

    public Person() {
        this.id = -1;
        this.name = "";
        this.surname = "";
        this.lastname = "";
        this.phone = "";
    }

    public Person(int id, String surname, String name, String lastname, String phone) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.lastname = lastname;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
