package com.academiajedi.androiddveloper.conexaoservidorprojeto;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by alexsoaresdesiqueira on 11/02/17.
 */

public class PreferenciaUsuario {

    public static final String PREF_ID = "CONEXAO_SERVIDOR_PROJETO";
    public static final String CHAVE_CONFIG = "CHAVE_CONFIG";


    public static void setString (Context context, String key, String value){
        SharedPreferences preferences = context.getSharedPreferences(PREF_ID, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString (Context context, String key){
        SharedPreferences preferences = context.getSharedPreferences(PREF_ID, 0);
        String valor = preferences.getString(key, "");
        return valor;
    }
}
