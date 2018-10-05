package com.camilo.proyectoandroid;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

import config.Sqlite;

public class MainActivity extends AppCompatActivity {

    /*TextView tvregistar;
    Button btnIngresar;*/


    Sqlite bd = new Sqlite(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton ilogin = (ImageButton) findViewById(R.id.login);
        ilogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder mbuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_login,null);

                final EditText musuario = (EditText) mView.findViewById(R.id.txtusuario);
                final EditText mclave = (EditText) mView.findViewById(R.id.txtclave);
                Button minicio = (Button) mView.findViewById(R.id.btniniciar);

                minicio.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try {
                            Cursor cursor = bd.validarusu(musuario.getText().toString(),mclave.getText().toString());
                            if (cursor.getCount()>0){
                                Intent intent = new Intent(getApplicationContext(),Principal.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(getApplicationContext(),"Usuario o clave incorrectos",Toast.LENGTH_SHORT).show();
                            }
                            musuario.setText("");
                            mclave.setText("");
                            musuario.findFocus();

                        }catch (android.database.SQLException e){
                            e.printStackTrace();
                        }
                    }
                });

                mbuilder.setView(mView);
                AlertDialog dialog = mbuilder.create();
                dialog.show();

                TextView tregistrar = (TextView) mView.findViewById(R.id.tvregistrese);
                tregistrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder mbuilder2 = new AlertDialog.Builder(MainActivity.this);
                        View mView2 = getLayoutInflater().inflate(R.layout.dialog_registro,null);

                        Button btnGrabarUsu = (Button) mView2.findViewById(R.id.btnregistrar);
                        final EditText edusu = (EditText) mView2.findViewById(R.id.edusuario);
                        final EditText edcla = (EditText) mView2.findViewById(R.id.edclave);

                        btnGrabarUsu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                bd.abrir();
                                bd.insertarReg(String.valueOf(edusu.getText()),String.valueOf(edcla.getText()));
                                bd.cerrar();

                                Toast.makeText(getApplicationContext(),"Registro almacenado con exito",Toast.LENGTH_SHORT).show();

                                edusu.setText("");
                                edcla.setText("");
                                edusu.findFocus();

                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }
                        });

                        mbuilder2.setView(mView2);
                        AlertDialog dialog = mbuilder2.create();
                        dialog.show();

                    }
                });
            }

        });


    }
}
