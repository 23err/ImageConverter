package com.example.imageconverter.presenters

import android.graphics.Bitmap

interface IRepository {
    fun getFileFromDrawable():Bitmap
    fun getFile(fileName: String):Bitmap
    fun saveFile(byteArray: ByteArray): String
}
