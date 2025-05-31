package at.ac.fhcampuswien.fhmdb.api;

import at.ac.fhcampuswien.fhmdb.models.Genre;

public class MovieAPIRequestBuilder {
    private static final String DELIMITER = "&";
    private final String baseUrl;
    private String query;
    private Genre genre;
    private String releaseYear;
    private String ratingFrom;

    // Constructor takes the base URL
    public MovieAPIRequestBuilder(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    // Fluent interface methods for building the URL
    public MovieAPIRequestBuilder query(String query) {
        this.query = query;
        return this;
    }

    public MovieAPIRequestBuilder genre(String genre) {
        if (genre != null && !genre.isEmpty()) {
            try {
                this.genre = Genre.valueOf(genre.toUpperCase());
            } catch (IllegalArgumentException e) {
                // Invalid genre, ignore it
                this.genre = null;
            }
        }
        return this;
    }

    public MovieAPIRequestBuilder genre(Genre genre) {
        this.genre = genre;
        return this;
    }

    public MovieAPIRequestBuilder releaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
        return this;
    }

    public MovieAPIRequestBuilder releaseYear(int releaseYear) {
        this.releaseYear = String.valueOf(releaseYear);
        return this;
    }

    public MovieAPIRequestBuilder ratingFrom(String ratingFrom) {
        this.ratingFrom = ratingFrom;
        return this;
    }

    public MovieAPIRequestBuilder ratingFrom(double ratingFrom) {
        this.ratingFrom = String.valueOf(ratingFrom);
        return this;
    }

    // Build method that constructs the final URL
    public String build() {
        StringBuilder url = new StringBuilder(baseUrl);

        // Check if any parameters need to be added
        if (hasParameters()) {
            url.append("?");

            boolean firstParam = true;

            if (query != null && !query.isEmpty()) {
                if (!firstParam) url.append(DELIMITER);
                url.append("query=").append(query);
                firstParam = false;
            }

            if (genre != null) {
                if (!firstParam) url.append(DELIMITER);
                url.append("genre=").append(genre);
                firstParam = false;
            }

            if (releaseYear != null && !releaseYear.isEmpty()) {
                if (!firstParam) url.append(DELIMITER);
                url.append("releaseYear=").append(releaseYear);
                firstParam = false;
            }

            if (ratingFrom != null && !ratingFrom.isEmpty()) {
                if (!firstParam) url.append(DELIMITER);
                url.append("ratingFrom=").append(ratingFrom);
                firstParam = false;
            }
        }

        return url.toString();
    }

    // Helper method to check if any parameters are set
    private boolean hasParameters() {
        return (query != null && !query.isEmpty()) ||
                genre != null ||
                (releaseYear != null && !releaseYear.isEmpty()) ||
                (ratingFrom != null && !ratingFrom.isEmpty());
    }

    // Reset method to clear all parameters (optional)
    public MovieAPIRequestBuilder reset() {
        this.query = null;
        this.genre = null;
        this.releaseYear = null;
        this.ratingFrom = null;
        return this;
    }
}