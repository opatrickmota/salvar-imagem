package com.patrickmota.pocimagem

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.patrickmota.pocimagem.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.Date

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var magazineAdapter: MagazineAdapter
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setupAdapter()
    }

    private fun setupAdapter() {
        val recyclerView = binding.recyclerview
        magazineAdapter = MagazineAdapter(
            onItemClicked = { imageUrl ->
                viewModel.createBitmap(imageUrl)
                viewModel.bitmap.observe(this) {
                    when (it) {
                        null -> {
                            Toast.makeText(this, "Não é possivel baixar a imagem.", Toast.LENGTH_LONG).show()
                        }
                        else -> {
                            savedImagePath(it)
                        }
                    }
                }
            }
        )
        recyclerView.adapter = magazineAdapter

    }

    private fun savedImagePath(bitmap: Bitmap) {
        val directory = File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Revistas")
        if (!directory.exists()) {
            directory.mkdirs()
        }

        val imageFileName = "capa-${Date()}.jpeg"

        val imageFile = File(directory, imageFileName)
        try {
            val fOut: OutputStream = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut)

            fOut.flush()

            fOut.close()
            Toast.makeText(this, "SALVO COM SUCESSO", Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            Log.e("patrick", e.toString())
            Toast.makeText(this, "OCORREU UM ERRO", Toast.LENGTH_LONG).show()
        }
    }
}