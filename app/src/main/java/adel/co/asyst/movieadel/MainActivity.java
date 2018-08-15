package adel.co.asyst.movieadel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import adel.co.asyst.movieadel.adapter.MovieAdapter;
import adel.co.asyst.movieadel.fragment.FilterFragment;
import adel.co.asyst.movieadel.fragment.SearchFragment;
import adel.co.asyst.movieadel.model.MovieModel;
import adel.co.asyst.movieadel.retrofit.ApiClient;
import adel.co.asyst.movieadel.retrofit.ApiServices;
import adel.co.asyst.movieadel.retrofit.response.MovieResponse;
import adel.co.asyst.movieadel.utility.Constant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements FilterFragment.OnSubmitButtonListener, SearchFragment.OnSubmitListener {

    RecyclerView recyclerView;
    MovieAdapter movieAdapter;
    ArrayList<MovieModel> listMovie = new ArrayList<>();
    String search = "";
    String year = "";
    int page = 1;
    String sort_by = "";
    int pages;
    int totalpages;
    ProgressBar progressbar;
    boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerViewMovie);
        progressbar = findViewById(R.id.progresBar);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        movieAdapter = new MovieAdapter(this, listMovie, new MovieAdapter.onItemClickListener() {
            @Override
            public void onItemClickListener(MovieModel movieModel) {
                Toast.makeText(getApplicationContext(), movieModel.getTitle(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, OverviewActivity.class);
                intent.putExtra("movie", movieModel);
                startActivity(intent);
            }
        });

        getDataWithRetrofit();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    if (!isLoading) {
                        if (totalpages >= pages) {
                            progressbar.setVisibility(View.VISIBLE);
                            isLoading = true;
                            if (search.equalsIgnoreCase("")) {
                                getDataWithRetrofit();
                            } else {
                                searchDataWithRetrofit();
                            }
                        }
                    }
                }
            }
        });
        recyclerView.setAdapter(movieAdapter);
    }

    private void getDataWithRetrofit() {
        ApiServices apiServices = ApiClient.newInstance(getApplicationContext()).create(ApiServices.class);

        Call<MovieResponse> call = apiServices.getMovie(Constant.API_KEY, year, page, sort_by);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                progressbar.setVisibility(View.INVISIBLE);
                if (response.body() != null) {
                    if (response.body().getResults().size() > 0) {
                        totalpages = response.body().getTotal_pages();
                        page = response.body().getPage() + 1;
                        listMovie.addAll(response.body().getResults());
                        movieAdapter.notifyDataSetChanged();
                    }
                }
                isLoading = false;
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private void searchDataWithRetrofit() {
        ApiServices apiServices = ApiClient.newInstance(getApplicationContext()).create(ApiServices.class);

        Call<MovieResponse> call = apiServices.searchMovie(Constant.API_KEY, search, page);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                progressbar.setVisibility(View.INVISIBLE);
                if (response.body() != null) {
                    if (response.body().getResults().size() > 0) {
                        totalpages = response.body().getTotal_pages();
                        page = response.body().getPage() + 1;
                        listMovie.addAll(response.body().getResults());
                        movieAdapter.notifyDataSetChanged();
                    }
                }
                isLoading = false;
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error Occured", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.sort_menu:
                FilterFragment filterFragment = FilterFragment.newInstance(year, sort_by);
                filterFragment.show(getSupportFragmentManager(), "Filter");

                break;
            case R.id.search_menu:

                SearchFragment searchFragment = SearchFragment.newInstance(search);
                searchFragment.show(getSupportFragmentManager(), "Search");
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onSubmitButton(String year, String sort) {
        this.year = year;
        this.sort_by = sort;
        this.search = "";

        listMovie.clear();
        movieAdapter.notifyDataSetChanged();

        page = 1;

        getDataWithRetrofit();
    }

    @Override
    public void onSubmitButton(String search) {
        this.search = search;

        page = 1;
        listMovie.clear();
        movieAdapter.notifyDataSetChanged();
        searchDataWithRetrofit();
    }
}
