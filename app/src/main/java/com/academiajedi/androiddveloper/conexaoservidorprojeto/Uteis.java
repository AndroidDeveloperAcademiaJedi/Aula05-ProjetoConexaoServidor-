package com.academiajedi.androiddveloper.conexaoservidorprojeto;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by alexsoaresdesiqueira on 11/02/17.
 */

public class Uteis {

    public static String bytesParaString(InputStream ls){

        try{

            byte[] buffer = new byte[1024];
            //Armazena todos os bytes lidos
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            //descobrir a quantidade de bytes lidos
            int bytesLidos;
            //lendo um byte por vez
            while ((bytesLidos = ls.read(buffer)) != -1){
                //copia a quantidade de bytes lidos do buffer para o byteArrayOutputStream
                byteArrayOutputStream.write(buffer, 0, bytesLidos);
            }
            return new String(byteArrayOutputStream.toByteArray(), "UTF-8");

        }catch (Exception e){
            return "";
        }
    }

    public static Bitmap downloadImageMovie(String urlImage){
        try {
            URL url = new URL("http://image.tmdb.org/t/p/w185/"+urlImage);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            return null;
        }
    }
}
