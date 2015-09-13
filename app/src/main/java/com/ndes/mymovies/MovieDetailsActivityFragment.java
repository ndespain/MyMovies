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
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + trailer.getSource())));
                }
            }
        });

        MovieDetailTask task = new MovieDetailTask();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        new MovieDetailTask().execute(mMovieData.getMovieId());
    }

    private class MovieDetailTask extends AsyncTask<String, Void, MovieData> {
        public static final String YOUTUBE = "youtube";
        String LOG_TAG = MovieDetailTask.class.getSimpleName();

        @Override
        protected MovieData doInBackground(String... params) {
            String extraMovieInfo = getExtraMovieInfo(params[0]);
            MovieData movieData = extractMovieExtraInfoFromJson(extraMovieInfo);

            return movieData;
        }

        @Override
        protected void onPostExecute(MovieData movieData) {
//            super.onPostExecute(movieData);
            mMovieData.setTrailers(movieData.getTrailers());
            Button button = (Button) getView().findViewById(R.id.trailer1);

        }

        private String getExtraMovieInfo(String movieId) {
            //http://api.themoviedb.org/3/movie/211672?api_key=522f2ef5fc7f003a64790b1c6016ec3a&append_to_response=trailers
            Uri uri = Uri.parse(BASE_DETAIL_URL + movieId + "?").buildUpon().appendQueryParameter("api_key", ServiceUtils.API_KEY)
                    .appendQueryParameter("append_to_response", "trailers").build();


            URL url = null;
            try {
                url = new URL(uri.toString());
            } catch (MalformedURLException e) {
                Log.e(LOG_TAG, "Error ", e);
                e.printStackTrace();
            }
            return ServiceUtils.requestData(url);
        }

        private MovieData extractMovieExtraInfoFromJson(String extraMovieInfo) {
            MovieData data = new MovieData();
            try {
                JSONObject json = new JSONObject(extraMovieInfo);
                JSONArray trailers = json.getJSONObject("trailers").getJSONArray(YOUTUBE);
                for (int i = 0; i < trailers.length(); i++) {
                    JSONObject trailerJson = (JSONObject) trailers.get(i);

                    Trailer trailer = new Trailer(YOUTUBE, trailerJson.getString("name"), trailerJson.getString("source"));

                    data.addTrailer(trailer);


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return data;
        }

    }
}
