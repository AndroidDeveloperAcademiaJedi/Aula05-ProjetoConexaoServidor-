package com.academiajedi.androiddveloper.conexaoservidorprojeto;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

/**
 * Created by alexsoaresdesiqueira on 11/02/17.
 */

public class ConfiguracaoActivity extends AppCompatActivity {

    private Button btnSalvar;
    private CheckBox cbNao;
    private CheckBox cbSim;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);

        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        cbNao = (CheckBox) findViewById(R.id.cbNao);
        cbNao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbSim.isChecked()){
                    cbSim.setChecked(false);
                }
            }
        });
        cbSim = (CheckBox) findViewById(R.id.cbSim);
        cbSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbNao.isChecked()){
                    cbNao.setChecked(false);
                }
            }
        });

        veriifyShared();

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenciaUsuario.setString(ConfiguracaoActivity.this, PreferenciaUsuario.CHAVE_CONFIG, verificarCheck());
                Toast.makeText(ConfiguracaoActivity.this, "Salvo", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void veriifyShared(){
        String valor = PreferenciaUsuario.getString(ConfiguracaoActivity.this, PreferenciaUsuario.CHAVE_CONFIG);
        if(valor != ""){
            if(valor.equals("nao")){
                cbNao.setChecked(true);

            } else if(valor.equals("sim")){
                cbSim.setChecked(true);
            }
        }
    }

    public String verificarCheck(){
        String retorno = "";
        if(cbNao.isChecked()){
            retorno ="nao";
        } else if (cbSim.isChecked()){
            retorno = "sim";
        }

        return retorno;
    }
}
