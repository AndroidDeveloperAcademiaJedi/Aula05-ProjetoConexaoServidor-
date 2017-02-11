package com.academiajedi.androiddveloper.conexaoservidorprojeto;

import android.graphics.Bitmap;

import java.util.List;

/**
 * Created by alexsoaresdesiqueira on 10/02/17.
 */

public class MoviesDetailNow {

    private String vote_average;
    private List<String> genres;
    private String status;
    private String id;
    private List<String> production_countries;
    private String title;
    private String overview;
    private List<String> production_companies;
    private String release_date;
    private String original_title;
    private String poster_path;
    private Bitmap imageMovie;


    public Bitmap getImageMovie() {
        return imageMovie;
    }

    public void setImageMovie(Bitmap imageMovie) {
        this.imageMovie = imageMovie;
    }

    public String getVote_average ()
    {
        return vote_average;
    }

    public void setVote_average (String vote_average)
    {
        this.vote_average = vote_average;
    }


    public List<String> getGenres ()
    {
        return genres;
    }

    public void setGenres (List<String> genres)
    {
        this.genres = genres;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public List<String> getProduction_countries ()
    {
        return production_countries;
    }

    public void setProduction_countries (List<String> production_countries)
    {
        this.production_countries = production_countries;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getOverview ()
    {
        return overview;
    }

    public void setOverview (String overview)
    {
        this.overview = overview;
    }

    public List<String> getProduction_companies ()
    {
        return production_companies;
    }

    public void setProduction_companies (List<String> production_companies)
    {
        this.production_companies = production_companies;
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
