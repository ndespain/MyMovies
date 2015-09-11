package com.ndes.mymovies;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailsActivityFragment extends Fragment {

    public MovieDetailsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_details, container, false);

        Intent intent = getActivity().getIntent();
        MovieData movie = (MovieData) intent.getSerializableExtra("MOVIEDATA");

        TextView title = (TextView) view.findViewById(R.id.movieTitle);
        title.setText(movie.getTitle());

        TextView description = (TextView) view.findViewById(R.id.movieDescription);
        description.setText(movie.getOverview());

        ImageView moviePoster = (ImageView) view.findViewById(R.id.moviePoster);
        Picasso.with(getActivity()).load(movie.getImgSrc()).into(moviePoster);

        return view;
    }
}
