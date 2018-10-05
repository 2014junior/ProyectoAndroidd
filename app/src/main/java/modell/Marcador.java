package modell;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import config.Sqlite;

public class Marcador {
    String titulo;
    double latitud, longitud;

    public Marcador(String titulo, double latitud, double longitud) {
        this.titulo = titulo;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Marcador() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String toString(){
        return titulo;
    }

    public SQLiteDatabase lectura(Context context){
        Sqlite sqlite = new Sqlite(context);
        return sqlite.getReadableDatabase();
    }

    public SQLiteDatabase escritura(Context context){
        Sqlite sqlite = new Sqlite(context);
        return sqlite.getWritableDatabase();
    }

    public ArrayAdapter<Object> obtenerMarcadores (Context context){
        try{
            ArrayAdapter<Object> adapter = new ArrayAdapter<>(context,android.R.layout.simple_list_item_1);
            ArrayList<Object> arrayList = new ArrayList<>();
            Cursor cursor = lectura(context).rawQuery("select * from marcador",null);
            while(cursor.moveToNext()){
                arrayList.add(new Marcador(cursor.getString(0),cursor.getDouble(1),cursor
                        .getDouble(2)));
            }
            adapter.addAll(arrayList);
            return adapter;
        }catch (Exception e){
            return new ArrayAdapter<Object>(context,android.R.layout.simple_list_item_1);
        }
    }

    public long ingresar(Context context){
        ContentValues contentValues = new ContentValues();
        contentValues.put("titulo",titulo);
        contentValues.put("latitud",latitud);
        contentValues.put("longitud",longitud);
        return escritura(context).insert("marcador",null,contentValues);
    }
}
