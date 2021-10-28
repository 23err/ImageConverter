package com.example.imageconverter.presenters

import android.graphics.Bitmap
import android.util.Log
import com.example.imageconverter.models.Repository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.disposables.DisposableContainer
import moxy.MvpPresenter
import java.io.ByteArrayOutputStream
import java.lang.RuntimeException
import java.util.concurrent.TimeUnit

class MainPresenter(
    private val repository: IRepository = Repository()
) : MvpPresenter<MainView>() {
    var disposable: Disposable? = null

    fun convertPressed() {
        disposable?.dispose()
        viewState.cleanResult()
        viewState.showDialog()
        disposable = repository
            .getFileFromDrawable()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .flatMap { bitmap ->
                BitmapConverter().convertToPng(bitmap)
            }
            .observeOn(Schedulers.io())
            .flatMap {
                repository.saveFile(it)
            }
            .flatMap {
                repository.getFile(it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ resultBitmap ->
                viewState.showResult(resultBitmap)
                viewState.closeDialog()
            },{
                viewState.closeDialog()
                Log.e(TAG, "convertPressed: ${it.message}", )
            })
    }

    fun cancelPressed() {
        disposable?.dispose()
        viewState.closeDialog()
    }

    companion object{
        private val TAG = "MainPresenter"
    }
}