package com.camilo.proyectoandroid;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import modell.Marcador;

public class MapsActivity1 extends FragmentActivity implements OnMapReadyCallback {

    EditText titulo;
    Spinner marcadores;
    Button guardar;
    LatLng punto;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps1);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        titulo = (EditText)findViewById(R.id.titulo);
        marcadores = (Spinner)findViewById(R.id.marcadores);
        guardar = (Button)findViewById(R.id.guardar);



    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng Peru = new LatLng(-6.4877198, -76.3598708);
        mMap.addMarker(new MarkerOptions().position(Peru).title("Tarapoto, Perú"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Peru));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Peru,16));
        // Add a marker in Sydney and move the camera

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mMap.clear();
                punto = latLng;
                mMap.addMarker(new MarkerOptions().position(punto).title("Nuevo Marcador"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(punto,16));
                titulo.setEnabled(true);
                guardar.setEnabled(true);
            }
        });
        mostrar();
    }

    public void guardar(View view){
        Marcador marcador = new Marcador(titulo.getText().toString().trim(),punto.latitude,punto
                .longitude);
        marcador.ingresar(this);
        titulo.setText("");
        titulo.setEnabled(false);
        guardar.setEnabled(false);
        mostrar();
    }

    public void mostrar(){
        marcadores.setAdapter(new Marcador().obtenerMarcadores(this));

    }

    public void mostrarMarcador(View view){
        mMap.clear();
        Marcador m = (Marcador)marcadores.getSelectedItem();
        punto = new LatLng(m.getLatitud(),m.getLongitud());
        mMap.addMarker(new MarkerOptions().position(punto).title(m.getTitulo()));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(punto,17));
    }
}
