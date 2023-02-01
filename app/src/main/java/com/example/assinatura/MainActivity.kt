package com.example.assinatura

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val signatureView = SignatureView(this)
       val linearLayout =findViewById<LinearLayout>(R.id.line1)
        val button=findViewById<Button>(R.id.button)
        val button2=findViewById<Button>(R.id.button2)
       linearLayout.addView(signatureView)
        button2.setOnClickListener(){
            signatureView.clear()
        }
        button.setOnClickListener(){

           
            salvar()
        }
    }
    fun salvar(){
        val bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
        val file = File(Environment.getExternalStorageDirectory(), "image.jpeg")

        val stream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        stream.flush()
        stream.close()

    }
}