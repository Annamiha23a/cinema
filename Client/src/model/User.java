package model;

import java.io.Serializable;

public class User extends Person implements Serializable{
    private int id;
    private String login;
    private String password;
    private String role;
    private String work_phone;

    public User(){
        super();
        this.id = -1;
        this.login = "";
        this.password = "";
        this.role = "";
        this.work_phone = "";
    }

    public User(int person_id, String surname, String name, String lastname, String phone, int user_id, String login, String password, String role, String work_phone){
        super(person_id, surname, name, lastname, phone);
        this.id = user_id;
        this.login = login;
        this.password = password;
        this.role = role;
        if(work_phone == null) work_phone = "";
        this.work_phone = work_phone;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getPersonId() { return super.getId(); }

    public void setPersonId(int id) { super.setId(id);}

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getWork_phone() {
        return work_phone;
    }

    public void setWork_phone(String work_phone) {
        this.work_phone = work_phone;
    }
}