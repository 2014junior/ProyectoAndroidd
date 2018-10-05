package config;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Sqlite extends SQLiteOpenHelper {
    static String nombre="ProyectoAndroid";
    static int version = 1;

    public Sqlite(Context context) {
        super(context, nombre, null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

            String query = "create table usuario(id_usuario integer primary key autoincrement, usuario text, clave text)";
            db.execSQL(query);
            String marcador = "create table marcador (titulo text primary key,latitud decimal (10,8)," +
                "longitud decimal (10,8))";
            db.execSQL(marcador);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void abrir(){
        this.getWritableDatabase();
    }

    public void cerrar(){
        this.close();
    }

    // metodo que me permite insertar registros en la tabla usuario

    public void insertarReg(String nom, String cla){

        ContentValues valores = new ContentValues();
        valores.put("usuario",nom);
        valores.put("clave",cla);
        this.getWritableDatabase().insert("usuario",null,valores);

    }

    //metodo que permite validar si el usuario existe

    public Cursor validarusu(String usu, String pass)throws SQLException{

        Cursor cursor = null;

        cursor = this.getReadableDatabase().query("usuario", new String[]{"id_usuario",
                "usuario","clave"},"usuario like '"+usu+"'" +
                " and clave like '"+pass+"'",null,null,null,null);

        return cursor;
    }


}
