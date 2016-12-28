package com.profuturo.android.capturaimagenes;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    // Variables
    private String rutaImagen;

    // Components
    @Bind(R.id.coordinator)
    CoordinatorLayout coordinator;
    @Bind(R.id.img_preview)
    ImageView imgPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        limpiarImagen();
    }

    @OnClick(R.id.btnCapturePhoto)
    public void capturePhoto() {
        Snackbar.make(coordinator, "Capturar foto", Snackbar.LENGTH_SHORT)
                .show();
    }

    @OnClick(R.id.btnAddImage)
    public void addImage() {
        Snackbar.make(coordinator, "Agregar imagen", Snackbar.LENGTH_SHORT)
                .show();
    }

    @OnClick(R.id.btnDeleteImage)
    public void deleteImage() {
        limpiarImagen();
    }

    private void limpiarImagen() {
        rutaImagen = null;
        imgPreview.setImageResource(R.drawable.not_found);
        Snackbar.make(coordinator, "La imagen a sido borrada", Snackbar.LENGTH_SHORT)
                .show();
    }
}
