package com.example.assinatura

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.io.File
import java.io.FileOutputStream
import android.Manifest
import android.graphics.*
import android.util.Log
import androidx.core.content.ContextCompat

import androidx.core.view.drawToBitmap
import java.io.FileNotFoundException


    class MainActivity : AppCompatActivity() {
        private val REQUEST_WRITE_STORAGE = 1

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)
            supportActionBar!!.hide()
            val signatureView = SignatureView(this)
            signatureView.isDrawingCacheEnabled = true

            val linearLayout = findViewById<LinearLayout>(R.id.line1)
            val button = findViewById<Button>(R.id.button)
            val button2 = findViewById<Button>(R.id.button2)
            linearLayout.addView(signatureView)
            button2.setOnClickListener() {
                signatureView.clear()
            }



            button.setOnClickListener() {
                saveSignature()
            }
        }

        private fun saveSignature() {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_WRITE_STORAGE
                )
            } else {
                val signatureView = SignatureView(this)
                signatureView.isDrawingCacheEnabled = true
                signatureView.invalidate()
                val signatureBitmap = signatureView.drawingCache

                if (signatureBitmap != null) {
                    val file =
                        File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "signature.png")
                    try {
                        val stream = FileOutputStream(file)
                        signatureBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                        stream.close()
                        Toast.makeText(
                            this,
                            "Image saved successfully to " + file.absolutePath,
                            Toast.LENGTH_LONG
                        ).show()
                    } catch (e: Exception) {
                        Log.e("FileSaveError", e.message.toString())
                        Toast.makeText(this, "Error saving image", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this, "No signature to save", Toast.LENGTH_LONG).show()
                }

            }
        }
    }




