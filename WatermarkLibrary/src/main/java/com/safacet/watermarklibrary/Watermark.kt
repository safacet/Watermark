package com.safacet.watermarklibrary

import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.*
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import java.io.File
import java.io.FileOutputStream
import kotlin.io.path.fileVisitor

enum class WatermarkPosition {
    TOP_LEFT,
    TOP_RIGHT,
    BOTTOM_LEFT,
    BOTTOM_RIGHT,
    CUSTOM
}

class Watermark {

    constructor(ct: Context, file: File, photoText: String) {
        context = ct
        src = BitmapFactory.decodeFile(file.path)
        text = photoText
        textSize = 24 * context.resources.displayMetrics.scaledDensity
    }

    constructor(ct: Context, bitmap: Bitmap, photoText: String) {
        context = ct
        src = bitmap
        text = photoText
        textSize = 24 * context.resources.displayMetrics.scaledDensity
    }


    private var context: Context
    private var src: Bitmap
    private var text: String

    private var position = WatermarkPosition.BOTTOM_RIGHT
    private var textColor: Int = Color.WHITE
    private var textSize: Float
    private var textAlign = Paint.Align.LEFT
    private var backgroundColor: Int? = null
    private var widthRate: Int = 3
    private var alpha = 90
    private var w = 0F
    private var h = 0F

    fun setPosition(p: WatermarkPosition): Watermark {
        position = p
        return this
    }

    fun setPosition(width: Float, height: Float): Watermark {
        position = WatermarkPosition.CUSTOM
        w = width
        h = height
        return this
    }

    fun setTextColor(c: Int): Watermark {
        textColor = c
        return this
    }

    fun setTextSize(s: Int): Watermark {
        textSize = s * context.resources.displayMetrics.scaledDensity
        return this
    }

    fun setTextAlign(a: Paint.Align): Watermark {
        textAlign = a
        return this
    }

    fun setBackgroundColor(c: Int): Watermark {
        backgroundColor = c
        return this
    }

    fun setWidthRate(r: Int): Watermark {
        widthRate = r
        return this
    }

    fun setAlpha(a: Int): Watermark {
        alpha = a
        return this
    }

    fun getBitmap(): Bitmap {
        val paint = TextPaint().apply {
            color = textColor
            textSize = this@Watermark.textSize
            isAntiAlias = true
            textAlign = this@Watermark.textAlign
        }

        val staticLayout = StaticLayout.Builder.obtain(
                text, 0, text.length,
                paint, src.width/widthRate
            )
                .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                .setLineSpacing(0F, 1F)
                .setIncludePad(false)
                .build()

        val scaledBitmap = Bitmap.createBitmap(staticLayout.width, staticLayout.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(scaledBitmap)
        backgroundColor?.let {
            canvas.drawColor(it)
        }
        canvas.setTranslate()
        staticLayout.draw(canvas)

        val watermarkPaint = Paint().apply {
            alpha = this@Watermark.alpha
        }
        val result = Bitmap.createBitmap(src.width, src.height, src.config)
        Canvas(result).apply {
            drawBitmap(src, 0F, 0F, null)
            drawBitmap(scaledBitmap, getWidth(scaledBitmap), getHeight(scaledBitmap), watermarkPaint)
        }
        return result
    }

    fun saveToFile(file: File) {
        val bitmap = getBitmap()
        val fos = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
    }

    private fun Canvas.setTranslate() {
        val x = when(textAlign) {
            Paint.Align.RIGHT -> (src.width/widthRate).toFloat()
            Paint.Align.CENTER -> (src.width/widthRate/2).toFloat()
            else -> 0F
        }
        this.translate(x, 0F)
    }

    private fun getWidth(scaled: Bitmap): Float {
        return when(position) {
            WatermarkPosition.TOP_LEFT -> 0F
            WatermarkPosition.TOP_RIGHT -> (src.width - scaled.width).toFloat()
            WatermarkPosition.BOTTOM_LEFT -> 0F
            WatermarkPosition.BOTTOM_RIGHT -> (src.width - scaled.width).toFloat()
            WatermarkPosition.CUSTOM -> w
        }
    }

    private fun getHeight(scaled: Bitmap): Float {
        return when(position) {
            WatermarkPosition.TOP_LEFT -> 0F
            WatermarkPosition.TOP_RIGHT -> 0F
            WatermarkPosition.BOTTOM_LEFT -> (src.height - scaled.height).toFloat()
            WatermarkPosition.BOTTOM_RIGHT -> (src.height - scaled.height).toFloat()
            WatermarkPosition.CUSTOM -> h
        }
    }
}