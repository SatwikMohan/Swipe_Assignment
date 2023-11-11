package com.example.swipe_assignment

import android.Manifest
import android.R.attr.duration
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.swipe_assignment.ViewModels.AddActivityViewModel
import com.example.swipe_assignment.databinding.ActivityAddDataBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.DelicateCoroutinesApi
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream


class AddDataActivity : AppCompatActivity() {
    @OptIn(DelicateCoroutinesApi::class, DelicateCoroutinesApi::class)
    var file:File?=null
    lateinit var viewModal:AddActivityViewModel
    lateinit var binding: ActivityAddDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAddDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModal= ViewModelProvider(this).get()

        binding.SubmitButton.setOnClickListener {

            val product_name=binding.ProductNameEditText
            val product_type=binding.ProductTypeEditText
            val price=binding.PriceEditText
            val tax=binding.TaxEditText
            if(product_name.text.isEmpty()){
                product_name.setError("Invalid Emptiness")
                return@setOnClickListener
            }
            if(product_type.text.isEmpty()){
                product_type.setError("Invalid Emptiness")
                return@setOnClickListener
            }
            if(price.text.isEmpty()){
                price.setError("Invalid Emptiness")
                return@setOnClickListener
            }
            if(tax.text.isEmpty()){
                tax.setError("Invalid Emptiness")
                return@setOnClickListener
            }

            viewModal.sendDataToServer(
                product_name.text.toString(),
                product_type.text.toString(),
                price.text.toString(),
                tax.text.toString(),
                file
            )

        }//click

        viewModal.res.observe(this, Observer {
            val response=it
            if(response.isSuccessful){
                Toast.makeText(applicationContext,"Data Added Successfully", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(applicationContext,"Data Addition UnSuccessful", Toast.LENGTH_SHORT).show()
            }
        })

        binding.AddImageButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this, arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE ,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) , 0)
            }
            else {
                imageChooser()
            }

        }//click

    }//on create

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0) {
            if (grantResults.size > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(applicationContext, "Storage Permission Granted", Toast.LENGTH_SHORT)
                    .show()
                imageChooser()
            } else {
                Toast.makeText(applicationContext, "Storage Permission Denied", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    fun imageChooser() {
        val i = Intent()
        i.type = "image/*"
        i.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(i, "Select Image"), 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                val selectedImageUri: Uri? = data?.data
                if (null != selectedImageUri) {
                    file=File(getRealPathFromURI(selectedImageUri,this).toString())
                    val f=file
                    binding.FileFlag.text=f?.name
                }
            }
        }
    }

    fun getRealPathFromURI(uri: Uri, context: Context): String? {
        val returnCursor = context.contentResolver.query(uri, null, null, null, null)
        val nameIndex =  returnCursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        val sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE)
        returnCursor.moveToFirst()
        val name = returnCursor.getString(nameIndex)
        val size = returnCursor.getLong(sizeIndex).toString()
        val f = File(context.filesDir, name)
        try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val outputStream = FileOutputStream(f)
            var read = 0
            val maxBufferSize = 1 * 1024 * 1024
            val bytesAvailable: Int = inputStream?.available() ?: 0
            val bufferSize = Math.min(bytesAvailable, maxBufferSize)
            val buffers = ByteArray(bufferSize)
            while (inputStream?.read(buffers).also {
                    if (it != null) {
                        read = it
                    }
                } != -1) {
                outputStream.write(buffers, 0, read)
            }
            Log.e("File Size", "Size " + f.length())
            inputStream?.close()
            outputStream.close()
            Log.e("File Path", "Path " + f.path)

        } catch (e: java.lang.Exception) {
            Log.e("Exception", e.message!!)
        }
        return f.path
    }

}