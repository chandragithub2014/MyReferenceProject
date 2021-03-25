package com.ref.fileuploader.views

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.ref.baselibrary.navigator.launchActivity
import com.ref.fileuploader.R
import com.ref.fileuploader.extensions.UploadRequestBody
import com.ref.fileuploader.extensions.getFileName
import kotlinx.android.synthetic.main.activity_file_uploader.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class FileUploaderActivity : AppCompatActivity() , UploadRequestBody.UploadCallback{
    private var postPath: String? = null
    private var mediaPath: String? = null
    private var selectedImageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_uploader)

        browse_file_btn.setOnClickListener {
          /*  val galleryIntent = Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO)*/
     //       openImageChooser()
            radioButtonTestActivity()
        }

        upload_file_btn.setOnClickListener {
            uploadImage()
        }

    }

    private fun openImageChooser() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, REQUEST_PICK_PHOTO)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_PICK_PHOTO -> {
                    selectedImageUri = data?.data
                    imageView2.setImageURI(selectedImageUri)
                }
            }
        }
    }


    private fun uploadImage() {
       /* if (selectedImageUri == null) {
            layout_root.snackbar("Select an Image First")
            return
        }*/

        val parcelFileDescriptor =
            contentResolver.openFileDescriptor(selectedImageUri!!, "r", null) ?: return

        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(cacheDir, contentResolver.getFileName(selectedImageUri!!))
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)

        progressBar_hori.progress = 0
        val body = UploadRequestBody(file, "image", this)


        MultipartBody.Part.createFormData(
            "image",
            file.name,
            body
        )
        "json".toRequestBody("multipart/form-data".toMediaTypeOrNull())

        println("Body is $body")
        println("File Name is ${file.name}")
    /*    MyAPI().uploadImage(
            MultipartBody.Part.createFormData(
                "image",
                file.name,
                body
            ),
            "json".toRequestBody("multipart/form-data".toMediaTypeOrNull())
        ).enqueue(object : Callback<UploadResponse> {
            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                layout_root.snackbar(t.message!!)
                progress_bar.progress = 0
            }

            override fun onResponse(
                call: Call<UploadResponse>,
                response: Response<UploadResponse>
            ) {
                response.body()?.let {
                    layout_root.snackbar(it.message)
                    progress_bar.progress = 100
                }
            }
        })*/

    }
    companion object {

        private val REQUEST_PICK_PHOTO = 2



        /**
         * returning image / video
         */

    }

    override fun onProgressUpdate(percentage: Int) {
        progressBar_hori.progress = percentage
    }

    private fun radioButtonTestActivity(){
        launchActivity<RadioButtonTestActivity> {  }
    }
//https://www.simplifiedcoding.net/android-upload-file-to-server/
    //https://github.com/probelalkhan/android-upload-file-to-server/blob/master/app/build.gradle
}