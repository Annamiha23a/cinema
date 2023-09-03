package db;

import model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;

public class DAO extends DbConnector{
    public DAO() {
        super.connectToDB();
    }

    public User authorization(User user) throws SQLException {
        try {
            ResultSet rs = super.getStatement().executeQuery(String.format("SELECT * FROM user WHERE login='%s';", user.getLogin()));
            if(rs.next()){
                if (user.getPassword().equals(rs.getString("password"))) {
                    user.setRole(rs.getString("role"));
                    user.setId(Integer.parseInt(rs.getString("user_id")));
                    return user;
                }
                else{
                    user.setRole("wrong");
                    return user;
                }
            }
            else {
                user.setRole("wrong");
                return user;
            }
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }


    //---------------------ПОЛУЧЕНИЕ ВСЕЙ ТАБЛИЦЫ-----------------------------------

//вывод таблицы данных о админах
    public ArrayList<Admin> getAllAdmins() throws SQLException{
        try {
            ResultSet rs = super.getStatement().executeQuery(String.format("SELECT * FROM user INNER JOIN person on user.person_id=person.person_id\n" +
                    "INNER JOIN admin on user.user_id=admin.user_id WHERE user.role='admin';"));
            ArrayList<Admin> adminList = new ArrayList<>();
            while(rs.next()){
                Admin admin = new Admin(
                        Integer.parseInt(rs.getString("user_id")),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("work_phone"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("lastname"),
                        rs.getString("personal_phone"),
                        Integer.parseInt(rs.getString("person_id")),
                        Integer.parseInt(rs.getString("admin_id")),
                        rs.getString("rights"),
                        rs.getString("block")
                );
                adminList.add(admin);
            }
            return adminList;
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
//вывод данных о user
    public ArrayList<User> getAllUsers() throws SQLException{
        try {
            ResultSet rs = super.getStatement().executeQuery(String.format("SELECT * FROM user INNER JOIN person ON user.person_id=person.person_id WHERE user.role='user';"));
            ArrayList<User> userList = new ArrayList<>();
            while(rs.next()){
                User user = new User(
                        Integer.parseInt(rs.getString("person_id")),
                        rs.getString("surname"),
                        rs.getString("name"),
                        rs.getString("lastname"),
                        rs.getString("personal_phone"),
                        Integer.parseInt(rs.getString("user_id")),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("work_phone")
                );
                userList.add(user);
            }
            return userList;
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
//получение всех фильмов
    public ArrayList<Movie> getAllMovies() throws SQLException{
        try {
            ResultSet rs = super.getStatement().executeQuery(String.format("SELECT * FROM movie \n" ));
            ArrayList<Movie> movieList = new ArrayList<>();
            while(rs.next()){
                String schedule[];
                schedule = rs.getString("schedule").split("-", 14);
                for(int i = 0; i < schedule.length; i++){
                    if(schedule[i] == null) schedule[i] = "";
                }
                Movie movie = new Movie(
                        Integer.parseInt(rs.getString("movie_id")),
                        rs.getString("name"),
                        rs.getString("genre"),
                        rs.getString("country"),
                        rs.getString("year"),
                        rs.getString("duration"),
                        rs.getString("ageLimit"),
                        rs.getString("producer"),
                        schedule
                );
                movieList.add(movie);
            }
            return movieList;
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
    //получение всех клиентов
    public ArrayList<Client> getAllClients() throws SQLException{
        try {
            ResultSet rs = super.getStatement().executeQuery(String.format("SELECT * FROM client INNER JOIN person ON client.person_id=person.person_id;"));
            ArrayList<Client> clintList = new ArrayList<>();
            while(rs.next()){
                Client client = new Client(
                        Integer.parseInt(rs.getString("person_id")),
                        rs.getString("surname"),
                        rs.getString("name"),
                        rs.getString("lastname"),
                        rs.getString("personal_phone"),
                        Integer.parseInt(rs.getString("client_id")),
                        rs.getString("age")
                );
                clintList.add(client);
            }
            return clintList;
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    //получение расписания
    public ArrayList<Places> getRecordsPlaces(Movie movie){
        try{
            ArrayList<Places> placesList = new ArrayList<>();
            //взять расписание и номер дня
            ResultSet rs = super.getStatement().executeQuery(String.format("SELECT schedule, WEEKDAY(curdate()) as daynum FROM movie where movie_id='%d';", movie.getId()));
            if(rs.next()){
                String movieSchedule[] = rs.getString("schedule").split("-", 14);
                int curdamynum = Integer.parseInt(rs.getString("daynum"));
                int day = curdamynum;
                int interval = 0;
                int inplac=1;
                while(day < 7){
                    ResultSet rsDateFirst = super.getStatement().executeQuery(String.format("SELECT adddate(curdate(), interval '%d' day) AS date;", interval));
                    if(rsDateFirst.next()){
                        String currentTime = movieSchedule[day*2];
                        if(currentTime != null){
                            while(!currentTime.equals(movieSchedule[day*2+1])){


                                    Places places = new Places();


                                    Statement statement = super.getConnection().createStatement();
                                    //взять из бд билеты, которые уже заказали
                                    ResultSet rsOldRecord = statement.executeQuery(String.format("SELECT * FROM ticket \n" +
                                            "INNER JOIN client on ticket.client_id=client.client_id \n" +
                                            "INNER JOIN movie on ticket.movie_id=movie.movie_id\n" +
                                            "WHERE movie.movie_id='%d' AND time='%s' AND date='%s' ;", movie.getId(), currentTime, rsDateFirst.getString("date")));
                                    if (rsOldRecord.next()) {
                                        for(inplac=1; inplac<6; inplac++) {

                                        ResultSet rsOldRecordPl = super.getStatement().executeQuery(String.format("SELECT * FROM ticket \n" +
                                                "INNER JOIN client on ticket.client_id=client.client_id \n" +
                                                "INNER JOIN movie on ticket.movie_id=movie.movie_id\n" +
                                                "WHERE movie.movie_id='%d' AND time='%s' AND date='%s' and place='%s';", movie.getId(), currentTime, rsDateFirst.getString("date"), inplac));

                                        if (rsOldRecordPl.next()) {
                                        places.setPlace(rsOldRecordPl.getString("place"));
                                        places.setRegistrationTime(rsOldRecordPl.getString("registration_date"));
                                        places.setPhoneNumber(rsOldRecordPl.getString("phone_number"));
                                        places.setComment(rsOldRecordPl.getString("comment"));}
                                        }
                                    }
                                    places.setDate(rsDateFirst.getString("date"));
                                    places.setTime(currentTime);
                                    placesList.add(places);


                                    String hms[] = currentTime.split(":");
                                    int hour = Integer.parseInt(hms[0]);
                                    hour++;
                                    if (String.valueOf(hour).length() == 1)
                                        currentTime = "0" + String.valueOf(hour) + ":" + hms[1] + ":" + hms[2];
                                    else currentTime = String.valueOf(hour) + ":" + hms[1] + ":" + hms[2];
                                }

                        }
                    }
                    interval=interval+1;
                    day++;
                }
                day = 0;
                while(day < curdamynum){
                    ResultSet rsDateFirst = super.getStatement().executeQuery(String.format("SELECT adddate(curdate(), interval '%d' day) AS date;", interval));
                    if(rsDateFirst.next()){
                        String currentTime = movieSchedule[day*2];
                        if(currentTime != null){
                            while(!currentTime.equals(movieSchedule[day*2+1])){
                                Places schedule = new Places();

                                Statement statement = super.getConnection().createStatement();
                                ResultSet rsOldRecord1 = statement.executeQuery(String.format("SELECT * FROM ticket \n" +
                                        "INNER JOIN client on ticket.client_id=client.client_id \n" +
                                        "INNER JOIN movie on ticket.movie_id=movie.movie_id\n" +
                                        "WHERE movie.movie_id='%d' AND time='%s' AND date='%s';", movie.getId(), currentTime, rsDateFirst.getString("date")));

                                    if(rsOldRecord1.next()){
                                        for(inplac=1; inplac<6; inplac++) {

                                            ResultSet rsOldRecordPl1 = super.getStatement().executeQuery(String.format("SELECT * FROM ticket \n" +
                                                    "INNER JOIN client on ticket.client_id=client.client_id \n" +
                                                    "INNER JOIN movie on ticket.movie_id=movie.movie_id\n" +
                                                    "WHERE movie.movie_id='%d' AND time='%s' AND date='%s' and place='%s';", movie.getId(), currentTime, rsDateFirst.getString("date"), inplac));

                                            if (rsOldRecordPl1.next()) {
                                        schedule.setPlace(rsOldRecordPl1.getString("place"));
                                        schedule.setRegistrationTime(rsOldRecordPl1.getString("registration_date"));
                                        schedule.setPhoneNumber(rsOldRecordPl1.getString("phoneNumber"));
                                        schedule.setComment(rsOldRecordPl1.getString("comment"));}}
                                    }
                                    schedule.setDate(rsDateFirst.getString("date"));
                                    schedule.setTime(currentTime);

                                    placesList.add(schedule);


                                String hms[] = currentTime.split(":");
                                int hour = Integer.parseInt(hms[0]);
                                hour++;
                                if(String.valueOf(hour).length()==1) currentTime = "0" + String.valueOf(hour) + ":" + hms[1] + ":" + hms[2];
                                else currentTime = String.valueOf(hour) + ":" + hms[1] + ":" + hms[2];
                            }
                        }
                    }
                    interval=interval+1;
                    day++;
                }
            }
            return placesList;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public ArrayList<Ticket> getAllTickets(){
        try {
            ResultSet rs = super.getStatement().executeQuery(String.format("SELECT * FROM ticket WHERE date>=curdate();"));
            ArrayList<Ticket> ticketsList = new ArrayList<>();
            while(rs.next()){
                Ticket ticket = new Ticket(
                        Integer.parseInt(rs.getString("ticket_id")),
                        rs.getString("registration_date"),
                        rs.getString("date"),
                        rs.getString("time"),
                        rs.getString("place"),
                        rs.getString("comment"),
                        Integer.parseInt(rs.getString("movie_id")),
                        Integer.parseInt(rs.getString("client_id"))
                );
                ticketsList.add(ticket);
            }
            return ticketsList;
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public ArrayList<Ticket> getAllTicketMovie(Movie movie){
        try {
            ResultSet rs = super.getStatement().executeQuery(String.format("SELECT * FROM ticket WHERE date>=curdate() AND movie_id='%d';", movie.getId()));
            ArrayList<Ticket> ticketsList = new ArrayList<>();
            while(rs.next()){
                Ticket ticket = new Ticket(
                        Integer.parseInt(rs.getString("visit_id")),
                        rs.getString("registration_date"),
                        rs.getString("date"),
                        rs.getString("time"),
                        rs.getString("place"),
                        rs.getString("comment"),
                        Integer.parseInt(rs.getString("movie_id")),
                        Integer.parseInt(rs.getString("client_id"))
                );
                ticketsList.add(ticket);
            }
            return ticketsList;
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public String getCheck(Ticket ticket) throws SQLException{
        try{
            ResultSet rs = super.getStatement().executeQuery(String.format("select * from ticket \n" +
                    "inner join client on ticket.client_id=client.client_id\n" +
                    "inner join person on person.person_id=client.person_id\n" +
                    "inner join movie on ticket.movie_id=movie.movie_id\n" +
                    "where ticket.ticket_id='%d';", ticket.getId()));
            while(rs.next()){
                String result = "";
                result += rs.getString("date") + "#";
                result += rs.getString("time") + "#";
                result += rs.getString("personal_phone") + "#";
                result += rs.getString("place") + "#";
                result += rs.getString("movie.name" ) + "#";
                result += rs.getString("surname") + "#";
                result += rs.getString("name") + "#";
                result += rs.getString("lastname") + "#";
                //result += rs.getString("work_phone") + "#";
                return result;
            }
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return "";
    }


    //-------------------------ДОБАВЛЕНИЕ ДАННЫХ-------------------------------------

    //добавление нового админа
    public String addAdmin(Admin newAdmin){
        String addData[] = {
                String.format("INSERT INTO person (name, surname, lastname, personal_phone) VALUES('%s', '%s', '%s', '%s');", newAdmin.getName(), newAdmin.getSurname(), newAdmin.getLastname(), newAdmin.getPhone()),
                String.format("INSERT INTO user (login, password, role, work_phone, person_id) VALUES('%s', '%s', '%s', '%s', last_insert_id());", newAdmin.getLogin(), newAdmin.getPassword(), newAdmin.getRole(),newAdmin.getWork_phone()),
                String.format("INSERT INTO admin (rights, block, user_id) VALUES('%s', '%s', last_insert_id());", newAdmin.getRights(), newAdmin.getBlock())
        };
        return addData(addData);
    }

    //добавление пользователя
    public String addUser(User user){
        String addData[] = {
                String.format("INSERT INTO person (name, surname, lastname, personal_phone) VALUES('%s', '%s', '%s', '%s');", user.getName(), user.getSurname(), user.getLastname(), user.getPhone()),
                String.format("INSERT INTO user (login, password, role, work_phone, person_id) VALUES('%s', '%s', '%s', '%s', last_insert_id());", user.getLogin(), user.getPassword(), user.getRole(), user.getWork_phone())
        };
        return addData(addData);
    }
    //добавление фильма, отредачить потом поля
    public String addMovie(Movie movie){
        String addData[] = {
                String.format("INSERT INTO movie (movie_id, name, genre, country, year, duration, ageLimit, producer, schedule) VALUES( last_insert_id(), '%s', '%s', '%s', '%s','%s', '%s', '%s', '%s');", movie.getName(), movie.getGenre(), movie.getCountry(), movie.getYear(), movie.getDuration(), movie.getAgeLimit(), movie.getProducer(), "-------------")
        };
        return addData(addData);
    }
    //добавление клиента
    public String addClient(Client client){
        String addData[] = {
                String.format("INSERT INTO person (name, surname, lastname, personal_phone) VALUES('%s', '%s', '%s', '%s');", client.getName(), client.getSurname(), client.getLastname(), client.getPhone()),
                String.format("INSERT INTO client (age, person_id) VALUES('%s', last_insert_id());", client.getAge())
        };
        return addData(addData);
    }

    public String addTicket(Ticket ticket){
        String addData[] = {
                String.format("INSERT INTO ticket (registration_date, date, time, place, comment, movie_id, client_id) " +
                        "VALUES(CURDATE(), '%s', '%s', '%s','%s', '%d', '%d');", ticket.getDate(), ticket.getTime(), ticket.getPlace(),
                        ticket.getComment(), ticket.getMovie_id(), ticket.getClient_id())
        };
        return addData(addData);
    }

    private String addData(String[] data){
        try{
            for(int i = 0; i < data.length;i++){
                super.getStatement().execute(data[i]);
            }
            return "Успешно добавлено!";
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return "Не удалось добавить данные!";
    }

    //--------------------------ОБНОВЛЕНИЕ ДАННЫХ--------------------------------------

    //обновить запись об админе
    public String updateAdmin(Admin admin) throws SQLException{
        String statement = String.format("UPDATE user INNER JOIN person ON person.person_id=user.person_id \n" +
                        "INNER JOIN admin ON admin.user_id=user.user_id\n" +
                        "SET name='%s',surname='%s',lastname='%s',personal_phone='%s'," +
                        "login='%s',work_phone='%s',rights='%s',block='%s'\n" +
                        " WHERE user.user_id='%d';", admin.getName(), admin.getSurname(), admin.getLastname(), admin.getPhone(),
                admin.getLogin(), admin.getWork_phone(), admin.getRights(), admin.getBlock(), admin.getUserId());
        try {
            super.getStatement().executeUpdate(statement);
            return "Успешно сохранено!";
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return "Не удалось изменить данные";
    }
    //обновить запись о пользователе
    public String updateUser(User user) throws SQLException{
        String statement = String.format("UPDATE user INNER JOIN person ON person.person_id=user.person_id \n" +
                        "SET name='%s',surname='%s',lastname='%s',personal_phone='%s'," +
                        "login='%s',work_phone='%s'\n" +
                        " WHERE user.user_id='%d';", user.getName(), user.getSurname(), user.getLastname(), user.getPhone(),
                user.getLogin(), user.getWork_phone(), user.getId());
        return updateData(statement);
    }
    //обавить запись о фильме
    public String updateMovie(Movie movie) throws SQLException{
        String schedule = "";
        for(int i = 0; i < 14; i++){
            schedule += movie.getSchedule()[i];
            if(i == 13) break;
            schedule += "-";
        }
        String statement = String.format("UPDATE movie  \n" +
                        "SET name='%s',genre='%s',country='%s',year='%s',duration='%s',ageLimit='%s',producer='%s',schedule='%s'\n" +
                        "WHERE movie.movie_id='%d';", movie.getName(), movie.getGenre(), movie.getCountry(), movie.getYear(),
                movie.getDuration(), movie.getAgeLimit(), movie.getProducer(),   schedule, movie.getId());
        return updateData(statement);
    }
    //обновление моих пользовательских данных
    public String updateMyUserData(User user) throws SQLException{
        String statement = String.format("UPDATE user SET login='%s',password='%s',work_phone='%s' where user_id='%d';",
                user.getLogin(), user.getPassword(), user.getWork_phone(), user.getId());
        try {
            ResultSet rs1 = super.getStatement().executeQuery(String.format("select * from user WHERE login='%s' and user_id!='%d';", user.getLogin(), user.getId()));
            if(rs1.next()){return "Пользователь с таким логином уже существует!"; }
            super.getStatement().executeUpdate(statement);
            return "Успешно сохранено!";
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return "Не удалось изменить данные";
    }
    //обновление данных личности по конкретному пользователю
    public String updatePerson(User user) throws SQLException{
        String statement = String.format("UPDATE person inner join user on person.person_id=user.user_id SET name='%s', surname='%s', " +
                "lastname='%s', personal_phone='%s' where user_id='%d';", user.getName(), user.getSurname(),user.getLastname(), user.getPhone(), user.getId());
        return updateData(statement);
    }
    //обновление пароля
    public String updatePassword(User user){
        String statement = String.format("UPDATE user SET password='%s' WHERE user.user_id='%d';", user.getPassword(), user.getId());
        return updateData(statement);
    }
    //обновление записи о клиенте
   public String updateClient(Client client){
        String statement = String.format("UPDATE client INNER JOIN person ON person.person_id=client.person_id \n" +
                "SET name='%s',surname='%s',lastname='%s',personal_phone='%s'," +
                "age='%s'\n" +
                " WHERE client.client_id='%d';", client.getName(), client.getSurname(), client.getLastname(), client.getPhone(), client.getAge(),  client.getId());
        return updateData(statement);
    }
    //обновление данных
    private String updateData(String statement){
        try {
            super.getStatement().executeUpdate(statement);
            return "Успешно сохранено!";
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return "Не удалось изменить данные";
    }

    //------------------------------УДАЛЕНИЕ ДАННЫХ----------------------------------

    //удаление записи об админе
    public String deleteAdmin(Admin deleteAdmin){
        String statement = String.format("DELETE from admin, user, person " +
                "USING admin, user, person " +
                "WHERE user.user_id=admin.user_id && user.person_id=person.person_id && user.login='%s';", deleteAdmin.getLogin());
        try{
            super.getStatement().execute(statement);
            return "Успешно удалено!";
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return "Не удалось удалить данные!";
    }

    //удаление записи об пользователе
    public String deleteUser(User deleteUser){
        String statement = String.format("DELETE from user, person " +
                "USING user, person " +
                "WHERE user.person_id=person.person_id && user.login='%s';", deleteUser.getLogin());
        try{
            super.getStatement().execute(statement);
            return "Успешно удалено!";
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return "Не удалось удалить данные!";
    }
    //удаление записи об клиенте
   public String deleteClient(Client client){
        String statement = String.format("DELETE from person, client,  ticket " +
                "USING person,client,  ticket " +
                "WHERE client.person_id=person.person_id && client.client_id=ticket.client_id && client.client_id='%d';", client.getId());
        try{
            super.getStatement().execute(statement);
            return "Успешно удалено!";
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return "Не удалось удалить данные!";
    }
    //удаление записи об фильме
    public String deleteMovie(Movie movie){
        String statement = String.format("DELETE from  movie USING movie WHERE  movie.movie_id='%d';", movie.getId());
        try{
            super.getStatement().execute(statement);
            return "Успешно удалено!";
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return "Не удалось удалить данные!";
    }
}
