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
            /**Armazena todos os bytes lidos*/
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            /**descobrir a quantidade de bytes lidos*/
            int bytesLidos;
            /**lendo um byte por vez*/
            while ((bytesLidos = ls.read(buffer)) != -1){
                /**copia a quantidade de bytes lidos do buffer para o byteArrayOutputStream*/
                byteArrayOutputStream.write(buffer, 0, bytesLidos);
            }
            return new String(byteArrayOutputStream.toByteArray(), "UTF-8");

        }catch (Exception e){
            return "";
        }
    }

    public static Bitmap downloadImageMovie(String urlImage){
        try {
            /**Monta a URL a partir de uma String*/
            URL url = new URL("http://image.tmdb.org/t/p/w185/"+urlImage);
            /**riamos o objeto HttpURLConnection e obtemos a conexão com determinada url atraves do meotdo .openConnection.*/
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true); /**Informa que iremos apenas realizar leitura nessa determinada conexão*/
            connection.connect(); /**estabelece uma conexão com o servidor*/
            InputStream input = connection.getInputStream(); /**realiza o download da imagem*/
            Bitmap myBitmap = BitmapFactory.decodeStream(input); /**converte os bytes de retono do servidor em um bitmap**/
            return myBitmap;
        } catch (Exception e) {
            return null;
        }
    }
}
