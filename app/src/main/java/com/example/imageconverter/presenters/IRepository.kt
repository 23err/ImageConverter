package com.example.imageconverter.presenters

import android.graphics.Bitmap
import io.reactivex.rxjava3.core.Single

interface IRepository {
    fun getFileFromDrawable(): Single<Bitmap>
    fun getFile(fileName: String):Single<Bitmap>
    fun saveFile(byteArray: ByteArray): Single<String>
}
