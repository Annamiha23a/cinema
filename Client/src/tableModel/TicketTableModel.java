package tableModel;

import model.Client;
import model.Ticket;
import model.Movie;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TicketTableModel implements TableModel{
    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();

    private List<Client> clients;
    private List<Movie> movies;

    private List<Ticket> tickets;

    public TicketTableModel(List<Ticket> tickets , List<Client> clients){
        this.tickets = tickets;
        this.clients = clients;

    }

    public String getSurname(Ticket ticket){
        for (int i = 0; i < clients.size(); i++) {
            if (ticket.getClient_id() == clients.get(i).getId()) {
                return clients.get(i).getSurname();
            }
        }
        return "";
    }



    @Override
    public int getRowCount() {
        return tickets.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Дата";
            case 1:
                return "Время";
            case 2:
                return "Место";
            case 3:
                return "Дата регистрации";
            case 4:
                return "Фамилия";
            case 5:
                return "Комментарий";
            case 6:
                return "ID Фильма";
        }
        return "";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0: return String.class;
            case 1: return String.class;
            case 2: return String.class;
            case 3: return String.class;
            case 4: return String.class;
            case 5: return String.class;
            case 6: return String.class;
            default: return Object.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Ticket ticket = tickets.get(rowIndex);
        switch (columnIndex){
            case 0:
                return ticket.getDate();
            case 1:
                return ticket.getTime();
            case 2:
                return ticket.getPlace();
            case 3:
                return ticket.getRegistrationDate();
            case 4:
                return getSurname(ticket);
            case 5:
                return ticket.getComment();
            case 6:
                return ticket.getMovie_id();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

    @Override
    public void addTableModelListener(TableModelListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeTableModelListener(TableModelListener listener) {
        listeners.remove(listener);
    }

}
