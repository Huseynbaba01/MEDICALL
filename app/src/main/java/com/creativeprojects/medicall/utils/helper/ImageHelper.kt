package com.creativeprojects.medicall.utils.helper

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory


object ImageHelper {
    //TODO Can be used to convert any text to the bitmap (maybe for the Google Map)
    /*fun textAsBitmap(text: String, textSize: Float, textColor: Int): Bitmap {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.textSize = textSize
        paint.color = textColor
        paint.textAlign = Paint.Align.LEFT
        var baseline = -paint.ascent() // ascent() is negative
        val width = (paint.measureText(text) + 0.5f).toInt() // round
        val height = (baseline + 4 * (paint.descent() + 0.5f)).toInt()
        val image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(image)
        val lines = text.split("\n").toTypedArray()
        for (line in lines) {
            canvas.drawText(line, 0f, baseline, paint)
            baseline += 30f
        }
        return image
    }
*/
    fun getBitmapDescriptor(
        context: Context?,
        drawableId: Int,
        width: Int,
        height: Int
    ): BitmapDescriptor? {
        if (context == null) {
            return null
        }
        val vectorDrawable = ContextCompat.getDrawable(context, drawableId)
        vectorDrawable!!.setBounds(0, 0, width, height)
        val bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bm)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bm)
    }

    fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

}