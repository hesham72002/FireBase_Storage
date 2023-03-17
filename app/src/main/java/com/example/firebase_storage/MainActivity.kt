package com.example.firebase_storage

import android.app.ProgressDialog
import android.content.Intent
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.firebase_storage.databinding.ActivityMainBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.UUID

class MainActivity : AppCompatActivity() {
    val reqCode = 100
    private lateinit var pdf: Uri
     private lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        textView =findViewById(R.id.txt)
        var storageRef = Firebase.storage.reference
        binding.btnChoose.setOnClickListener {
            val intent =Intent().setType("application/pdf").setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(Intent.createChooser(intent,"Select PDF Files..."),reqCode)


        }
        binding.btnUpload.setOnClickListener {
            binding.btnUpload.isVisible =false
            binding.btnChoose.isVisible =false
            binding.progressBar.isVisible =true
            storageRef.child("File/"+UUID.randomUUID().toString())
                .putFile(pdf)
                .addOnSuccessListener {
                    binding.progressBar.isVisible =false
                    binding.btnUpload.isVisible =true
                    binding.btnChoose.isVisible =true
                    textView.text = "Success"
                }
                .addOnFailureListener {
                    textView.text = "Failure"
                    binding.progressBar.isVisible =false
                    binding.btnUpload.isVisible =true
                    binding.btnChoose.isVisible =true
                }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("hshm","start choose")
            pdf = data!!.data!!
        textView.text =pdf.toString()
    }
}