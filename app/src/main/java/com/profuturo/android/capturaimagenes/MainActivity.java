package com.profuturo.android.capturaimagenes;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.profuturo.android.capturaimagenes.camscanner.CamScannerActivity;
import com.profuturo.android.capturaimagenes.camscanner.ProcesarImagenesActivity;
import com.profuturo.android.capturaimagenes.camscanner.Util;
import com.profuturo.android.capturaimagenes.camscanner.Validacion;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    // Constantes
    private static final int SELECT_PICTURE = 200;

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
        if (Validacion.motorImagesIsInstalled(this)) {
            Intent i = new Intent(this, CamScannerActivity.class);
            startActivityForResult(i, 0);
        } else {
            mostrarMensaje("No se encuentra instalado el motor de imágenes");
        }
    }

    @OnClick(R.id.btnAddImage)
    public void addImage() {
        if (Validacion.motorImagesIsInstalled(this)) {
            Intent iGallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            iGallery.setType("image/*");
            startActivityForResult(iGallery, SELECT_PICTURE);
        } else {
            mostrarMensaje("No se encuentra instalado el motor de imágenes");
        }
    }

    @OnClick(R.id.btnDeleteImage)
    public void deleteImage() {
        limpiarImagen();
    }

    @SuppressWarnings("static-access")
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PICTURE && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = this.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String ruta = cursor.getString(columnIndex);
            cursor.close();

            Intent intent = new Intent(this, ProcesarImagenesActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("pathGalleryImage", ruta);
            intent.putExtras(bundle);
            startActivityForResult(intent, 80);

        }

        if (requestCode != SELECT_PICTURE && resultCode == Activity.RESULT_OK) {

			/* Obtiene la ruta de donde se obtiene la imagen */
            String ruta = data.getStringExtra("img_name");

            if (ruta != null) {
                Util.mostrarImagen(imgPreview, ruta);

            }
        }
    }

    private void limpiarImagen() {
        rutaImagen = null;
        imgPreview.setImageResource(R.drawable.not_found);
        mostrarMensaje("La imagen a sido borrada");
    }

    private void mostrarMensaje(String mensaje) {
        Snackbar.make(coordinator, mensaje, Snackbar.LENGTH_SHORT)
                .show();
    }
}
