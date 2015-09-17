package com.ndes.mymovies;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ndes.mymovies.model.MovieData;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ndespain on 9/3/15.
 */
public class MovieArrayAdapter extends ArrayAdapter<MovieData> {
//    private Context context;
    private int mResource;
    private List<MovieData> mMovieData;

    public MovieArrayAdapter(Context context, int resource, List<MovieData> movieData) {
        super(context, resource, movieData);
//        this.context = context;
        mResource = resource;
        mMovieData = movieData;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)getContext()).getLayoutInflater();
        View gridItem = inflater.inflate(mResource, parent, false);

        TextView movieName = (TextView) gridItem.findViewById(R.id.movieName);
        movieName.setText(mMovieData.get(position).getTitle());

        ImageView moviePoster = (ImageView) gridItem.findViewById(R.id.movieImg);
        Picasso.with(getContext()).load(mMovieData.get(position).getImgSrc()).into(moviePoster);


        return gridItem;
    }
}
