package at.ac.fhcampuswien.fhmdb.states;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;

import java.util.Comparator;

public class DescendingSortState implements SortState {
    @Override
    public void sortMovies(ObservableList<Movie> movies) {
        movies.sort(Comparator.comparing(Movie::getTitle).reversed());
    }

    @Override
    public SortState getNextState() {
        return new AscendingSortState();
    }

    @Override
    public String getStateDescription() {
        return "Descending";
    }
}
