package org.unyde.mapintegrationlib.worker.map_json_download_worker

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

class MapJsonParseWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {


    override fun doWork(): Result {
        val image_name = inputData.getString("Image_Name")
        val file_name = inputData.getString("file_name")
        val city = inputData.getString("city")
        val mall_id = inputData.getString("Mall_Id")


        return Result.success()
    }

}