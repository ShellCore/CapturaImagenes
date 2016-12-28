package com.profuturo.android.capturaimagenes.camscanner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;

public class Util {

    public static final String FASE_2 = "FASE 2";

    public static void crearDirectorio(Context context, String ruta) {
        File carpetaImg = new File(ruta);
        if (!carpetaImg.exists()) {
            carpetaImg.mkdir();
        }
    }

    public static String getDirectorioFotos(Context context, String nombreCarpeta) {
        String ruta = null;
        ruta = getPathImages(nombreCarpeta);
        crearDirectorio(context, ruta);
        return ruta;
    }

    public static String getPathImages(String nombreCarpeta) {
        String ruta = null;
        ruta = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .getPath() + "/" + nombreCarpeta + "/";
        return ruta;
    }

    public static void mostrarImagen(ImageView imageView, String url) {
        int tamanioFoto = 170;
        imageView.setImageBitmap(decodificarBitmap(url, tamanioFoto,
                tamanioFoto));
    }

    private static Bitmap decodificarBitmap(String rutaImagen, int ancho,
                                            int alto) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(rutaImagen, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, ancho, alto);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(rutaImagen, options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
