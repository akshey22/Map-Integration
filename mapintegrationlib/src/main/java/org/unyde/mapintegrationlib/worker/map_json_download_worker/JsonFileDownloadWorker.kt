package org.unyde.mapintegrationlib.worker.map_json_download_worker

import android.annotation.SuppressLint
import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import okhttp3.OkHttpClient
import okhttp3.Request
import org.unyde.mapintegrationlib.worker.helper.LiveDataHelper
import java.io.*
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit


/////https://s3.ap-south-1.amazonaws.com/ally-production-images/cluster/105/map/expo_groundfloor.zip
class JsonFileDownloadWorker(private val mContext: Context, workerParameters: WorkerParameters) :
        Worker(mContext, workerParameters) {
    private val liveDataHelper: LiveDataHelper

    init {
        liveDataHelper = LiveDataHelper.getInstance()
    }

    @SuppressLint("RestrictedApi", "CheckResult")
    override fun doWork(): Result {
        var outputData: Data? = null
        val url = inputData.getString("json")
        val city = inputData.getString("city")
        val mall_id = inputData.getString("Mall_Id")
        var spilted = url!!.split("/".toRegex()).reversed().dropLastWhile({ it.isEmpty() }).toTypedArray()
        try {
            val myDir = applicationContext.getExternalFilesDir(null)
            val path = "$myDir/MAPFILE/" + city + "/" + mall_id

            val buf = ByteArray(2048)
            var byteRead: Int

            val f = File(path + "/", spilted[0])
            if (!f.parentFile.exists()) {
                f.parentFile.mkdirs()
                f.createNewFile()
            } else {
                f.delete()
                f.createNewFile()
            }

            var outStream = BufferedOutputStream(FileOutputStream(File(path + "/" + spilted[0])))


            val builder = OkHttpClient.Builder()
            builder.connectTimeout(5, TimeUnit.MINUTES) // connect timeout
                    .writeTimeout(5, TimeUnit.MINUTES) // write timeout
                    .readTimeout(5, TimeUnit.MINUTES) // read timeout
            val client = builder.build()

            val request = Request.Builder()
                    .url(inputData.getString("json"))
                    .build()
            var total: Long = 0
            val response = client.newCall(request).execute()
            val lengthOfFile = response.body()!!.contentLength()
            var `in` = response.body()?.byteStream()
            byteRead = `in`!!.read(buf)
            while (byteRead != -1) {
                total += byteRead.toLong() //total = total + len1
                val percent = (total * 100 / lengthOfFile).toInt()
                liveDataHelper!!.updatePercentage(percent)
                outStream.write(buf, 0, byteRead)
                byteRead = `in`.read(buf)

            }
            `in`.close()
            outStream.close()

            outputData = Data.Builder().putString("Image_Name", path + "/")
                    .putString("file_name", path + "/" + spilted[0])
                    .putString("city", city)
                    .putString("Mall_Id", mall_id).build()

        } catch (e: Exception) {
            return Result.failure()
            e.printStackTrace()
        } catch (e: SocketTimeoutException) {
            return Result.failure()
            e.printStackTrace()
        }


        return Result.Success(outputData!!)
    }


}