package org.unyde.mapintegrationlib.worker.map_file_download_worker

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

class FileUnzipWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    var _zipFile: File? = null
    val _zipFileStream: InputStream? = null
    override fun doWork(): Result {
        val image_name = inputData.getString("Image_Name")
        val file_name = inputData.getString("file_name")
        val city = inputData.getString("city")
        val mall_id = inputData.getString("Mall_Id")
        _zipFile= File(file_name)

        try {
            var fin: InputStream? = _zipFileStream
            if (fin == null) {
                fin = FileInputStream(_zipFile)
            }
            val zin = ZipInputStream(fin)
            var ze: ZipEntry? = null
            ze=zin.nextEntry
            while (ze != null) {

                if (ze!!.isDirectory) {
                    _dirChecker(image_name!!+ze.name)
                } else {
                    val fout = FileOutputStream(File(image_name,ze.name))
                    val baos = BufferedOutputStream(fout)
                    val buffer = ByteArray(1024)
                    var count: Int
                    count = zin.read(buffer)
                    // reading and writing
                    while (count != -1) {
                        baos.write(buffer, 0, count)
                        count = zin.read(buffer)
                    }

                    baos.close()
                    zin.closeEntry()
                    ze=zin.nextEntry
                }

            }
            zin.close()
        } catch (e: Exception) {
            return Result.failure()
        }




        return Result.success()
    }

    private fun _dirChecker(dir: String) {
        val f = File(dir)
        Log.i("FileUnzipWorker", "creating dir $dir")

        if (dir.length >= 0 && !f.isDirectory) {
            f.mkdirs()
        }
    }
}