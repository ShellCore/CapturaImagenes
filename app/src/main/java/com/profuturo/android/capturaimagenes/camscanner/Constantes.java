package com.profuturo.android.capturaimagenes.camscanner;

import android.os.Environment;

public class Constantes {
	public static String DIRNAME = "imagenes";
	public static String dirImage = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/" + DIRNAME;
}