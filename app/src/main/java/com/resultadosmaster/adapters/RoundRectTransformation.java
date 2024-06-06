package com.resultadosmaster.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.squareup.picasso.Transformation;

/**
 * Clase para aplicar una transformación de bordes redondos a una imagen.
 */
public class RoundRectTransformation implements Transformation {

    private float radius; // Radio de los bordes redondos

    /**
     * Constructor que toma el radio de los bordes redondos.
     *
     * @param radius El radio de los bordes redondos.
     */
    public RoundRectTransformation(float radius) {
        this.radius = radius;
    }


    /**
     * Método para aplicar la transformación a la imagen.
     *
     * @param source La imagen original.
     * @return La imagen con bordes redondos.
     */
    @Override
    public Bitmap transform(Bitmap source) {
        int width = source.getWidth();
        int height = source.getHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height, source.getConfig());

        // Crear un lienzo para dibujar el bitmap
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();

        // Configurar un shader con la imagen original para aplicarla al lienzo
        BitmapShader shader = new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        // Rectángulo con bordes redondos
        canvas.drawRoundRect(0, 0, width, height, radius, radius, paint);

        // Liberar la imagen original
        source.recycle();

        return bitmap;
    }

    /**
     * Método para obtener una clave única para esta transformación.
     *
     * @return La clave de la transformación.
     */
    @Override
    public String key() {
        return "roundedRect";
    }
}

