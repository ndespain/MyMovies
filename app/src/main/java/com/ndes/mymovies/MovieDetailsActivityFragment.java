package com.ndes.mymovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ndes.mymovies.model.MovieData;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailsActivityFragment extends Fragment {

    private final String BASE_DETAIL_URL = "http://api.themoviedb.org/3/movie/";
    private MovieData mMovieData;
    private Button mPlayTrailerBtn;

    public MovieDetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);

        Intent intent = getActivity().getIntent();
        mMovieData = (MovieData) intent.getSerializableExtra("MOVIEDATA");

        TextView title = (TextView) view.findViewById(R.id.movieTitle);
        title.setText(mMovieData.getTitle());

        TextView description = (TextView) view.findViewById(R.id.movieDescription);
        description.setText(mMovieData.getOverview());

        ImageView moviePoster = (ImageView) view.findViewById(R.id.moviePoster);
        Picasso.with(getActivity()).load(mMovieData.getImgSrc()).into(moviePoster);

        mPlayTrailerBtn = (Button) view.findViewById(R.id.trailer1);
        mPlayTrailerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Trailer trailer = mMovieData.getTrailer(0);
                if (trailer != null) {
                    Toast.makeText(getActivity(), mMovieData.getTrailers().get(0).getName(), Toast.LENGTH_SHORT).show();
                    watchYoutubeVideo(trailer.getSource());
                }
            }
        });

        MovieDetailTask task = new MovieDetailTask();

        return view;
    }

    public void watchYoutubeVideo(String id){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            intent=new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v="+id));
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(getActivity(), "No app to show trailer.", Toast.LENGTH_LONG);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        new MovieDetailTask().execute(mMovieData);
    }

    private class MovieDetailTask extends AsyncTask<MovieData, Void, MovieData> {
        public static final String YOUTUBE = "youtube";
        String LOG_TAG = MovieDetailTask.class.getSimpleName();

        @Override
        protected MovieData doInBackground(MovieData... params) {
            MovieData movieData = params[0];
            String extraMovieInfo = getExtraMovieInfo(movieData.getMovieId());

            extractMovieExtraInfoFromJson(movieData, extraMovieInfo);
            return movieData;
        }

        @Override
        protected void onPostExecute(MovieData movieData) {
//            super.onPostExecute(movieData);
//            mMovieData.setTrailers(movieData.getTrailers());
            Button button = (Button) getView().findViewById(R.id.trailer1);

        }

        private String getExtraMovieInfo(String movieId) {
            Uri uri = Uri.parse(BASE_DETAIL_URL + movieId + "?").buildUpon().appendQueryParameter("api_key", getString(R.string.api_key))
                    .appendQueryParameter("append_to_response", "trailers,releases").build();


            URL url = null;
            try {
                url = new URL(uri.toString());
            } catch (MalformedURLException e) {
                Log.e(LOG_TAG, "Error ", e);
                e.printStackTrace();
            }
            return ServiceUtils.requestData(url);
        }

        private void extractMovieExtraInfoFromJson(MovieData movieData, String extraMovieInfo) {
            try {
                JSONObject json = new JSONObject(extraMovieInfo);
                JSONArray trailers = json.getJSONObject("trailers").getJSONArray(YOUTUBE);
                for (int i = 0; i < trailers.length(); i++) {
                    JSONObject trailerJson = (JSONObject) trailers.get(i);

                    Trailer trailer = new Trailer(YOUTUBE, trailerJson.getString("name"), trailerJson.getString("source"));

                    movieData.addTrailer(trailer);
                }

                JSONArray releases = json.getJSONObject("releases").getJSONArray("countries");
                for (int i = 0; i <releases.length(); i++) {
                    JSONObject release = (JSONObject) releases.get(i);
                    if ("US".equals(release.getString("iso_3166_1"))) {
                        movieData.setRating(release.getString("certification"));
                        movieData.setReleaseDate(release.getString("release_date"));
                        break;
                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

//            return movieData;
        }

    }
}
