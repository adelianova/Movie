package adel.co.asyst.movieadel.retrofit.response;

import java.util.ArrayList;

import adel.co.asyst.movieadel.model.MovieModel;

public class MovieResponse {

    int page;
    int total_results;
    int total_pages;

    ArrayList<MovieModel> results;

    public int getPage() {
        return page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public ArrayList<MovieModel> getResults() {
        return results;
    }
}
