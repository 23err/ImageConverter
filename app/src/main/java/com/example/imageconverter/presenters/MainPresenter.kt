package com.example.imageconverter.presenters

import android.graphics.Bitmap
import com.example.imageconverter.models.Repository
import moxy.MvpPresenter
import java.io.ByteArrayOutputStream

class MainPresenter(
    private val repository: IRepository = Repository()
) : MvpPresenter<MainView>() {

    fun convertPressed() {
        viewState.cleanResult()
        viewState.showDialog()
        val bitmap = repository.getFileFromDrawable()
        var byteArrayOutputStream: ByteArrayOutputStream? = null
        var path: String? = null
        try {
            byteArrayOutputStream = ByteArrayOutputStream().apply {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, this)
                path = repository.saveFile(this.toByteArray())
                close()
            }
        } catch (e: Throwable) {
            byteArrayOutputStream?.close()
            throw e
        }
        path?.let {
            val convertedBitmapPng = repository.getFile(it)
            viewState.showResult(convertedBitmapPng)
        }

    }

    fun cancelPressed() {
        viewState.closeDialog()
    }

}