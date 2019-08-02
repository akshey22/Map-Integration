package org.unyde.mapintegrationlib

import android.app.Activity
import android.content.Context
import org.unyde.mapintegrationlib.viewmodel.ClusterDetailViewModel
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.work.*
import org.unyde.mapintegrationlib.worker.helper.LiveDataHelper
import org.unyde.mapintegrationlib.worker.map_file_download_worker.FileUnzipWorker
import org.unyde.mapintegrationlib.worker.map_file_download_worker.MapFileDownloadWorker
import org.unyde.mapintegrationlib.worker.map_json_download_worker.JsonFileDownloadWorker
import org.unyde.mapintegrationlib.worker.map_json_download_worker.MapJsonParseWorker


private var mViewModel_cluster: ClusterDetailViewModel? = null
private var mWorkManager: WorkManager? = null

class MapIntgrationMain
{

   companion object
   {
       fun s(c: FragmentActivity, cluster_id: String) {
           mViewModel_cluster = ViewModelProviders.of(c).get(ClusterDetailViewModel::class.java!!)
           mViewModel_cluster!!.init(c, "28.554810", cluster_id)
           mViewModel_cluster!!.clusterDetails.observeForever { clusterDetail ->

               if (clusterDetail.data.size > 0) {
                   for (i in 0 until clusterDetail.data.size) {

                       for (j in 0 until clusterDetail.data.get(i).clusterFloorDetailsList!!.size) {
                           var cluster_id = clusterDetail.data.get(i).clusterFloorDetailsList!!.get(j).cluster_id.toString()
                           var url_map = clusterDetail.data.get(i).clusterFloorDetailsList!!.get(j).floor_map.toString()
                           var url_json = clusterDetail.data.get(i).clusterFloorDetailsList!!.get(j).floor_json_file.toString()
                           startMapDownloadWorker(c,"Noida", cluster_id, url_map, url_json,"MAP"+cluster_id + "Download" + j)
                           //  startMapJsonDownloadWorker("Noida", cluster_id, url_json, "JSON"+cluster_id + "Download" + j)
                       }

                   }

               } else {


               }

           }

       }

       fun startMapDownloadWorker(c: Activity,city: String, mall_id: String, url: String, url_json: String, uniqueWork: String) {

           mWorkManager = WorkManager.getInstance()
           val data = Data.Builder()
               .putString("images", url)
               .putString("city", city)
               .putString("Mall_Id", mall_id)
               .build()

           val constraints = Constraints.Builder()
               .setRequiredNetworkType(NetworkType.CONNECTED)

           val oneTimeRequest = OneTimeWorkRequest.Builder(MapFileDownloadWorker::class.java)
               .setInputData(data)
               .setConstraints(constraints.build())
               .build()


           val data1 = Data.Builder()
               .putString("json", url_json)
               .putString("city", city)
               .putString("Mall_Id", mall_id)
               .build()


           val oneTimeRequest1 = OneTimeWorkRequest.Builder(JsonFileDownloadWorker::class.java)
               .setInputData(data1)
               .setConstraints(constraints.build())
               .build()


           var continuation = mWorkManager!!
               .beginUniqueWork(
                   uniqueWork,
                   ExistingWorkPolicy.KEEP,
                   oneTimeRequest
               )

           //Toast.makeText(c, "Starting worker", Toast.LENGTH_SHORT).show()


           val save = OneTimeWorkRequest.Builder(FileUnzipWorker::class.java!!)
               .addTag(uniqueWork)
               .build()

           val saveDB = OneTimeWorkRequest.Builder(MapJsonParseWorker::class.java!!)
               .addTag(uniqueWork)
               .build()

           continuation = continuation.then(save)
           continuation = continuation.then(oneTimeRequest1)
           continuation = continuation.then(saveDB)

           // Actually start the work
           continuation.enqueue()

           LiveDataHelper.getInstance().observePercentage()
               .observeForever {
                   Toast.makeText(c,it.toString(),Toast.LENGTH_LONG).show()
               }

       }
   }


   /* fun s(c: Activity, cluster_id: String) {

        mViewModel_cluster!!.init(c, "28.554810", cluster_id)
        mViewModel_cluster!!.clusterDetails.observeForever { clusterDetail ->

            if (clusterDetail.data.size > 0) {
                for (i in 0 until clusterDetail.data.size) {

                    for (j in 0 until clusterDetail.data.get(i).clusterFloorDetailsList!!.size) {
                        var cluster_id = clusterDetail.data.get(i).clusterFloorDetailsList!!.get(j).cluster_id.toString()
                        var url_map = clusterDetail.data.get(i).clusterFloorDetailsList!!.get(j).floor_map.toString()
                        var url_json = clusterDetail.data.get(i).clusterFloorDetailsList!!.get(j).floor_json_file.toString()
                        startMapDownloadWorker(c,"Noida", cluster_id, url_map, url_json,"MAP"+cluster_id + "Download" + j)
                        //  startMapJsonDownloadWorker("Noida", cluster_id, url_json, "JSON"+cluster_id + "Download" + j)
                    }

                }

            } else {


            }

        }

    }*/



}
