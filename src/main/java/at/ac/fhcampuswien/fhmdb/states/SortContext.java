package at.ac.fhcampuswien.fhmdb.states;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import javafx.collections.ObservableList;

public class SortContext {
    private SortState currentState;

    public SortContext() {
        this.currentState = new NotSortedState();
    }

    public void sortMovies(ObservableList<Movie> movies) {
        currentState.sortMovies(movies);
        // Transition to next state after sorting
        currentState = currentState.getNextState();
    }

    public String getCurrentStateDescription() {
        return currentState.getStateDescription();
    }

    public SortState getCurrentState() {
        return currentState;
    }

    // Method to apply specific sort state without changing the current state
    public void applySortState(ObservableList<Movie> movies, SortState state) {
        state.sortMovies(movies);
    }

    // Reset to not sorted state
    public void reset() {
        this.currentState = new NotSortedState();
    }
}