package com.ndes.mymovies;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.GridView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MoviesActivityFragment extends Fragment {

    private MovieArrayAdapter mMovieAdapter;

    public MoviesActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        MovieData movieData = new MovieData("Groundhog Day", "Really good movie", "later");
        List<MovieData> movies = new ArrayList<>();
        movies.add(movieData);
        movies.add(new MovieData("Ladyhawke", "Another good movie", "later"));
        movies.add(new MovieData("Movie3", "Another good movie", "later"));
        movies.add(new MovieData("Movie4", "Another good movie", "later"));
        movies.add(new MovieData("Movie5", "Another good movie", "later"));
        movies.add(new MovieData("Movie6", "Another good movie", "later"));

        //http://api.themoviedb.org/3/discover/movie?page=1&release_date.gte=2013-05-12&release_date.lte=2013-05-14&api_key=522f2ef5fc7f003a64790b1c6016ec3a
//        http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg
//        Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);

        mMovieAdapter = new MovieArrayAdapter(getActivity(), R.layout.grid_item_movie, movies);
        GridView moviesGrid = (GridView) view.findViewById(R.id.gridViewMovies);
        moviesGrid.setAdapter(mMovieAdapter);

        return view;
    }
}


/*
// show The Image
new DownloadImageTask((ImageView) findViewById(R.id.imageView1))
            .execute("http://java.sogeti.nl/JavaBlog/wp-content/uploads/2009/04/android_icon_256.png");
}

public void onClick(View v) {
    startActivity(new Intent(this, IndexActivity.class));
    finish();

}

private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}
 */
