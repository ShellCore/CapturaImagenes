package com.profuturo.android.capturaimagenes.camscanner;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import java.io.File;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
@SuppressLint("DefaultLocale")
@SuppressWarnings("unused")
public class CamScannerActivity extends Activity {

    /* Variables mejora de imagenes */
    private static final int PHOTO_FILE = 0;

    private String output = " ";
    private boolean continuaEjecucion;

    private String urlImagen;
    private String urlEstadoCuenta;
    private Bitmap bitmap;

    public int clic;

    private String mOutputImagePath;
    private String mOutputPdfPath;
    private String mOutputOrgPath;

//	private CSOpenAPI mApi;

    private final int REQ_CODE_CALL_CAMSCANNER = 2;
    private String path = "";

    // VARIABLES PDF
    /**
     * The resulting PDF.
     */
    public static final String RESULT_PDF = Environment.getExternalStorageDirectory().getPath() + "/contrato.pdf";

    private File file;

    private String nombreImagen = "fdeeImages.jpg";

    public static void crearDirectorio(Context context, String ruta) {
        File carpetaImg = new File(ruta);
        boolean carpetaExistente = true;

        if (!carpetaImg.exists()) {
            if (carpetaImg.delete()) {
            }
            carpetaExistente = carpetaImg.mkdir();
        } else {
            carpetaExistente = carpetaImg.mkdir();
        }

        if (!carpetaExistente) {
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // setContentView(R.layout.fotografias);

        crearDirectorio(this, Constantes.dirImage);

        // path = Constantes.dirImage + File.separator + nombreImagen;
        path = Constantes.DIRNAME + File.separator;

		/* APi CamScanner */
//		mApi = CSOpenApiFactory.createCSOpenApi(CamScannerActivity.this, Constantes.APP_KEY, null);

        clic = PHOTO_FILE;

		/* MOGC: Cambio por el motor de imágenes */
        Bundle bundle = new Bundle();
        Intent launchIntent = new Intent();
        launchIntent.setComponent(new ComponentName("mx.com.profuturo.motor", "mx.com.profuturo.motor.CameraUI"));
        bundle.putString("nombreDocumento", nombreImagen);
        bundle.putString("rutaDestino", "");
        bundle.putBoolean("esCamara", true);
        launchIntent.putExtras(bundle);
        startActivityForResult(launchIntent, PHOTO_FILE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        switch (requestCode) {
            case PHOTO_FILE:
                Uri uri;
                File file;

                if (data != null) {
                    try {
                        String ruta = data.getStringExtra("rutaImagen");
                        mOutputImagePath = ruta;
                    } catch (Exception ex) {

                    }
                    mOutputPdfPath = Constantes.dirImage + "/scanned.pdf";
                    mOutputOrgPath = Constantes.dirImage + "/org.jpg";
                    uri = Uri.fromFile(new File(mOutputImagePath));

                    switch (clic) {
                        case PHOTO_FILE:

                            uri = Uri.fromFile(new File(mOutputImagePath));

                            // Elimina el archivo pdf y org imagen original
                            file = new File(mOutputPdfPath);
                            file.delete();

                            file = new File(mOutputOrgPath);
                            file.delete();

                            Intent returnIntent = new Intent();

                            returnIntent.putExtra("img_name", mOutputImagePath);
                            setResult(RESULT_OK, returnIntent);
                            finish();
                    }
                } else {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("img_name", "");
                    setResult(RESULT_OK, returnIntent);
                    finish();
                }
                break;

            default:
                if (resultCode == 0) {
                    finish();
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i("Informacion: ", "Usuario pucho la tecla atras");
        finish();

    }

}