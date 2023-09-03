package model;

import java.io.Serializable;

public class Client extends Person implements Serializable {
    private int id;
    private String age;

    public Client(){
        super();
        this.id=-1;
        this.age="";
    }

    public Client(int id, String surname, String name, String lastname, String phone, int id_c, String age){
        super(id, surname, name, lastname, phone);
        this.id=id_c;
        this.age=age;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
