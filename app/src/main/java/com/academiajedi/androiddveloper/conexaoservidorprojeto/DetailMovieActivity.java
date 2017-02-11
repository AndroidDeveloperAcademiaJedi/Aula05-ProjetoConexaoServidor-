package com.academiajedi.androiddveloper.conexaoservidorprojeto;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexsoaresdesiqueira on 11/02/17.
 */

public class DetailMovieActivity extends AppCompatActivity {

    private Button btnVote;
    private ProgressDialog pdia;
    private String idMovie;
    private String voto;

    private TextView tvVoto;
    private TextView tvTitle;
    private TextView tvTitleOriginal;
    private TextView tvdatalancamento;
    private TextView tvGenero;
    private TextView tvDescricaoMovie;
    private ImageView imvMovieDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idMovie = extras.getString("movieID");
            new getDetailMovies().execute();
        }

        tvVoto = (TextView) findViewById(R.id.tvVoto);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitleOriginal = (TextView) findViewById(R.id.tvTitleOriginal);
        tvdatalancamento = (TextView) findViewById(R.id.tvdatalancamento);
        tvGenero = (TextView) findViewById(R.id.tvGenero);
        tvDescricaoMovie = (TextView) findViewById(R.id.tvDescricaoMovie);
        imvMovieDetail = (ImageView) findViewById(R.id.imvMovieDetail);

        btnVote = (Button) findViewById(R.id.btnVote);
        btnVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailMovieActivity.this);
                builder.setTitle("Qual seu voto para esse filme?");

                // Set up the input
                final EditText input = new EditText(DetailMovieActivity.this);
                input.setHint("Ex.: 8");
                input.setFilters(new InputFilter[] {new InputFilter.LengthFilter(1)});
                input.setInputType(InputType.TYPE_CLASS_NUMBER);

                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("Votar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        voto = input.getText().toString();
                        new sendVote().execute();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alerta = builder.create();
                alerta.show();
            }
        });
    }

    private void atualizarDadosView(MoviesDetailNow moviesDetailNow){
        tvVoto.setText(moviesDetailNow.getVote_average());
        tvTitle.setText(moviesDetailNow.getTitle());
        tvTitleOriginal.setText(moviesDetailNow.getOriginal_title());
        tvdatalancamento.setText(moviesDetailNow.getRelease_date());
        String generos = "";
        if(moviesDetailNow.getGenres().size() > 0){
            for (String value:moviesDetailNow.getGenres()){
                if(generos != ""){
                    generos += ", ";

                }
                generos += value;
            }

        }
        tvGenero.setText(generos);
        tvDescricaoMovie.setText(moviesDetailNow.getOverview());
        imvMovieDetail.setImageBitmap(moviesDetailNow.getImageMovie());
    }

    /**
     * AsyncTask para enviar voto
     */

    private class sendVote extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdia = new ProgressDialog(DetailMovieActivity.this);
            pdia.setMessage("Loading...");
            pdia.show();
        }

        @Override
        protected String doInBackground(Void... params) {

            try{
                String returnStr = "";
                URL url = new URL(ApiURLs.RATING_MOVIE(idMovie));

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod(String.valueOf(MetodosHTTP.POST));
                urlConnection.setDoInput(true); /**Informa que iremos realizar operacao de leitura nessa determinada conexão*/
                urlConnection.setDoOutput(true);/**Informa que iremos realizar operacao de escrita nessa determinada conexão, ou seja, iremos enviar alguma parametro no seu corpo*/

                /**
                 * O nosso servidor espera um json, entao vamos converter a nossa string em um objeto JsonObject
                 */
                JSONObject json = new JSONObject();
                json.put("value",Double.parseDouble(voto));

                /**
                 * Vamos transformar agora o nosso jsonObjet em bytes para enviar para o servidor
                 */
                OutputStream out = urlConnection.getOutputStream();
                out.write(json.toString().getBytes());
                out.close();

                if(urlConnection.getResponseCode() != HttpURLConnection.HTTP_BAD_REQUEST){
                    returnStr = Uteis.bytesParaString(urlConnection.getInputStream());
                    urlConnection.disconnect();
                }

                return convertJson(returnStr);

            }catch (Exception e){
                Log.e("Erro json", e.getMessage());
                return null;
            }

        }

        public String convertJson(String jsonString){
            try{

                JSONObject jsonReturnApi = new JSONObject(jsonString);

                return jsonReturnApi.getString("status_message");

            } catch (Exception e){
                Log.e("Erro json", e.getMessage());
                return "";
            }
        }

        @Override
        protected void onPostExecute(String voto) {
            super.onPostExecute(voto);
            pdia.dismiss();
            Toast.makeText(DetailMovieActivity.this, voto, Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * AsyncTask para buscar os filmes
     */

    private class getDetailMovies extends AsyncTask<Void, Void, MoviesDetailNow> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdia = new ProgressDialog(DetailMovieActivity.this);
            pdia.setMessage("Loading...");
            pdia.show();
        }

        @Override
        protected MoviesDetailNow doInBackground(Void... params) {

            try{
                String returnStr = "";

                URL url = new URL(ApiURLs.GET_DETAIL_MOVIE_LIST(idMovie));

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    returnStr = Uteis.bytesParaString(urlConnection.getInputStream());
                }

                return convertJsonToMovies(returnStr);

            }catch (Exception e){
                Log.e("Erro json", e.getMessage());
                return null;
            }

        }

        @Override
        protected void onPostExecute(MoviesDetailNow movieDetail) {
            super.onPostExecute(movieDetail);
            pdia.dismiss();
            atualizarDadosView(movieDetail);
        }



        public MoviesDetailNow convertJsonToMovies(String json){
            try{

                MoviesDetailNow movieDetail = new MoviesDetailNow();

                JSONObject jsonReturnApi = new JSONObject(json);
                JSONArray jsonArrayGenres = jsonReturnApi.getJSONArray("genres");
                JSONArray jsonArrayCountries = jsonReturnApi.getJSONArray("production_countries");
                JSONArray jsonArrayCompanies = jsonReturnApi.getJSONArray("production_companies");

                List<String> genres = new ArrayList<>();

                for (int i = 0; i<jsonArrayGenres.length(); i++){
                    JSONObject jsonObject = jsonArrayGenres.getJSONObject(i);
                    genres.add(jsonObject.getString("name"));
                }

                List<String> countries = new ArrayList<>();

                for (int i = 0; i<jsonArrayCountries.length(); i++){
                    JSONObject jsonObject = jsonArrayCountries.getJSONObject(i);
                    countries.add(jsonObject.getString("name"));
                }

                List<String> companies = new ArrayList<>();

                for (int i = 0; i<jsonArrayCompanies.length(); i++){
                    JSONObject jsonObject = jsonArrayCompanies.getJSONObject(i);
                    companies.add(jsonObject.getString("name"));
                }

                movieDetail.setGenres(genres);
                movieDetail.setProduction_companies(companies);
                movieDetail.setProduction_countries(countries);
                movieDetail.setTitle(jsonReturnApi.getString("title"));
                movieDetail.setRelease_date(jsonReturnApi.getString("release_date"));
                movieDetail.setPoster_path(jsonReturnApi.getString("poster_path"));
                movieDetail.setOriginal_title(jsonReturnApi.getString("original_title"));
                movieDetail.setId(jsonReturnApi.getString("id"));
                movieDetail.setImageMovie(Uteis.downloadImageMovie(jsonReturnApi.getString("poster_path")));
                movieDetail.setOverview(jsonReturnApi.getString("overview"));
                movieDetail.setVote_average(jsonReturnApi.getString("vote_average"));
                movieDetail.setStatus(jsonReturnApi.getString("status"));


                return movieDetail;

            } catch (Exception e){
                Log.e("Erro json", e.getMessage());
                return null;
            }

        }
    }

}
