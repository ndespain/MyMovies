package com.ndes.mymovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ndes.mymovies.model.MovieData;
import com.ndes.mymovies.model.SortBy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MoviesActivityFragment extends Fragment {

    public static final String MOVIE_DATA_KEY = "movieData";
    private final String BASE_URL = "http://api.themoviedb.org/3/discover/movie?";

    private MovieArrayAdapter mMovieAdapter;
    private MovieData[] mMovies;
    private Spinner mSpinner;

    public MoviesActivityFragment() {
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable(MOVIE_DATA_KEY, mMovies);

    }

    @Override
    public void onViewStateRestored(final Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        // todo: use this to make sure we have internet connection so we don't crash
        ConnectivityManager cm = (ConnectivityManager) getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork!=null && activeNetwork.isConnectedOrConnecting();


        String[] displays = getResources().getStringArray(R.array.pref_sort_options_display);
        String[] values = getResources().getStringArray(R.array.pref_sort_option_values);

        List<SortBy> sortByOptions = new ArrayList<>();
        for (int i = 0; i < displays.length; i++) {
            SortBy sortBy = new SortBy(displays[i], values[i]);
            sortByOptions.add(sortBy);
        }

        mSpinner = (Spinner) view.findViewById(R.id.sort_by_spinner);
        ArrayAdapter<SortBy> adapter = new ArrayAdapter<>(getActivity(),R.layout.simple_list_item,sortByOptions);
        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SortBy sort = (SortBy) parent.getAdapter().getItem(position);
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(getString(R.string.pref_sort_by_key), sort.getDataValue());
                editor.commit();

                updateMovies();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//        final MovieData movieData = new MovieData("Groundhog Day", "Really good movie", "later");
        List<MovieData> movies = new ArrayList<>();
//        movies.add(movieData);
//        movies.add(new MovieData("Ladyhawke", "Another good movie", "later"));
//        movies.add(new MovieData("Movie3", "Another good movie", "later"));
//        movies.add(new MovieData("Movie4", "Another good movie", "later"));
//        movies.add(new MovieData("Movie5", "Another good movie", "later"));
//        movies.add(new MovieData("Movie6", "Another good movie", "later"));


        mMovieAdapter = new MovieArrayAdapter(getActivity(), R.layout.grid_item_movie, movies);
        GridView moviesGrid = (GridView) view.findViewById(R.id.gridViewMovies);
        moviesGrid.setAdapter(mMovieAdapter);

        moviesGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                showToast(getActivity(), mMovies[position].getTitle() + "\n" + mMovies[position].getOverview());
                Intent intent = new Intent(parent.getContext(), MovieDetailsActivity.class);
                intent.putExtra("MOVIEDATA", mMovies[position]);
                startActivity(intent);
            }
        });

        return view;
    }

    private void showToast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.show();
    }


    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    private void updateMovies() {

        new MoviesTask().execute();

    }

    public class MoviesTask extends AsyncTask <Void, Void, MovieData[]> {

        private final String LOG_TAG = MoviesTask.class.getSimpleName();

        @Override
        protected MovieData[] doInBackground(Void... params) {
            String fullData = getMovieData();

            return extractMovieDataFromJson(fullData);
        }

        @Override
        protected void onPostExecute(MovieData[] movies) {
//            super.onPostExecute(movieData);
            mMovies = movies;
            mMovieAdapter.clear();
            if(movies != null) {
                mMovieAdapter.addAll(movies);
            }
        }

        private MovieData[] extractMovieDataFromJson(String fullData) {

            final String tmdb_movieId = "id";
            final String tmdb_results = "results";
            final String tmdb_poster = "poster_path";
            final String tmdb_title = "title";
            final String tmdb_overview = "overview";
            MovieData[] movies = null;

            try {
                JSONObject moviesJson = new JSONObject(fullData);
                JSONArray moviesArray = moviesJson.getJSONArray(tmdb_results);

                movies = new MovieData[moviesArray.length()];
                for (int i = 0; i < moviesArray.length(); i++) {
                    JSONObject movieInfo = moviesArray.getJSONObject(i);
                    MovieData movie = new MovieData(movieInfo.getString(tmdb_movieId), movieInfo.getString(tmdb_title), movieInfo.getString(tmdb_overview), movieInfo.getString(tmdb_poster));
                    movie.setVoteAverage((float) movieInfo.getDouble("vote_average"));
                    movie.setVoteCount(movieInfo.getInt("vote_count"));

                    movies[i] = movie;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return movies;


        }

        private String getMovieData() {

/*
http://docs.themoviedb.apiary.io/#reference/discover/discovermovie
https://www.themoviedb.org/documentation/api/discover
sort_by
    popularity.asc
    popularity.desc
    release_date.asc
    release_date.desc
    revenue.asc
    revenue.desc
    primary_release_date.asc
    primary_release_date.desc
    original_title.asc
    original_title.desc
    vote_average.asc
    vote_average.desc
    vote_count.asc
    vote_count.desc
 */
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String sortBy = preferences.getString(getString(R.string.pref_sort_by_key), getString(R.string.pref_sort_by_default)) + ".desc";

            Uri uri = Uri.parse(BASE_URL).buildUpon().appendQueryParameter("api_key", getString(R.string.api_key))
                    .appendQueryParameter("certification_country", "US")
                    .appendQueryParameter("certification.lte","PG-13")
                    .appendQueryParameter("sort_by", sortBy).build();


            URL url = null;
            try {
                url = new URL(uri.toString());
            } catch (MalformedURLException e) {
                Log.e(LOG_TAG, "Error ", e);
                e.printStackTrace();
            }
            return ServiceUtils.requestData(url);

        }
    }
}


