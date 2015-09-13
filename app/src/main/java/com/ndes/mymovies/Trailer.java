package com.ndes.mymovies;

/**
 * Created by neildespain on 9/11/15.
 */
public class Trailer {
    String type; // youtube or quicktime
    String name;
    String source;

    public Trailer(String type, String name, String source) {
        this.type = type;
        this.name = name;
        this.source = source;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
