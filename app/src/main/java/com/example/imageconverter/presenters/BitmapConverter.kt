package com.example.imageconverter.presenters

import android.graphics.Bitmap
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Single
import java.io.ByteArrayOutputStream
import java.lang.RuntimeException

class BitmapConverter {
    fun convertToPng(bitmap: Bitmap): @NonNull Single<ByteArray> {
        var byteArrayOutputStream: ByteArrayOutputStream? = null
        var byteArray: ByteArray? = null
        try {
            byteArrayOutputStream = ByteArrayOutputStream().apply {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, this)
                byteArray = this.toByteArray()
                close()
            }
        } catch (e: Throwable) {
            byteArrayOutputStream?.close()
            throw e
        }
        return byteArray?.let { Single.just(it) }
            ?: Single.error(RuntimeException("Произошла ошибка при конвертации файла"))
    }
}