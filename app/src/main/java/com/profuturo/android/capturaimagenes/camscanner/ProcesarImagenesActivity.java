package com.profuturo.android.capturaimagenes.camscanner;

import java.io.File;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
@SuppressLint("DefaultLocale")
@SuppressWarnings("unused")
public class ProcesarImagenesActivity extends Activity {

	/* Variables mejora de imagenes */
	private static final int PHOTO_FILE = 0;

	private String output = " ";
	private boolean continuaEjecucion;

	public int clic;

	private String mOutputImagePath;
	private String mOutputPdfPath;
	private String mOutputOrgPath;

	private final int REQ_CODE_CALL_CAMSCANNER = 2;

	private File file;

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

		crearDirectorio(this, Constantes.dirImage);

		clic = PHOTO_FILE;

		Bundle bundle = getIntent().getExtras();
		String urlImagen = bundle.getString("pathGalleryImage");

		Bundle bundleProceso = new Bundle();
		Intent launchIntent =new Intent();
		launchIntent.setComponent(new ComponentName("mx.com.profuturo.motor","mx.com.profuturo.motor.CameraUI"));
		bundleProceso.putString("nombreDocumento", "Obtenido de folder");
		bundleProceso.putString("rutaDestino", "TMP_FOLDER/");
		bundleProceso.putString("origenImagen", urlImagen);
		bundleProceso.putBoolean("esCamara", false);
		launchIntent.putExtras( bundleProceso);
		startActivityForResult(launchIntent,PHOTO_FILE);

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, final Intent data) {
		switch (requestCode) {
			case PHOTO_FILE:
				//Util.logMessage(" Entro caso PHOTO_FILE 1");
				Uri uri;
				String ruta = "";
				if (data != null) {
					try {
						ruta = data.getStringExtra("rutaImagen");
						mOutputImagePath = ruta;
					} catch (Exception ex) {

					}

					mOutputPdfPath = Constantes.dirImage + "/scanned.pdf";
					mOutputOrgPath = Constantes.dirImage + "/org.jpg";
					uri = Uri.fromFile(new File(mOutputImagePath));

					switch (clic) {
						case PHOTO_FILE:
							//Util.logMessage(" Entro caso PHOTO_FILE 2");

							uri = Uri.fromFile(new File(mOutputImagePath));

					/*
					 * Elimina el archivo pdf y org imagen original
					 */
							file = new File(mOutputPdfPath);
							file.delete();

							file = new File(mOutputOrgPath);
							file.delete();

							Intent returnIntent = new Intent();

							/**
							 * Metodo para comprimir las imagenes para que pesen
							 * menos de un mega.
							 */
						{

						}

						returnIntent.putExtra("img_name", mOutputImagePath);
						setResult(RESULT_OK, returnIntent);

						finish();
					}
				}else{
					Intent returnIntent = new Intent();
					returnIntent.putExtra("img_name", "");
					setResult(RESULT_OK, returnIntent);

					finish();
				}
				break;
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();

	}
}