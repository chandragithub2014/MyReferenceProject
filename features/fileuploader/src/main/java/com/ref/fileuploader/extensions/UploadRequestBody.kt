package com.ref.fileuploader.extensions

import android.os.Handler
import android.os.Looper
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.File
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.FileInputStream

class UploadRequestBody(private val file: File,
                        private val contentType: String,
                        private val callback: UploadCallback) : RequestBody(){

    override fun contentType() = "$contentType/*".toMediaTypeOrNull()
    override fun writeTo(sink: BufferedSink) {
        val length = file.length()
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        val fileInputStream = FileInputStream(file)
        var uploaded = 0L
        fileInputStream.use { inputStream ->
            var read: Int
            val handler = Handler(Looper.getMainLooper())
            while (inputStream.read(buffer).also { read = it } != -1) {
                handler.post(ProgressUpdater(uploaded, length))
                uploaded += read
                sink.write(buffer, 0, read)
            }
        }
    }

    override fun contentLength() = file.length()

    interface UploadCallback {
        fun onProgressUpdate(percentage: Int)
    }
    inner class ProgressUpdater(
        private val uploaded: Long,
        private val total: Long
    ) : Runnable {
        override fun run() {
            callback.onProgressUpdate((100 * uploaded / total).toInt())
        }
    }
    companion object {
        private const val DEFAULT_BUFFER_SIZE = 2048
    }
}