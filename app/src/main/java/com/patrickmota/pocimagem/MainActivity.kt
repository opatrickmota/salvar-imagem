package com.patrickmota.pocimagem

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.patrickmota.pocimagem.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.ByteArrayOutputStream
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
                viewModel.createBitmapToSave(imageUrl)
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
            },
            onSharedImage = { imageUrl ->
                viewModel.createBitmapToShare(imageUrl)
                viewModel.bitmapToShare.observe(this) {
                    when (it) {
                        null -> {
                            Toast.makeText(this, "Não é possivel compartilhar a imagem.", Toast.LENGTH_LONG).show()
                        }
                        else -> {
                            val imageUri = getUriFromBitmap(it) ?: return@observe
                            val shareIntent: Intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_STREAM, imageUri)
                                type = "image/jpeg"
                            }
                            startActivity(Intent.createChooser(shareIntent, null))
                        }
                    }
                }
            }
        )
        recyclerView.adapter = magazineAdapter

    }

    private fun getUriFromBitmap(bitmap: Bitmap): Uri? {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(this.contentResolver, bitmap, "Revistas", null)
        val uri = Uri.parse(path)
        return uri
    }

    private fun savedImagePath(bitmap: Bitmap) {
        val imageFile = createFile()

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

    private fun createFile(): File {
        val directory = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            "Revistas"
        )
        if (!directory.exists()) {
            directory.mkdirs()
        }

        val imageFileName = "capa-${Date()}.jpeg".replace(":", "-")

        return File(directory, imageFileName)
    }
}