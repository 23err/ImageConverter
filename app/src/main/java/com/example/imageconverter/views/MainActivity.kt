package com.example.imageconverter.views

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.imageconverter.R
import com.example.imageconverter.databinding.ActivityMainBinding
import com.example.imageconverter.presenters.MainPresenter
import com.example.imageconverter.presenters.MainView
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import java.io.*

class MainActivity : MvpAppCompatActivity(), MainView {
    private lateinit var binding: ActivityMainBinding
    private val presenter by moxyPresenter { MainPresenter() }
    private val alertDialog: AlertDialog by lazy {
        AlertDialog.Builder(MainActivity@this)
            .setMessage(R.string.do_converting)
            .setCancelable(false)
            .setNegativeButton(R.string.cancel) { dialog, number ->
                presenter.cancelPressed()
            }
            .create()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            btnConvert.setOnClickListener {
                showDialog()
                presenter.convertPressed()
            }
            ivJpeg.setImageResource(R.drawable.maxresdefault)
        }
    }

    override fun showDialog() {
        alertDialog.show()
    }

    override fun closeDialog() {
        alertDialog.cancel()
    }

    override fun cleanResult() {
        binding.ivPng.setImageDrawable(null)
    }

    override fun showResult(bitmap: Bitmap) {
        binding.ivPng.setImageBitmap(bitmap)
    }
}