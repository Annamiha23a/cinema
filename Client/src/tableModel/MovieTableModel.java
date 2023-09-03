package tableModel;

import model.Movie;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MovieTableModel implements TableModel{
    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();

    private List<Movie> movies;

    public MovieTableModel(List<Movie> movies){
        this.movies = movies;
    }


    @Override
    public int getRowCount() {
        return movies.size();
    }

    @Override
    public int getColumnCount() {
        return 8;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "ID";
            case 1:
                return "Название";
            case 2:
                return "Жанр";
            case 3:
                return "Страна";
            case 4:
                return "Год выхода";
            case 5:
                return "Длительность";
            case 6:
                return "Возростное органичение";
            case 7:
                return "Режиссеры";
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
            default: return Object.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Movie movie = movies.get(rowIndex);
        switch (columnIndex){
            case 0:
                return movie.getId();
            case 1:
                return movie.getName();
            case 2:
                return movie.getGenre();
            case 3:
                return movie.getCountry();
            case 4:
                return movie.getYear();
            case 5:
                return movie.getDuration();
            case 6:
                return movie.getAgeLimit();
            case 7:
                return movie.getProducer();
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

    public void addRow(Movie movie){
        movies.add(movie);
    }
}
