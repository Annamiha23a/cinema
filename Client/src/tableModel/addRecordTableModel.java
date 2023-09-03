package tableModel;

import model.Places;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class addRecordTableModel implements TableModel{

    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();

    private List<Places> places;

    public addRecordTableModel(List<Places> places){
        this.places = places;
    }

    @Override
    public int getRowCount() {
        return places.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Дата";
            case 1:
                return "Время";
//            case 2:
//                return "Место";
//            case 3:
//                return "Дата регистрации записи";
//            case 4:
//                return "Номер телефона клиента";
//            case 5:
//                return "Комментарий";
        }
        return "";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0: return String.class;
            case 1: return String.class;
//            case 2: return String.class;
//            case 3: return String.class;
//            case 4: return String.class;
//            case 5: return String.class;
            default: return Object.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Places sch = places.get(rowIndex);
        switch (columnIndex){
            case 0:
                return sch.getDate();
            case 1:
                return sch.getTime();
//            case 2:
//                return sch.getPlace();
//            case 3:
//                return sch.getRegistrationTime();
//            case 4:
//                return sch.getPhoneNumber();
//            case 5:
//                return sch.getComment();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {}

    @Override
    public void addTableModelListener(TableModelListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeTableModelListener(TableModelListener listener) {
        listeners.remove(listener);
    }
}
