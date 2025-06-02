package at.ac.fhcampuswien.fhmdb.database;

import at.ac.fhcampuswien.fhmdb.observer.Observable;
import at.ac.fhcampuswien.fhmdb.observer.Observer;
import com.j256.ormlite.dao.Dao;

import java.util.ArrayList;
import java.util.List;

public class WatchlistRepository implements Observable {
    private static WatchlistRepository instance;
    private Dao<WatchlistMovieEntity, Long> dao;
    private final List<Observer> observers = new ArrayList<>();

    private WatchlistRepository() throws DataBaseException {
        try {
            this.dao = DatabaseManager.getInstance().getWatchlistDao();
        } catch (Exception e) {
            throw new DataBaseException(e.getMessage());
        }
    }

    public static WatchlistRepository getInstance() throws DataBaseException {
        if (instance == null) {
            instance = new WatchlistRepository();
        }
        return instance;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    public List<WatchlistMovieEntity> getWatchlist() throws DataBaseException {
        try {
            return dao.queryForAll();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataBaseException("Fehler beim Lesen der Watchlist");
        }
    }

    public int addToWatchlist(WatchlistMovieEntity movie) throws DataBaseException {
        try {
            long count = dao.queryBuilder().where().eq("apiId", movie.getApiId()).countOf();
            if (count == 0) {
                int result = dao.create(movie);
                notifyObservers("Film erfolgreich zur Watchlist hinzugefügt!");
                return result;
            } else {
                notifyObservers("Film ist bereits in der Watchlist!");
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            notifyObservers("Fehler beim Hinzufügen zur Watchlist!");
            throw new DataBaseException("Fehler beim Hinzufügen zur Watchlist");
        }
    }

    public int removeFromWatchlist(String apiId) throws DataBaseException {
        try {
            int result = dao.delete(dao.queryBuilder().where().eq("apiId", apiId).query());
            if (result > 0) {
                notifyObservers("Film erfolgreich aus der Watchlist entfernt!");
            } else {
                notifyObservers("Film nicht in der Watchlist gefunden!");
            }
            return result;
        } catch (Exception e) {
            notifyObservers("Fehler beim Entfernen aus der Watchlist!");
            throw new DataBaseException("Fehler beim Entfernen aus der Watchlist");
        }
    }
}