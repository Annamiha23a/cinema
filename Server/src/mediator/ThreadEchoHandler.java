package mediator;

import db.DAO;
import model.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ThreadEchoHandler implements Runnable{
    Socket clientSocket = null;
    ObjectInputStream input;
    ObjectOutputStream output;

    public ThreadEchoHandler(Socket clientSocket) {
        try {
            this.clientSocket = clientSocket;
            input = new ObjectInputStream(clientSocket.getInputStream());
            output = new ObjectOutputStream(clientSocket.getOutputStream());
        }
        catch(Exception e) {

        }
    }

    @Override
    public void run() {
        try {
            DAO dao = new DAO();
            String command = "";
            while(!command.equals("exit")){
                command = (String) input.readObject();

                switch (command){

                    case "authorization":
                        User authorizationUser = (User) input.readObject();
                        output.writeObject(dao.authorization(authorizationUser));
                        break;
                    case "getAllAdmins":
                        output.writeObject(dao.getAllAdmins());
                        break;
                    case "getAllUsers":
                        output.writeObject(dao.getAllUsers());
                        break;
                    case "getAllMovies":
                        output.writeObject(dao.getAllMovies());
                        break;
                    case "getAllClients":
                        output.writeObject(dao.getAllClients());
                        break;
                    case "getRecordsPlaces":
                        Movie movie = (Movie) input.readObject();
                        output.writeObject(dao.getRecordsPlaces(movie));
                        break;
                    case "getAllTickets":
                        output.writeObject(dao.getAllTickets());
                        break;
                    case "getAllTicketMovies":
                        Movie workmovie = (Movie) input.readObject();
                        output.writeObject(dao.getAllTicketMovie(workmovie));
                        break;
                    case "insertAdmin":
                        Admin newAdmin = (Admin) input.readObject();
                        output.writeObject(dao.addAdmin(newAdmin));
                        break;
                    case "insertUser":
                        User newUser = (User) input.readObject();
                        output.writeObject(dao.addUser(newUser));
                        break;
                    case "insertMovie":
                        Movie newMovie = (Movie) input.readObject();
                        output.writeObject(dao.addMovie(newMovie));
                        break;
                    case "insertClient":
                        Client newClient = (Client) input.readObject();
                        output.writeObject(dao.addClient(newClient));
                        break;
                    case "insertTicket":
                        Ticket addTicket = (Ticket) input.readObject();
                        output.writeObject(dao.addTicket(addTicket));
                        break;
                    case "updateMyUserData":
                        User updateMyUserData = (User) input.readObject();
                        output.writeObject(dao.updateMyUserData(updateMyUserData));
                        break;
                    case "updatePassword":
                        User updatePassword = (User) input.readObject();
                        output.writeObject(dao.updatePassword(updatePassword));
                        break;
                    case "updatePerson":
                        User updatePerson = (User) input.readObject();
                        output.writeObject(dao.updatePerson(updatePerson));
                        break;
                    case "updateAdmin":
                        Admin updateAdmin = (Admin) input.readObject();
                        output.writeObject(dao.updateAdmin(updateAdmin));
                        break;
                    case "updateUser":
                        User updateUser = (User) input.readObject();
                        output.writeObject(dao.updateUser(updateUser));
                        break;
                   case "updateMovie":
                        Movie updateMovie = (Movie) input.readObject();
                        output.writeObject(dao.updateMovie(updateMovie));
                        break;
                    case "updateClient":
                        Client updateClient = (Client) input.readObject();
                        output.writeObject(dao.updateClient(updateClient));
                        break;
                    case "deleteAdmin":
                        Admin deleteAdmin = (Admin) input.readObject();
                        output.writeObject(dao.deleteAdmin(deleteAdmin));
                        break;
                    case "deleteUser":
                        User deleteUser = (User) input.readObject();
                        output.writeObject(dao.deleteUser(deleteUser));
                        break;
                    case "deleteClient":
                        Client deletClient = (Client) input.readObject();
                        output.writeObject(dao.deleteClient(deletClient));
                        break;
                    case "deleteMovie":
                        Movie deleteMovie = (Movie) input.readObject();
                        output.writeObject(dao.deleteMovie(deleteMovie));
                        break;
                    case "getCheck":
                        Ticket currentTicket = (Ticket) input.readObject();
                        output.writeObject(dao.getCheck(currentTicket));
                        break;


                }
            }
        }
        catch (Exception e) {
            System.out.println("Закрыто подключение...\nКоличество активных подключений: " + --Mediator.connectionsCounter + "\n");
        }
    }


}
