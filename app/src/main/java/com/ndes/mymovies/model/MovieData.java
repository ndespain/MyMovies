package com.ndes.mymovies.model;

import com.ndes.mymovies.Trailer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ndespain on 9/3/15.
 */
public class MovieData implements Serializable {
    private String movieId;
    private String title;
    private String overview;
    private String imgSrc;
    private String releaseDate;
    private String rating;
    private double voteAverage;
    private double voteCount;
    private List<Trailer> trailers;

    private final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185/";

    public MovieData() {

    }

    public MovieData(String movieId, String title, String overview, String imgSrc) {
        this.movieId = movieId;
        this.title = title;
        this.overview = overview;
        this.imgSrc = imgSrc;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getImgSrc() {
        return BASE_IMAGE_URL + imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public Trailer getTrailer(int index) {
        if (trailers != null && trailers.size() > index) {
            return trailers.get(index);
        } else {
            return null;
        }
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
    }

    public void addTrailer(Trailer trailer) {
        if (trailers == null) {
            trailers = new ArrayList<>();
        }
        trailers.add(trailer);
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(final String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(final String rating) {
        this.rating = rating;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(final double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public double getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(final double voteCount) {
        this.voteCount = voteCount;
    }
}
