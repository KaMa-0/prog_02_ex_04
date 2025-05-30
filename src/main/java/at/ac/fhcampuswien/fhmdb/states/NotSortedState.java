package at.ac.fhcampuswien.fhmdb.states;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;

public class NotSortedState implements SortState {
    @Override
    public void sortMovies(ObservableList<Movie> movies) {
        // Do nothing - keep original order
    }

    @Override
    public SortState getNextState() {
        return new AscendingSortState();
    }

    @Override
    public String getStateDescription() {
        return "Not Sorted";
    }
}
