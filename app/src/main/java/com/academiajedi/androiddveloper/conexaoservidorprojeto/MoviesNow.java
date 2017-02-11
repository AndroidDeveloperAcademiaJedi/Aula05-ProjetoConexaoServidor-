package com.academiajedi.androiddveloper.conexaoservidorprojeto;

import android.graphics.Bitmap;

/**
 * Created by alexsoaresdesiqueira on 10/02/17.
 */

public class MoviesNow {

    private String id;
    private String title;
    private String release_date;
    private String original_title;
    private String poster_path;
    private Bitmap imageMovie;

    public MoviesNow() {

    }

    public Bitmap getImageMovie() {
        return imageMovie;
    }

    public void setImageMovie(Bitmap imageMovie) {
        this.imageMovie = imageMovie;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getRelease_date ()
    {
        return release_date;
    }

    public void setRelease_date (String release_date)
    {
        this.release_date = release_date;
    }

    public String getOriginal_title ()
    {
        return original_title;
    }

    public void setOriginal_title (String original_title)
    {
        this.original_title = original_title;
    }

    public String getPoster_path ()
    {
        return poster_path;
    }

    public void setPoster_path (String poster_path)
    {
        this.poster_path = poster_path;
    }

}
