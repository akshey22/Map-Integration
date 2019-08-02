package org.unyde.mapintegrationlib.repository;

import android.content.Context;
import androidx.lifecycle.MutableLiveData;
import org.unyde.mapintegrationlib.model.GetClusterDataList;


import org.unyde.mapintegrationlib.network.APIInterface;
import org.unyde.mapintegrationlib.network.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClusterDetailRepository {

    private APIInterface apiInterface;


    public ClusterDetailRepository() {

    }

    public MutableLiveData<GetClusterDataList> getClusterDetail(Context context, String token, String cluster_id) {
        final MutableLiveData<GetClusterDataList> getClusterDetailMutableLiveData = new MutableLiveData<>();
        apiInterface = ApiClient.getClientToken1().create(APIInterface.class);
        Call<GetClusterDataList> call = apiInterface.getClusterDataList("c23fe6f2986f46a67b7917795ea4aa64","9208","0",cluster_id);

        call.enqueue(new Callback<GetClusterDataList>() {
            @Override
            public void onResponse(Call<GetClusterDataList> call, Response<GetClusterDataList> response) {
                if(response.body()!=null)
                {
                    getClusterDetailMutableLiveData.setValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<GetClusterDataList> call, Throwable t) {

            }
        });

        return getClusterDetailMutableLiveData;
    }







}
