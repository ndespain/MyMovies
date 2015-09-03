package com.ndes.mymovies;

/**
 * Created by ndespain on 9/3/15.
 */
public class MovieData {
    String title;
    String description;
    String imgSrc;

    public MovieData(String title, String description, String imgSrc) {
        this.title = title;
        this.description = description;
        this.imgSrc = imgSrc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }
}
