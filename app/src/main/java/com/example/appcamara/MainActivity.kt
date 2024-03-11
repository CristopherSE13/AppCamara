package com.example.appcamara

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.view.inputmethod.InputBinding
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.example.appcamara.databinding.ActivityMainBinding
import java.io.File
import java.net.URL

class MainActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var file: File
    private var rutaFotoActual =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btntomarfoto.setOnClickListener(this)
        binding.btncompartir.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btntomarfoto -> tomarfoto()
            R.id.btncompartir -> compartirfoto()
        }
    }

    private fun compartirfoto(){

    }

    private fun tomarfoto(){
        //abrircamara.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
            it.resolveActivity(packageManager).also {
                    componente ->
                crearArchivoFoto()
                val fotoURL: Uri =
                    FileProvider.getUriForFile(applicationContext,"com.example.appcamara.fileprovider",file
                    )
                it.putExtra(MediaStore.EXTRA_OUTPUT,fotoURL)
            }

        }
        abrircamara.launch(intent)
    }
    private val abrircamara = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){
        result ->
        if(result.resultCode == RESULT_OK){
            val data = result.data!!
            val imagenBitMap = data.extras!!.get("data") as Bitmap
            binding.ivimagen.setImageBitmap(imagenBitMap)
        }
    }
    private fun crearArchivoFoto(){
        val directorioImg = getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        file = File.createTempFile("IMG_${System.currentTimeMillis()}",".jpg",directorioImg)
        rutaFotoActual = file.absolutePath
    }

}
