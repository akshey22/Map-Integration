package org.unyde.mapintegrationlib.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Data {

    @SerializedName("id")
    var id: Int = 0


    @SerializedName("display_priority")
    var display_priority: Int = 0

    @SerializedName("icon")
    var icon: String? = ""

    @SerializedName("store_type_id")
    var store_type_id: Int = 0

    @SerializedName("name")
    var name: String = ""

    var add_in_panel: Int =0



    @SerializedName("type")
    var type: String? = ""

    var groupable: Int = 0
    var data_source: Int = 0
    var is_utility: Int = 0
    var count: Int = 0

    var range: Int = 0

    var transaction_time: Int = 0
    var is_store: Int = 0

    @SerializedName("cluster_floor_details")
    @Expose
    var clusterFloorDetailsList: List<GetClusterFloorDetailsList>? = null




}
