package model;

import java.io.Serializable;

public class Admin extends User implements Serializable{
    private int id;
    private String rights;
    private String block;

    public Admin() {
        this.id = -1;
        this.rights = "";
        this.block = "";
    }

    public Admin(int user_id, String login, String password, String role, String workPhone,
                 String name, String surname, String lastname, String phone, int person_id,
                 int admin_id, String rights, String block){
        setUserId(user_id);
        setLogin(login);
        setPassword(password);
        setRole(role);
        if(workPhone == null) workPhone="";
        setWork_phone(workPhone);
        if(name == null) name="";
        setName(name);
        if(surname == null) surname="";
        setSurname(surname);
        if(lastname == null) lastname="";
        setLastname(lastname);
        if(phone == null) phone="";
        setPhone(phone);
        setPersonId(person_id);
        this.id = admin_id;
        this.rights = rights;
        this.block = block;
    }


    public Admin(Admin admin) {
        this.id = admin.getId();
        this.rights = admin.getRights();
        this.block = admin.getBlock();
        setUserId(admin.getUserId());
        setLogin(admin.getLogin());
        setPassword(admin.getPassword());
        setRole(admin.getRole());
        setWork_phone(admin.getWork_phone());
        setSurname(admin.getSurname());
        setName(admin.getName());
        setLastname(admin.getLastname());
        setPhone(admin.getPhone());
    }

    public Admin(int id, String rights, String block, int user_id) {
        this.id = id;
        this.rights = rights;
        this.block = block;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {return super.getId(); }

    public void setUserId(int id) { super.setId(id); }

    public String getRights() {
        return rights;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

}
