package com.example.imageconverter.presenters

import android.graphics.Bitmap
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView : MvpView {
    fun showDialog()
    fun closeDialog()
    fun cleanResult()
    fun showResult(bitmap: Bitmap)
}