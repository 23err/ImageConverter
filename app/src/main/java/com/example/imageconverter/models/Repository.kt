package com.example.imageconverter.models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.imageconverter.App
import com.example.imageconverter.R
import com.example.imageconverter.presenters.IRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import java.io.File
import java.io.FileOutputStream
import java.util.*

class Repository : IRepository {
    override fun getFileFromDrawable(): Single<Bitmap> {

        return Single.fromCallable {
            BitmapFactory.decodeResource(
                App.instance.resources,
                R.drawable.maxresdefault
            )
        }
    }

    override fun getFile(fileName: String): Single<Bitmap> {
        return Single.fromCallable{BitmapFactory.decodeFile(fileName)}
    }

    override fun saveFile(byteArray: ByteArray): Single<String> {
        return Single.fromCallable {
            val file =
                File(App.instance.applicationContext.getExternalFilesDir(null), CAT_FILE_NAME)
            if (file.exists()) {
                file.delete()
            }
            file.createNewFile()

            var fileOutputStream: FileOutputStream? = null
            try {
                fileOutputStream = FileOutputStream(file).apply {
                    write(byteArray)
                    flush()
                }
            } catch (e: Throwable) {
                fileOutputStream?.close()
                throw e
            }
            file.absolutePath
        }
    }

    companion object {
        private val CAT_FILE_NAME = "cat.png"
    }
}