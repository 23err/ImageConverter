package com.example.imageconverter.views

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
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
        AlertDialog.Builder(this)
            .setMessage(R.string.do_converting)
            .setCancelable(false)
            .setNegativeButton(R.string.cancel) { p0, p1 ->
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
                presenter.convertPressed()

                val file = File(applicationContext.getExternalFilesDir(null), "cat.png")
                if (file.exists()) {
                    file.delete()
                }
                file.createNewFile()
                var fileOutputStream: FileOutputStream? = null
                try {
                    fileOutputStream = FileOutputStream(file)
                    fileOutputStream?.apply {
                        val bt = BitmapFactory.decodeResource(resources, R.drawable.maxresdefault)

                        bt.compress(Bitmap.CompressFormat.PNG, 100, this)
                        flush()
                        close()
                    }
                } catch (e: Throwable) {
                    fileOutputStream?.close()
                    throw e
                }
                println("file : ${file.absolutePath}")
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
        binding.ivPng.setImageResource(0)
    }

    override fun showResult(bitmap: Bitmap) {
        binding.ivPng.setImageBitmap(bitmap)
    }
}