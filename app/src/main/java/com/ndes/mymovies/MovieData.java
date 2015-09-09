package com.ndes.mymovies;

/**
 * Created by ndespain on 9/3/15.
 */
public class MovieData {
    String title;
    String overview;
    String imgSrc;
    private final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w185/";

    public MovieData(String title, String overview, String imgSrc) {
        this.title = title;
        this.overview = overview;
        this.imgSrc = imgSrc;
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
}
