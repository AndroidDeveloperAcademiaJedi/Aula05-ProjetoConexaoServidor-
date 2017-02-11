package com.academiajedi.androiddveloper.conexaoservidorprojeto;

/**
 * Created by alexsoaresdesiqueira on 10/02/17.
 */

public class ApiURLs {

    public static final String GET_LIST_MOVIES_NOW = "https://api.themoviedb.org/3/movie/now_playing?api_key=164cc22d93959f1d405f7dbe79f326a9&language=pt-BR&page=1";
    public static final String GET_DETAIL_MOVIE_LIST (String idMovie){
        return "https://api.themoviedb.org/3/movie/"+idMovie+"?api_key=164cc22d93959f1d405f7dbe79f326a9&language=pt-BR";
    }
    public static final String RATING_MOVIE (String idMovie){
        return "https://api.themoviedb.org/3/movie/"+idMovie+"/rating?api_key=164cc22d93959f1d405f7dbe79f326a9&guest_session_id=e133a5fe0f2ae48524c34700bd689239";
    }


}
