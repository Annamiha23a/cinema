package tableModel;

import model.Admin;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AdminTableModel implements TableModel {

    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();

    private List<Admin> admins;

    public AdminTableModel(List<Admin> admins){
        this.admins = admins;
    }


    @Override
    public int getRowCount() {
        return admins.size();
    }

    @Override
    public int getColumnCount() {
        return 9;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "ID";
            case 1:
                return "Фамилия";
            case 2:
                return "Имя";
            case 3:
                return "Отчество";
            case 4:
                return "Логин";
            case 5:
                return "Личный телефон";
            case 6:
                return "Рабочий телефон";
            case 7:
                return "Права доступа";
            case 8:
                return "Блокировка";
        }
        return "";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0: return Integer.class;
            case 1: return String.class;
            case 2: return String.class;
            case 3: return String.class;
            case 4: return String.class;
            case 5: return String.class;
            case 6: return String.class;
            case 7: return String.class;
            case 8: return String.class;
            default: return Object.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Admin admin = admins.get(rowIndex);
        switch (columnIndex){
            case 0:
                return admin.getUserId();
            case 1:
                return admin.getSurname();
            case 2:
                return admin.getName();
            case 3:
                return admin.getLastname();
            case 4:
                return admin.getLogin();
            case 5:
                return admin.getPhone();
            case 6:
                return admin.getWork_phone();
            case 7:
                return admin.getRights();
            case 8:
                return admin.getBlock();
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

    public void addRow(Admin admin){
        admins.add(admin);
    }
}
