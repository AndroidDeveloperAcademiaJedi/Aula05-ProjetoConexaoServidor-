package com.academiajedi.androiddveloper.conexaoservidorprojeto;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecycleViewClickHack{

    private Button btnBuscarFilmes;
    private RecyclerView rvMovies;
    private ProgressDialog pdia;
    private MoviesAdapter moviesAdapter;
    private List<MoviesNow> moviesNowList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvMovies = (RecyclerView) findViewById(R.id.rvMovies);

        btnBuscarFilmes = (Button) findViewById(R.id.btnBuscarFilmes);
        btnBuscarFilmes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moviesNowList = new ArrayList<MoviesNow>();
                new getMovies().execute(); /** Executa o nosso asyncTask **/
            }
        });

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvMovies.setLayoutManager(llm);

        veriifyShared();/** verifica no shared a opção que o usuario marcou na tela de configurações **/
    }

    public void veriifyShared(){
        String valor = PreferenciaUsuario.getString(MainActivity.this, PreferenciaUsuario.CHAVE_CONFIG);
        if(valor != ""){
            if(valor.equals("sim")){
                moviesNowList = new ArrayList<MoviesNow>();
                new getMovies().execute();  /** Executa o nosso asyncTask **/
            }
        }
    }

    /** Chama a tela de detalhes do filme passando o ID do filme em que o usuario clicou **/
    public void chamarMovieDetail(String idMovie){
        Intent i = new Intent(MainActivity.this, DetailMovieActivity.class);
        i.putExtra("movieID", idMovie);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inicial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.menu_configuracoes:
                Intent i = new Intent(MainActivity.this, ConfiguracaoActivity.class);
                startActivity(i);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickListener(int position) {
        chamarMovieDetail(moviesNowList.get(position).getId());
    }

    /**
     * AsyncTask para buscar os filmes
     */

    private class getMovies extends AsyncTask<Void, Void, List<MoviesNow>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            /** Cria e mostra um progress na tela para informar ao usuario que estamos realizando alguma atividade e que ele deve esperar **/

            pdia = new ProgressDialog(MainActivity.this);
            pdia.setMessage("Loading...");
            pdia.show();
        }

        @Override
        protected List<MoviesNow> doInBackground(Void... params) {

            try{
                String returnStr = "";

                /** Criando um objeto URL atraves de uma String **/

                URL url = new URL(ApiURLs.GET_LIST_MOVIES_NOW);

                /** Obtendo a conexão com o servidor */
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {   /** Verificando o response code do servidor, ou seja verificar se foi com sucesso*/
                    returnStr = Uteis.bytesParaString(urlConnection.getInputStream()); /** Transformando os meus bytes em string **/
                }

                return convertJsonToMovies(returnStr); /** convert a string json em uma lista de filmes **/

            }catch (Exception e){
                Log.e("Erro json", e.getMessage());
                return null;
            }

        }

        @Override
        protected void onPostExecute(List<MoviesNow> moviesNows) {
            super.onPostExecute(moviesNows);
            pdia.dismiss();
            moviesNowList = moviesNows;

            moviesAdapter = new MoviesAdapter(moviesNowList);
            moviesAdapter.setRecycleViewClick(MainActivity.this);
            rvMovies.setAdapter(moviesAdapter);

        }

        public List<MoviesNow> convertJsonToMovies(String json){
            try{

                List<MoviesNow> moviesNows = new ArrayList<>();

                JSONObject jsonReturnApi = new JSONObject(json);  /** converto a minha string em um objeto do tipo JsonObject **/
                JSONArray jsonArray = jsonReturnApi.getJSONArray("results"); /** Vamos capturar no nosso json que o servidor retorno a tag results que é um JSONArray */

                for (int i = 0; i<jsonArray.length(); i++){  /*** Vamos percorrer o nosso JsonArray para mostrar o nosso objeto de filmes */
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    MoviesNow moviesNow = new MoviesNow();
                    moviesNow.setId(jsonObject.getString("id")); /*** recuperar um determinado valor do nosso json e atribuindo na nossa variavel */
                    moviesNow.setImageMovie(Uteis.downloadImageMovie(jsonObject.getString("poster_path")));
                    moviesNow.setOriginal_title(jsonObject.getString("original_title"));
                    moviesNow.setPoster_path(jsonObject.getString("poster_path"));
                    moviesNow.setRelease_date(jsonObject.getString("release_date"));
                    moviesNow.setTitle(jsonObject.getString("title"));

                    moviesNows.add(moviesNow);
                }

                return moviesNows;

            } catch (Exception e){
                Log.e("Erro json", e.getMessage());
                return null;
            }

        }
    }
}